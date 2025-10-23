package com.example.osullivanmoviesapp.Vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.osullivanmoviesapp.R;

public class ContactActivity extends AppCompatActivity {

    private MapActivity MapActivity;
    private Button btnSMS, btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        this.MapActivity = (MapActivity)
                fragmentManager.findFragmentById(R.id.fragment_map);

        btnSMS = findViewById(R.id.btnSMS);
        btnCall = findViewById(R.id.btnCall);

        //programmer le bouton pour  appeler
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri number = Uri.parse("tel:481 529 3355" );
                Intent callIntent = new Intent(Intent.ACTION_DIAL,number);
                startActivity(callIntent);
            }
        });

        //programmer le boutonSMS vers le numéro de téléphone
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //r.le numéro de téléphone saisie
                String smsNumber=String.format("smsto: %s","tel:481 529 3355");
                //R.le texte du sms
                String sms ="";
                //créer l'intent d'envoie de sms
                Intent smsIntent =new Intent(Intent.ACTION_SENDTO);
                //passer lr numéro de téléphone vers cet intent
                smsIntent.setData(Uri.parse(smsNumber));
                //Passer le texte du sms vers cet intent
                smsIntent.putExtra("sms_body",sms);
                //Démarrer l'intent
                startActivity(smsIntent);
            }
        });
    }
}