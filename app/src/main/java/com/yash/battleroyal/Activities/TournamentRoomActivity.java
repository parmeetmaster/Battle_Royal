package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityTournamentRoomBinding;

import static android.content.ContentValues.TAG;

public class TournamentRoomActivity extends AppCompatActivity {

    ActivityTournamentRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTournamentRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = FirebaseAuth.getInstance().getUid();





//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, getString(R.string.TournamentRoomAd), adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                mInterstitialAd = interstitialAd;
//                mInterstitialAd.show(TournamentRoomActivity.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//        });

        binding.restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TournamentRoomActivity.this, MainActivity.class));
                finishAffinity();
            }
        });

    }
}