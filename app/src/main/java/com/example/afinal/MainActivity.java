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

    int buttonClickCount = 0;
    List<String> entryNum = new ArrayList<>();
    List<String> speed = new ArrayList<>();
    List<String> pitch = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entryNum);
    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, speed);
    ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pitch); 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            addToLog();
        });

        next = findViewById(R.id.next);
        next.setOnClickListener(v ->  {
            nextPage();
        });
    }

    public void speak() {
        String toSpeak = input.getText().toString();
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

    public void addToLog() {
        float pitchF = (float) pitchBar.getProgress() / 50;
        if (pitchF < 0.1) {
            pitchF = (float) 0.1;
        }
        float speedF = (float) speedBar.getProgress() / 50;
        if (speedF < 0.1) {
            speedF = (float) 0.1;
        }

        ListView log = findViewById(R.id.Log);

        buttonClickCount++;
        String count = Integer.toString(buttonClickCount);
        entryNum.add(count);

        String pitchLevel = Float.toString(pitchF);
        pitch.add(pitchLevel);

        String speedLevel = Float.toString(speedF);
        speed.add(speedLevel);



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
        Intent next = new Intent(this, Main2Activity.class);
        startActivity(next);
    }
}
