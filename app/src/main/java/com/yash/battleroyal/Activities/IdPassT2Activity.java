package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityIdPassBinding;
import com.yash.battleroyal.databinding.ActivityIdPassT2Binding;

import static android.content.ContentValues.TAG;

public class IdPassT2Activity extends AppCompatActivity {

    TextView option_2, option_3, option_4;

    ActivityIdPassT2Binding binding;
    FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIdPassT2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = FirebaseAuth.getInstance().getUid();


//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, getString(R.string.RoomAd), adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                mInterstitialAd = interstitialAd;
//                mInterstitialAd.show(IdPassT2Activity.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//        });

        option_2 = findViewById(R.id.option_2);
        option_3 = findViewById(R.id.option_3);
        option_4 = findViewById(R.id.option_4);

        database = FirebaseFirestore.getInstance();

        final String matchId = getIntent().getStringExtra("matchId");
        final String roomId = getIntent().getStringExtra("roomId");



        database.collection("T2")
                .document(matchId)
                .collection("room")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        option_3.setText(documentSnapshot.getString("roomID"));
                        option_2.setText(documentSnapshot.getString("roomPrize"));
                        option_4.setText(documentSnapshot.getString("roomPass"));
                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(IdPassT2Activity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}