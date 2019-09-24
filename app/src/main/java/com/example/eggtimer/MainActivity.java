package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.nio.channels.SeekableByteChannel;

public class MainActivity extends AppCompatActivity {

    SeekBar sbCounter;
    TextView tvTimer;
    Button btnGo;
    CountDownTimer countDownTimer;

    Boolean counter = false;

    public void updateTimer(int secondsLeft)
    {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9)
        {
            secondString = "0" + secondString;
        }

        tvTimer.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void resetTimer()
    {
        tvTimer.setText("0:30");
        sbCounter.setProgress(30);
        sbCounter.setEnabled(true);
        countDownTimer.cancel();
        btnGo.setText("GO!");
        counter = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sbCounter = findViewById(R.id.sbCounter);
        tvTimer = findViewById(R.id.tvTimer);
        btnGo = findViewById(R.id.btnGo);

        sbCounter.setMax(600);
        sbCounter.setProgress(30);

        sbCounter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter)
                {
                    resetTimer();
                }
                else {
                    counter = true;
                    sbCounter.setEnabled(false);
                    btnGo.setText("STOP!");

                    countDownTimer = new CountDownTimer(sbCounter.getProgress() * 1000 + 200, 1000) {
                        @Override
                        public void onTick(long l) {

                            updateTimer((int) l / 1000);

                        }

                        @Override
                        public void onFinish() {

                            updateTimer(0);
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                            mediaPlayer.start();
                            resetTimer();

                        }
                    }.start();

                }

            }
        });
    }
}
