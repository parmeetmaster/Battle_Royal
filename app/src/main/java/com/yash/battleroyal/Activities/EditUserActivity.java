package com.yash.battleroyal.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.yash.battleroyal.R;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class EditUserActivity extends AppCompatActivity {

    private Button updateBtn;
    private EditText nameBox, emailBox, idBox , mobileBox;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String userID;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        String uid = FirebaseAuth.getInstance().getUid();




//        AdRequest adRequest = new AdRequest.Builder().build();
//        InterstitialAd.load(this, getString(R.string.editUserAd), adRequest, new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                super.onAdLoaded(interstitialAd);
//                mInterstitialAd = interstitialAd;
//                mInterstitialAd.show(EditUserActivity.this);
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//            }
//        });



        nameBox = findViewById(R.id.nameBox);
        emailBox = findViewById(R.id.emailBox);
        idBox = findViewById(R.id.idBox);
        mobileBox = findViewById(R.id.mobileBox);
        updateBtn = findViewById(R.id.updateBtn);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseAuth.getCurrentUser().getUid();
        dialog = new ProgressDialog(EditUserActivity.this);
        dialog.setMessage("Updating. Please Wait");


        DocumentReference reference = firestore.collection("users").document(userID);
        reference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nameBox.setText(value.getString("name"));
                emailBox.setText(value.getString("email"));
                idBox.setText(value.getString("characterID"));
                mobileBox.setText(value.getString("mobileNumber"));
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                if (nameBox.getText().toString().isEmpty() || emailBox.getText().toString().isEmpty() ||idBox.getText().toString().isEmpty() ||mobileBox.getText().toString().isEmpty()){
                    Toast.makeText(EditUserActivity.this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email= emailBox.getText().toString();
                firebaseUser.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference = firestore.collection("users").document(firebaseUser.getUid());
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("email",""+email);
                        hashMap.put("name",nameBox.getText().toString());
                        hashMap.put("characterID",idBox.getText().toString());
                        hashMap.put("mobileNumber",mobileBox.getText().toString());
                        documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditUserActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                                dialog.dismiss();
                            }

                        });
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditUserActivity.this, "Email Already Exist", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }
}