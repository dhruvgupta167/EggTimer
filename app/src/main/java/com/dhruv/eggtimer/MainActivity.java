package com.dhruv.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;
    Button goButton;


    boolean timerActive = false;

    public String convert (int seconds) {

        String minutesPlusSeconds = Integer.toString(seconds/60) + ":" ;

        String secondString = Integer.toString(seconds%60);

        if (secondString.length() == 1) {
            secondString = "0" + secondString;
        }

        minutesPlusSeconds = minutesPlusSeconds + secondString;

        return minutesPlusSeconds;
    }

    public void onClick (View view) {

        CountDownTimer countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (timerActive) {
                    timerTextView.setText(convert((int) millisUntilFinished/1000));
                    timerSeekBar.setEnabled(false);
                } else {
                    cancel();

                    timerSeekBar.setEnabled(true);
                    goButton.setText("GO!");
                    timerSeekBar.setProgress( (int) millisUntilFinished/1000);
                    timerTextView.setText(convert(timerSeekBar.getProgress()));
                }
            }

            @Override
            public void onFinish() {

                MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                mplayer.start();

                timerActive = false;
                timerSeekBar.setEnabled(true);
                goButton.setText("GO!");
                timerSeekBar.setProgress(30);
                timerTextView.setText(convert(timerSeekBar.getProgress()));
            }
        };

        if (!timerActive) {
            timerActive = true;
            goButton.setText("STOP");
            timerSeekBar.setEnabled(false);
            countDownTimer.start();

        } else {

            timerActive = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.timerCountDown);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        goButton = (Button) findViewById(R.id.button);

        int maxSeconds = 300;
        int startingSeconds = 30;

        timerSeekBar.setMax(maxSeconds);
        timerSeekBar.setProgress(startingSeconds);

        timerTextView.setText(convert(startingSeconds));

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 1;

                if (progress < min) {
                    timerSeekBar.setProgress(min);
                }

                timerTextView.setText(convert(timerSeekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
