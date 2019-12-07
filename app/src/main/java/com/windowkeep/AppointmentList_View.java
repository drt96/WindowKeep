package com.windowkeep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppointmentList_View extends AppCompatActivity {

    private CalendarView calendarView;
    private int dateMonth;
    private int dateDay;
    private int dateYear;
    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list__view);
        calendarView = findViewById(R.id.calendarView);
        listView = findViewById(R.id.lv_aptDates);
        list = new ArrayList<String>();

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);

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
                            if (quote.getAptDate().equalsIgnoreCase(dateMonth + "/" + dateDay + "/" + dateYear)) {
                                String aptInfo = "" + quote.getAptDate() + " - " + quote.getAptTime();
                                list.add(aptInfo);
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
    }
}
