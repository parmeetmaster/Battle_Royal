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
import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.Model.TournamentRegistrationModel;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityTournamentRegistrationBinding;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class TournamentRegistrationActivity extends AppCompatActivity {

    ActivityTournamentRegistrationBinding binding;
    FirebaseFirestore database;
    User user;
    CrewModel crewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTournamentRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = FirebaseAuth.getInstance().getUid();


//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, getString(R.string.TournamentRegAd), adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                mInterstitialAd = interstitialAd;
//                mInterstitialAd.show(TournamentRegistrationActivity.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//        });

        database = FirebaseFirestore.getInstance();


        String tournamentId = getIntent().getStringExtra("tournamentId");

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


        binding.fixtureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TournamentRegistrationActivity.this, TournamentFixturesActivity.class);
                intent.putExtra("tournamentId",tournamentId);
                startActivity(intent);
            }
        });


        binding.slotListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TournamentRegistrationActivity.this, SlotListActivity.class);
                intent.putExtra("tournamentId",tournamentId);
                startActivity(intent);
            }
        });

        binding.resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TournamentRegistrationActivity.this, TournamentResultActivity.class);
                intent.putExtra("tournamentId",tournamentId);
                startActivity(intent);
            }
        });


        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.collection("users")
                        .document(FirebaseAuth.getInstance().getUid())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        database
                                .collection("tournament")
                                .document(tournamentId)
                                .collection("registration")
                                .document(uid)
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {

                                    if (task.getResult().exists()) {
                                        Toast.makeText(TournamentRegistrationActivity.this, "You have Already Registered", Toast.LENGTH_SHORT).show();

                                    }
                                    else if (crewModel == null){
                                        Toast.makeText(TournamentRegistrationActivity.this, "Create Your Crew Before Registering", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        user = documentSnapshot.toObject(User.class);
                                        String uid = FirebaseAuth.getInstance().getUid();
                                        TournamentRegistrationModel registrationModel = new TournamentRegistrationModel(uid, user.getCharacterID(), user.getName(), crewModel.getCrewName());
                                        database
                                                .collection("tournament")
                                                .document(tournamentId)
                                                .collection("registration")
                                                .document(uid)
                                                .set(registrationModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(TournamentRegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(TournamentRegistrationActivity.this, TournamentRoomActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
