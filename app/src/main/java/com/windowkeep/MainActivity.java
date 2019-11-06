package com.windowkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // Create variable for button
    private Button quoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Hello everyone from Daniel");
        System.out.println("Hello");
        System.out.println("Tanner says hello");
        System.out.println("Aaron says hello");

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

        //Create the on click listener and create the create quote activity
        quoteButton = findViewById(R.id.quoteButton);
        quoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createQuote();
            }
        });
    }

    private void createQuote() {
        Intent intent = new Intent(this, CreateQuote.class);
        startActivity(intent);
    }
}
