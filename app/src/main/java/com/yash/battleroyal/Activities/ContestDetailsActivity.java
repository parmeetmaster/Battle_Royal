package com.yash.battleroyal.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Adapter.ContestAdapter;
import com.yash.battleroyal.Adapter.GameNameAdapter;
import com.yash.battleroyal.Model.ContestModel;
import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.Model.GameNameModel;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityContestDetailsBinding;
import com.yash.battleroyal.databinding.ActivityMatchDetailsBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ContestDetailsActivity extends AppCompatActivity {

    ActivityContestDetailsBinding binding;
    FirebaseFirestore database;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContestDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        String gameId = getIntent().getStringExtra("gameId");
        String gameName = getIntent().getStringExtra("gameName");


        final ArrayList<ContestModel> contestModels = new ArrayList<>();
        final ContestAdapter adapter = new ContestAdapter(this, contestModels);


        binding.textView4.setText(gameName);

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

        binding.currentCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContestDetailsActivity.this, PaytmActivity.class);
                startActivity(intent);
            }
        });



        database.collection("game")
                .document(gameId)
                .collection("contest")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            Intent intent = new Intent(ContestDetailsActivity.this, ContestDetailsActivity.class);
                            intent.putExtra("gameId", gameId);
                            contestModels.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                ContestModel model = snapshot.toObject(ContestModel.class);
                                model.setcId(snapshot.getId());
                                contestModels.add(model);
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }
                });

        binding.gameView.setLayoutManager(new LinearLayoutManager(this));
        binding.gameView.setAdapter(adapter);

    }
}