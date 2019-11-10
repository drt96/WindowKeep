package com.windowkeep;

/*
 TODO I just realised that each floor could have multiple window of differing sizes.
  We ought to handle that perhaps by having each Qoute have a <list> of WindowDetails with an
  implementation of WindowDetails per each floor.
  What do you guys think? If you don't get it try making a non default constructor for this class and seeing if it makes  any sense the way it is.
*/
public class WindowDetails {
    private int windows;
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
