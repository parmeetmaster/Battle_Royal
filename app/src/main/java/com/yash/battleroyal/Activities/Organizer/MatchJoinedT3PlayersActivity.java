package com.yash.battleroyal.Activities.Organizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Adapter.ContestPlayersAdapter;
import com.yash.battleroyal.Adapter.Organizer.ContestPlayersOrgAdapter;
import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.Model.RegistrationRequest;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityMatchJoinedT2PlayersBinding;
import com.yash.battleroyal.databinding.ActivityMatchJoinedT3PlayersBinding;

import java.util.ArrayList;

public class MatchJoinedT3PlayersActivity extends AppCompatActivity {
    FirebaseFirestore database;
    User user;
    CrewModel crewModel;
    ActivityMatchJoinedT3PlayersBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMatchJoinedT3PlayersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseFirestore.getInstance();
        String matchId = getIntent().getStringExtra("matchId");


        final ArrayList<RegistrationRequest> registrationRequests = new ArrayList<>();
        final ContestPlayersOrgAdapter adapter = new ContestPlayersOrgAdapter(this, registrationRequests);


        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);

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

        String uid = FirebaseAuth.getInstance().getUid();

        database
                .collection("T3")
                .document(matchId)
                .collection("registration")
                .orderBy("teamName", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    RegistrationRequest registrationRequest = snapshot.toObject(RegistrationRequest.class);
                    registrationRequests.add(registrationRequest);
                }
                adapter.notifyDataSetChanged();

            }
        });

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}