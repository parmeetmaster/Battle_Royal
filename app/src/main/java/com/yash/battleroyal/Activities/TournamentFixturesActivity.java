package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Adapter.FixtureAdapter;
import com.yash.battleroyal.Adapter.SlotListAdapter;
import com.yash.battleroyal.Model.FixtureModel;
import com.yash.battleroyal.Model.SlotListModel;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivitySlotListBinding;
import com.yash.battleroyal.databinding.ActivityTournamentFixturesBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TournamentFixturesActivity extends AppCompatActivity {

    ActivityTournamentFixturesBinding binding;
    FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTournamentFixturesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = FirebaseAuth.getInstance().getUid();



//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, getString(R.string.slotListAd), adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                mInterstitialAd = interstitialAd;
//                mInterstitialAd.show(TournamentFixturesActivity.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//        });

        database = FirebaseFirestore.getInstance();

        final ArrayList<FixtureModel> fixtureModels = new ArrayList<>();
        final FixtureAdapter adapter = new FixtureAdapter(this, fixtureModels);

        final String tournamentId = getIntent().getStringExtra("tournamentId");

        database.collection("tournament")
                .document(tournamentId)
                .collection("fix")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            fixtureModels.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                FixtureModel model = snapshot.toObject(FixtureModel.class);
                                model.setFixId(snapshot.getId());
                                fixtureModels.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });



        binding.fixtureList.setLayoutManager(new LinearLayoutManager(this));
        binding.fixtureList.setAdapter(adapter);




    }
}