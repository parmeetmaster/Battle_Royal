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
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.yash.battleroyal.Activities.ForgetPasswordActivity;
import com.yash.battleroyal.Activities.LoginActivity;
import com.yash.battleroyal.Activities.MainActivity;
import com.yash.battleroyal.Activities.RegisterActivity;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityLoginNewBinding;

public class LoginNewActivity extends AppCompatActivity {

    ActivityLoginNewBinding binding;
    float v=0;

    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in");

        if(auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginNewActivity.this, MainActivity.class));
            finish();
        }

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;
                email = binding.etUsername.getText().toString();
                pass = binding.etPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginNewActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginNewActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.show();

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful()) {
                            startActivity(new Intent(LoginNewActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginNewActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        binding.tvIForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginNewActivity.this, ForgetPassNewActivity.class));
            }
        });

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginNewActivity.this, RegisterNewActivity.class));
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
                Intent intent = new  Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse("https://www.youtube.com/channel/UCb4gUaJTLTsxbhH98-SqvOQ"));
                startActivity(intent);
            }
        });

        binding.ivInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(Intent.ACTION_VIEW);
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
        binding.loginBtn.setTranslationX(700);

        binding.ivFacebook.setAlpha(v);
        binding.ivInstagram.setAlpha(v);
        binding.ivTwitter.setAlpha(v);
        binding.ivLinkedIn.setAlpha(v);
        binding.cardView.setAlpha(v);
        binding.loginBtn.setAlpha(v);


        binding.cardView.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        binding.loginBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();

        binding.ivFacebook.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        binding.ivInstagram.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        binding.ivTwitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        binding.ivLinkedIn.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }
}