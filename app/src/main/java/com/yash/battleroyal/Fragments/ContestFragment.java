package com.yash.battleroyal.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Adapter.GameNameAdapter;
import com.yash.battleroyal.Adapter.MatchAdapter;
import com.yash.battleroyal.Model.GameNameModel;
import com.yash.battleroyal.Model.MatchModel;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.FragmentContestBinding;
import com.yash.battleroyal.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class ContestFragment extends Fragment {

    public ContestFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentContestBinding binding;
    FirebaseFirestore database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContestBinding.inflate(inflater, container, false);

        final ArrayList<GameNameModel> gameNameModels = new ArrayList<>();
        final GameNameAdapter adapter = new GameNameAdapter(getContext(), gameNameModels);

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

        binding.gameView.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.gameView.setAdapter(adapter);


        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}