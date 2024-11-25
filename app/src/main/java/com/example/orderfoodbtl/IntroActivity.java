package com.example.orderfoodbtl;

import android.content.Intent;
import android.graphics.Insets;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class IntroActivity extends AppCompatActivity {
    SeekBar sb;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);

        sb = (SeekBar) findViewById(R.id.sb1);
        CountDownTimer countDownTimer = new CountDownTimer(60000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                int number;
                Random random = new Random();
                number = random.nextInt(30);
                sb.setProgress(sb.getProgress() + number);
                if (sb.getProgress() >= sb.getMax() && flag == true) {
                    Intent intent = new Intent(IntroActivity.this, SignInActivity.class);
                    startActivity(intent);
                    flag = false;
                    finish();
                }
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
        sb.setEnabled(false);
        sb.setThumb(null);
    }
}
