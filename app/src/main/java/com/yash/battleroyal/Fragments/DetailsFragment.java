package com.yash.battleroyal.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Activities.CrewActivity;
import com.yash.battleroyal.Activities.EditUserActivity;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.FragmentDetailsBinding;


public class DetailsFragment extends Fragment {

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentDetailsBinding binding;
    FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        firestore = FirebaseFirestore.getInstance();

        binding.crewBtn.setVisibility(View.GONE);

        binding.userTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editUserBtn.setVisibility(View.VISIBLE);
                binding.crewBtn.setVisibility(View.GONE);

                binding.userTv.setTextColor(getResources().getColor(R.color.black));
                binding.userTv.setBackgroundColor(getResources().getColor(R.color.white));

                binding.crewTv.setTextColor(getResources().getColor(R.color.white));
                binding.crewTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        binding.crewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editUserBtn.setVisibility(View.GONE);
                binding.crewBtn.setVisibility(View.VISIBLE);

                binding.crewTv.setTextColor(getResources().getColor(R.color.black));
                binding.crewTv.setBackgroundColor(getResources().getColor(R.color.white));

                binding.userTv.setTextColor(getResources().getColor(R.color.white));
                binding.userTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        binding.crewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bye1", "bye");
                firestore.collection("users")
                        .whereEqualTo("characterID", true) // <-- This line
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.d("bye2", "ok");
                                    for (DocumentSnapshot document : task.getResult()) {
                                        Log.d("bye3", document.getId().toString() + " => " + document.getData().toString());
                                    }
                                } else {
                                    Log.d("bye2", "Error getting documents: ", task.getException());
                                }
                            }
                        });

                Intent intent = new Intent(DetailsFragment.this.getActivity(), CrewActivity.class);
                startActivity(intent);
            }
        });

        binding.editUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsFragment.this.getActivity(), EditUserActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}