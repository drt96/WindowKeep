package com.windowkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class CreateQuote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quote);

        Toast toast = Toast.makeText(getApplicationContext(),"This is the create quote screen", Toast.LENGTH_LONG);
        toast.show();
    }
}
