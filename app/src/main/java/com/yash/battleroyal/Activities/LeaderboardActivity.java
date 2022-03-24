package com.yash.battleroyal.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Adapter.LeaderboardAdapter;
import com.yash.battleroyal.Adapter.LeaderboardTopEarnerAdapter;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityContestDetailsBinding;
import com.yash.battleroyal.databinding.ActivityLeaderboardBinding;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    ActivityLeaderboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.TErecyclerView.setVisibility(View.GONE);

        binding.mwTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.TErecyclerView.setVisibility(View.GONE);

                binding.mwTv.setTextColor(getResources().getColor(R.color.black));
                binding.mwTv.setBackgroundColor(getResources().getColor(R.color.white));

                binding.teTv.setTextColor(getResources().getColor(R.color.white));
                binding.teTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        binding.teTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.recyclerView.setVisibility(View.GONE);
                binding.TErecyclerView.setVisibility(View.VISIBLE);

                binding.teTv.setTextColor(getResources().getColor(R.color.black));
                binding.teTv.setBackgroundColor(getResources().getColor(R.color.white));

                binding.mwTv.setTextColor(getResources().getColor(R.color.white));
                binding.mwTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });


        FirebaseFirestore database = FirebaseFirestore.getInstance();

        final ArrayList<User> users = new ArrayList<>();
        final LeaderboardAdapter adapter = new LeaderboardAdapter(this, users);

        final ArrayList<User> users1 = new ArrayList<>();
        final LeaderboardTopEarnerAdapter leaderboardTopEarnerAdapter = new LeaderboardTopEarnerAdapter(this, users1);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.TErecyclerView.setAdapter(leaderboardTopEarnerAdapter);
        binding.TErecyclerView.setLayoutManager(new LinearLayoutManager(this));



        //long totalCoins = user.getbCoins()+user.getCoins();
        database.collection("users")
                .orderBy("tierPoint", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    User user = snapshot.toObject(User.class);
                    users.add(user);
                }
                adapter.notifyDataSetChanged();
            }
        });

        database.collection("users")
                .orderBy("coins" , Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    User user1 = snapshot.toObject(User.class);

                    users1.add(user1);
                }
                leaderboardTopEarnerAdapter.notifyDataSetChanged();
            }
        });


    }
}