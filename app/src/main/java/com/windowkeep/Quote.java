package com.windowkeep;
/*
 A class that handles the making of a Qoute independant of the making of an appointment
 TODO SEE windowDetails.class and Customer.class for queries
*/
public class Quote {
    private Date qouteDate;
    private Customer customer;
    private Dollar dollar;
    private WindowDetails windowDetails;

    /* This is the only constructor that should be needed */
    public Quote(Date qouteDate, Customer customer, Dollar dollar, WindowDetails windowDetails) {
        this.qouteDate = qouteDate;
        this.customer = customer;
        this.dollar = dollar;
        this.windowDetails = windowDetails;
    }

    /* Getters and Setters */
    public Date getQouteDate() {
        return qouteDate;
    }

    public void setQouteDate(Date qouteDate) {
        this.qouteDate = qouteDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Dollar getDollar() {
        return dollar;
    }

    public void setDollar(Dollar dollar) {
        this.dollar = dollar;
    }

    public WindowDetails getWindowDetails() {
        return windowDetails;
    }

    public void setWindowDetails(WindowDetails windowDetails) {
        this.windowDetails = windowDetails;
    }

    @Override
    public String toString() {
        return "Quote:" +
                "\nQoute: " + qouteDate +
                "\n" + customer +
                "\n" + dollar +
                "\nWindow Details: " + windowDetails;
    }
}
