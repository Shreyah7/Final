package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class OpenScreen extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen);
        start = findViewById(R.id.start);
        start.setOnClickListener(v -> {
            Intent next = new Intent(this, MainActivity.class);
            startActivity(next);

        });
    }

}
