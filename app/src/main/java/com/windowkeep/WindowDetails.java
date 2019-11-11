package com.windowkeep;

/*
 TODO I just realised that each floor could have multiple window of differing sizes.
  We ought to handle that perhaps by having each Qoute have a <list> of WindowDetails with an
  implementation of WindowDetails per each floor.
  What do you guys think? If you don't get it try making a non default constructor for this class and seeing if it makes  any sense the way it is.

  Make a dummy algorithm that we can use as a modal for our modal sorry if that's confusing

  A potential fix is to have a class for floors that has int, small, medium large. So that we can see how many of each window type is on each floor
  and then have this class add it all up
*/
public class WindowDetails {
    private int windows; // More than 20 windows makes for a commercial client with a unique pricing system
    private int floors;
    private int Size;


    /* Getters and Setters */
    public int getWindows() {
        return windows;
    }

    public void setWindows(int windows) {
        this.windows = windows;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
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
                ", # of Floors: " + floors +
                ", Size of Windows: " + Size;
    }
}
