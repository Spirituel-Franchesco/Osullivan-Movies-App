package com.example.osullivanmoviesapp.Vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.osullivanmoviesapp.R;

public class ConnectionActivity extends AppCompatActivity {

    private EditText edNameLogin;
    private EditText edPasswordLogin;
    private Button RegisterNowbtn;
    private Button Loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        edNameLogin = findViewById(R.id.edNameLogin);
        edPasswordLogin = findViewById(R.id.edPasswordLogin);
        RegisterNowbtn = findViewById(R.id.RegisterBtnLogin);
        Loginbtn = findViewById(R.id.LoginBtn);

        // Pré-remplir le champ nom avec le dernier utilisateur
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String lastName = prefs.getString("name", "");
        edNameLogin.setText(lastName);

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

                String name = edNameLogin.getText().toString();
                String password = edPasswordLogin.getText().toString();

                String savedName = prefs.getString("name", "");
                String savedPassword = prefs.getString("password", "");

                if (name.equals(savedName) && password.equals(savedPassword)) {
                    Toast.makeText(ConnectionActivity.this, "Connexion réussie !", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(ConnectionActivity.this, MenuActivity.class);
                    startActivity(i);
                    finish();
                }
                else if (!savedName.equals(name) && savedPassword.equals(password))
                {
                    Toast.makeText(ConnectionActivity.this, "User introuvable", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ConnectionActivity.this, "User et/ou password incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}