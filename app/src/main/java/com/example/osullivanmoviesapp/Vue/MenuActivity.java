package com.example.osullivanmoviesapp.Vue;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.osullivanmoviesapp.R;

public class MenuActivity extends AppCompatActivity {

    private Button btnExploreMovies;
    private Button btnQuiz;
    private Button btnContact;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnExploreMovies = findViewById(R.id.btnExploreMovies);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnContact = findViewById(R.id.btnContact);

        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, QuizActivity.class);
                startActivity(i);
            }
        });
    }
}