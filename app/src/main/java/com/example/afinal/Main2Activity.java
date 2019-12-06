package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    Button clear;


    Bundle logR;
    ArrayAdapter<String> arrayAdapter;
    ListView log;
    ArrayList logArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        LinearLayout logLay = findViewById(R.id.logLayout);

        logR = getIntent().getBundleExtra("test");
        logArray = logR.getStringArrayList("test");


        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, logArray);
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
