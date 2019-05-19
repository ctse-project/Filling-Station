package com.example.fillingstation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fillingstation.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.DataFormatException;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    EditText uname, pwd, emailValue,phoneNo;
    Button signup;
    private ProgressDialog progressDialog;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth =FirebaseAuth.getInstance();
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("mege");

       /// myRef.setValue("Helljjjjjjjjjo");

        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
        emailValue = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phone_no);

        signup = findViewById(R.id.signup);

        progressDialog = new ProgressDialog(this);

        signup.setOnClickListener(this);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference usertable = database.getReference("user");


//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final ProgressDialog msg = new ProgressDialog(Signup.this);
//                msg.setMessage("validating");
//                msg.show();
//
//                usertable.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.child(uname.getText().toString()).exists()){
//                            msg.dismiss();
//                            Toast.makeText(Signup.this, "Sign up Successfull", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                        else{
//                            msg.dismiss();
//
//                            if(!pwd.getText().toString().equals(repwd.getText().toString())){
//                                Toast.makeText(Signup.this, "Passwords does not match", Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                User user = new User(uname.getText().toString(), pwd.getText().toString(),repwd.getText().toString(),phoneNo.getText().toString());
//                                usertable.child(uname.getText().toString()).setValue(user);
//                                Toast.makeText(Signup.this, "Sign up Successfull", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });
    }

    private void registerUser(){

        //getting email and password from edit texts
        final String email = emailValue.getText().toString().trim();
        final String password  = pwd.getText().toString().trim();
        final String phone = phoneNo.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(uname.getText().toString(), pwd.getText().toString(),emailValue.getText().toString(),phoneNo.getText().toString());
                            FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Signup.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                    } else {
                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        //calling register method on click
        registerUser();
    }
}