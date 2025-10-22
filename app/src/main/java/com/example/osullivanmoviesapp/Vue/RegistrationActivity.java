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

import com.example.osullivanmoviesapp.Modele.DatabaseHelper;
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
                String name = edNameRegister.getText().toString().trim();
                String email = edEmailRegister.getText().toString().trim();
                String password = edPasswordRegister.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
                    return;
                }

                DatabaseHelper dbHelper = new DatabaseHelper(RegistrationActivity.this);

                boolean success = dbHelper.registerUser(name, email, password);
                if (success) {
                    Toast.makeText(RegistrationActivity.this, "Inscription réussie !", Toast.LENGTH_LONG).show();

                    // Après inscription, retourne à la page de connexion
                    Intent i = new Intent(RegistrationActivity.this, ConnectionActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Cet utilisateur existe déjà", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}