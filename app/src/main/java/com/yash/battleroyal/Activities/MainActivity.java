package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.yash.battleroyal.Fragments.ContestFragment;
import com.yash.battleroyal.Fragments.DetailsFragment;
import com.yash.battleroyal.Fragments.HomeFragment;
import com.yash.battleroyal.R;
import com.yash.battleroyal.Fragments.SettingsFragment;
import com.yash.battleroyal.databinding.ActivityMainBinding;
import com.google.firebase.messaging.FirebaseMessaging;
import com.iammert.library.readablebottombar.ReadableBottomBar;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseRemoteConfig remoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int currentVersionCode = getCurrentVersionCode();
        Log.d("myApp", String.valueOf(currentVersionCode));

        remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(5)
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);

        remoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            final String new_version_code = remoteConfig.getString("new_version_code");
                            if (Integer.parseInt(new_version_code) > getCurrentVersionCode()) {
                                showUpdateDialog();
                            }
                        }
                    }
                });





        FirebaseMessaging.getInstance().subscribeToTopic("notification");

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//                Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
//                for (String adapterClass : statusMap.keySet()) {
//                    AdapterStatus status = statusMap.get(adapterClass);
//                    Log.d("MyApp", String.format(
//                            "Adapter name: %s, Description: %s, Latency: %d",
//                            adapterClass, status.getDescription(), status.getLatency()));
//                }
//            }
//        });
//
//        AdRequest adRequest = new AdRequest.Builder().build();

        //MediationTestSuite.launch(MainActivity.this);


        FragmentTransaction homeTrans = getSupportFragmentManager().beginTransaction();
        homeTrans.replace(R.id.content, new HomeFragment());
        homeTrans.commit();

        binding.bottomNavigationView.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        transaction.replace(R.id.content, new HomeFragment());
                        break;
                    case 1:
                        transaction.replace(R.id.content, new ContestFragment());
                        break;
                    case 2:
                        transaction.replace(R.id.content, new DetailsFragment());
                        break;
                    case 3:
                        transaction.replace(R.id.content, new SettingsFragment());
                        break;
                }
                transaction.commit();
            }
        });
    }

    private void showUpdateDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New Update Available.. Latest Version is:"+String.valueOf(getCurrentVersionCode()+1))
                .setMessage("Please Update The App In Order to Continue! Make Sure to clear browser cache to avoid installing previous version again. Current Version:"+String.valueOf(getCurrentVersionCode()))
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.brgp.in/battle-royal.apk")));
                        }catch (Exception e){
                            Toast.makeText(MainActivity.this, "Something Went Wrong. Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
        dialog.setCancelable(false);
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