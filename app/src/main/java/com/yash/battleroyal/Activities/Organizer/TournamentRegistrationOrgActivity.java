package com.yash.battleroyal.Activities.Organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityOrgMatchBinding;
import com.yash.battleroyal.databinding.ActivityTournamentRegistrationOrgBinding;

public class TournamentRegistrationOrgActivity extends AppCompatActivity {

    ActivityTournamentRegistrationOrgBinding binding;

    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;
    User user;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTournamentRegistrationOrgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        String uid = FirebaseAuth.getInstance().getUid();
        final String tournamentId = getIntent().getStringExtra("tournamentId");

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TournamentRegistrationOrgActivity.this, TournamentJoinedOrgActivity.class);
                intent.putExtra("tournamentId", tournamentId);
                startActivity(intent);
            }
        });

        binding.fixtureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TournamentRegistrationOrgActivity.this, TournamentIdResultOrgActivity.class);
                intent.putExtra("tournamentId", tournamentId);
                startActivity(intent);
            }
        });
        binding.slotListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TournamentRegistrationOrgActivity.this, TournamentIdResultOrgActivity.class);
                intent.putExtra("tournamentId", tournamentId);
                startActivity(intent);
            }
        });
        binding.resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TournamentRegistrationOrgActivity.this, TournamentIdResultOrgActivity.class);
                intent.putExtra("tournamentId", tournamentId);
                startActivity(intent);
            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference productIdRef = database.collection("tournament").document(tournamentId);
                productIdRef.delete().addOnSuccessListener(aVoid -> Snackbar.make(findViewById(android.R.id.content), "Tournament deleted!", Snackbar.LENGTH_LONG).show());
                DocumentReference productId1Ref = database.collection("organizer").document(uid).collection("tournament").document(tournamentId);
                productId1Ref.delete().addOnSuccessListener(aVoid -> Snackbar.make(findViewById(android.R.id.content), "Tournament deleted!", Snackbar.LENGTH_LONG).show());


            }
        });



    }
}