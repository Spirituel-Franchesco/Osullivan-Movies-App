package com.example.osullivanmoviesapp.Vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.osullivanmoviesapp.R;

public class ConnectionActivity extends AppCompatActivity {

    private Button RegisterNowbtn;
    private Button Loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        RegisterNowbtn = findViewById(R.id.RegisterBtnLogin);
        Loginbtn = findViewById(R.id.LoginBtn);

        RegisterNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConnectionActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConnectionActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
}