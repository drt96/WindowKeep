package com.windowkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/* View and Presenter for creating a quote */
@SuppressLint("ParcelCreator")
public class CreateQuote_View extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /* Create FirebaseDatabase variable */
    private FirebaseDatabase database;

    /* Create calls variables from Activity UI. Using "location" for address ET field */
    private Button saveQuote;
    private EditText eT_name, eT_address, eT_email, eT_phone_number, small_windows, medium_windows, large_windows;
    private TextView quoteDate, totalPrice, aptDate, latLongLocation;
    private static String name, address, email, phone_number, m_date, a_date, price;

    private Quote quote;
    private static double quoteAmount;

    /* Variables for the small, medium, and large number of windows that change when you select a new spinner option */
    private static int bS, bM, bL, oneS, oneM, oneL, twoS, twoM, twoL, comS, comM, comL;
    private Spinner floorsSpinner;
    private static double latitude, longitude;
    private ID id;

    private String aptTime;

    /* Member data used to populate the windowDetails class for each quote */
    private Floors basement, one, two, commercial;
    private List<Floors> floorsList;

    public CreateQuote_View() {
    }

    /* Initialize data */
    public static void resetQuoteFields() {
        bS = 0;
        bM = 0;
        bL = 0;
        oneS = 0;
        oneM = 0;
        oneL = 0;
        twoS = 0;
        twoM = 0;
        twoL = 0;
        comS = 0;
        comM = 0;
        comL = 0;
        name = "";
        address = "";
        phone_number = "";
        email = "";
        price = "";
        a_date = "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quote);

        /* Get class variables */
        quoteDate = findViewById(R.id.tV_CurrentDate);
        aptDate = findViewById(R.id.tV_aptDate);
        totalPrice = findViewById(R.id.tV_totalPrice);
        eT_name = findViewById(R.id.eT_Name);
        eT_address = findViewById(R.id.eTM_Address);
        eT_email = findViewById(R.id.eT_Email);
        eT_phone_number = findViewById(R.id.eT_Phone);
        small_windows = findViewById(R.id.eT_sWindows);
        medium_windows = findViewById(R.id.eT_mWindows);
        large_windows = findViewById(R.id.eT_lWindows);
        saveQuote = findViewById(R.id.btn_SaveQuote);
        latLongLocation = findViewById(R.id.tV_Location);

        basement = new Floors(0, 0, 0);
        one = new Floors(0, 0, 0);
        two = new Floors(0, 0, 0);
        commercial = new Floors(0, 0, 0);

        /* DON'T FORGET ABOUT THIS */
        floorsList = new ArrayList<Floors>();
        floorsList.add(basement);
        floorsList.add(one);
        floorsList.add(two);
        floorsList.add(commercial);

        fillTextFields();

        /* Creating onClickListener for "Save Quote" button */
        saveQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFB();
            }
        });

        /*
         Sets the location of the current quote and keeps it consistent as we change to and from the selectDate view.
         Just some error checking to make sure the location is correct
        */
        Intent incomingIntent = getIntent();
        Bundle extras = incomingIntent.getExtras();

        if (extras.containsKey("latitude")) {
            id = new ID(extras.getDouble("latitude"), extras.getDouble("longitude"));
            latitude = id.getLatitude();
            longitude = id.getLongitude();
            Log.i("loc", id.getLatitude() + " " + id.getLongitude());
        } else {
            id = new ID(latitude, longitude);
        }

        latLongLocation.setText(id.toString());

        if (extras.containsKey("month")) {
            Log.i("loc", "" + extras.getString("time"));
            String dateString = (extras.getInt("month") + "/" + extras.getInt("day") + "/" + extras.getInt("year"));
            a_date = dateString;
            aptTime = extras.getString("time");
            aptDate.setText("Apt Date: " + dateString + " - " + extras.getString("time"));
        }

        /* Initialize FirebaseDatabase with Instance */
        database = FirebaseDatabase.getInstance();

        /* Setting up the spinner for selecting which floor to input a number of windows on */
        floorsSpinner = findViewById(R.id.s_floors);
        ArrayAdapter<CharSequence> floorAdapter = ArrayAdapter.createFromResource(this, R.array.numFloors, android.R.layout.simple_spinner_item);
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorsSpinner.setAdapter(floorAdapter);
        floorsSpinner.setOnItemSelectedListener(this);

        /*
         Text Watcher for the text fields at the top (name, email, etc). This will save the info
         put in the fields and repopulate the text boxes when we leave the view and come back to it.
         Without this, the fields will be blank when we leave the view and return to it (IE when we select a
         date from the calendar and then return to the createQuoteView)
        */
        TextWatcher inputFieldsWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                name = eT_name.getText().toString();
                address = eT_address.getText().toString();
                email = eT_email.getText().toString();
                phone_number = eT_phone_number.getText().toString();
                price = totalPrice.getText().toString();
            }
        };

        /* A listener for when number of windows editText changes */
        TextWatcher smallTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                String currentFloor = floorsSpinner.getSelectedItem().toString();

                if (currentFloor.equalsIgnoreCase("Basement")) {
                    if (!small_windows.getText().toString().equalsIgnoreCase("")) {
                        bS = Integer.parseInt(small_windows.getText().toString());
                    } else {
                        bS = 0;
                    }
                    basement.setSmall(bS);
                } else if (currentFloor.equalsIgnoreCase("1")) {
                    if (!small_windows.getText().toString().equalsIgnoreCase("")) {
                        oneS = Integer.parseInt(small_windows.getText().toString());
                    } else {
                        oneS = 0;
                    }
                    one.setSmall(oneS);
                } else if (currentFloor.equalsIgnoreCase("2")) {
                    if (!small_windows.getText().toString().equalsIgnoreCase("")) {
                        twoS = Integer.parseInt(small_windows.getText().toString());
                    } else {
                        twoS = 0;
                    }
                    two.setSmall(twoS);
                } else if (currentFloor.equalsIgnoreCase("Commercial")) {
                    if (!small_windows.getText().toString().equalsIgnoreCase("")) {
                        comS = Integer.parseInt(small_windows.getText().toString());
                    } else {
                        comS = 0;
                    }
                    commercial.setSmall(comS);
                }
            }
        };

        TextWatcher mediumTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                String currentFloor = floorsSpinner.getSelectedItem().toString();

                if (currentFloor.equalsIgnoreCase("Basement")) {
                    if (!medium_windows.getText().toString().equalsIgnoreCase("")) {
                        bM = Integer.parseInt(medium_windows.getText().toString());
                    } else {
                        bM = 0;
                    }
                    basement.setMedium(bM);
                } else if (currentFloor.equalsIgnoreCase("1")) {
                    if (!medium_windows.getText().toString().equalsIgnoreCase("")) {
                        oneM = Integer.parseInt(medium_windows.getText().toString());
                    } else {
                        oneM = 0;
                    }
                    one.setMedium(oneM);
                } else if (currentFloor.equalsIgnoreCase("2")) {
                    if (!medium_windows.getText().toString().equalsIgnoreCase("")) {
                        twoM = Integer.parseInt(medium_windows.getText().toString());
                    } else {
                        twoM = 0;
                    }
                    two.setMedium(twoM);
                } else if (currentFloor.equalsIgnoreCase("Commercial")) {
                    if (!medium_windows.getText().toString().equalsIgnoreCase("")) {
                        comM = Integer.parseInt(medium_windows.getText().toString());
                    } else {
                        comM = 0;
                    }
                    commercial.setMedium(comM);
                }
            }
        };

        TextWatcher largeTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                String currentFloor = floorsSpinner.getSelectedItem().toString();

                if (currentFloor.equalsIgnoreCase("Basement")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        bL = Integer.parseInt(large_windows.getText().toString());
                    } else {
                        bL = 0;
                    }
                    basement.setLarge(bL);
                } else if (currentFloor.equalsIgnoreCase("1")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        oneL = Integer.parseInt(large_windows.getText().toString());
                    } else {
                        oneL = 0;
                    }
                    one.setLarge(oneL);
                } else if (currentFloor.equalsIgnoreCase("2")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        twoL = Integer.parseInt(large_windows.getText().toString());
                    } else {
                        twoL = 0;
                    }
                    two.setLarge(twoL);
                } else if (currentFloor.equalsIgnoreCase("Commercial")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        comL = Integer.parseInt(large_windows.getText().toString());
                    } else {
                        comL = 0;
                    }
                    commercial.setLarge(comL);
                }
            }
        };

        View.OnFocusChangeListener smallFocusListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    small_windows.setText("");
                } else {
                    if (small_windows.getText().toString().equalsIgnoreCase("")) {
                        small_windows.setText("0");
                    }
                }
            }
        };

        View.OnFocusChangeListener mediumFocusListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    medium_windows.setText("");
                } else {
                    if (medium_windows.getText().toString().equalsIgnoreCase("")) {
                        medium_windows.setText("0");
                    }
                }
            }
        };

        View.OnFocusChangeListener largeFocusListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    large_windows.setText("");
                } else {
                    if (large_windows.getText().toString().equalsIgnoreCase("")) {
                        large_windows.setText("0");
                    }
                }
            }
        };

        /* Setting up listeners for when the text changes in the number of windows editText fields */
        small_windows.addTextChangedListener(smallTextWatcher);
        medium_windows.addTextChangedListener(mediumTextWatcher);
        large_windows.addTextChangedListener(largeTextWatcher);

        eT_name.addTextChangedListener(inputFieldsWatcher);
        eT_address.addTextChangedListener(inputFieldsWatcher);
        eT_email.addTextChangedListener(inputFieldsWatcher);
        eT_phone_number.addTextChangedListener(inputFieldsWatcher);

        small_windows.setOnFocusChangeListener(smallFocusListener);
        medium_windows.setOnFocusChangeListener(mediumFocusListener);
        large_windows.setOnFocusChangeListener(largeFocusListener);

        setTodaysDate();
    }

    @Override /* This function is an abstract function from the spinner that needs implementation */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner floorsSpinner = findViewById(R.id.s_floors);
        String currentFloor = floorsSpinner.getSelectedItem().toString();

        if (currentFloor.equalsIgnoreCase("Basement")) {
            small_windows.setText("" + bS);
            medium_windows.setText("" + bM);
            large_windows.setText("" + bL);
        } else if (currentFloor.equalsIgnoreCase("1")) {
            small_windows.setText("" + oneS);
            medium_windows.setText("" + oneM);
            large_windows.setText("" + oneL);
        } else if (currentFloor.equalsIgnoreCase("2")) {
            small_windows.setText("" + twoS);
            medium_windows.setText("" + twoM);
            large_windows.setText("" + twoL);
        } else if (currentFloor.equalsIgnoreCase("Commercial")) {
            small_windows.setText("" + comS);
            medium_windows.setText("" + comM);
            large_windows.setText("" + comL);
        }
    }

    @Override /* This function is an abstract function from the spinner that needs implementation */
    public void onNothingSelected(AdapterView<?> parent) {
        /* Intentionally left blank - an abstract function of the spinner */
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(this, SelectDate_View.class);
        startActivity(intent);
    }

    /* Just abstracting the repetitive work for calculating a quote because it happens for more than one button click */
    private void initializeQuote() {
        Customer customer = new Customer(id,
                eT_name.getText().toString(),
                eT_address.getText().toString(),
                eT_phone_number.getText().toString(),
                eT_email.getText().toString());
        WindowDetails windowDetails = new WindowDetails(floorsList);
        if (!a_date.isEmpty()) {
            quote = new Quote(m_date, a_date, aptTime, customer, quoteAmount, windowDetails);
            System.out.println("This totally worked.");
        } else {
            quote = new Quote(m_date, customer, windowDetails);
        }

        boolean isCommercial = floorsSpinner.getSelectedItem().toString().equalsIgnoreCase("Commercial");
        quote.calculateAmount(isCommercial);
    }

    public void CalculatePrice(View view) {
        initializeQuote();
        quoteAmount = quote.getAmount();
        totalPrice.setText("Total Price: $ " + quoteAmount);
        Toast.makeText(this, "Price Calculated", Toast.LENGTH_LONG).show();
    }

    public void setTodaysDate() {
        java.util.Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy '-' HH:mm z");
        String formattedDate = df.format(date);
        m_date = date.toString();
        quoteDate.setText("Quote Date: " + formattedDate);
    }

    public void fillTextFields() {
        eT_name.setText(name);
        eT_email.setText(email);
        eT_phone_number.setText(phone_number);
        eT_address.setText(address);
        totalPrice.setText("Total Price: $" + quoteAmount);
    }


    /* Method for saving quote data to Firebase Database */
    private void saveToFB() {
        /* SORRY I (DANIEL) CHANGED IT A BIT
         Create the new customer passing in values from activity.
         We don't need getText and toString for location because
         we're passing the Location class to the Customer class
         via Parcelable
        */
        initializeQuote();

        // TODO LOOK AT WISHLIST FOR FIREBASE AWESOMENESS

        /* Initialize the database reference based off of the Firebase variable above */
        DatabaseReference myReference = database.getReference();
        myReference.child("Quote Data").push().setValue(quote);
        Toast.makeText(this, "Saved to Database", Toast.LENGTH_LONG).show();
        finish();
    }
}
