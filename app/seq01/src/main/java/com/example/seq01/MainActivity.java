package com.example.seq01;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    AssetManager am;
    SoundPool sp;
    int sound01, sound02, sound03;
    int streamSound;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button soundButton1 = findViewById(R.id.soundButton1);
        Button soundButton2 = findViewById(R.id.soundButton2);
        Button soundButton3 = findViewById(R.id.soundButton3);
        Button testButton = findViewById(R.id.reaction_startButton);

        TextView soundText1 = findViewById(R.id.firstSound_text);
        TextView soundText2 = findViewById(R.id.secondSound_text);
        TextView soundText3 = findViewById(R.id.thirdSound_text);
        TextView reactionText1 = findViewById(R.id.reaction_text1);
        TextView reactionText2 = findViewById(R.id.reaction_text2);
        TextView reactionText3 = findViewById(R.id.reaction_text3);
        TextView reactionText4 = findViewById(R.id.reaction_text4);

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sp = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(1)
                .build();

        sound01 = loadSound("Sound_01.wav");


    }

    private int loadSound (String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = am.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return sp.load(afd, 1);
    }

    private  int playSound (int sound) {
        if (sound > 0) {
            streamSound = sp.play(1,1,1,1,0, 1);
        }
        return streamSound;
    }
}
