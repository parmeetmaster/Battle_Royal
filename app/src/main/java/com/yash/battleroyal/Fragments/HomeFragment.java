package com.yash.battleroyal.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yash.battleroyal.Activities.NotificationActivity;
import com.yash.battleroyal.Activities.TournamentActivity;
import com.yash.battleroyal.Adapter.MatchAdapter;
import com.yash.battleroyal.Adapter.MatchT1Adapter;
import com.yash.battleroyal.Adapter.MatchT2Adapter;
import com.yash.battleroyal.Adapter.NotificationAdapter;
import com.yash.battleroyal.Adapter.SliderAdapter;
import com.yash.battleroyal.Model.MatchModel;
import com.yash.battleroyal.Model.MatchT1Model;
import com.yash.battleroyal.Model.MatchT2Model;
import com.yash.battleroyal.Model.NotificationModel;
import com.yash.battleroyal.Model.SliderModel;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    FragmentHomeBinding binding;
    FirebaseFirestore database;
    User user;
    int FEE=1;
    long points = 1 * FEE;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        database = FirebaseFirestore.getInstance();

        long points = 1 * FEE;

        final ArrayList<MatchModel> match = new ArrayList<>();
        final MatchAdapter adapter = new MatchAdapter(getContext(), match);

        final ArrayList<MatchT2Model> matchT2Models = new ArrayList<>();
        final MatchT2Adapter matchT2Adapter = new MatchT2Adapter(getContext(), matchT2Models);


        final ArrayList<MatchT1Model> matchT1Models = new ArrayList<>();
        final MatchT1Adapter matchT1Adapter = new MatchT1Adapter(getContext(), matchT1Models);

        final ArrayList<NotificationModel> notificationModels = new ArrayList<>();
        final NotificationAdapter notificationAdapter = new NotificationAdapter(getContext(), notificationModels);

        final ArrayList<SliderModel> sliderModels = new ArrayList<>();
        final SliderAdapter sliderAdapter = new SliderAdapter(getContext(), sliderModels);



        binding.matchT2List.setVisibility(View.GONE);
        binding.matchT1List.setVisibility(View.GONE);


        binding.t3Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.matchList.setVisibility(View.VISIBLE);
                binding.matchT2List.setVisibility(View.GONE);
                binding.matchT1List.setVisibility(View.GONE);

                binding.t3Tv.setTextColor(getResources().getColor(R.color.color_white));
                binding.t3Tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.game_button_bg));

                binding.t2Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t2Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.t1Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t1Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });


        binding.t2Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.matchList.setVisibility(View.GONE);
                binding.matchT2List.setVisibility(View.VISIBLE);
                binding.matchT1List.setVisibility(View.GONE);

                binding.t2Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t2Tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.game_button_bg));

                binding.t3Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t3Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.t1Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t1Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        binding.t1Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.matchList.setVisibility(View.GONE);
                binding.matchT2List.setVisibility(View.GONE);
                binding.matchT1List.setVisibility(View.VISIBLE);

                binding.t1Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t1Tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.game_button_bg));

                binding.t3Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t3Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.t2Tv.setTextColor(getResources().getColor(R.color.white));
                binding.t2Tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        database.collection("T3")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            match.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                MatchModel model = snapshot.toObject(MatchModel.class);
                                model.setMatchId(snapshot.getId());
                                match.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        database.collection("T2")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            matchT2Models.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                MatchT2Model matchT2Model = snapshot.toObject(MatchT2Model.class);
                                matchT2Model.setMatchId(snapshot.getId());
                                matchT2Models.add(matchT2Model);
                            }
                            matchT2Adapter.notifyDataSetChanged();
                        }
                    }
                });

        database.collection("T1")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            matchT1Models.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                MatchT1Model matchT1Model = snapshot.toObject(MatchT1Model.class);
                                matchT1Model.setMatchId(snapshot.getId());
                                matchT1Models.add(matchT1Model);
                            }
                            matchT1Adapter.notifyDataSetChanged();
                        }
                    }
                });


        database.collection("notification")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            notificationModels.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                NotificationModel notificationModel = snapshot.toObject(NotificationModel.class);
                                notificationModel.setnId(snapshot.getId());
                                notificationModels.add(notificationModel);
                            }
                            notificationAdapter.notifyDataSetChanged();
                        }
                    }
                });

        database.collection("slider")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d(TAG, "Error:" + error.getMessage());
                        } else {
                            sliderModels.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                SliderModel sliderModel = snapshot.toObject(SliderModel.class);
                                sliderModel.setSliderId(snapshot.getId());
                                sliderModels.add(sliderModel);
                            }
                            sliderAdapter.notifyDataSetChanged();
                        }
                    }
                });


//        rewAd();


        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String body = "Hey! I am playing Battle Royal. Want to Battle Against Me.. Join me and play this exciting BGMI Custom Rooms for Free. Download From PlayStore And Earn Real Money Too! https://brgp.in/battle-royal.apk";
                String sub = "Your Subject";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });


        binding.tournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), TournamentActivity.class);
                startActivity(intent);
            }
        });
        binding.notificationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });


        binding.matchList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.matchList.setAdapter(adapter);

        binding.matchT2List.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.matchT2List.setAdapter(matchT2Adapter);

        binding.matchT1List.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.matchT1List.setAdapter(matchT1Adapter);

        LinearLayoutManager nlinearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.noticeList.setLayoutManager(nlinearLayoutManager);
        binding.noticeList.setAdapter(notificationAdapter);

        LinearLayoutManager slinearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.sliderList.setLayoutManager(slinearLayoutManager);
        binding.sliderList.setAdapter(sliderAdapter);

        //The LinearSnapHelper will snap the center of the target child view to the center of the attached RecyclerView , it's optional if you want , you can use it
        final LinearSnapHelper nlinearSnapHelper = new LinearSnapHelper();
        nlinearSnapHelper.attachToRecyclerView(binding.noticeList);

        final int ntime = 2000;
        final Timer ntimer = new Timer();
        ntimer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (nlinearLayoutManager.findLastCompletelyVisibleItemPosition() < (notificationAdapter.getItemCount() - 1)) {

                    nlinearLayoutManager.smoothScrollToPosition(binding.noticeList, new RecyclerView.State(), nlinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                }

                else if (nlinearLayoutManager.findLastCompletelyVisibleItemPosition() == (notificationAdapter.getItemCount() - 1)) {

                    nlinearLayoutManager.smoothScrollToPosition(binding.noticeList, new RecyclerView.State(), 0);
                }
            }
        }, 0, ntime);

        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(binding.sliderList);

        final int time = 3000;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (slinearLayoutManager.findLastCompletelyVisibleItemPosition() < (sliderAdapter.getItemCount() - 1)) {

                    slinearLayoutManager.smoothScrollToPosition(binding.sliderList, new RecyclerView.State(), slinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                }

                else if (slinearLayoutManager.findLastCompletelyVisibleItemPosition() == (sliderAdapter.getItemCount() - 1)) {

                    slinearLayoutManager.smoothScrollToPosition(binding.sliderList, new RecyclerView.State(), 0);
                }
            }
        }, 0, time);



        // Inflate the layout for this fragment
        return binding.getRoot();

    }


//    public void rewAd(){
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        RewardedAd.load(this.getActivity(), getString(R.string.earnCoinAd),
//                adRequest, new RewardedAdLoadCallback(){
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error.
//                        mRewardedAd = null;
//                    }
//
//                    @Override
//                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
//                        mRewardedAd = rewardedAd;
//                    }
//                });
//    }





}