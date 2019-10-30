package com.windowkeep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Hello everyone from Daniel");
        System.out.println("Hello back");
        System.out.println("Tanner says hello");
        System.out.println("Aaron says hello");
    }
}
