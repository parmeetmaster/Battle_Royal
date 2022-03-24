package com.yash.battleroyal.Activities.Organizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yash.battleroyal.Model.CrewModel;
import com.yash.battleroyal.Model.MatchModel;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityNewTierMatchBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class NewTierMatchActivity extends AppCompatActivity {

    ActivityNewTierMatchBinding binding;

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

        binding = ActivityNewTierMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NewTierMatchActivity.this, android.R.layout.simple_list_item_1,getResources()
                .getStringArray(R.array.tierList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.tierSpinner.setAdapter(myAdapter);





        String uid = FirebaseAuth.getInstance().getUid();


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
        String date = binding.dateNewTv.getText().toString();
        String map = binding.mapNewTv.getText().toString();
        String time = binding.timeNewTv.getText().toString();
        String name = map+" "+date+ " "+time;

        binding.profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        binding.joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MatchModel model = new MatchModel(uid, name, tier);
                String uid = FirebaseAuth.getInstance().getUid();
                progressDialog.setMessage("Uploding image");
                progressDialog.show();
                StorageReference reference = storageReference.child("images/" + uid);
                reference.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri imageUri = uri;
                                progressDialog.dismiss();
                                Snackbar.make(findViewById(android.R.id.content), "Image uploaded.", Snackbar.LENGTH_SHORT).show();
                                //Toast.makeText(NewTierMatchActivity.this, "Upload Done", Toast.LENGTH_LONG).show();
                                //After upload Complete we have to store the Data to firestore.

                                String tier = binding.tierSpinner.getSelectedItem().toString();
                                String date = binding.dateNewTv.getText().toString();
                                String map = binding.mapNewTv.getText().toString();
                                String time = binding.timeNewTv.getText().toString();
                                String name1 = map+" "+date+" "+time;
                                Map<String, Object> file = new HashMap<>();
                                file.put("matchImage", imageUri.toString());
                                file.put("matchName", name1);//
                                database.collection("organizer")
                                        .document(uid)
                                        .collection(tier).document(name1)
                                        .set(file)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {

                                            @Override
                                            public void onSuccess(Void aVoid) {
                                               // Log.d(TAG, "DocumentSnapshot successfully written!");

                                                String tier = binding.tierSpinner.getSelectedItem().toString();
                                                String date = binding.dateNewTv.getText().toString();
                                                String map = binding.mapNewTv.getText().toString();
                                                String time = binding.timeNewTv.getText().toString();
                                                String name1 = map+" "+date+" "+time;
                                                Map<String, Object> file = new HashMap<>();
                                                file.put("matchImage", imageUri.toString());
                                                file.put("matchName", name1);//
                                                database.collection(tier).document(name1)
                                                        .set(file)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                  @Override
                                                                                  public void onSuccess(Void unused) {

                                                                                      Toast.makeText(NewTierMatchActivity.this, "Added Successful", Toast.LENGTH_SHORT).show();
                                                                                  }
                                                                              });

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
                })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                                double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                                progressDialog.setMessage("Progress"+(int) progressPercent+"%");
                            }
                        });
            }
        });
    }

    private void choosePicture() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            image_uri = data.getData();
            binding.profileIv.setImageURI(image_uri);
          //  uploadPicture();
        }
    }
}