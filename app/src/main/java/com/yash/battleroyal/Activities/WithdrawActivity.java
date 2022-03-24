package com.yash.battleroyal.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;

public class WithdrawActivity extends AppCompatActivity {

    User user;
    FirebaseFirestore database;
    TextView amountTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        String uid = FirebaseAuth.getInstance().getUid();


        String money = getIntent().getStringExtra("money");

        amountTv = findViewById(R.id.amountTv);

        amountTv.setText(money);

        long points = -1 * (Integer.parseInt(money));
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));
    }
}