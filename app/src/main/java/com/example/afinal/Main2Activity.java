package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    Button clear;


    ArrayList<String> logR = getIntent().getStringArrayListExtra("test");
    ArrayAdapter<String> arrayAdapter;
    ListView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, logR);
        log = (ListView) findViewById(R.id.Log);
        if(log != null) {
            log.setAdapter(arrayAdapter);
        }

        clear = findViewById(R.id.clearButton);

        if (log != null) {
            clear.setOnClickListener(v -> {
                clearLog();
            });
        }

        arrayAdapter.notifyDataSetChanged();
    }

    public void clearLog() {
        logR.clear();
        arrayAdapter.notifyDataSetChanged();
    }
}
