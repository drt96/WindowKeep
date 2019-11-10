package com.windowkeep;

/*
    Handles the logic to make and use US dollars

    For now the class is pretty arbitrary, but can quickly be modified
    to handle addition and subtraction of $'s
 */
public class Dollar {
    private long wholeNumber;
    private int cents;

    public Dollar(long wholeNumber, int cents) {
        this.wholeNumber = wholeNumber;
        this.cents = cents;
    }

    @Override
    public String toString() {
        return "$ " + wholeNumber +
                "." + cents;
    }

    public long getWholeNumber() {
        return wholeNumber;
    }

    public void setWholeNumber(long wholeNumber) {
        this.wholeNumber = wholeNumber;
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }
}
