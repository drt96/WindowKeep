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
    private static final Dollar basePrice = new Dollar(100, 0);

    private static final Dollar s0 = new Dollar(2, 50);
    private static final Dollar m0 = new Dollar(3, 50);
    private static final Dollar l0 = new Dollar(4, 50);

    private static final Dollar s1 = new Dollar(2, 25);
    private static final Dollar m1 = new Dollar(3, 25);
    private static final Dollar l1 = new Dollar(4, 25);

    private static final Dollar s2 = new Dollar(2, 75);
    private static final Dollar m2 = new Dollar(3, 75);
    private static final Dollar l2 = new Dollar(4, 75);

    private static final Dollar sC = new Dollar(1, 50);
    private static final Dollar mC = new Dollar(2, 50);
    private static final Dollar lC = new Dollar(3, 50);

    /* Private Data */
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


    // Use window details to calculate the quote dollar amount
    public Dollar calculateAmount(boolean isCommerical)
    {
        Dollar amount;


        if (isCommerical)
        {
            amount.add(basePrice).add(windowDetails.getFloors().get(3).getSmall() s0) ;
        } else {
         }

        return amount;
    }


    @Override
    public String toString() {
        return "Quote:" +
                /* Date, customer, Dollar and WindowDetails has their own toString */
                "\nQoute " + qouteDate +
                "\n" + customer +
                "\n" + dollar +
                "\nWindow Details: " + windowDetails;
    }
}
