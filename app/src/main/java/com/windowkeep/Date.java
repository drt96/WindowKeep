package com.windowkeep;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/*
    Handles the logic to make and use Dates and Times
 */
public class Date {
    /* Default zone */
    private static final String ZONE = "America/Boise";

    /* Private data for Date.class */
    private int day;
    private int month;
    private int year;
    private String time;

    private ZonedDateTime zdt;
    private ZoneId zoneId;
    private DateTimeFormatter formatter;

    /* Default Constructor */
    public Date() {
        /* In case you want to change the zone */
        zoneId = ZoneId.of(ZONE);
        zdt = ZonedDateTime.now(zoneId);
        /* Locale is for language */
        zdt.format(formatter.withLocale(Locale.US));  // Or Locale.CANADA_FRENCH and so on.
        /* Format to the time zone */
        formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

    }

    /* Getters and Setters */
    public int getDay() {
        return zdt.getDayOfMonth();
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return zdt.getMonthValue();
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return zdt.getYear();
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTime() {
        return zdt.getHour() + " " + zdt.getMinute();
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Date\n" +
                "Month: " + month +
                " Day: " + day +
                " Year: " + year +
                " and Time: " + time;
    }
}
