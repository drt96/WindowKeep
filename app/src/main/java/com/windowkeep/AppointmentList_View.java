package com.windowkeep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;

public class AppointmentList_View extends AppCompatActivity {

    private CalendarView calendarView;
    private int dateMonth;
    private int dateDay;
    private int dateYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list__view);
        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                dateMonth = month + 1;
                dateDay = day;
                dateYear = year;
            }
        });
    }
}
