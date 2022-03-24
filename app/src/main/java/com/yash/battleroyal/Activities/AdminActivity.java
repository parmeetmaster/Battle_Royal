package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Activities.Organizer.NewPaidContestOrgActivity;
import com.yash.battleroyal.Activities.Organizer.NewPaidSelectOrgActivity;
import com.yash.battleroyal.Activities.Organizer.NewTierMatchActivity;
import com.yash.battleroyal.Activities.Organizer.NewTournamentOrgActivity;
import com.yash.battleroyal.Adapter.Organizer.MatchT1OrgAdapter;
import com.yash.battleroyal.Adapter.Organizer.MatchT2OrgAdapter;
import com.yash.battleroyal.Adapter.Organizer.MatchT3OrgAdapter;
import com.yash.battleroyal.Adapter.Organizer.TournamentOrgAdapter;
import com.yash.battleroyal.Fragments.SettingsFragment;
import com.yash.battleroyal.Model.MatchModel;
import com.yash.battleroyal.Model.MatchT1Model;
import com.yash.battleroyal.Model.MatchT2Model;
import com.yash.battleroyal.Model.TournamentModel;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityAdminBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;
    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        final ArrayList<MatchModel> match = new ArrayList<>();
        final MatchT3OrgAdapter adapter = new MatchT3OrgAdapter(this, match);

        final ArrayList<MatchT2Model> matchT2Models = new ArrayList<>();
        final MatchT2OrgAdapter matchT2Adapter = new MatchT2OrgAdapter(this, matchT2Models);

        final ArrayList<MatchT1Model> matchT1Models = new ArrayList<>();
        final MatchT1OrgAdapter matchT1Adapter = new MatchT1OrgAdapter(this, matchT1Models);

        final ArrayList<TournamentModel> tournamentModels = new ArrayList<>();
        final TournamentOrgAdapter tournamentOrgAdapter = new TournamentOrgAdapter(this, tournamentModels);


        binding.matchT2List.setVisibility(View.GONE);
        binding.matchT1List.setVisibility(View.GONE);
        binding.tournamentList.setVisibility(View.GONE);

        String uid = FirebaseAuth.getInstance().getUid();



        binding.t3Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.matchList.setVisibility(View.VISIBLE);
                binding.matchT2List.setVisibility(View.GONE);
                binding.matchT1List.setVisibility(View.GONE);
                binding.tournamentList.setVisibility(View.GONE);

                binding.t3Tv.setTextColor(getResources().getColor(R.color.black));
                binding.t3Tv.setBackgroundColor(getResources().getColor(R.color.white));

                binding.t2Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t2Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.t1Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t1Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.tournamentTv.setTextColor(getResources().getColor(R.color.white));
                binding.tournamentTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });


        binding.t2Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.matchList.setVisibility(View.GONE);
                binding.matchT2List.setVisibility(View.VISIBLE);
                binding.matchT1List.setVisibility(View.GONE);
                binding.tournamentList.setVisibility(View.GONE);

                binding.t2Tv.setTextColor(getResources().getColor(R.color.black));
                binding.t2Tv.setBackgroundColor(getResources().getColor(R.color.white));

                binding.t3Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t3Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.t1Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t1Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                binding.tournamentTv.setTextColor(getResources().getColor(R.color.white));
                binding.tournamentTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        binding.t1Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.matchList.setVisibility(View.GONE);
                binding.matchT2List.setVisibility(View.GONE);
                binding.matchT1List.setVisibility(View.VISIBLE);
                binding.tournamentList.setVisibility(View.GONE);

                binding.t1Tv.setTextColor(getResources().getColor(R.color.black));
                binding.t1Tv.setBackgroundColor(getResources().getColor(R.color.white));

                binding.t3Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t3Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.t2Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t2Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.tournamentTv.setTextColor(getResources().getColor(R.color.white));
                binding.tournamentTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        binding.tournamentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.matchList.setVisibility(View.GONE);
                binding.matchT2List.setVisibility(View.GONE);
                binding.matchT1List.setVisibility(View.GONE);
                binding.tournamentList.setVisibility(View.VISIBLE);
                binding.tournamentTv.setTextColor(getResources().getColor(R.color.black));
                binding.tournamentTv.setBackgroundColor(getResources().getColor(R.color.white));

                binding.t3Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t3Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.t2Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t2Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.t1Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t1Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });


        binding.addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.collection("users").document(firebaseAuth.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (document.get("scrim") != null) {
                                    Intent intent = new Intent(AdminActivity.this, NewTierMatchActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AdminActivity.this, "Not Eligible for Conducting Scrims", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(AdminActivity.this, "Whatsapp us to get this role", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

            }
        });
        binding.addNewTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.collection("users").document(firebaseAuth.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (document.get("tournament") != null) {
                                    Intent intent = new Intent(AdminActivity.this, NewTournamentOrgActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AdminActivity.this, "Not Eligible for Adding Tournaments", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(AdminActivity.this, "Whatsapp us to get this role", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

            }
        });
//        binding.addPaidBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(AdminActivity.this, NewPaidSelectOrgActivity.class);
//                startActivity(intent);
//            }
//        });

        binding.addPaidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.collection("users").document(firebaseAuth.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (document.get("paid") != null) {
                                    Intent intent = new Intent(AdminActivity.this, NewPaidSelectOrgActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AdminActivity.this, "Not Eligible for Paid Contest", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(AdminActivity.this, "Whatsapp us to get this role", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });


        database.collection("organizer")
                .document(uid)
                .collection("T3")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            match.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                MatchModel model = snapshot.toObject(MatchModel.class);
                                model.setMatchId(snapshot.getId());
                                match.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        database.collection("organizer")
                .document(uid)
                .collection("T2")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            matchT2Models.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                MatchT2Model matchT2Model = snapshot.toObject(MatchT2Model.class);
                                matchT2Model.setMatchId(snapshot.getId());
                                matchT2Models.add(matchT2Model);
                            }
                            matchT2Adapter.notifyDataSetChanged();
                        }
                    }
                });

        database.collection("organizer")
                .document(uid)
                .collection("T1")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            matchT1Models.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                MatchT1Model matchT1Model = snapshot.toObject(MatchT1Model.class);
                                matchT1Model.setMatchId(snapshot.getId());
                                matchT1Models.add(matchT1Model);
                            }
                            matchT1Adapter.notifyDataSetChanged();
                        }
                    }
                });

        database.collection("organizer")
                .document(uid)
                .collection("tournament")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            tournamentModels.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                TournamentModel tournamentModel = snapshot.toObject(TournamentModel.class);
                                tournamentModel.setTournamentId(snapshot.getId());
                                tournamentModels.add(tournamentModel);
                            }
                            tournamentOrgAdapter.notifyDataSetChanged();
                        }
                    }
                });

        binding.matchList.setLayoutManager(new GridLayoutManager(this,2));
        binding.matchList.setAdapter(adapter);

        binding.matchT2List.setLayoutManager(new GridLayoutManager(this,2));
        binding.matchT2List.setAdapter(matchT2Adapter);

        binding.matchT1List.setLayoutManager(new GridLayoutManager(this,2));
        binding.matchT1List.setAdapter(matchT1Adapter);

        binding.tournamentList.setLayoutManager(new LinearLayoutManager(this));
        binding.tournamentList.setAdapter(tournamentOrgAdapter);

    }
}