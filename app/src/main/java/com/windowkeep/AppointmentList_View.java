package com.windowkeep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class AppointmentList_View extends AppCompatActivity {

    /* Private Data */
    private CalendarView calendarView;
    private int dateMonth;
    private int dateDay;
    private int dateYear;
    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private static final String DIGIT_AND_DECIMAL_REGEX = "[^\\d.]";
    private ID selectedItemID;

    public static final class AppointmentListComparator {
        public static Comparator<String> createAppointmentOrderComparator() {
            return Comparator.comparingInt(AppointmentList_View::parseStringToNumber);
        }
    }

    private static int parseStringToNumber(String aptTimes) {

        final String digitsOnly = aptTimes.replaceAll(DIGIT_AND_DECIMAL_REGEX, "");

        if ("".equals(digitsOnly)) return 0;

        try {
            return Integer.parseInt(digitsOnly);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list__view);
        calendarView = findViewById(R.id.calendarView);
        listView = findViewById(R.id.lv_aptDates);
        list = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        selectedItemID = new ID();

        /* Used to populate the list view with data based on dates selected on the calender */
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                dateMonth = month + 1;
                dateDay = day;
                dateYear = year;
                list.clear();
                arrayAdapter.notifyDataSetChanged();

                FirebaseDatabase.getInstance().getReference().child("Quote Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Quote quote = snapshot.getValue(Quote.class);
                            if (quote.getAptDate().contentEquals(dateMonth + "/" + dateDay + "/" + dateYear)) {

                                List<String> aptTimeList =
                                        Arrays.asList("\nName: " + quote.getCustomer().getName() + "\n" +
                                                "Address: " + quote.getCustomer().getAddress() + "\n" +
                                                "Appointment time: " + quote.getAptTime() + "\n");


                                aptTimeList.sort(AppointmentListComparator.createAppointmentOrderComparator());
                                list.addAll(aptTimeList);
                                //list.add(String.valueOf(aptTimeList));
                                listView.setAdapter(arrayAdapter);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        /* When an item is clicked we will get the options to A) see it on the Map B) delete it or C) export it for the client */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemString = (listView.getItemAtPosition(position).toString());
                arrayAdapter.notifyDataSetChanged();

                /* Passing the lat and long to the map activity so it can zoom in on the clicked appointment's pin */

                FirebaseDatabase.getInstance().getReference().child("Quote Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Quote quote = snapshot.getValue(Quote.class);
                            if (("\nName: " + quote.getCustomer().getName() + "\n" +
                                    "Address: " + quote.getCustomer().getAddress() + "\n" +
                                    "Appointment time: " + quote.getAptTime() + "\n").equalsIgnoreCase(selectedItemString)) {
                                selectedItemID = new ID(quote.getCustomer().getID().getLatitude(), quote.getCustomer().getID().getLongitude());

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Bundle extras = new Bundle();
                                extras.putDouble("lat", selectedItemID.getLatitude());
                                extras.putDouble("long", selectedItemID.getLongitude());
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

/*
                // Daniel export to Calender
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_CONTACTS)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Permission is not granted
                            // Should we show an explanation?
                            if (ActivityCompat.shouldShowRequestPermissionRationale(new Activity(),
                                    Manifest.permission.READ_CONTACTS)) {
                                // Show an explanation to the user *asynchronously* -- don't block
                                // this thread waiting for the user's response! After the user
                                // sees the explanation, try again to request the permission.
                            } else {
                                // No explanation needed; request the permission
                                ActivityCompat.requestPermissions(getApplicationContext(),
                                        new String[]{Manifest.permission.READ_CONTACTS},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            }
                        } else {
                            // Permission has already been granted
                        }

                        File path = getApplicationContext().getFilesDir();
                        File file = new File(path, "Calender_Data.txt");

                        try {
                            FileOutputStream stream = new FileOutputStream(file);
                            try {
                                stream.write("text-to-write".getBytes());
                                Toast.makeText(getApplicationContext(), "Successfully exported to Calender_Data.txt", Toast.LENGTH_LONG).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                stream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }printStackTrace
                });
*/
            }
        });
    }
}