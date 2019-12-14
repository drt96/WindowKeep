package com.windowkeep;

/* One instance of the floor class, used to populate the data for a floor */
public class Floors {
    private int small;
    private int medium;
    private int large;

    public Floors() {
    }

    // Use 0 if nothing
    public Floors(int small, int medium, int large) {
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    /* Getters and Setters */
    public int getSmall() {
        return small;
    }

    public void setSmall(int small) {
        this.small = small;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getLarge() {
        return large;
    }

    public void setLarge(int large) {
        this.large = large;
    }

    @Override
    public String toString() {
        return "Floor" +
                "\n# small: " + small +
                "\n# medium: " + medium +
                "\n# large: " + large;
    }
}