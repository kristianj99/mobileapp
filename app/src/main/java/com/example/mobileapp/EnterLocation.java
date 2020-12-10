package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EnterLocation extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_location);

        Button btnContinue = findViewById(R.id.enterLoc_Continue);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick (View view) {
        if (view.getId() == R.id.enterLoc_Continue) {
            Intent intent = new Intent(getApplicationContext(), SatellitePasses.class);

            startActivity(intent);
        }
    }
}
