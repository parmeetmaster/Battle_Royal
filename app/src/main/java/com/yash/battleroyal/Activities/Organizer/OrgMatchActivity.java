package com.yash.battleroyal.Activities.Organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityMatchDetailsBinding;
import com.yash.battleroyal.databinding.ActivityOrgMatchBinding;

public class OrgMatchActivity extends AppCompatActivity {

    ActivityOrgMatchBinding binding;

    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;
    User user;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrgMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        database = FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        String uid = FirebaseAuth.getInstance().getUid();
        final String matchId = getIntent().getStringExtra("matchId");

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(OrgMatchActivity.this, android.R.layout.simple_list_item_1,getResources()
                .getStringArray(R.array.tierList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.tierSpinner.setAdapter(myAdapter);

        String tier = binding.tierSpinner.getSelectedItem().toString();



        binding.joinPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tier = binding.tierSpinner.getSelectedItem().toString();
                Intent intent = new Intent(OrgMatchActivity.this, MatchJoinedT1Activity.class);
                intent.putExtra("matchId", matchId);
                intent.putExtra("tier", tier);
                startActivity(intent);
            }
        });

        binding.idPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgMatchActivity.this, NewIdPassOrgActivity.class);
                intent.putExtra("matchId", matchId);
                startActivity(intent);
            }
        });
        binding.slotListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgMatchActivity.this, NewSlotListOrgActivity.class);
                intent.putExtra("matchId", matchId);
                startActivity(intent);
            }
        });
        binding.resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgMatchActivity.this, NewResultOrgActivity.class);
                intent.putExtra("matchId", matchId);
                startActivity(intent);
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tier = binding.tierSpinner.getSelectedItem().toString();

                DocumentReference productIdRef = database.collection(tier).document(matchId);
                productIdRef.delete().addOnSuccessListener(aVoid -> Snackbar.make(findViewById(android.R.id.content), "Match deleted!", Snackbar.LENGTH_LONG).show());
                DocumentReference productId1Ref = database.collection("organizer").document(uid).collection(tier).document(matchId);
                productId1Ref.delete().addOnSuccessListener(aVoid -> Snackbar.make(findViewById(android.R.id.content), "Match deleted!", Snackbar.LENGTH_LONG).show());


            }
        });


    }
}