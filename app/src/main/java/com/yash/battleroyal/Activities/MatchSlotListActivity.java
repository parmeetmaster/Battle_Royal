package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Adapter.SlotListAdapter;
import com.yash.battleroyal.Fragments.HomeFragment;
import com.yash.battleroyal.Model.SlotListModel;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityMatchSlotListBinding;
import com.yash.battleroyal.databinding.ActivitySlotListBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MatchSlotListActivity extends AppCompatActivity {

    ActivityMatchSlotListBinding binding;
    FirebaseFirestore database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchSlotListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = FirebaseAuth.getInstance().getUid();

//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, getString(R.string.slotListAd), adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                mInterstitialAd = interstitialAd;
//                mInterstitialAd.show(MatchSlotListActivity.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//        });

        database = FirebaseFirestore.getInstance();

        final ArrayList<SlotListModel> slotListModels = new ArrayList<>();
        final SlotListAdapter adapter = new SlotListAdapter(this, slotListModels);


        final String matchId = getIntent().getStringExtra("matchId");

        database.collection("T3")
                .document(matchId)
                .collection("slotlist")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            slotListModels.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                SlotListModel model = snapshot.toObject(SlotListModel.class);
                                model.setSlotId(snapshot.getId());
                                slotListModels.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });



        binding.slotList.setLayoutManager(new LinearLayoutManager(this));
        binding.slotList.setAdapter(adapter);

    }
}