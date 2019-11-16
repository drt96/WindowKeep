package com.windowkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateQuote_View extends AppCompatActivity {

    private Button saveQuote;
    private Button b_select_appointment;
    private EditText first_name;
//    private EditText custAddress;
    private DatabaseReference mDatabaseReference;
    private Date appointmentDate;
    private TextView tv_appointmentDate;

    public CreateQuote_View() {

    }
    public CreateQuote_View(String custFirstName, String toString) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quote);
        b_select_appointment = findViewById(R.id.b_select_appointment);

        // This catches the date information from the SelectDate_View and stores it here
        Intent incomingIntent = getIntent();
        Bundle extras = incomingIntent.getExtras();
        if (extras != null) {
            appointmentDate = new Date(extras.getInt("day"), extras.getInt("month"), extras.getInt("year"), extras.getString("time"));
            tv_appointmentDate.setText(appointmentDate.toString());
        }
        // ---

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

    public void openCalendar(View view) {
        Intent intent = new Intent(this, SelectDate_View.class);
        startActivity(intent);
    }
}
