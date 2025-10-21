package com.example.osullivanmoviesapp.Vue;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.osullivanmoviesapp.R;

import java.security.PrivateKey;

public class RegistrationActivity extends AppCompatActivity {

    private EditText edNameRegister;
    private EditText edPasswordRegister;
    private EditText edEmailRegister;
    private Button btnRegister;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edNameRegister = findViewById(R.id.edNameRegister);
        edPasswordRegister = findViewById(R.id.edPasswordRegister);
        edEmailRegister = findViewById(R.id.edEmailRegister);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edNameRegister.getText().toString();
                String password = edPasswordRegister.getText().toString();
                String email = edEmailRegister.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(RegistrationActivity.this, "Crédentials invalides", Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();

                    Toast.makeText(RegistrationActivity.this, "Inscription réussie !", Toast.LENGTH_LONG).show();
                }
                Intent i = new Intent(RegistrationActivity.this, ConnectionActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}