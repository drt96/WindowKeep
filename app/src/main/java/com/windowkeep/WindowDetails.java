package com.windowkeep;

import java.util.List;

/* Handles the data for windows on all floors */
public class WindowDetails {
    private int windows; // Number of
    private List<Floors> floors; /* More than 3 floors makes for a commercial client with a unique pricing system */
    private int Size; /* 1 Small, 2 Medium , 3 Large,  0 for nothing */

    /* Constructor
    * Only gets made once each floor has been worked on
    * */
    public WindowDetails(int windows, List<Floors> floors, int size) {
        this.windows = windows;
        this.floors = floors;
        Size = size;
    }

    /* Getters and Setters */
    public int getWindows() {
        return windows;
    }

    public void setWindows(int windows) {
        this.windows = windows;
    }

    public List<Floors> getFloors() {
        return floors;
    }

    public void setFloors(List<Floors> floors) {
        this.floors = floors;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    @Override
    public String toString() {
        return "Details:\n" +
                "# of Windows: " + windows +
                ", # of Floors: " + floors.toString() +
                ", Size of Windows: " + Size;
    }
}
