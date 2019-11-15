package com.windowkeep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

public class SelectDate_View extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CalendarView calendarView;
    private TextView t_selectedDate;
    private int dateMonth;
    private int dateDay;
    private int dateYear;
    private String dateTime;
    private String hours;
    private String minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date__view);
        calendarView = findViewById(R.id.calendarView);
        t_selectedDate = findViewById(R.id.t_selectedDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                dateMonth = month + 1;
                dateDay = day;
                dateYear = year;
                t_selectedDate.setText(dateMonth + "/" + dateDay + "/" + dateYear);
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
        extras.putString("time", dateTime);

        intent.putExtras(extras);
        startActivity(intent);
    }
}
