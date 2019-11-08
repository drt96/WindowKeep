package com.windowkeep;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

// Time zone America/Boise
/*
    Handles the logic to make and use Dates and Times
 */
public class Date {
    private String day;
    private String month;
    private String year;
    private String time;

    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM );
    String output = zdt.format( formatter.withLocale( Locale.US ) );  // Or Locale.CANADA_FRENCH and so on.

    public String getDay() {
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
