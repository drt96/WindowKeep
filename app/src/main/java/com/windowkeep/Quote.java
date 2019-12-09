package com.windowkeep;
/*
 A class that handles the making of a Quote independent of the making of an appointment
*/

/*
     Calculator Algorithm

  Domestic clients (3 floors):
        Floor 0:
    Small:    $2.50
    Medium:   $3.50
    Large:    $4.50
        Floor 1:
    Small:    $2.25
    Medium:   $3.25
    Large:    $4.25
        Floor 2:
    Small:    $3.75
    Medium:   $4.75
    Large:    $5.75

  Commercial clients (4 or more floors)
    Base price: $100
    Small:      $1.50
    Medium:     $2.50
    Large:      $3.50
*/

public class Quote {
    /* Const data */
    private static final double basePrice = 100.0;

    private static final double s0 = 2.50;
    private static final double m0 = 3.50;
    private static final double l0 = 4.50;

    private static final double s1 = 2.25;
    private static final double m1 = 3.25;
    private static final double l1 = 4.25;

    private static final double s2 = 2.75;
    private static final double m2 = 3.75;
    private static final double l2 = 4.75;

    private static final double sC = 1.50;
    private static final double mC = 2.50;
    private static final double lC = 3.50;

    /* Private Data */
    private String quoteDate;
    private String aptDate;
    private String aptTime;
    private Customer customer;
    private WindowDetails windowDetails;
    private double amount;


    /* These should be the only constructors that should be needed */
    public Quote() {
        quoteDate = "";
        aptTime = "";
        aptDate = "";
        amount = 0.0;
    }

    public Quote(String quoteDate, Customer customer, WindowDetails windowDetails) {
        this.quoteDate = quoteDate;
        this.customer = customer;
        this.windowDetails = windowDetails;
    }

    public Quote(String quoteDate, String aptDate, String aptTime, Customer customer, double amount, WindowDetails windowDetails) {
        this.quoteDate = quoteDate;
        this.aptDate = aptDate;
        this.aptTime = aptTime;
        this.customer = customer;
        this.amount = amount;
        this.windowDetails = windowDetails;
    }

    /* Getters and Setters */

    public String getAptDate() {
        return aptDate;
    }

    public void setAptDate(String aptDate) {
        this.aptDate = aptDate;
    }

    public String getAptTime() {
        return aptTime;
    }

    public void setAptTime(String aptTime) {
        this.aptTime = aptTime;
    }

    public String getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(String quoteDate) {
        this.quoteDate = quoteDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public WindowDetails getWindowDetails() {
        return windowDetails;
    }

    public void setWindowDetails(WindowDetails windowDetails) {
        this.windowDetails = windowDetails;
    }

    /* Use window details to calculate the quote double amount
     * It's ugly but it should work. I was too lazy to solve it in a loop*/
    public void calculateAmount(boolean isCommercial) {
        double calcAmount = 0;
        Floors temp;
        int i = 3; /* Commercial Client */

        if (isCommercial) {
            // Locations with more than the second floor are commercial
            temp = windowDetails.getFloors().get(i);
            calcAmount += (basePrice +
                    (sC * temp.getSmall()) +
                    (mC * temp.getMedium()) +
                    (lC * temp.getLarge()));
        } else {
            i--;
            temp = windowDetails.getFloors().get(i--);
            calcAmount += (s2 * temp.getSmall()) +
                    (m2 * temp.getMedium()) +
                    (l2 * temp.getLarge());
            temp = windowDetails.getFloors().get(i--);
            calcAmount += (s1 * temp.getSmall()) +
                    (m1 * temp.getMedium()) +
                    (l1 * temp.getLarge());
            temp = windowDetails.getFloors().get(i);
            calcAmount += (s0 * temp.getSmall()) +
                    (m0 * temp.getMedium()) +
                    (l0 * temp.getLarge());
        }
        amount = calcAmount;
    }


    @Override
    public String toString() {
        return "Quote:" +
                /* Date, customer and WindowDetails has their own toString */
                "\nQuote Date " + quoteDate +
                "\nAppointment Date" + aptDate +
                "\nAppointment Time" + aptTime +
                "\n" + customer +
                "\nWindow Details: " + windowDetails +
                "\n$ " + amount;
    }
}
