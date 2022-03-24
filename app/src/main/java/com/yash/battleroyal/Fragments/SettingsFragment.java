package com.yash.battleroyal.Fragments;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Activities.AdminActivity;
import com.yash.battleroyal.Activities.ContactUsActivity;
import com.yash.battleroyal.Activities.LeaderboardActivity;
import com.yash.battleroyal.Activities.LoginActivity;
import com.yash.battleroyal.Activities.MainActivity;
import com.yash.battleroyal.Activities.PaytmActivity;
import com.yash.battleroyal.Activities.RegisterActivity;
import com.yash.battleroyal.Activities.RulesActivity;
import com.yash.battleroyal.Activities.login.LoginNewActivity;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.FragmentSettingsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SettingsFragment extends Fragment {

    com.yash.battleroyal.databinding.FragmentSettingsBinding binding;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());

        firebaseAuth = FirebaseAuth.getInstance();

        database = FirebaseFirestore.getInstance();


        binding.supportTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsFragment.this.getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });

        binding.leaderboardTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsFragment.this.getActivity(), LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        binding.policiesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsFragment.this.getActivity(), RulesActivity.class);
                startActivity(intent);
            }
        });

        binding.walletTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsFragment.this.getActivity(), PaytmActivity.class);
                startActivity(intent);
            }
        });
        binding.adminTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.collection("users").document(firebaseAuth.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (document.get("admin") != null) {
                                    Intent intent = new Intent(SettingsFragment.this.getActivity(), AdminActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SettingsFragment.this.getContext(), "You are Not an Organizer", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(SettingsFragment.this.getContext(), "Whatsapp us to get this role", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });

        binding.inviteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
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
        });


//        binding.referralTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingsFragment.this.getActivity(), ReferralActivity.class);
//                startActivity(intent);
//            }
//        });

        binding.logoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(SettingsFragment.this.getContext(), "LogOut Successfully!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingsFragment.this.getContext(), LoginNewActivity.class);
                startActivity(intent);
            }
        });


        binding.ytTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse("https://www.youtube.com/channel/UCb4gUaJTLTsxbhH98-SqvOQ"));
                startActivity(intent);
            }
        });

        binding.igTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.instagram.android");
                intent.setData(Uri.parse("https://www.instagram.com/battle_royal_official/"));
                startActivity(intent);
            }
        });


        binding.faqTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SettingsFragment.this.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_popup_faq);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        binding.aboutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SettingsFragment.this.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_popup_about_us);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.show();
            }
        });


        return binding.getRoot();

    }
}


