package com.windowkeep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class SelectDate_View extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CalendarView calendarView;
    private TextView t_selectedDate;
    private int dateMonth;
    private int dateDay;
    private int dateYear;
    private String dateTime;
    private String hours;
    private String minutes;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date__view);
        calendarView = findViewById(R.id.calendarView);
        t_selectedDate = findViewById(R.id.t_selectedDate);
        confirmButton = findViewById(R.id.b_confirmDate);

        confirmButton.setEnabled(false);
        confirmButton.setVisibility(View.INVISIBLE);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                java.util.Date date = Calendar.getInstance().getTime();
                String monthString = (String) DateFormat.format("MM", date);
                String dayString = (String) DateFormat.format("dd", date);
                String yearString = (String) DateFormat.format("yyyy", date);
                int dayNum = 0;
                int monthNum = 0;
                int yearNum = 0;

                /* Convert all the string values to integers with these try statements */
                try {
                    dayNum = Integer.parseInt(dayString);
                } catch (NumberFormatException nfe) {
                }

                try {
                    yearNum = Integer.parseInt(yearString);
                } catch (NumberFormatException nfe) {
                }

                try {
                    monthNum = Integer.parseInt(monthString);
                } catch (NumberFormatException nfe) {
                }

                /*
                 THIS LOGIC IS FOR WHEN IT DOES NOT WORK
                 current date = 12/9/2019      selected date = 11/14/2019
                */
                if (yearNum > year || (monthNum > (month + 1) && yearNum == year) ||
                        (monthNum == month + 1 && yearNum == year && dayNum > day)) {
                    t_selectedDate.setText("Invalid Date");
                    confirmButton.setEnabled(false);
                    confirmButton.setVisibility(View.INVISIBLE);
                } else {
                    dateMonth = month + 1;
                    dateDay = day;
                    dateYear = year;
                    t_selectedDate.setText(dateMonth + "/" + dateDay + "/" + dateYear);
                    confirmButton.setEnabled(true);
                    confirmButton.setVisibility(View.VISIBLE);
                }
            }
        });

        Spinner hourSpinner = findViewById(R.id.hour_spinner);
        Spinner minutesSpinner = findViewById(R.id.minutes_spinner);

        ArrayAdapter<CharSequence> hoursAdapter = ArrayAdapter.createFromResource(this, R.array.hourStrings, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> minutesAdapter = ArrayAdapter.createFromResource(this, R.array.minutesStrings, android.R.layout.simple_spinner_item);

        hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        hourSpinner.setAdapter(hoursAdapter);
        minutesSpinner.setAdapter(minutesAdapter);

        hourSpinner.setOnItemSelectedListener(this);
        minutesSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /* Once a data is selected it the user will be able to confirm that data and have it show up in a calendar */
    public void confirmDate(View view) {
        Spinner hourSpinner = findViewById(R.id.hour_spinner);
        Spinner minutesSpinner = findViewById(R.id.minutes_spinner);

        hours = hourSpinner.getSelectedItem().toString();
        minutes = minutesSpinner.getSelectedItem().toString();
        dateTime = hours + ":" + minutes;

        Intent intent = new Intent(this, CreateQuote_View.class);
        Bundle extras = new Bundle();

        extras.putInt("month", dateMonth);
        extras.putInt("day", dateDay);
        extras.putInt("year", dateYear);
        extras.putString("time", dateTime + " MST");

        intent.putExtras(extras);
        startActivity(intent);
    }
}
