package com.windowkeep;

/*
    Handles the logic to make and use locations, the ID of the Application
 */
public class Location {
    private double Latitude;
    private double Longitude;

    /* Constructors */
    public Location() {
        Latitude = 0;
        Longitude = 0;
    }

    public Location(double latitude, double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    /* Getters and Setters */
    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format("Lat: %.3f", Latitude) + String.format(", Long: %.3f", Longitude);
    }
}
