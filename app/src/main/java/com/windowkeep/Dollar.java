package com.windowkeep;

/*
    Handles the logic to make and use US dollars

    For now the class is pretty arbitrary, but can quickly be modified
    to handle addition and subtraction of $'s
 */
public class Dollar {
    private long wholeNumber;
    private int cents;

    /* Constructors */
    public Dollar() {
        this.wholeNumber = 0;
        this.cents = 0;
    }

    public Dollar(long wholeNumber, int cents) {
        this.wholeNumber = wholeNumber;
        this.cents = cents;
    }

    /* Getters and Setters */
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

    @Override
    public String toString() {
        return "$ " + wholeNumber +
                "." + cents;
    }

    /* Add dollar amounts */
    public Dollar add(Dollar toAdd)
    {
        this.wholeNumber +=  toAdd.getWholeNumber();
        this.cents +=  toAdd.getCents();
        return this;
    }
    /* multiply dollar amounts */
    public Dollar multiply(int toMultiply)
    {
        this.wholeNumber *=  toMultiply.getWholeNumber();
        this.cents +=  toMultiply.getCents();
        return this;
    }
}
