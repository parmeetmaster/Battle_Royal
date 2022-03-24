
package com.yash.battleroyal.Activities.Organizer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Activities.AdminActivity;
import com.yash.battleroyal.Activities.ContestDetailsActivity;
import com.yash.battleroyal.Adapter.Organizer.ContestOrgAdapter;
import com.yash.battleroyal.Adapter.Organizer.ContestOrgPreviousAdapter;
import com.yash.battleroyal.Adapter.Organizer.MatchT3OrgAdapter;
import com.yash.battleroyal.Model.ContestModel;
import com.yash.battleroyal.Model.ContestPrevModel;
import com.yash.battleroyal.Model.MatchModel;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityAdminBinding;
import com.yash.battleroyal.databinding.ActivityNewPaidContestOrgBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NewPaidContestOrgActivity extends AppCompatActivity {

    ActivityNewPaidContestOrgBinding binding;
    FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPaidContestOrgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();

        String gameId = getIntent().getStringExtra("gameId");
        String gameName = getIntent().getStringExtra("gameName");
        String uid = FirebaseAuth.getInstance().getUid();



        final ArrayList<ContestModel> contestModels = new ArrayList<>();
        final ContestOrgAdapter adapter = new ContestOrgAdapter(this, contestModels);
        final ArrayList<ContestPrevModel> contestPrevModels = new ArrayList<>();
        final ContestOrgPreviousAdapter previousAdapter = new ContestOrgPreviousAdapter(this, contestPrevModels);

        binding.previousMatchList.setVisibility(View.GONE);



        binding.t3Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.matchList.setVisibility(View.VISIBLE);
                binding.previousMatchList.setVisibility(View.GONE);

                binding.t3Tv.setTextColor(getResources().getColor(R.color.black));
                binding.t3Tv.setBackgroundColor(getResources().getColor(R.color.white));

                binding.t1Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t1Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        binding.t1Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.matchList.setVisibility(View.GONE);
                binding.previousMatchList.setVisibility(View.VISIBLE);

                binding.t1Tv.setTextColor(getResources().getColor(R.color.black));
                binding.t1Tv.setBackgroundColor(getResources().getColor(R.color.white));

                binding.t3Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t3Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        database.collection("organizer")
                .document(uid)
                .collection(gameId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            Intent intent = new Intent(NewPaidContestOrgActivity.this, NewPaidContestOrgActivity.class);
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

        database.collection("previous")
                .document(uid)
                .collection("my")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            Intent intent = new Intent(NewPaidContestOrgActivity.this, NewPaidContestOrgActivity.class);
                            intent.putExtra("gameId", gameId);
                            contestPrevModels.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                ContestPrevModel model = snapshot.toObject(ContestPrevModel.class);
                                model.setcId(snapshot.getId());
                                contestPrevModels.add(model);
                            }
                            previousAdapter.notifyDataSetChanged();
                        }
                    }
                });


        binding.matchList.setLayoutManager(new LinearLayoutManager(this));
        binding.matchList.setAdapter(adapter);
        binding.previousMatchList.setLayoutManager(new LinearLayoutManager(this));
        binding.previousMatchList.setAdapter(previousAdapter);

        binding.addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewPaidContestOrgActivity.this, NewPaidContestOrgIndActivity.class);
                startActivity(intent);
            }
        });



    }
}