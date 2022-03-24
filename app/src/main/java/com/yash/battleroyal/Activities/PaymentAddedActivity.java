package com.yash.battleroyal.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yash.battleroyal.R;

public class PaymentAddedActivity extends AppCompatActivity {

    TextView amountTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_added);

        String txnAmountString = getIntent().getStringExtra("txnAmountString");

        amountTv = findViewById(R.id.amountTv);

        amountTv.setText(txnAmountString);



        long points = 1 * (Integer.parseInt(txnAmountString));
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));
    }
}