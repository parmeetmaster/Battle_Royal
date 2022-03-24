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
import com.yash.battleroyal.Adapter.ResultAdapter;
import com.yash.battleroyal.Adapter.SlotListAdapter;
import com.yash.battleroyal.Model.ResultModel;
import com.yash.battleroyal.Model.SlotListModel;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityMatchResultBinding;
import com.yash.battleroyal.databinding.ActivityMatchResultT2Binding;
import com.yash.battleroyal.databinding.ActivitySlotListBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MatchResultT2Activity extends AppCompatActivity {


    ActivityMatchResultT2Binding binding;
    FirebaseFirestore database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchResultT2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = FirebaseAuth.getInstance().getUid();





//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, getString(R.string.slotListAd), adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                mInterstitialAd = interstitialAd;
//                mInterstitialAd.show(MatchResultT2Activity.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//        });

        database = FirebaseFirestore.getInstance();

        final ArrayList<ResultModel> resultModels = new ArrayList<>();
        final ResultAdapter adapter = new ResultAdapter(this, resultModels);

        final String matchId = getIntent().getStringExtra("matchId");

        database.collection("T2")
                .document(matchId)
                .collection("result")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            resultModels.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                ResultModel model = snapshot.toObject(ResultModel.class);
                                model.setResultId(snapshot.getId());
                                resultModels.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });



        binding.resultList.setLayoutManager(new LinearLayoutManager(this));
        binding.resultList.setAdapter(adapter);


    }
}