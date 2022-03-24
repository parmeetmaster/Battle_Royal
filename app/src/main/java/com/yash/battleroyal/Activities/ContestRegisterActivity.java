package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.Model.RegistrationContestRequest;
import com.yash.battleroyal.Model.RegistrationRequest;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityContestDetailsBinding;
import com.yash.battleroyal.databinding.ActivityContestRegisterBinding;
import com.yash.battleroyal.databinding.ActivityMatchDetailsBinding;

import org.jetbrains.annotations.NotNull;

public class ContestRegisterActivity extends AppCompatActivity {

    ActivityContestRegisterBinding binding;

    FirebaseFirestore database;
    User user;
    CrewModel crewModel;
    ProgressDialog dialog;
    private static int totalFee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContestRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(ContestRegisterActivity.this);
        dialog.setMessage("Please Wait");


        String contestId = getIntent().getStringExtra("contestId");
        String contestName = getIntent().getStringExtra("contestName");
        String contestSlots = getIntent().getStringExtra("contestSlots");
        String entryFee = getIntent().getStringExtra("entryFee");
        String gameId = getIntent().getStringExtra("gameId");
        String bonus = getIntent().getStringExtra("bonus");

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                //binding.currentCoins.setText(user.getCoins() + "");

            }
        });


        int i1 = Integer.parseInt(entryFee);

        int i2 = Integer.parseInt(bonus);


        binding.textView4.setText(contestName);



        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("Crew")
                .document("CrewDetails")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                crewModel = documentSnapshot.toObject(CrewModel.class);
                if (crewModel!=null){
                    binding.teamName.setText(crewModel.getCrewName());
                }
                else {binding.teamName.setText("SOLO");}
            }
        });




        binding.idPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();

                //Getting Registration Details
                database
                        .collection("game")
                        .document(gameId)
                        .collection("contest")
                        .document(contestId)
                        .collection("registration")
                        .document(uid)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().exists()) {
                                Intent intent = new Intent(ContestRegisterActivity.this, IdPassContestActivity.class);
                                intent.putExtra("contestId", contestId);
                                intent.putExtra("gameId", gameId);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ContestRegisterActivity.this, "You have Not Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        binding.joinPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContestRegisterActivity.this, ContestJoinedPlayersActivity.class);
                intent.putExtra("contestId", contestId);
                intent.putExtra("gameId", gameId);
                startActivity(intent);


            }
        });




        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();

                dialog.show();
                //Getting Registration Details
                database
                        .collection("game")
                        .document(gameId)
                        .collection("contest")
                        .document(contestId)
                        .collection("registration")
                        .document(uid)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        dialog.dismiss();
                                        Toast.makeText(ContestRegisterActivity.this, "You have Already Registered", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //Registering the User
                                        database
                                                .collection("game")
                                                .document(gameId)
                                                .collection("contest")
                                                .document(contestId)
                                                .collection("registration")
                                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                        int counter = (int) queryDocumentSnapshots.size();
                                                        database.collection("users")
                                                                .document(FirebaseAuth.getInstance().getUid())
                                                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                        user = documentSnapshot.toObject(User.class);
                                                                        String uid = FirebaseAuth.getInstance().getUid();
                                                                        String bonusCoins = user.getbCoins() + "";
                                                                        String teamName = binding.teamName.getText().toString();
                                                                        //binding.currentCoins.setText(user.getCoins() + "");
                                                                        int currentBonus = Integer.parseInt(bonusCoins) - i2;//

                                                                        //Checking Conditions
                                                                        if (Integer.parseInt(bonusCoins) >= i2) {
                                                                            totalFee = i1 - Integer.valueOf(bonus);
                                                                            if ((counter < Integer.valueOf(contestSlots) && user.getCoins() >= totalFee)) {

                                                                                RegistrationContestRequest request = new RegistrationContestRequest(uid, user.getName(), user.getCharacterID(),teamName);
                                                                                database
                                                                                        .collection("game")
                                                                                        .document(gameId)
                                                                                        .collection("contest")
                                                                                        .document(contestId)
                                                                                        .collection("registration")
                                                                                        .document(uid)
                                                                                        .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void unused) {
                                                                                                dialog.dismiss();
                                                                                                long points = -1 * (Integer.valueOf(totalFee));
                                                                                                long bPoints = -1 * (Integer.valueOf(bonus));
                                                                                                FirebaseFirestore database = FirebaseFirestore.getInstance();
                                                                                                database.collection("users")
                                                                                                        .document(FirebaseAuth.getInstance().getUid())
                                                                                                        .update("coins", FieldValue.increment(points));
                                                                                                       database.collection("users")
                                                                                                               .document(FirebaseAuth.getInstance().getUid())
                                                                                                               .update("bCoins", FieldValue.increment(bPoints));
                                                                                                       Toast.makeText(ContestRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                                                                       Intent intent = new Intent(ContestRegisterActivity.this, RoomActivity.class);
                                                                                                       startActivity(intent);
                                                                                                   }
                                                                                               });
                                                                                           }
                                                                                       }
                                                                        else if ((counter < Integer.valueOf(contestSlots) && user.getCoins() >= i1)) {
                                                                            RegistrationContestRequest request = new RegistrationContestRequest(uid, user.getName(), user.getCharacterID(),teamName);
                                                                            database
                                                                                    .collection("game")
                                                                                    .document(gameId)
                                                                                    .collection("contest")
                                                                                    .document(contestId)
                                                                                    .collection("registration")
                                                                                    .document(uid)
                                                                                    .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void unused) {
                                                                                            dialog.dismiss();
                                                                                            long points = -1 * i1;
                                                                                            FirebaseFirestore database = FirebaseFirestore.getInstance();
                                                                                            database.collection("users")
                                                                                                    .document(FirebaseAuth.getInstance().getUid())
                                                                                                    .update("coins", FieldValue.increment(points));
                                                                                            Toast.makeText(ContestRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                                                            Intent intent = new Intent(ContestRegisterActivity.this, RoomActivity.class);
                                                                                            startActivity(intent);
                                                                                        }
                                                                                    });
                                                                        }
                                                                        else
                                                                        {
                                                                            dialog.dismiss();
                                                                            Toast.makeText(ContestRegisterActivity.this, "Registration full Or Insufficient Balance", Toast.LENGTH_SHORT).show();
                                                                        }




//                                                if (counter < (Integer.valueOf(contestSlots)) && (user.getCoins() >= totalFee)) {
//                                                    String uid = FirebaseAuth.getInstance().getUid();
//                                                    RegistrationContestRequest request = new RegistrationContestRequest(uid, user.getName(), user.getCharacterID());
//                                                    database
//                                                            .collection("game")
//                                                            .document(gameId)
//                                                            .collection("contest")
//                                                            .document(contestId)
//                                                            .collection("registration")
//                                                            .document(uid)
//                                                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void unused) {
//                                                            dialog.dismiss();
//                                                            long points = -1 * (Integer.valueOf(totalFee));
//                                                            long bPoints = -1 * (Integer.valueOf(bonus));
//                                                            FirebaseFirestore database = FirebaseFirestore.getInstance();
//                                                            database.collection("users")
//                                                                    .document(FirebaseAuth.getInstance().getUid())
//                                                                    .update("coins", FieldValue.increment(points));
//                                                            database.collection("users")
//                                                                    .document(FirebaseAuth.getInstance().getUid())
//                                                                    .update("bCoins", FieldValue.increment(bPoints));
//                                                            Toast.makeText(ContestRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(ContestRegisterActivity.this, RoomActivity.class);
//                                                            startActivity(intent);
//                                                        }
//                                                    });

                                                                               }
                                                                       });
                                                                   }
                                                       });
                                                   }
                                               }

            }
        });
                                                   }
        });
    }
}

