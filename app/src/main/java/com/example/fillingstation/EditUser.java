package com.example.fillingstation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.fillingstation.Model.User;
import com.example.fillingstation.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.Logger;

public class EditUser extends AppCompatActivity{

     EditText uname, pwd, emailValue,phoneNo;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        uname = findViewById(R.id.edit_username);
//        pwd = findViewById(R.id.edit_password);
//        emailValue = findViewById(R.id.edit_email);
//        phoneNo = findViewById(R.id.edit_phone_no);

        Button updateUser = findViewById(R.id.update_button);

//        updateUser.setOnClickListener(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
        retrieveCurrentUserData();


//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            String email,phone,password;
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                User currentUser =dataSnapshot.child(currentFirebaseUser.getUid()).getValue(User.class);
////                emailValue.setText(currentUser.getEmail());
//                uname.setText(dataSnapshot.child(currentFirebaseUser.getUid()).child("username").getValue(String.class));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
//        emailValue.setText(user.getEmail());
//        phoneNo.setText(user.getPhoneNumber());
        //        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void retrieveCurrentUserData(){
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String userKey = user.getUid();

        mDb.child("user").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue(String.class);
                String phoneNumber =dataSnapshot.child("phoneNumber").getValue(String.class);
                String emailData=dataSnapshot.child("email").getValue(String.class);
                String passwordData=dataSnapshot.child("password").getValue(String.class);

                EditText usernameEditText,phoneNo,password,email;
                usernameEditText =findViewById(R.id.edit_username);
                phoneNo=findViewById(R.id.edit_phone_no);
                password=findViewById(R.id.edit_password);
                email =findViewById(R.id.edit_email);


                usernameEditText.setText(username);
                phoneNo.setText(phoneNumber);
                password.setText(passwordData);
                email.setText(emailData);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    public void updateUser(){

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String userKey = user.getUid();

        EditText usernameEditText,phoneNo,password,email;
        usernameEditText =findViewById(R.id.edit_username);
        phoneNo=findViewById(R.id.edit_phone_no);
        password=findViewById(R.id.edit_password);
        email=findViewById(R.id.edit_email);

        mDb.child("user").child(userKey).child("username").setValue(usernameEditText.getText().toString());
        mDb.child("user").child(userKey).child("email").setValue(email.getText().toString());
        mDb.child("user").child(userKey).child("password").setValue(password.getText().toString());
        mDb.child("user").child(userKey).child("phoneNumber").setValue(phoneNo.getText().toString());

        Toast.makeText(EditUser.this, "Successfully Updated User", Toast.LENGTH_LONG).show();


    }

//    @Override
    public void onClick(View v) {
        updateUser();
    }
}
