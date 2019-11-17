package com.windowkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateQuote_View extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button saveQuote;
    private EditText name;
    private EditText address;
    private EditText email;
    private EditText phone_number;
    private EditText small_windows;
    private EditText medium_windows;
    private EditText large_windows;
    private FirebaseDatabase database;
    // Variables for the small, medium, and large number of windows that change when you select a new spinner option
    private static int bS;
    private static int bM;
    private static int bL;
    private static int oneS;
    private static int oneM;
    private static int oneL;
    private static int twoS;
    private static int twoM;
    private static int twoL;
    private static int comS;
    private static int comM;
    private static int comL;


    public CreateQuote_View(String custFirstName, String toString) {
    }

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


        // Setting up the spinner for selecting which floor to input a number of windows on
        Spinner floorsSpinner = findViewById(R.id.s_floors);
        ArrayAdapter<CharSequence> floorAdapter = ArrayAdapter.createFromResource(this, R.array.numFloors, android.R.layout.simple_spinner_item);
        floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorsSpinner.setAdapter(floorAdapter);
        floorsSpinner.setOnItemSelectedListener(this);


        // A listener for when number of windows editText changes
        TextWatcher textWatcher = new TextWatcher() {
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
                if (floorsSpinner.getSelectedItem() == "Basement") {
                    bS = Integer.parseInt(small_windows.getText().toString());
                    bM = Integer.parseInt(medium_windows.getText().toString());
                    bL = Integer.parseInt(large_windows.getText().toString());
                }
                else if (floorsSpinner.getSelectedItem() == "1") {
                    oneS = Integer.parseInt(small_windows.getText().toString());
                    oneM = Integer.parseInt(medium_windows.getText().toString());
                    oneL = Integer.parseInt(large_windows.getText().toString());
                }
                else if (floorsSpinner.getSelectedItem() == "2") {
                    twoS = Integer.parseInt(small_windows.getText().toString());
                    twoM = Integer.parseInt(medium_windows.getText().toString());
                    twoL = Integer.parseInt(large_windows.getText().toString());
                }
                else if (floorsSpinner.getSelectedItem() == "Commercial") {
                    comS = Integer.parseInt(small_windows.getText().toString());
                    comM = Integer.parseInt(medium_windows.getText().toString());
                    comL = Integer.parseInt(large_windows.getText().toString());
                }
            }
        };

        // Setting up listeners for when the text changes in the number of windows editText fields
        small_windows.addTextChangedListener(textWatcher);
        medium_windows.addTextChangedListener(textWatcher);
        large_windows.addTextChangedListener(textWatcher);
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(this, SelectDate_View.class);
        startActivity(intent);
    }

    @Override // This funciton is an abstract function from the spinner that needs implementation
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override // This funciton is an abstract function from the spinner that needs implementation
    public void onNothingSelected(AdapterView<?> parent) {
        //Intentionally left blank - an abstract function of the spinner
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
}
