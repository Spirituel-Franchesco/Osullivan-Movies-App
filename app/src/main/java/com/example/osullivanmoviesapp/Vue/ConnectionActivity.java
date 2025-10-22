package com.example.osullivanmoviesapp.Vue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.osullivanmoviesapp.Modele.DatabaseHelper;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.example.osullivanmoviesapp.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class ConnectionActivity extends AppCompatActivity {

    private EditText edNameLogin;
    private EditText edPasswordLogin;
    private Button RegisterNowbtn;
    private Button Loginbtn;

    private InterstitialAd mInterstitialAd; // pub interstitielle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);


        edNameLogin = findViewById(R.id.edNameLogin);
        edPasswordLogin = findViewById(R.id.edPasswordLogin);
        RegisterNowbtn = findViewById(R.id.RegisterBtnLogin);
        Loginbtn = findViewById(R.id.LoginBtn);

        // Pré-remplir le champ nom avec le dernier utilisateur enregistré en base
        DatabaseHelper dbHelper = new DatabaseHelper(ConnectionActivity.this);
        String lastName = dbHelper.getLastRegisteredUser();
        edNameLogin.setText(lastName);


        // Initialisation du SDK
        MobileAds.initialize(this, initializationStatus -> {});

        // Charger la publicité interstitielle
        loadInterstitialAd();

        RegisterNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConnectionActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });

//        Loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String name = edNameLogin.getText().toString();
//                String password = edPasswordLogin.getText().toString();
//
//                String savedName = prefs.getString("name", "");
//                String savedPassword = prefs.getString("password", "");
//
//                if (name.equals(savedName) && password.equals(savedPassword)) {
//                    Toast.makeText(ConnectionActivity.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();
//
////                    Intent i = new Intent(ConnectionActivity.this, MenuActivity.class);
////                    startActivity(i);
////                    finish();
//
//                    // Afficher la pub si elle est prête
//                    if (mInterstitialAd != null) {
//                        mInterstitialAd.show(ConnectionActivity.this);
//                    } else {
//                        // Si la pub n’est pas encore prête, aller directement au menu
//                        goToMenu();
//                    }
//                }
//                else if (!savedName.equals(name) && savedPassword.equals(password))
//                {
//                    Toast.makeText(ConnectionActivity.this, "User introuvable", Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    Toast.makeText(ConnectionActivity.this, "User et/ou password incorrect", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edNameLogin.getText().toString().trim();
                String password = edPasswordLogin.getText().toString().trim();

                DatabaseHelper dbHelper = new DatabaseHelper(ConnectionActivity.this);
//                String lastName = dbHelper.getLastRegisteredUser();
//                edNameLogin.setText(lastName);

                if (dbHelper.checkUser(name, password)) {
                    Toast.makeText(ConnectionActivity.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();

                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(ConnectionActivity.this);
                    } else {
                        goToMenu();
                    }
                }
                    else if (dbHelper.checkUserExists(name)) {
                        Toast.makeText(ConnectionActivity.this, "Mot de passe incorrect", Toast.LENGTH_LONG).show();


                    } else {
                    Toast.makeText(ConnectionActivity.this, "User introuvable", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(InterstitialAd interstitialAd) {
//                        super.onAdLoaded(interstitialAd);
                        mInterstitialAd = interstitialAd;

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
//                                super.onAdDismissedFullScreenContent();
                                goToMenu();
                                loadInterstitialAd(); // recharge la prochaine pub
                            }
                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError)
                            {
                                mInterstitialAd = null;
                                goToMenu();
                            }
                        });
                    }
                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError)
                        {
                            mInterstitialAd = null;
                        }
                    });
                }

    private void goToMenu() {
        Intent i = new Intent(ConnectionActivity.this, MenuActivity.class);
        startActivity(i);
    }
}