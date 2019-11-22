package com.windowkeep;

import android.widget.EditText;

/*
    Handles the logic to make and use Customers
 */
public class Customer {
    private Location location;
    private String name;
    private String phoneNumber;
    private Description description;
    private String email;

    /*
     Constructors:
     Not every customer will have a phone number but if an apt is made an email is needed
    */

    // Default for create quote activity
    public Customer(String name) {}

    public Customer(Location location) {
        this.location = location;
    }

    // Default Coçnstructor for appointment
    public Customer(Location location, String name, String email) {
        this.location = location;
        this.name = name;
        this.email = email;
    }

    // Constructor with all information
    public Customer(Location location, String name, String phoneNumber, String email) {
        this.location = location;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    /* Getters and Setters */
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // TODO: Figure out enum getters and setters. Is this the right way? I think it is. (Daniel)
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Customer" +
                "\nLocation: " + location +
                "\nName: " + name +
                "\nPhoneNumber: " + phoneNumber +
                "\nEmail: " + email +
                "\nDescription: " + description;
    }
}
