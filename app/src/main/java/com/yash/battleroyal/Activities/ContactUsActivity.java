package com.yash.battleroyal.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yash.battleroyal.R;

public class ContactUsActivity extends AppCompatActivity {

    TextView whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        whatsapp = findViewById(R.id.whatsapp);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toNumber = "919993512187"; //Number without with country code and without '+' prifix
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" + toNumber + "?body=" + ""));
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });
    }
}