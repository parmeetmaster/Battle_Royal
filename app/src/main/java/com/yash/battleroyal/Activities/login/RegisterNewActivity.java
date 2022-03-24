package com.yash.battleroyal.Activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Activities.MainActivity;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.databinding.ActivityRegisterNewBinding;

import java.util.Map;

public class RegisterNewActivity extends AppCompatActivity {

    float v = 0;
    Animation animation;
    ActivityRegisterNewBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Creating new account");

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass, name, characterID, mobileNumber, etReferPhno;

                email = binding.etUsername.getText().toString();
                pass = binding.etPassword.getText().toString();
                name = binding.etIGNName.getText().toString();
                characterID = binding.etCID.getText().toString();
                mobileNumber = binding.etMobilename.getText().toString();
                etReferPhno = binding.etReferPhno.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterNewActivity.this, "Enter your IN-Game Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(characterID)) {
                    Toast.makeText(RegisterNewActivity.this, "Enter your characterID", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterNewActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mobileNumber)) {
                    Toast.makeText(RegisterNewActivity.this, "Enter your Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                final User user = new User(name, email, pass, characterID, mobileNumber, etReferPhno);

                dialog.show();
                auth.createUserWithEmailAndPassword(email, pass).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String uid = task.getResult().getUser().getUid();

                                    database
                                            .collection("users")
                                            .document(uid)
                                            .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                      /*      database.collection("users")
                                                    .whereEqualTo("etReferPhno", user.getReferPhno())
                                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            Log.d("", document.getId() + " => " + document.getData());
                                                        }
                                                    } else {
                                                        Log.d("", "Error getting documents: ", task.getException());
                                                    }
                                                }
                                            });*/
                                            if (user.getReferPhno().isEmpty()) {
                                                if (task.isSuccessful()) {
                                                    dialog.dismiss();
                                                    startActivity(new Intent(RegisterNewActivity.this, MainActivity.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(RegisterNewActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                updateReferenceBcoins(user);
                                            }


                                        }
                                    });
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(RegisterNewActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterNewActivity.this, LoginNewActivity.class));
            }
        });


        binding.ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toNumber = "919993512187"; //Number without with country code and without '+' prifix
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" + toNumber + "?body=" + ""));
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });

        binding.ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse("https://www.youtube.com/channel/UCb4gUaJTLTsxbhH98-SqvOQ"));
                startActivity(intent);
            }
        });

        binding.ivInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.instagram.android");
                intent.setData(Uri.parse("https://www.instagram.com/battle_royal_official/"));
                startActivity(intent);
            }
        });

        binding.ivLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/NeB67Ava75")));
            }
        });


        binding.ivFacebook.setTranslationY(300);
        binding.ivInstagram.setTranslationY(300);
        binding.ivTwitter.setTranslationY(300);
        binding.ivLinkedIn.setTranslationY(300);
        binding.cardView.setTranslationX(300);
        binding.signupBtn.setTranslationX(700);

        binding.ivFacebook.setAlpha(v);
        binding.ivInstagram.setAlpha(v);
        binding.ivTwitter.setAlpha(v);
        binding.ivLinkedIn.setAlpha(v);
        binding.cardView.setAlpha(v);
        binding.signupBtn.setAlpha(v);


        binding.cardView.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        binding.signupBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();

        binding.ivFacebook.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        binding.ivInstagram.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        binding.ivTwitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        binding.ivLinkedIn.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }

    void updateReferenceBcoins(User user) {
        FirebaseFirestore database;
        database = FirebaseFirestore.getInstance();
        if (!user.getReferPhno().isEmpty()) {
            // get refer phone number
            database
                    .collection("users")
                    .whereEqualTo("mobileNumber", user.getReferPhno())
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot documentSnapshot = task.getResult();
                        Map<String, Object> map = documentSnapshot.getDocuments().get(0).getData();
                        // update referal user points
                        double refernceUserBcoins = Double.parseDouble(map.get("bCoins").toString());
                        refernceUserBcoins = refernceUserBcoins + 2; // change number here to update rewards
                        database.collection("users")
                                .document(documentSnapshot.getDocuments().get(0).getId()).
                                update("bCoins", refernceUserBcoins).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Reference bonus add", Toast.LENGTH_SHORT).show();
                               updateOwnBcoins(user);
                                }
                            }
                        });
                        //   Log.d("sap",documentSnapshot.getDocuments().get(0).getData().toString());

                      /*  database.collection("users")
                                .document(documentSnapshot.getDocuments()
                                        .get(0).getId()).update({'bCoins':});*/
                    }else{
                        dialog.dismiss();
                        startActivity(new Intent(RegisterNewActivity.this, MainActivity.class));
                        finish();
                    }
                }
            });


        }
    }

    void updateOwnBcoins(User user) {
        FirebaseFirestore database;
        database = FirebaseFirestore.getInstance();
        if (!user.getReferPhno().isEmpty()) {
            // get refer phone number
            database
                    .collection("users")
                    .whereEqualTo("mobileNumber", user.getMobileNumber())
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot documentSnapshot = task.getResult();
                        Map<String, Object> map = documentSnapshot.getDocuments().get(0).getData();
                        // update referal user points
                        double refernceUserBcoins = Double.parseDouble(map.get("bCoins").toString());
                        refernceUserBcoins = refernceUserBcoins + 1; // change number here to update rewards

                        database.collection("users")
                                .document(documentSnapshot.getDocuments().get(0).getId()).
                                update("bCoins", refernceUserBcoins).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "User bonus added", Toast.LENGTH_SHORT).show();
                                   // move next screen
                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        startActivity(new Intent(RegisterNewActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterNewActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        //   Log.d("sap",documentSnapshot.getDocuments().get(0).getData().toString());

                      /*  database.collection("users")
                                .document(documentSnapshot.getDocuments()
                                        .get(0).getId()).update({'bCoins':});*/
                    }
                }
            });


        }
    }


}