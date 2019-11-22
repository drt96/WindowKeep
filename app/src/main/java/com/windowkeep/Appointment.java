package com.windowkeep;

/* A class to handle the data for the creation and editing of one appointment */
public class Appointment {
    private String appointmentDate;
    private Quote quote;

    /*
     Only Constructor that we should need.
     Noting that Quote has a Customer
     and Customer has a Location
     and Location is the unique ID thought-out the app.
     So that we can have multiple appointments with each customer.
     (SEE toString if that doesn't make sense)
    */
    public Appointment(String appointmentDate, Quote quote) {
        this.appointmentDate = appointmentDate;
        this.quote = quote;
    }

    /* Getters and setters */
    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "Appointment: " + appointmentDate
                + "\n" + quote
                + "\n" + "Location: " + quote.getCustomer().getLocation().toString();
    }
}

