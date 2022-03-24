package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.Model.RegistrationRequest;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityMatchDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;
import static android.icu.text.TimeZoneFormat.Style.ZONE_ID;
import static android.provider.UserDictionary.Words.APP_ID;

public class Match_Details extends AppCompatActivity {


    ActivityMatchDetailsBinding binding;

    FirebaseFirestore database;
    User user;
    CrewModel crewModel;

   // private InterstitialAd mInterstitialAd;
//    private com.facebook.ads.InterstitialAd interstitialAd;


//    final private String APP_ID = "app34ed12990b584cd389";
//    final private String ZONE_ID = "vz5d20fd9f6a9c465bb8";
//    final private String TAG = "AdColonyDemo";
//    private AdColonyInterstitial ad;
//    private AdColonyInterstitialListener listener;
//    private AdColonyAdOptions adOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = FirebaseAuth.getInstance().getUid();

//        AdColonyAppOptions appOptions = new AdColonyAppOptions()
//                .setUserID(uid)
//                .setKeepScreenOn(true);
//
//        // Configure AdColony in your launching Activity's onCreate() method so that cached ads can
//        // be available as soon as possible.
//        AdColony.configure(this, APP_ID, ZONE_ID);
//
//        // Ad specific options to be sent with request
//        adOptions = new AdColonyAdOptions();
//
//        // Set up listener for interstitial ad callbacks. You only need to implement the callbacks
//        // that you care about. The only required callback is onRequestFilled, as this is the only
//        // way to get an ad object.
//        listener = new AdColonyInterstitialListener() {
//            @Override
//            public void onRequestFilled(AdColonyInterstitial adColonyInterstitial) {
//                Log.d(TAG, "onRequestFilled");
//                Match_Details.this.ad = adColonyInterstitial;
//                ad.show();
//            }
//
//            @Override
//            public void onRequestNotFilled(AdColonyZone zone) {
//                Log.d(TAG, "NotonRequestFilled");
//            }
//        };
//        AdColony.requestInterstitial(ZONE_ID, listener, adOptions);

//        AudienceNetworkAds.initialize(this);
//
//        interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.RoomAd));
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
//                Log.e("inad", "loaded");
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
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        super.onAdLoaded(interstitialAd);
//                        mInterstitialAd = interstitialAd;
//                        mInterstitialAd.show(Match_Details.this);
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        super.onAdFailedToLoad(loadAdError);
//                    }
//                });
              //  binding.adView.loadAd(adRequest);


       database = FirebaseFirestore.getInstance();
        String matchId = getIntent().getStringExtra("matchId");

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                binding.currentCoins.setText(String.valueOf(user.getCoins()));

                //binding.currentCoins.setText(user.getCoins() + "");

            }
        });

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Crew")
                .document("CrewDetails")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                crewModel = documentSnapshot.toObject(CrewModel.class);
            }
        });


        database
                .collection("T3")
                .document(matchId)
                .collection("registration")
              .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int counter = (int) queryDocumentSnapshots.size();

                //Convert counter to string
                String userCounter = String.valueOf(counter);

                //Showing the user counter in the textview
                binding.registeredUsers.setText(userCounter);
            }
        });

        binding.currentCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Match_Details.this, PaytmActivity.class);
                startActivity(intent);
            }
        });







        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();

                //Getting Registration Details
                database
                        .collection("T3")
                        .document(matchId)
                        .collection("registration")
                        .document(uid)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().exists()) {
                                Toast.makeText(Match_Details.this, "You have Already Registered", Toast.LENGTH_SHORT).show();

                            }
                            else if (crewModel == null){
                                Toast.makeText(Match_Details.this, "Create Your Crew Before Registering", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //Registering the User

                                database
                                        .collection("T3")
                                        .document(matchId)
                                        .collection("registration")
                                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        int counter = (int) queryDocumentSnapshots.size();

                                        //Checking Conditions

                                        if ((counter < 101) && (user.getCoins() >= 0)) {
                                            String uid = FirebaseAuth.getInstance().getUid();
                                            RegistrationRequest request = new RegistrationRequest(uid,user.getName(),user.getCharacterID(), crewModel.getCrewName() );
                                            database
                                                    .collection("T3")
                                                    .document(matchId)
                                                    .collection("registration")
                                                    .document(uid)
                                                    .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Match_Details.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Match_Details.this, RoomActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                        } else {
                                            Toast.makeText(Match_Details.this, "Registration full", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });

        binding.idPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();

                //Getting Registration Details
                database
                        .collection("T3")
                        .document(matchId)
                        .collection("registration")
                        .document(uid)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().exists()) {
                                Intent intent = new Intent(Match_Details.this, IdPassActivity.class);
                                intent.putExtra("matchId",matchId);
                                startActivity(intent);

                            } else {
                                Toast.makeText(Match_Details.this, "You have Not Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        binding.slotListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Match_Details.this, MatchSlotListActivity.class);
                intent.putExtra("matchId",matchId);
                startActivity(intent);
            }
        });

        binding.resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Match_Details.this, MatchResultActivity.class);
                intent.putExtra("matchId",matchId);
                startActivity(intent);
            }
        });

    }
}