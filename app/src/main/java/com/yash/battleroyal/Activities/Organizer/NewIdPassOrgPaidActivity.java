package com.yash.battleroyal.Activities.Organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityNewIdPassOrgBinding;
import com.yash.battleroyal.databinding.ActivityNewIdPassOrgPaidBinding;

import java.util.HashMap;
import java.util.Map;

public class NewIdPassOrgPaidActivity extends AppCompatActivity {

    ActivityNewIdPassOrgPaidBinding binding;
    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;
    User user;
    CrewModel crewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewIdPassOrgPaidBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        String uid = FirebaseAuth.getInstance().getUid();
        final String gameId = getIntent().getStringExtra("gameId");
        final String contestId = getIntent().getStringExtra("contestId");


        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);

                //binding.currentCoins.setText(user.getCoins() + "");

            }
        });

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Crew")
                .document("CrewDetails")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                crewModel = documentSnapshot.toObject(CrewModel.class);
            }
        });

        String id = binding.idNewTv.getText().toString();
        String pass = binding.passNewTv.getText().toString();


        binding.joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Uploding..Please Wait!");
                progressDialog.show();

                String id = binding.idNewTv.getText().toString();
                String pass = binding.passNewTv.getText().toString();
                Map<String, Object> file = new HashMap<>();
                file.put("roomID", id);
                file.put("roomPass", pass);//
                database.collection("game")
                        .document(gameId)
                        .collection("contest")
                        .document(contestId)
                        .collection("room")
                        .document(uid)
                        .set(file).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(NewIdPassOrgPaidActivity.this, "ID-Pass Updated..!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}