package com.yash.battleroyal.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Creating new account");


        binding.createNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass, name, characterID,mobileNumber;

                email = binding.emailBox.getText().toString();
                pass = binding.passwordBox.getText().toString();
                name = binding.nameBox.getText().toString();
                characterID = binding.idBox.getText().toString();
                mobileNumber = binding.mobileBox.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(characterID)) {
                    Toast.makeText(RegisterActivity.this, "Enter your characterID", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mobileNumber)) {
                    Toast.makeText(RegisterActivity.this, "Enter your Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                final User user = new User(name ,email,pass,characterID,mobileNumber,"update activity to show data here");

                dialog.show();
                auth.createUserWithEmailAndPassword(email, pass).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String uid = task.getResult().getUser().getUid();
                            database
                                    .collection("users")
                                    .document(uid)
                                    .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        dialog.dismiss();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });



    }

    void UpdateBitpoints(){
        Log.d("emails ", auth.getCurrentUser().getEmail());
        FirebaseFirestore database;
        database = FirebaseFirestore.getInstance();
        database
                .collection("users")
                .whereEqualTo("email", auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {


                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("commplete data", document.getId() + " => " + document.getData().get("mobileNumber"));
                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        String body = "Hey! I am playing Battle Royal. Want to Battle Against Me.. Join me and play this exciting BGMI Custom Rooms for Free.\n" +
                                "Use my refer code " + document.getData().get("mobileNumber") + "  And earn Bonus coins. Download Now And Earn Real Money Too! https://brgp.in/battle-royal.apk";
                        String sub = "Your Subject";
                        myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
                        myIntent.putExtra(Intent.EXTRA_TEXT, body);
                        startActivity(Intent.createChooser(myIntent, "Share Using"));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }


}