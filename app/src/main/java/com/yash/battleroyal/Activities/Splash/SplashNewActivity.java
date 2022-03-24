package com.yash.battleroyal.Activities.Splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cuberto.liquid_swipe.LiquidPager;
import com.google.firebase.auth.FirebaseAuth;
import com.yash.battleroyal.Activities.LoginActivity;
import com.yash.battleroyal.Activities.MainActivity;
import com.yash.battleroyal.Activities.Splash.adapter.ViewPagerAdapter;
import com.yash.battleroyal.Activities.SplashActivity;
import com.yash.battleroyal.Activities.login.LoginNewActivity;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityRegisterBinding;
import com.yash.battleroyal.databinding.ActivitySplashNewBinding;

public class SplashNewActivity extends AppCompatActivity {

    ActivitySplashNewBinding binding;
    Animation animation;
    LiquidPager pager;
    ViewPagerAdapter adapter;

    private TextView textView19, textView18;
    FirebaseAuth auth;
    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        textView19 = findViewById(R.id.textView19);
        textView18 = findViewById(R.id.textView18);


        int currentVersionCode = getCurrentVersionCode();
        Log.d("myApp", String.valueOf(currentVersionCode));

        textView19.setText(String.valueOf(currentVersionCode));
        auth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(auth.getCurrentUser() != null) {
                    startActivity(new Intent(SplashNewActivity.this, MainActivity.class));
                    finish();
                }
//                else {
//                    startActivity(new Intent(SplashNewActivity.this, LoginNewActivity.class));
//                }
            }
        }, 3000);
//
//        textView18.setTranslationY(300);
//        textView19.setTranslationY(300);
//        textView18.setAlpha(v);
//        textView19.setAlpha(v);
        textView18.animate().translationY(1400).setDuration(1000).setStartDelay(2500);
        textView19.animate().translationY(1400).setDuration(1000).setStartDelay(2500);


        pager = findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),1);
        pager.setAdapter(adapter);

        animation = AnimationUtils.loadAnimation(this,R.anim.o_b);
        pager.startAnimation(animation);

        binding.img.animate().translationY(-5000).setDuration(1000).setStartDelay(2500);
        binding.logo.animate().translationY(2000).setDuration(1000).setStartDelay(2500);
        binding.appName.animate().translationY(2000).setDuration(1000).setStartDelay(2500);
        binding.icon.animate().translationY(1400).setDuration(1000).setStartDelay(2500);
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