package com.yash.battleroyal.Activities;

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
import com.yash.battleroyal.Adapter.LeaderboardAdapter;
import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.Model.RegistrationRequest;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityContestJoinedPlayersBinding;
import com.yash.battleroyal.databinding.ActivityContestRegisterBinding;

import java.util.ArrayList;

public class ContestJoinedPlayersActivity extends AppCompatActivity {

    FirebaseFirestore database;
    User user;
    CrewModel crewModel;
    ActivityContestJoinedPlayersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContestJoinedPlayersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();

        String contestId = getIntent().getStringExtra("contestId");
        String gameId = getIntent().getStringExtra("gameId");


        final ArrayList<RegistrationRequest> registrationRequests = new ArrayList<>();
        final ContestPlayersAdapter adapter = new ContestPlayersAdapter(this, registrationRequests);


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

        database.collection("game")
                .document(gameId)
                .collection("contest")
                .document(contestId)
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