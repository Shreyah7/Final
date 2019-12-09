package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.scwang.wave.MultiWaveHeader;

import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextToSpeech convert;
    EditText input;
    SeekBar pitchBar;
    SeekBar speedBar;
    Button enter;
    Button next;
    MultiWaveHeader waveFoot;
    String toSpeak;

    int buttonClickCount = 0;
    ArrayList<String> logRecords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logRecords.add("test");

        enter = findViewById(R.id.enter);


        convert = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    convert.setLanguage(Locale.US);
                    enter.setEnabled(true);
                }
            }
        });

        input = findViewById(R.id.type);
        pitchBar = findViewById(R.id.pbar);
        speedBar = findViewById(R.id.sbar);
        waveFoot = findViewById(R.id.wfoot);
        waveFoot.setProgress(1);
        waveFoot.setWaveHeight(50);
        waveFoot.setStartColor(Color.CYAN);
        waveFoot.setCloseColor(Color.RED);
        waveFoot.setGradientAngle(45);
        waveFoot.setVelocity(10);

        enter.setOnClickListener(v -> {
            speak();
            addToLog(toSpeak);
        });

        next = findViewById(R.id.next);
        next.setOnClickListener(v -> {
            nextPage();
        });

        //LinearLayout logLay = findViewById(R.id.logLayout);
        //View logChunk = getLayoutInflater().inflate(R.layout.chunk_log, logLay,false);
        //logLay.addView(logChunk);
    }

    public void speak() {
        toSpeak = input.getText().toString();
        float pitchF = (float) pitchBar.getProgress() / 50;
        if (pitchF < 0.1) {
            pitchF = (float) 0.1;
        }
        float speedF = (float) speedBar.getProgress() / 50;
        if (speedF < 0.1) {
            speedF = (float) 0.1;
        }
        convert.setPitch(pitchF);
        convert.setSpeechRate(speedF);
        waveFoot.setVelocity(speedF + 50);

        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_LONG).show();
        convert.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void addToLog(String word) {
        float pitchF = (float) pitchBar.getProgress() / 50;
        if (pitchF < 0.1) {
            pitchF = (float) 0.1;
        }
        float speedF = (float) speedBar.getProgress() / 50;
        if (speedF < 0.1) {
            speedF = (float) 0.1;
        }

        buttonClickCount++;
        String count = Integer.toString(buttonClickCount);
        String pitchLevel = Float.toString(pitchF);
        String speedLevel = Float.toString(speedF);
        String data = "Entry: " + count + " | Pitch: "
                + pitchLevel + " | Speed: " + speedLevel + " | Text: " + word;
        logRecords.add(data);
    }

    @Override
    public void onPause() {
        if (convert != null) {
            convert.stop();
            convert.shutdown();
            waveFoot.setVelocity(1);
        }
        super.onPause();
    }

    public void nextPage() {
        Bundle logs = new Bundle();
        logs.putStringArrayList("test",logRecords);

        Intent next = new Intent(this, Main2Activity.class);
        if (logRecords != null) {
          next.putExtra("test", logs);
        }
        startActivity(next);
    }
}
