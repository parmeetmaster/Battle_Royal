package com.yash.battleroyal.Activities.Organizer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Adapter.GameNameAdapter;
import com.yash.battleroyal.Adapter.Organizer.GameNameOrgAdapter;
import com.yash.battleroyal.Model.GameNameModel;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityContestDetailsBinding;
import com.yash.battleroyal.databinding.ActivityNewPaidSelectOrgBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NewPaidSelectOrgActivity extends AppCompatActivity {

    ActivityNewPaidSelectOrgBinding binding;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewPaidSelectOrgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ArrayList<GameNameModel> gameNameModels = new ArrayList<>();
        final GameNameOrgAdapter adapter = new GameNameOrgAdapter(this, gameNameModels);

        database = FirebaseFirestore.getInstance();

        database.collection("game")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            gameNameModels.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                GameNameModel model = snapshot.toObject(GameNameModel.class);
                                model.setGameId(snapshot.getId());
                                gameNameModels.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        binding.gameView.setLayoutManager(new GridLayoutManager(this,2));
        binding.gameView.setAdapter(adapter);


    }
}