package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CrewActivity extends AppCompatActivity {


    private Button createCrewBtn, updateCrewBtn;
    private EditText crew2, crew3, crew4, crew5,crewName;
    private TextView crew1;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew);

        crew1 = findViewById(R.id.crew1);
        crew2 = findViewById(R.id.crew2);
        crew3 = findViewById(R.id.crew3);
        crew4 = findViewById(R.id.crew4);
        crew5 = findViewById(R.id.crew5);
        crewName = findViewById(R.id.crewName);
        createCrewBtn = findViewById(R.id.createCrewBtn);
        updateCrewBtn = findViewById(R.id.updateCrewBtn);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference reference = firestore.collection("users").document(userID);
        reference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                crew1.setText(value.getString("name"));

            }
        });


        reference.collection("Crew")
                .document("CrewDetails")
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        crew2.setText(value.getString("crew2"));
                        crew3.setText(value.getString("crew3"));
                        crew4.setText(value.getString("crew4"));
                        crew5.setText(value.getString("crew5"));
                        crewName.setText(value.getString("crewName"));
                    }
                });

        crew2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String crewSecond;
                crewSecond = crew2.getText().toString().trim();
                firestore.collection("users")
                        .whereEqualTo("characterID", crewSecond)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.d("bye2", "okkk");
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //Toast.makeText(CrewActivity.this, "2 Exist", Toast.LENGTH_SHORT).show();
                                        Log.d("bye10", document.getId() + " => " + document.getData());

                                        Log.d("bye11",""+document.getData().get("name").toString());
                                        String crew2nd = document.getData().get("name").toString();
                                        crew2.setText(crew2nd);
                                        return;
                                    }
                                    Toast.makeText(CrewActivity.this, "Player Doesn't Exist", Toast.LENGTH_SHORT).show();
                                }return;
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        crew3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (crew2 == null) {
                    Toast.makeText(CrewActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    String crewSecond;
                    crewSecond = crew3.getText().toString().trim();
                    firestore.collection("users")
                            .whereEqualTo("characterID", crewSecond)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("bye2", "okkk");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            //Toast.makeText(CrewActivity.this, "2 Exist", Toast.LENGTH_SHORT).show();
                                            Log.d("bye10", document.getId() + " => " + document.getData());

                                            Log.d("bye11", "" + document.getData().get("name").toString());
                                            String crew2nd = document.getData().get("name").toString();
                                            crew3.setText(crew2nd);
                                            return;
                                        }
                                        Toast.makeText(CrewActivity.this, "Player Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    }
                                    return;
                                }
                            });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        crew4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (crew2 == null) {
                    Toast.makeText(CrewActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    String crewSecond;
                    crewSecond = crew4.getText().toString().trim();
                    firestore.collection("users")
                            .whereEqualTo("characterID", crewSecond)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("bye2", "okkk");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            //Toast.makeText(CrewActivity.this, "2 Exist", Toast.LENGTH_SHORT).show();
                                            Log.d("bye10", document.getId() + " => " + document.getData());

                                            Log.d("bye11", "" + document.getData().get("name").toString());
                                            String crew2nd = document.getData().get("name").toString();
                                            crew4.setText(crew2nd);
                                            return;
                                        }
                                        Toast.makeText(CrewActivity.this, "Player Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    }
                                    return;
                                }
                            });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        crew5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (crew2 == null) {
                    Toast.makeText(CrewActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    String crewSecond;
                    crewSecond = crew5.getText().toString().trim();
                    firestore.collection("users")
                            .whereEqualTo("characterID", crewSecond)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("bye2", "okkk");
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            //Toast.makeText(CrewActivity.this, "2 Exist", Toast.LENGTH_SHORT).show();
                                            Log.d("bye10", document.getId() + " => " + document.getData());

                                            Log.d("bye11", "" + document.getData().get("name").toString());
                                            String crew2nd = document.getData().get("name").toString();
                                            crew5.setText(crew2nd);
                                            return;
                                        }
                                        Toast.makeText(CrewActivity.this, "Player Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    }
                                    return;
                                }
                            });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        updateCrewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crewTeamName,crewFirst, crewSecond, crewThird, crewFourth, crewFifth;
                crewTeamName = crewName.getText().toString().trim();
                crewFirst = crew1.getText().toString().trim();
                crewSecond = crew2.getText().toString().trim();
                crewThird = crew3.getText().toString().trim();
                crewFourth = crew4.getText().toString().trim();
                crewFifth = crew5.getText().toString().trim();

                if (TextUtils.isEmpty(crewTeamName)) {
                    Toast.makeText(CrewActivity.this, "Enter Crew/Team Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(crewSecond)) {
                    Toast.makeText(CrewActivity.this, "Enter Crew 2 Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(crewThird)) {
                    Toast.makeText(CrewActivity.this, "Enter Crew 3 Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(crewFourth)) {
                    Toast.makeText(CrewActivity.this, "Enter Crew 4 Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (crewFirst==crewSecond || crewFirst==crewThird || crewFirst==crewFourth || crewThird==crewSecond || crewFourth==crewSecond ||crewThird==crewFourth ){
                    Toast.makeText(CrewActivity.this, "Can't have two Same player in one crew", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("crewName",""+crewTeamName);
                hashMap.put("crew2",""+crewSecond);
                hashMap.put("crew3",""+crewThird);
                hashMap.put("crew4",""+crewFourth);
                hashMap.put("crew5",""+crewFifth);

                firestore.collection("users")
                        .document(FirebaseAuth.getInstance().getUid())
                        .collection("Crew")
                        .document("CrewDetails")
                        .update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CrewActivity.this, "Crew Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(CrewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        createCrewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crewTeamName,crewFirst, crewSecond, crewThird, crewFourth, crewFifth;
                crewTeamName = crewName.getText().toString().trim();
                crewFirst = crew1.getText().toString().trim();
                crewSecond = crew2.getText().toString().trim();
                crewThird = crew3.getText().toString().trim();
                crewFourth = crew4.getText().toString().trim();
                crewFifth = crew5.getText().toString().trim();
                Log.d("bye2", "okk");


                if (crewFirst==crewSecond || crewFirst==crewThird || crewFirst==crewFourth || crewThird==crewSecond || crewFourth==crewSecond ||crewThird==crewFourth ){
                    Toast.makeText(CrewActivity.this, "Can't have two Same player in one crew", Toast.LENGTH_SHORT).show();
                }
                else {
                    firestore.collection("users")
                            .document(FirebaseAuth.getInstance().getUid())
                            .collection("Crew")
                            .document("CrewDetails")
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                if (task.getResult().exists()) {
                                    Toast.makeText(CrewActivity.this, "You Already have Existing Crew", Toast.LENGTH_SHORT).show();

                                } else {
                                    if (TextUtils.isEmpty(crewTeamName)) {
                                        Toast.makeText(CrewActivity.this, "Enter Crew/Team Name", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (TextUtils.isEmpty(crewSecond)) {
                                        Toast.makeText(CrewActivity.this, "Enter Crew 2 Name", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (TextUtils.isEmpty(crewThird)) {
                                        Toast.makeText(CrewActivity.this, "Enter Crew 3 Name", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (TextUtils.isEmpty(crewFourth)) {
                                        Toast.makeText(CrewActivity.this, "Enter Crew 4 Name", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    final CrewModel crewModel = new CrewModel(crewTeamName, crewFirst, crewSecond, crewThird, crewFourth, crewFifth);
                                    Log.e("bye41",""+crewModel.toString());
                                    Log.e("bye41",""+crewModel.getCrew3().toString());
                                    firestore.collection("users")
                                            .document(FirebaseAuth.getInstance().getUid())
                                            .collection("Crew")
                                            .document("CrewDetails")
                                            .set(crewModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(CrewActivity.this, "Crew Created", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull @NotNull Exception e) {
                                                    Toast.makeText(CrewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}