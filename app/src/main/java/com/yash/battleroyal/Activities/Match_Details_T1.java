package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.Model.RegistrationRequest;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityMatchDetailsT1Binding;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;


public class Match_Details_T1 extends AppCompatActivity {


    ActivityMatchDetailsT1Binding binding;

    FirebaseFirestore database;
    User user;
    CrewModel crewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMatchDetailsT1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String uid = FirebaseAuth.getInstance().getUid();


//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, getString(R.string.RoomAd), adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                mInterstitialAd = interstitialAd;
//                mInterstitialAd.show(Match_Details_T1.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//        });
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
                .collection("T1")
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



        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();

                //Getting Registration Details
                database
                        .collection("T1")
                        .document(matchId)
                        .collection("registration")
                        .document(uid)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().exists()) {
                                Toast.makeText(Match_Details_T1.this, "You have Already Registered", Toast.LENGTH_SHORT).show();

                            }

                            else if (crewModel == null){
                                Toast.makeText(Match_Details_T1.this, "Create Your Crew Before Registering", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //Registering the User

                                database
                                        .collection("T1")
                                        .document(matchId)
                                        .collection("registration")
                                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        int counter = (int) queryDocumentSnapshots.size();

                                        //Checking Conditions

                                        if ((counter < 101) && (user.getCoins() >= 0)&& (user.getTierPoint() >=121)) {
                                            String uid = FirebaseAuth.getInstance().getUid();
                                            RegistrationRequest request = new RegistrationRequest(uid, user.getName(), user.getCharacterID(), crewModel.getCrewName());
                                            database
                                                    .collection("T1")
                                                    .document(matchId)
                                                    .collection("registration")
                                                    .document(uid)
                                                    .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Match_Details_T1.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Match_Details_T1.this, RoomActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                        } else {
                                            Toast.makeText(Match_Details_T1.this, "Registration full Or Not Eligible for T1", Toast.LENGTH_SHORT).show();
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
                        .collection("T1")
                        .document(matchId)
                        .collection("registration")
                        .document(uid)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().exists()) {
                                Intent intent = new Intent(Match_Details_T1.this, IdPassT1Activity.class);
                                intent.putExtra("matchId",matchId);
                                startActivity(intent);

                            } else {
                                Toast.makeText(Match_Details_T1.this, "You have Not Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        binding.slotListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Match_Details_T1.this, SlotListT1Activity.class);
                intent.putExtra("matchId",matchId);
                startActivity(intent);
            }
        });

        binding.resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Match_Details_T1.this, MatchResultT1Activity.class);
                intent.putExtra("matchId",matchId);
                startActivity(intent);
            }
        });


    }
}