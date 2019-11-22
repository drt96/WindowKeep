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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;

/* View and Presenter for creating a quote */
@SuppressLint("ParcelCreator")
public class CreateQuote_View extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Parcelable {

    /* Create FirebaseDatabase variable */
    private FirebaseDatabase database;

    /* Create calls variables from Activity UI. Using "location" for address ET field */
    private Button saveQuote;
    private EditText eT_name, eT_address, eT_email, eT_phone_number, small_windows, medium_windows, large_windows;
    private TextView quoteDate;
    private static String name, address, email, phone_number;

    /* Variables for the small, medium, and large number of windows that change when you select a new spinner option */
    private static int bS, bM, bL, oneS, oneM, oneL, twoS, twoM, twoL, comS, comM, comL;
    private Spinner floorsSpinner;
    private double quoteAmount;
    private static double latitude, longitude;
    private Location location;

    private String date;

    public CreateQuote_View() {
    }

    /*
     This is the Create_View constructor for the Location
     class Parcel.
    */
    protected CreateQuote_View(Parcel in) {
        quoteAmount = in.readDouble();
    }

    public static final Creator<CreateQuote_View> CREATOR = new Creator<CreateQuote_View>() {
        @Override
        public CreateQuote_View createFromParcel(Parcel in) {
            return new CreateQuote_View(in);
        }

        @Override
        public CreateQuote_View[] newArray(int size) {
            return new CreateQuote_View[size];
        }
    };

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quote);

        /* Get class variables */
        quoteDate = findViewById(R.id.tV_CurrentDate);
        eT_name = findViewById(R.id.eT_Name);
        eT_address = findViewById(R.id.eTM_Address);
        eT_email = findViewById(R.id.eT_Email);
        eT_phone_number = findViewById(R.id.eT_Phone);
        small_windows = findViewById(R.id.eT_sWindows);
        medium_windows = findViewById(R.id.eT_mWindows);
        large_windows = findViewById(R.id.eT_lWindows);
        saveQuote = findViewById(R.id.btn_SaveQuote);

        fillTextFields();

        // Sets the location of the current quote and keeps it consistent as we change to and from the selectDate view.
        // Just some error checking to make sure the location is correct
        Intent incomingIntent = getIntent();
        if (    incomingIntent.getExtras().getDouble("longitude") != 0 &&
                incomingIntent.getExtras().getDouble("latitude") != 0) {
            Bundle extras = incomingIntent.getExtras();
            location = new Location(extras.getDouble("latitude"), extras.getDouble("longitude"));
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.i("loc", location.getLatitude() + " " + location.getLongitude());
        }
        else {
            location = new Location(latitude, longitude);
            Log.i("loc", location.getLatitude() + " " + location.getLongitude());
        }


        /* Initialize FirebaseDatabase with Instance */
        database = FirebaseDatabase.getInstance();

        /* Creating onClickListener for "Save Quote" button */
        saveQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFB();
            }
        });

        /* Setting up the spinner for selecting which floor to input a number of windows on */
        floorsSpinner = findViewById(R.id.s_floors);
        ArrayAdapter<CharSequence> floorAdapter = ArrayAdapter.createFromResource(this, R.array.numFloors, android.R.layout.simple_spinner_item);
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorsSpinner.setAdapter(floorAdapter);
        floorsSpinner.setOnItemSelectedListener(this);

        // Textwatcher for the text fields at the top (name, email, etc). This will save the info
        // put in the fields and repopulate the text boxes when we leave the view and come back to it.
        // Without this, the fields will be blank when we leave the view and return to it (IE when we select a
        // date from the calendar and then return to the createQuoteView)
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
                } else if (currentFloor.equalsIgnoreCase("1")) {
                    if (!small_windows.getText().toString().equalsIgnoreCase("")) {
                        oneS = Integer.parseInt(small_windows.getText().toString());
                    } else {
                        oneS = 0;
                    }
                } else if (currentFloor.equalsIgnoreCase("2")) {
                    if (!small_windows.getText().toString().equalsIgnoreCase("")) {
                        twoS = Integer.parseInt(small_windows.getText().toString());
                    } else {
                        twoS = 0;
                    }
                } else if (currentFloor.equalsIgnoreCase("Commercial")) {
                    if (!small_windows.getText().toString().equalsIgnoreCase("")) {
                        comS = Integer.parseInt(small_windows.getText().toString());
                    } else {
                        comS = 0;
                    }
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
                } else if (currentFloor.equalsIgnoreCase("1")) {
                    if (!medium_windows.getText().toString().equalsIgnoreCase("")) {
                        oneM = Integer.parseInt(medium_windows.getText().toString());
                    } else {
                        oneM = 0;
                    }
                } else if (currentFloor.equalsIgnoreCase("2")) {
                    if (!medium_windows.getText().toString().equalsIgnoreCase("")) {
                        twoM = Integer.parseInt(medium_windows.getText().toString());
                    } else {
                        twoM = 0;
                    }
                } else if (currentFloor.equalsIgnoreCase("Commercial")) {
                    if (!medium_windows.getText().toString().equalsIgnoreCase("")) {
                        comM = Integer.parseInt(medium_windows.getText().toString());
                    } else {
                        comM = 0;
                    }
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
                } else if (currentFloor.equalsIgnoreCase("1")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        oneL = Integer.parseInt(large_windows.getText().toString());
                    } else {
                        oneL = 0;
                    }
                } else if (currentFloor.equalsIgnoreCase("2")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        twoL = Integer.parseInt(large_windows.getText().toString());
                    } else {
                        twoL = 0;
                    }
                } else if (currentFloor.equalsIgnoreCase("Commercial")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        comL = Integer.parseInt(large_windows.getText().toString());
                    } else {
                        comL = 0;
                    }
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

    // TODO: This is what we need to get done next
    public void CalculatePrice() {

        Customer customer = new Customer(location, eT_name.toString(), eT_phone_number.toString(), eT_email.toString());
        // Floors floors = new Floors(int,int,int);
        // WindowDetails windowDetails = new WindowDetails();
        // Quote quote = new Quote(qouteDate, customer, WindowDetails windowDetails, double amount) {
        boolean isCommercial = floorsSpinner.getSelectedItem().toString().equalsIgnoreCase("Commercial");
        // quoteAmount = quote.calculateAmount(isCommercial);

    }

    /* TODO: I think we could use this throughout, get rid of the date class and instead just member data: String Date */
    public void setTodaysDate() {
        java.util.Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(date);
        quoteDate.setText("Date: " + formattedDate);
    }

    public void fillTextFields() {
        eT_name.setText(name);
        eT_email.setText(email);
        eT_phone_number.setText(phone_number);
        eT_address.setText(address);
    }

    /* Method for saving quote data to Firebase Database */
    private void saveToFB() {
        /*
         Create the new customer passing in values from activity.
         We don't need getText and toString for location because
         we're passing the Location class to the Customer class
         via Parcelable
        */
        Customer customer = new Customer(location, eT_name.getText().toString(), eT_phone_number.getText().toString()
                , eT_email.getText().toString());

        /* Initialize the database reference based off of the Firebase vaiable above */
        DatabaseReference myReference = database.getReference();
        myReference.child("Quote Data").push().setValue(customer);
        finish();

    }

    /* Abstract for parcelable */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quoteAmount);
    }
}
