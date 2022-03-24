package com.yash.battleroyal.Activities.Organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yash.battleroyal.Activities.AdminActivity;
import com.yash.battleroyal.Activities.ContestJoinedPlayersActivity;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityContestJoinedPlayersBinding;
import com.yash.battleroyal.databinding.ActivityContestRegisterOrgBinding;

public class ContestRegisterOrgActivity extends AppCompatActivity {

    ActivityContestRegisterOrgBinding binding;

    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;
    User user;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContestRegisterOrgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        String uid = FirebaseAuth.getInstance().getUid();

        String gameId = getIntent().getStringExtra("gameId");
        String contestId = getIntent().getStringExtra("contestId");

        binding.joinPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ContestRegisterOrgActivity.this, ContestJoinedPlayersActivity.class);
                intent.putExtra("contestId", contestId);
                intent.putExtra("gameId", gameId);
                startActivity(intent);
            }
        });

        binding.idPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ContestRegisterOrgActivity.this, NewIdPassOrgPaidActivity.class);
                intent.putExtra("contestId", contestId);
                intent.putExtra("gameId", gameId);
                startActivity(intent);
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference productIdRef = database.collection("game").document(gameId).collection("contest").document(contestId);
                productIdRef.delete().addOnSuccessListener(aVoid -> Snackbar.make(findViewById(android.R.id.content), "Contest deleted!", Snackbar.LENGTH_LONG).show());
                DocumentReference productId2Ref = database.collection("game").document(gameId).collection("contest").document(contestId).collection("registration").document();
                productId2Ref.delete().addOnSuccessListener(aVoid -> Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG).show());
                DocumentReference productId3Ref = database.collection("game").document(gameId).collection("contest").document(contestId).collection("room").document();
                productId3Ref.delete().addOnSuccessListener(aVoid -> Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG).show());
                DocumentReference productId1Ref = database.collection("organizer").document(uid).collection(gameId).document(contestId);
                productId1Ref.delete().addOnSuccessListener(aVoid -> Snackbar.make(findViewById(android.R.id.content), "Match deleted!", Snackbar.LENGTH_LONG).show());


            }
        });


    }
}