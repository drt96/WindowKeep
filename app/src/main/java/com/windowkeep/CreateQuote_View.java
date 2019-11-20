package com.windowkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/* View and Presenter for creating a quote */
public class CreateQuote_View extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseDatabase database;
    private Button saveQuote;
    private EditText address
    ,name
    ,email
    ,phone_number
    ,small_windows
    ,medium_windows
    ,large_windows;
    /* Variables for the small, medium, and large number of windows that change when you select a new spinner option */
    private static int bS, bM, bL, oneS, oneM, oneL, twoS, twoM, twoL, comS, comM, comL;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quote);

        name = findViewById(R.id.eT_Name);
        address = findViewById(R.id.eTM_Address);
        email = findViewById(R.id.eT_Email);
        phone_number = findViewById(R.id.eT_Phone);
        small_windows = findViewById(R.id.eT_sWindows);
        medium_windows = findViewById(R.id.eT_mWindows);
        large_windows = findViewById(R.id.eT_lWindows);

        database = FirebaseDatabase.getInstance();

        /* Setting up the spinner for selecting which floor to input a number of windows on */
        Spinner floorsSpinner = findViewById(R.id.s_floors);
        ArrayAdapter<CharSequence> floorAdapter = ArrayAdapter.createFromResource(this, R.array.numFloors, android.R.layout.simple_spinner_item);
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorsSpinner.setAdapter(floorAdapter);
        floorsSpinner.setOnItemSelectedListener(this);

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
                    }
                    else {
                        bS = 0;
                    }
                }
                else if (currentFloor.equalsIgnoreCase("1")) {
                    if (!small_windows.getText().toString().equalsIgnoreCase("")) {
                        oneS = Integer.parseInt(small_windows.getText().toString());
                    }
                    else {
                        oneS = 0;
                    }
                }
                else if (currentFloor.equalsIgnoreCase("2")) {
                    if (!small_windows.getText().toString().equalsIgnoreCase("")) {
                        twoS = Integer.parseInt(small_windows.getText().toString());
                    }
                    else {
                        twoS = 0;
                    }
                }
                else if (currentFloor.equalsIgnoreCase("Commercial")) {
                    if (!small_windows.getText().toString().equalsIgnoreCase("")) {
                        comS = Integer.parseInt(small_windows.getText().toString());
                    }
                    else {
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
                    }
                    else {
                        bM = 0;
                    }
                }
                else if (currentFloor.equalsIgnoreCase("1")) {
                    if (!medium_windows.getText().toString().equalsIgnoreCase("")) {
                        oneM = Integer.parseInt(medium_windows.getText().toString());
                    }
                    else {
                        oneM = 0;
                    }
                }
                else if (currentFloor.equalsIgnoreCase("2")) {
                    if (!medium_windows.getText().toString().equalsIgnoreCase("")) {
                        twoM = Integer.parseInt(medium_windows.getText().toString());
                    }
                    else {
                        twoM = 0;
                    }
                }
                else if (currentFloor.equalsIgnoreCase("Commercial")) {
                    if (!medium_windows.getText().toString().equalsIgnoreCase("")) {
                        comM = Integer.parseInt(medium_windows.getText().toString());
                    }
                    else {
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
                // Do nothing!
            }

            @Override
            public void afterTextChanged(Editable s) {
                String currentFloor = floorsSpinner.getSelectedItem().toString();

                if (currentFloor.equalsIgnoreCase("Basement")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        bL = Integer.parseInt(large_windows.getText().toString());
                    }
                    else {
                        bL = 0;
                    }
                }
                else if (currentFloor.equalsIgnoreCase("1")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        oneL = Integer.parseInt(large_windows.getText().toString());
                    }
                    else {
                        oneL = 0;
                    }
                }
                else if (currentFloor.equalsIgnoreCase("2")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        twoL = Integer.parseInt(large_windows.getText().toString());
                    }
                    else {
                        twoL = 0;
                    }
                }
                else if (currentFloor.equalsIgnoreCase("Commercial")) {
                    if (!large_windows.getText().toString().equalsIgnoreCase("")) {
                        comL = Integer.parseInt(large_windows.getText().toString());
                    }
                    else {
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

        small_windows.setOnFocusChangeListener(smallFocusListener);
        medium_windows.setOnFocusChangeListener(mediumFocusListener);
        large_windows.setOnFocusChangeListener(largeFocusListener);
    }

    @Override /* This function is an abstract function from the spinner that needs implementation */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner floorsSpinner = findViewById(R.id.s_floors);
        String currentFloor = floorsSpinner.getSelectedItem().toString();

        if (currentFloor.equalsIgnoreCase("Basement")) {
            small_windows.setText("" + bS);
            medium_windows.setText("" + bM);
            large_windows.setText("" + bL);
        }
        else if (currentFloor.equalsIgnoreCase("1")) {
            small_windows.setText("" + oneS);
            medium_windows.setText("" + oneM);
            large_windows.setText("" + oneL);
        }
        else if (currentFloor.equalsIgnoreCase("2")) {
            small_windows.setText("" + twoS);
            medium_windows.setText("" + twoM);
            large_windows.setText("" + twoL);
        }
        else if (currentFloor.equalsIgnoreCase("Commercial")) {
            small_windows.setText("" + comS);
            medium_windows.setText("" + comM);
            large_windows.setText("" + comL);
        }
    }

    @Override /* This function is an abstract function from the spinner that needs implementation */
    public void onNothingSelected(AdapterView<?> parent) {
        /* Intentionally left blank - an abstract function of the spinner */
    }

    public static void resetWindowCount() {
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
    }


    public void openCalendar(View view) {
        Intent intent = new Intent(this, SelectDate_View.class);
        startActivity(intent);
    }
}
