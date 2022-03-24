package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Adapter.TournamentAdapter;
import com.yash.battleroyal.Model.TournamentModel;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityTournamentDetailsBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TournamentActivity extends AppCompatActivity {

    ActivityTournamentDetailsBinding binding;
    FirebaseFirestore database;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTournamentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = FirebaseAuth.getInstance().getUid();


//
//        AudienceNetworkAds.initialize(this);
//
//        interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.TournamentDetailsAd));
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
//        InterstitialAd.load(this, getString(R.string.TournamentDetailsAd), adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                mInterstitialAd = interstitialAd;
//                mInterstitialAd.show(TournamentActivity.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//        });

        database = FirebaseFirestore.getInstance();

        final ArrayList<TournamentModel> tournamentModels = new ArrayList<>();

        final TournamentAdapter adapter = new TournamentAdapter(this, tournamentModels);

        database.collection("tournament")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            tournamentModels.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                TournamentModel model = snapshot.toObject(TournamentModel.class);
                                model.setTournamentId(snapshot.getId());
                                tournamentModels.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });


        binding.tournamentList.setLayoutManager(new LinearLayoutManager(this));
        binding.tournamentList.setAdapter(adapter);
//
//        binding.slotListBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TournamentDetailsActivity.this, SlotListActivity.class);
//                startActivity(intent);
//            }
//        });






    }
}