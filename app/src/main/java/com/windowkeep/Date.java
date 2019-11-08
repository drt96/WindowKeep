package com.windowkeep;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/*
    Handles the logic to make and use Dates and Times
 */
public class Date {
    /* Default zone */
    public static final String ZONE = "America/Boise";

    /* Private data for Date.class */
    private String day;
    private String month;
    private String year;
    private String time;
    private ZonedDateTime zdt;
    private ZoneId zoneId;
    private DateTimeFormatter formatter;

    public Date() {
        /* In case you want to change the zone */
        zoneId = ZoneId.of(ZONE);
        zdt = ZonedDateTime.now(zoneId);
        /* Format to the time zone */
        formatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy hh:mm a" );

    }

    public String getDay() {
        /* Locale is for language */
        zdt.format(formatter.withLocale( Locale.US ) );  // Or Locale.CANADA_FRENCH and so on.

        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
