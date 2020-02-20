package com.example.sequnsorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageEvents;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.net.rtp.AudioStream;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SoundPool sp;
    int sound01;

    int streamSound01;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button button01 = findViewById(R.id.button01);
            final TextView text01 = findViewById(R.id.textView01);

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sp = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

            try {
                sound01 = sp.load(getAssets().openFd("Sound_01.wav"), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            button01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                text01.setText("Гудим");
                streamSound01 = sp.play(sound01, 1, 1, 1, 0, 1);

                }
            });



        }






    }
