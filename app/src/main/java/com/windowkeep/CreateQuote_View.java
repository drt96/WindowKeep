package com.windowkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateQuote_View extends AppCompatActivity {

    private Button saveQuote;
    private EditText first_name;
//    private EditText custAddress;
    private DatabaseReference mDatabaseReference;

    public CreateQuote_View() {

    }
    public CreateQuote_View(String custFirstName, String toString) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quote);

        first_name = findViewById(R.id.custFirstName);
//        custAddress = findViewById(R.id.custAddress);
        saveQuote = findViewById(R.id.quoteButton);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        saveQuote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Quote quote;
                quote = new Quote(first_name.getText().toString());
                mDatabaseReference.child("CustomerData").push().setValue(quote);
                finish();
            }
        });

    }
}
