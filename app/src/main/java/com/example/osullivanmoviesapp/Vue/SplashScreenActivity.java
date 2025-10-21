package com.example.osullivanmoviesapp.Vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.osullivanmoviesapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo_icon);
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.side_slide));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, ConnectionActivity.class);
                startActivity(i);
                finish();
            }
        }, 4000);
    }
}