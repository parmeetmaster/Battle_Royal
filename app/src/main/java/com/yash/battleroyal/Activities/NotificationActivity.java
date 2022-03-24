package com.yash.battleroyal.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Adapter.NotificationAdapter;
import com.yash.battleroyal.Model.NotificationModel;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityContestDetailsBinding;
import com.yash.battleroyal.databinding.ActivityNotificationBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NotificationActivity extends AppCompatActivity {

    FirebaseFirestore database;
    ActivityNotificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();


        final ArrayList<NotificationModel> notificationModels = new ArrayList<>();
        final NotificationAdapter notificationAdapter = new NotificationAdapter(this, notificationModels);

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


        binding.noticeList.setLayoutManager(new LinearLayoutManager(this));
        binding.noticeList.setAdapter(notificationAdapter);



    }
}