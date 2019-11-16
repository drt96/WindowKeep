package com.windowkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateQuote_View extends AppCompatActivity {

    private Button saveQuote;
    private EditText name;
    private EditText address;
    private EditText email;
    private EditText phone_number;
    private FirebaseDatabase database;

    public CreateQuote_View() {

    }

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


        database = FirebaseDatabase.getInstance();
        saveQuote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Quote quote = new Quote();
                database.getReference("Customer info").push().setValue(quote);

            }
        });
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(this, SelectDate_View.class);
        startActivity(intent);
    }
}
