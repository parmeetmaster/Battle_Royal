package com.yash.battleroyal.Activities.Organizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yash.battleroyal.Activities.Match_Details;
import com.yash.battleroyal.Activities.login.RegisterNewActivity;
import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityNewPaidContestOrgIndBinding;
import com.yash.battleroyal.databinding.ActivityNewTournamentOrgBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class NewPaidContestOrgIndActivity extends AppCompatActivity {

    ActivityNewPaidContestOrgIndBinding binding;

    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;
    User user;
    CrewModel crewModel;

    private StorageReference storageReference;

    //image picked uri
    private Uri image_uri;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewPaidContestOrgIndBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        String uid = FirebaseAuth.getInstance().getUid();
        final String matchId = getIntent().getStringExtra("matchId");

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources()
                .getStringArray(R.array.gameList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.tierSpinner.setAdapter(myAdapter);


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

        String tier = binding.tierSpinner.getSelectedItem().toString();
        String contestName = binding.contestName.getText().toString();
        String bonusCoinsNewTv = binding.bonusCoinsNewTv.getText().toString();
        String dateNewTv = binding.dateNewTv.getText().toString();
        String timeNewTv = binding.timeNewTv.getText().toString();
        String typeNewTv = binding.typeNewTv.getText().toString();
        String winnersNewTv = binding.winnersNewTv.getText().toString();
        String mapNewTv = binding.mapNewTv.getText().toString();
        String perKillNewTv = binding.perKillNewTv.getText().toString();
        String feeNewTv = binding.feeNewTv.getText().toString();
        String slotsNewTv = binding.slotsNewTv.getText().toString();
        String perspectiveNewTv = binding.perspectiveNewTv.getText().toString();


        binding.joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contestName1, bonusCoinsNewTv1, dateNewTv1, timeNewTv1, typeNewTv1,
                        winnersNewTv1, mapNewTv1, perKillNewTv1, feeNewTv1, slotsNewTv1, perspectiveNewTv1;

                contestName1 = binding.contestName.getText().toString();
                bonusCoinsNewTv1 = binding.bonusCoinsNewTv.getText().toString();
                dateNewTv1 = binding.dateNewTv.getText().toString();
                timeNewTv1 = binding.timeNewTv.getText().toString();
                typeNewTv1 = binding.typeNewTv.getText().toString();
                winnersNewTv1 = binding.winnersNewTv.getText().toString();
                mapNewTv1 = binding.mapNewTv.getText().toString();
                perKillNewTv1 = binding.perKillNewTv.getText().toString();
                feeNewTv1 = binding.feeNewTv.getText().toString();
                slotsNewTv1 = binding.slotsNewTv.getText().toString();
                perspectiveNewTv1 = binding.perspectiveNewTv.getText().toString();

                if (TextUtils.isEmpty(contestName1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter Contest Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(bonusCoinsNewTv1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter bonus Coins ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(dateNewTv1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter Match date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(timeNewTv1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(typeNewTv1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter Match type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(winnersNewTv1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter winner prize", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mapNewTv1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter map", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(perKillNewTv1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter per Kill prize", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(feeNewTv1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter match fee", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(slotsNewTv1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter total slots", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(perspectiveNewTv1)) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Enter match perspective", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((Integer.valueOf(bonusCoinsNewTv1) > Integer.valueOf(feeNewTv1))) {
                    Toast.makeText(NewPaidContestOrgIndActivity.this, "Bonus Can't Be greter than Entry Fee", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Uploding..Please Wait!");
                progressDialog.show();

                String contestName = binding.contestName.getText().toString();
                database
                        .collection("previous")
                        .document(uid)
                        .collection("my")
                        .document(uid + contestName)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().exists()) {
                                progressDialog.dismiss();
                                Toast.makeText(NewPaidContestOrgIndActivity.this, "Already used this contest name", Toast.LENGTH_SHORT).show();

                            } else {
                                String tier = binding.tierSpinner.getSelectedItem().toString();
                                String contestName = binding.contestName.getText().toString();
                                String bonusCoinsNewTv = binding.bonusCoinsNewTv.getText().toString();
                                String dateNewTv = binding.dateNewTv.getText().toString();
                                String timeNewTv = binding.timeNewTv.getText().toString();
                                String typeNewTv = binding.typeNewTv.getText().toString();
                                String winnersNewTv = binding.winnersNewTv.getText().toString();
                                String mapNewTv = binding.mapNewTv.getText().toString();
                                String perKillNewTv = binding.perKillNewTv.getText().toString();
                                String feeNewTv = binding.feeNewTv.getText().toString();
                                String slotsNewTv = binding.slotsNewTv.getText().toString();
                                String perspectiveNewTv = binding.perspectiveNewTv.getText().toString();
                                Map<String, Object> file = new HashMap<>();
                                file.put("cName", contestName);//
                                file.put("cDate", dateNewTv);//
                                file.put("cTime", timeNewTv);
                                file.put("cMap", mapNewTv);//
                                file.put("cPerspective", perspectiveNewTv);//
                                file.put("cWinner", winnersNewTv);
                                file.put("cKill", perKillNewTv);//
                                file.put("cFee", feeNewTv);//
                                file.put("cType", typeNewTv);
                                file.put("cSlots", slotsNewTv);//
                                file.put("cBonus", bonusCoinsNewTv);//
                                database.collection("organizer")
                                        .document(uid)
                                        .collection(tier).document(uid + contestName)
                                        .set(file)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                String tier = binding.tierSpinner.getSelectedItem().toString();
                                                String contestName = binding.contestName.getText().toString();
                                                String bonusCoinsNewTv = binding.bonusCoinsNewTv.getText().toString();
                                                String dateNewTv = binding.dateNewTv.getText().toString();
                                                String timeNewTv = binding.timeNewTv.getText().toString();
                                                String typeNewTv = binding.typeNewTv.getText().toString();
                                                String winnersNewTv = binding.winnersNewTv.getText().toString();
                                                String mapNewTv = binding.mapNewTv.getText().toString();
                                                String perKillNewTv = binding.perKillNewTv.getText().toString();
                                                String feeNewTv = binding.feeNewTv.getText().toString();
                                                String slotsNewTv = binding.slotsNewTv.getText().toString();
                                                String perspectiveNewTv = binding.perspectiveNewTv.getText().toString();
                                                Map<String, Object> file = new HashMap<>();
                                                file.put("cName", contestName);//
                                                file.put("cDate", dateNewTv);//
                                                file.put("cTime", timeNewTv);
                                                file.put("cMap", mapNewTv);//
                                                file.put("cPerspective", perspectiveNewTv);//
                                                file.put("cWinner", winnersNewTv);
                                                file.put("cKill", perKillNewTv);//
                                                file.put("cFee", feeNewTv);//
                                                file.put("cType", typeNewTv);
                                                file.put("cSlots", slotsNewTv);//
                                                file.put("cBonus", bonusCoinsNewTv);//
                                                database.collection("game")
                                                        .document(tier)
                                                        .collection("contest")
                                                        .document(uid + contestName)
                                                        .set(file).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        String tier = binding.tierSpinner.getSelectedItem().toString();
                                                        String contestName = binding.contestName.getText().toString();
                                                        String bonusCoinsNewTv = binding.bonusCoinsNewTv.getText().toString();
                                                        String dateNewTv = binding.dateNewTv.getText().toString();
                                                        String timeNewTv = binding.timeNewTv.getText().toString();
                                                        String typeNewTv = binding.typeNewTv.getText().toString();
                                                        String winnersNewTv = binding.winnersNewTv.getText().toString();
                                                        String mapNewTv = binding.mapNewTv.getText().toString();
                                                        String perKillNewTv = binding.perKillNewTv.getText().toString();
                                                        String feeNewTv = binding.feeNewTv.getText().toString();
                                                        String slotsNewTv = binding.slotsNewTv.getText().toString();
                                                        String perspectiveNewTv = binding.perspectiveNewTv.getText().toString();
                                                        Map<String, Object> file = new HashMap<>();
                                                        file.put("cName", contestName);//
                                                        file.put("cDate", dateNewTv);//
                                                        file.put("cTime", timeNewTv);
                                                        file.put("cMap", mapNewTv);//
                                                        file.put("cPerspective", perspectiveNewTv);//
                                                        file.put("cWinner", winnersNewTv);
                                                        file.put("cKill", perKillNewTv);//
                                                        file.put("cFee", feeNewTv);//
                                                        file.put("cType", typeNewTv);
                                                        file.put("cSlots", slotsNewTv);//
                                                        file.put("cBonus", bonusCoinsNewTv);//
                                                        database.collection("previous")
                                                                .document(uid)
                                                                .collection("my")
                                                                .document(uid + contestName)
                                                                .set(file).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                                progressDialog.dismiss();
                                                                Toast.makeText(NewPaidContestOrgIndActivity.this, "Contest Updated..!!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })

                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        //  Log.w(TAG, "Error writing document", e);
                                                                    }
                                                                });


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


