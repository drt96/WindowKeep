package com.windowkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    private DatabaseReference mDatabaseReference;

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
        phone_number = findViewById(R.id.eT_Number);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        saveQuote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Quote quote;
                quote = new Quote();
                mDatabaseReference.child("CustomerData").push().setValue(quote);
                finish();
            }
        });

    }
}
