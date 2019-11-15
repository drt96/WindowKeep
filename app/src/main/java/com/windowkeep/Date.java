package com.windowkeep;

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

    /* Default Constructor */
    public Date() {
        this.day = 26;
        this.month = 10;
        this.year = 1996;
        this.time = "Hammer Time";
    }

    /* NonDefault Constructor */
    public Date(String time) {
        this.time = time;
    }

    public Date(int day, int month, int year, String time) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
    }

    /* Getters and Setters */
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year + " AT " + time;
    }
}
