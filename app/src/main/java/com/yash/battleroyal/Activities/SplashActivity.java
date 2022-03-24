package com.yash.battleroyal.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.yash.battleroyal.R;

public class SplashActivity extends AppCompatActivity {

    private TextView textView19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView19 = findViewById(R.id.textView19);


        int currentVersionCode = getCurrentVersionCode();
        Log.d("myApp", String.valueOf(currentVersionCode));

        textView19.setText(String.valueOf(currentVersionCode));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }, 1500);
    }

    private int getCurrentVersionCode(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        }catch (Exception e){
            Log.d("myApp", e.getMessage());
        }
        return packageInfo.versionCode;
    }

}