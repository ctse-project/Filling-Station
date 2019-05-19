package com.example.fillingstation;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Signin extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    Button signin;
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.uname);
        password = findViewById(R.id.pass);
        signin = findViewById(R.id.signin);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference usertable = database.getReference("user");

//        signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final ProgressDialog msg = new ProgressDialog(Signin.this);
//                msg.setMessage("Validating");
//                msg.show();
//
//                usertable.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        if(dataSnapshot.child(username.getText().toString()).exists()){
//                            msg.dismiss();
//
//                            User user = dataSnapshot.child(username.getText().toString()).getValue(User.class);
//
//                            if(user.getPassword().equals(password.getText().toString())){
//
//                                Intent home = new Intent(Signin.this, Home.class);
//                                Intent editUser = new Intent(Signin.this,EditUser.class);
//                                editUser.putExtra("USERNAME",username.getText().toString());
//                                home.putExtra("USERNAME", username.getText().toString());
////                                startActivity(home);
////                                Intent editUser = new Intent(Signin.this,EditUser.class);
//                                startActivity(editUser);
//                                finish();
//                            }
//                            else{
//                                Toast.makeText(Signin.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        else{
//                            msg.dismiss();
//                            Toast.makeText(Signin.this, "User does not exist", Toast.LENGTH_SHORT).show();
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

    private void userLogin(){
        String email = username.getText().toString().trim();
        String passworda  = password.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(passworda)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

//        progressDialog.setMessage("Registering Please Wait...");
//        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, passworda)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            Intent home = new Intent(Signin.this, Home.class);
                            home.putExtra("USERNAME", username.getText().toString());
                            startActivity(home);
//                            startActivity(new Intent(getApplicationContext(), MapsActivity_RegisterStation.class));
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        userLogin();
    }
}