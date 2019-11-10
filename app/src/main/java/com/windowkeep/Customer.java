package com.windowkeep;

/*
    Handles the logic to make and use Customers
 */
public class Customer {
    private Location location;
    private String name;
    private String phoneNumber;
    private Description description;

    /*
     Constructors:
     Not every customer will have a phone number

     TODO Should we have gmail/ e-mail addresses
      in addition to or instead of phone numbers so that we can better handle calender sync?
    */
    public Customer(Location location, String name) {
        this.location = location;
        this.name = name;
    }

    public Customer(Location location, String name, String phoneNumber) {
        this.location = location;
        this.name = name;
        this.phoneNumber = phoneNumber;
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

    // TODO: Figure out enum getters and setters. Is this the right way?
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
                "\nDescription: " + description;
    }
}
