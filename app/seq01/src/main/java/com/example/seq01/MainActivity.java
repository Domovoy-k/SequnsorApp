package com.example.seq01;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    AssetManager am;
    SoundPool sp;
    Handler h;
    MyTask mt;
    private int sound01, sound02, sound03;
    int streamSound;

    Button soundButton1, soundButton2, soundButton3, testButton;
    TextView soundText1, soundText2, soundText3, reactionText1, reactionText2, reactionText3, reactionText4;
    LinearLayout react;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sp = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(attributes)
                .build();

        am = getAssets();

        h = new Handler();

        soundButton1 = findViewById(R.id.soundButton1);
        soundButton2 = findViewById(R.id.soundButton2);
        soundButton3 = findViewById(R.id.soundButton3);
        testButton = findViewById(R.id.reaction_startButton);

        soundText1 = findViewById(R.id.firstSound_text);
        soundText2 = findViewById(R.id.secondSound_text);
        soundText3 = findViewById(R.id.thirdSound_text);
        reactionText1 = findViewById(R.id.reaction_text1);
        reactionText2 = findViewById(R.id.reaction_text2);
        reactionText3 = findViewById(R.id.reaction_text3);
        reactionText4 = findViewById(R.id.reaction_text4);

        react = findViewById(R.id.reaction_layout);

        sound01 = loadSound("Sound_01.wav");
        sound02 = loadSound("Sound_02.wav");
        sound03 = loadSound("Sound_03.wav");

        soundButton1.setOnClickListener(onClickListener);
        soundButton2.setOnClickListener(onClickListener);
        soundButton3.setOnClickListener(onClickListener);
        testButton.setOnClickListener(onClickListener);

        soundText1.setOnLongClickListener(onLongClickListener);
        soundText2.setOnLongClickListener(onLongClickListener);
        soundText3.setOnLongClickListener(onLongClickListener);

        reactionText1.setOnDragListener(onDragListener);
        reactionText2.setOnDragListener(onDragListener);
        reactionText3.setOnDragListener(onDragListener);
        reactionText4.setOnDragListener(onDragListener);

    }

    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onLongClick(View v) {
            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
            v.startDragAndDrop(clipData, dsb, v, 0);
            return true;
        }
    };

/*    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData clipData = ClipData.newPlainText("", "");
                View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
                v.startDrag(clipData, dsb, v, 0);
                return true;
            } else {
                return false;
            }
        }
    };
*/
    View.OnDragListener onDragListener = new View.OnDragListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();
            final View view = (View) event.getLocalState();

            switch (action) {
                case DragEvent.ACTION_DROP:
                    if (v.getId() == R.id.reaction_text1) {
                        if (view.getId() == R.id.firstSound_text) {
                            reactionText1.setText("Sound1");
                            } else if (view.getId() == R.id.secondSound_text) {
                            reactionText1.setText("Sound2");
                            } else if (view.getId() == R.id.thirdSound_text) {
                            reactionText1.setText("Sound3");
                            }
                        } else if (v.getId() == R.id.reaction_text2) {
                            if (view.getId() == R.id.firstSound_text) {
                                reactionText2.setText("Sound1");
                            } else if (view.getId() == R.id.secondSound_text) {
                                reactionText2.setText("Sound2");
                            } else if (view.getId() == R.id.thirdSound_text) {
                                reactionText2.setText("Sound3");
                            }
                        } else if (v.getId() == R.id.reaction_text3) {
                            if (view.getId() == R.id.firstSound_text) {
                                reactionText3.setText("Sound1");
                            } else if (view.getId() == R.id.secondSound_text) {
                                reactionText3.setText("Sound2");
                            } else if (view.getId() == R.id.thirdSound_text) {
                                reactionText3.setText("Sound3");
                            }
                        } else if (v.getId() == R.id.reaction_text4) {
                        if (view.getId() == R.id.firstSound_text) {
                            reactionText4.setText("Sound1");
                        } else if (view.getId() == R.id.secondSound_text) {
                            reactionText4.setText("Sound2");
                        } else if (view.getId() == R.id.thirdSound_text) {
                            reactionText4.setText("Sound3");
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
            }
            return true;
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.soundButton1:
                    playSound(sound01);
                    break;
                case R.id.soundButton2:
                    playSound(sound02);
                    break;
                case R.id.soundButton3:
                    playSound(sound03);
                    break;
                case R.id.reaction_startButton:
                    mt = new MyTask();
                    mt.execute();
            }
        }
    };

    public class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            for (int i = 0; i < 4; i++) {

                switch (i) {
                    case 0:
                        if (reactionText1.getText() == "Sound1") {
                            playSound(sound01);
                        } else if (reactionText1.getText() == "Sound2") {
                            playSound(sound02);
                        } else if (reactionText1.getText() == "Sound3") {
                            playSound(sound03);
                        }
                        break;
                    case 1:
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (reactionText2.getText() == "Sound1") {
                            playSound(sound01);
                        } else if (reactionText2.getText() == "Sound2") {
                            playSound(sound02);
                        } else if (reactionText2.getText() == "Sound3") {
                            playSound(sound03);
                        }
                        break;
                    case 2:
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (reactionText3.getText() == "Sound1") {
                            playSound(sound01);
                        } else if (reactionText3.getText() == "Sound2") {
                            playSound(sound02);
                        } else if (reactionText3.getText() == "Sound3") {
                            playSound(sound03);
                        }
                        break;
                    case 3:
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (reactionText4.getText() == "Sound1") {
                            playSound(sound01);
                        } else if (reactionText4.getText() == "Sound2") {
                            playSound(sound02);
                        } else if (reactionText4.getText() == "Sound3") {
                            playSound(sound03);
                        }
                        break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "End of playing", Toast.LENGTH_LONG).show();
        }
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

    private void playSound (int sound) {
        if (sound > 0) {
            streamSound = sp.play(sound,1,1,1,0, 1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sp.release();
        sp = null;
    }
}
