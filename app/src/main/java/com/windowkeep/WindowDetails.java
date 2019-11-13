package com.windowkeep;

import java.util.List;

/* Handles the data for windows on all floors */
public class WindowDetails {
    private List<Floors> floors; /* More than 3 floors makes for a commercial client with a unique pricing system */

    /* Constructor
    * Only gets made once each floor has been worked on
    * */
    public WindowDetails(List<Floors> floors) {
        this.floors = floors;
    }

    /* Getters and Setters */
    public List<Floors> getFloors() {
        return floors;
    }

    public void setFloors(List<Floors> floors) {
        this.floors = floors;
    }

    @Override
    public String toString() {
        return "Details of" + floors.toString();
    }
}
