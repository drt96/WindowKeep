package com.windowkeep;

import android.os.Parcel;
import android.os.Parcelable;

/*
    Handles the logic to make and use locations, the ID of the Application
 */
public class Location implements Parcelable {
    private double latitude;
    private double longitude;

    /* Constructors */
    public Location() {
        latitude = 0;
        longitude = 0;
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Location(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    /* Getters and Setters */
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.longitude = latitude;
    }

    public double getLongitude() {return longitude; }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        Class c = getClass();
        return String.format("Lat: %.3f", latitude) + String.format(", Long: %.3f", longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
