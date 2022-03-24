package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityIdPassContestBinding;
import com.yash.battleroyal.databinding.ActivityIdPassT1Binding;

import static android.content.ContentValues.TAG;

public class IdPassContestActivity extends AppCompatActivity {

    TextView option_3, option_4;

    ActivityIdPassContestBinding binding;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIdPassContestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = FirebaseAuth.getInstance().getUid();

//        AudienceNetworkAds.initialize(this);
//
//      //  interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.RoomAd));
//        // Create listeners for the Interstitial Ad
//        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
//            @Override
//            public void onInterstitialDisplayed(Ad ad) {
//                // Interstitial ad displayed callback
//                Log.e(TAG, "Interstitial ad displayed.");
//            }
//
//            @Override
//            public void onInterstitialDismissed(Ad ad) {
//                // Interstitial dismissed callback
//                Log.e(TAG, "Interstitial ad dismissed.");
//            }
//
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                // Ad error callback
//                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
//            }
//
//
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//                // Interstitial ad is loaded and ready to be displayed
//                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
//                // Show the ad
//                interstitialAd.show();
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//                // Ad clicked callback
//                Log.d(TAG, "Interstitial ad clicked!");
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//                // Ad impression logged callback
//                Log.d(TAG, "Interstitial ad impression logged!");
//            }
//        };
//
//        // For auto play video ads, it's recommended to load the ad
//        // at least 30 seconds before it is shown
//        interstitialAd.loadAd(
//                interstitialAd.buildLoadAdConfig()
//                        .withAdListener(interstitialAdListener)
//                        .build());

//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, getString(R.string.RoomAd), adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                mInterstitialAd = interstitialAd;
//                mInterstitialAd.show(IdPassContestActivity.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//        });

        option_3 = findViewById(R.id.option_3);
        option_4 = findViewById(R.id.option_4);

        database = FirebaseFirestore.getInstance();

        final String gameId = getIntent().getStringExtra("gameId");
        final String contestId = getIntent().getStringExtra("contestId");

        database.collection("game")
                .document(gameId)
                .collection("contest")
                .document(contestId)
                .collection("room")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        option_3.setText(documentSnapshot.getString("roomID"));
                        option_4.setText(documentSnapshot.getString("roomPass"));
                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(IdPassContestActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}