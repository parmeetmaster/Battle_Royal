package com.yash.battleroyal.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.yash.battleroyal.Model.ServiceWrapper;
import com.yash.battleroyal.Model.Token_Res;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.Model.WithdrawRequest;
import com.yash.battleroyal.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static easypay.manager.PaytmAssist.getContext;

public class PaytmActivity extends AppCompatActivity {

    TextView currentCoinsTv, tenTv,fiftyTv,hundredTv,twoHundredTv,addBtn,
            currentCoinsWTv, tenWTv,fiftyWTv,hundredWTv,twoHundredWTv,withdrawBtn,
            addbalanceTv,withdrawTv,BonusCoinsTv;
    EditText amountEt, amountWEt, numberEt;
    CardView cardView2, cardView3;
    private ProgressBar progressBar;
    private String midString = "ASDZvV16634782725936", txnAmountString="", orderIdString="", txnTokenString="";
    private Integer ActivityRequestCode = 2;
    ProgressDialog dialog;

    FirebaseFirestore database;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        currentCoinsWTv = findViewById(R.id.currentCoinsWTv);
        addbalanceTv = findViewById(R.id.addbalanceTv);
        withdrawTv = findViewById(R.id.withdrawTv);
        tenWTv = findViewById(R.id.tenWTv);
        fiftyWTv = findViewById(R.id.fiftyWTv);
        hundredWTv = findViewById(R.id.hundredWTv);
        twoHundredWTv = findViewById(R.id.twoHundredWTv);
        amountWEt = findViewById(R.id.amountWEt);
        withdrawBtn = findViewById(R.id.withdrawBtn);
        currentCoinsTv = findViewById(R.id.currentCoinsTv);
        BonusCoinsTv = findViewById(R.id.BonusCoinsTv);
        tenTv = findViewById(R.id.tenTv);
        fiftyTv = findViewById(R.id.fiftyTv);
        hundredTv = findViewById(R.id.hundredTv);
        twoHundredTv = findViewById(R.id.twoHundredTv);
        numberEt = findViewById(R.id.numberEt);
        amountEt = findViewById(R.id.amountEt);
        addBtn = findViewById(R.id.addBtn);
        dialog = new ProgressDialog(PaytmActivity.this);
        dialog.setMessage("Processing. Please Wait");

        //generate umique ORDER ID
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min = 1000, max = 9999;

        int randomNum = rand.nextInt((max - min)+1) + min;
        orderIdString = date+String.valueOf(randomNum);

        database = FirebaseFirestore.getInstance();

        cardView3.setVisibility(View.GONE);

        addbalanceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView2.setVisibility(View.VISIBLE);
                cardView3.setVisibility(View.GONE);


                addbalanceTv.setTextColor(getResources().getColor(R.color.white));
                addbalanceTv.setBackgroundColor(getResources().getColor(R.color.darkOrange));

                withdrawTv.setTextColor(getResources().getColor(R.color.black));
                withdrawTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });

        withdrawTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView3.setVisibility(View.VISIBLE);
                cardView2.setVisibility(View.GONE);


                withdrawTv.setTextColor(getResources().getColor(R.color.white));
                withdrawTv.setBackgroundColor(getResources().getColor(R.color.darkOrange));

                addbalanceTv.setTextColor(getResources().getColor(R.color.black));
                addbalanceTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });




        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);

                currentCoinsTv.setText(user.getCoins()+"");
                currentCoinsWTv.setText(user.getCoins()+"");
                BonusCoinsTv.setText(user.getbCoins()+"");

                //binding.currentCoins.setText(user.getCoins() + "");

            }
        });


        tenTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEt.setText("10");
            }
        });
        tenWTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountWEt.setText("10");
            }
        });

        fiftyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEt.setText("50");
            }
        });
        fiftyWTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountWEt.setText("50");
            }
        });

        hundredTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEt.setText("100");
            }
        });
        hundredWTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountWEt.setText("100");
            }
        });

        twoHundredTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEt.setText("200");
            }
        });
        twoHundredWTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountWEt.setText("200");
            }
        });

        withdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();
                String money = amountWEt.getText().toString();
                String paytm = numberEt.getText().toString();
                if (Integer.valueOf(money)< 100) {
                    Toast.makeText(PaytmActivity.this, "Minimum 100 Coins Required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (paytm.isEmpty()){
                    dialog.dismiss();
                    Toast.makeText(PaytmActivity.this, "Enter Your Paytm Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (user.getCoins()< Integer.valueOf(money)){
                    dialog.dismiss();
                    Toast.makeText(PaytmActivity.this, "Not Enough Coins", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (Integer.valueOf(money)>1000){
                    dialog.dismiss();
                    Toast.makeText(PaytmActivity.this, "Can't Withdraw more than 1000 per day", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.show();
                if(Integer.valueOf(money) >= 100) {
                    WithdrawRequest request = new WithdrawRequest(uid, paytm, user.getName(),money);
                    database
                            .collection("withdraws")
                            .document(uid)
                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            Intent intent = new Intent(PaytmActivity.this, WithdrawActivity.class);
                            intent.putExtra("money", money);
                            startActivity(intent);
                            Toast.makeText(getContext(), "Request sent successfully.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txnAmountString = amountEt.getText().toString();
                if (Integer.valueOf(txnAmountString) < 0){
                    Toast.makeText(PaytmActivity.this, "Minimum to be added is Rs 10.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String error="";
                if (orderIdString.equalsIgnoreCase("")){
                    error="Enter Valid Order Id here\n";
                    Toast.makeText(PaytmActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                else if (txnAmountString.equalsIgnoreCase("")){
                    error="Enter Valid Amount Here\n";
                    Toast.makeText(PaytmActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                else {
                    gettoken();
                }
            }
        });

    }

    private void gettoken() {
        Log.e(TAG, "get token first");
        dialog.show();
        ServiceWrapper serviceWrapper = new ServiceWrapper(null);
        Call<Token_Res> call = serviceWrapper.getTokenCall("12345", midString, orderIdString, txnAmountString);
        call.enqueue(new Callback<Token_Res>() {
            @Override
            public void onResponse(Call<Token_Res> call, Response<Token_Res> response) {
                Log.e(TAG, " respo "+ response.isSuccessful() );
                dialog.dismiss();
                //progressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful() && response.body()!=null){
                        if (response.body().getBody().getTxnToken()!="") {
                            Log.e(TAG, " transaction token : "+response.body().getBody().getTxnToken());
                            startPaytmPayment(response.body().getBody().getTxnToken());
                        }else {
                            Toast.makeText(PaytmActivity.this, "Network Error! Try Again!", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, " Token status false");
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(PaytmActivity.this, "Network Error..Try Again!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, " error in Token Res "+e.toString());
                }
            }

            @Override
            public void onFailure(Call<Token_Res> call, Throwable t) {
                Toast.makeText(PaytmActivity.this, "Network Error..Try again!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Log.e(TAG, " response error "+t.toString());
            }

        });
}

    private void startPaytmPayment(String token) {
        txnTokenString = token;
        // for test mode use it
         //String host = "https://securegw-stage.paytm.in/";
        // for production mode use it
        String host = "https://securegw.paytm.in/";
        String orderDetails = "MID: " + midString + ", OrderId: " + orderIdString + ", TxnToken: " + txnTokenString
                + ", Amount: " + txnAmountString;
        //Log.e(TAG, "order details "+ orderDetails);

        String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+orderIdString;
        Log.e(TAG, " callback URL "+callBackUrl);

        PaytmOrder paytmOrder = new PaytmOrder(orderIdString, midString, txnTokenString, txnAmountString, callBackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){
            @Override
            public void onTransactionResponse(Bundle bundle) {
                Toast.makeText(PaytmActivity.this, "P success", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Response (onTransactionResponse) : "+bundle.toString());
            }

            @Override
            public void networkNotAvailable() {
                Log.e(TAG, "network not available ");
                Toast.makeText(PaytmActivity.this, "yes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorProceed(String s) {
                Log.e(TAG, " onErrorProcess "+s.toString());
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e(TAG, "Clientauth "+s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e(TAG, " UI error "+s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e(TAG, " error loading web "+s+"--"+s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e(TAG, "backPress ");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e(TAG, " transaction cancel "+s);
            }
        });

        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this, ActivityRequestCode);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG ," result code "+resultCode);
        // -1 means successful  // 0 means failed
        // one error is - nativeSdkForMerchantMessage : networkError
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }
            if (resultCode== -1){
                Toast.makeText(this, "Payment Successfully Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PaytmActivity.this, PaymentAddedActivity.class);
                intent.putExtra("txnAmountString", txnAmountString);
                startActivity(intent);
            }


            Log.e(TAG, " data "+  data.getStringExtra("nativeSdkForMerchantMessage"));
            Log.e(TAG, " data response - "+data.getStringExtra("response"));


/*
 data response - {"BANKNAME":"WALLET","BANKTXNID":"1395841115",
 "CHECKSUMHASH":"7jRCFIk6eRmrep+IhnmQrlrL43KSCSXrmMP5pH0hekXaaxjt3MEgd1N9mLtWyu4VwpWexHOILCTAhybOo5EVDmAEV33rg2VAS/p0PXdk\u003d",
 "CURRENCY":"INR","GATEWAYNAME":"WALLET","MID":"EAcR4116","ORDERID":"100620202152",
 "PAYMENTMODE":"PPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS",
 "TXNAMOUNT":"2.00","TXNDATE":"2020-06-10 16:57:45.0","TXNID":"202006101112128001101683631290118"}
  */

//
//            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage")
//                    + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();

        }else{
            Log.e(TAG, " payment failed");
        }

    }




}