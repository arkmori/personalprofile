package com.example.personalprofile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * SplashActivity displays a centerpiece photo and subtext for exactly 5 seconds
 * before automatically transitioning to the MainActivity.
 */
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION = 5000; // 5000 ms = 5 seconds
    private static final long UPDATE_INTERVAL = 50;   // Update progress every 50 ms

    private Handler handler;
    private Runnable navigateRunnable;
    private Runnable progressRunnable;

    private ProgressBar progressBar;
    private TextView txtTimer;
    private long elapsedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.splashProgressBar);
        txtTimer = findViewById(R.id.txtSplashTimer);

        if (progressBar != null) {
            progressBar.setMax((int) SPLASH_DURATION);
            progressBar.setProgress(0);
        }

        handler = new Handler(Looper.getMainLooper());

        // Runnable to launch MainActivity after 5 seconds
        navigateRunnable = () -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            finish(); // Ensure user cannot return to splash screen on back press
        };

        // Runnable to update progress bar and text smoothly
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                elapsedTime += UPDATE_INTERVAL;
                if (progressBar != null) {
                    progressBar.setProgress((int) Math.min(elapsedTime, SPLASH_DURATION));
                }

                long remainingSeconds = Math.max(1, (SPLASH_DURATION - elapsedTime + 999) / 1000);
                if (txtTimer != null) {
                    txtTimer.setText(getString(R.string.splash_timer_msg, remainingSeconds));
                }

                if (elapsedTime < SPLASH_DURATION) {
                    handler.postDelayed(this, UPDATE_INTERVAL);
                }
            }
        };

        // Start countdown progress and set 5-second navigation timer
        handler.post(progressRunnable);
        handler.postDelayed(navigateRunnable, SPLASH_DURATION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Prevent leaks or execution if activity is destroyed early
        if (handler != null) {
            if (navigateRunnable != null) {
                handler.removeCallbacks(navigateRunnable);
            }
            if (progressRunnable != null) {
                handler.removeCallbacks(progressRunnable);
            }
        }
    }
}
