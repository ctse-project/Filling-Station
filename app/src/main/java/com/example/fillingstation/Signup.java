package com.example.fillingstation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fillingstation.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.DataFormatException;

public class Signup extends AppCompatActivity {

    EditText uname, pwd, repwd,phoneNo;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


       // FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("mege");

       /// myRef.setValue("Helljjjjjjjjjo");

        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
        repwd = findViewById(R.id.repassword);
        phoneNo = findViewById(R.id.phone_no);

        signup = findViewById(R.id.signup);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference usertable = database.getReference("user");


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog msg = new ProgressDialog(Signup.this);
                msg.setMessage("validating");
                msg.show();

                usertable.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(uname.getText().toString()).exists()){
                            msg.dismiss();
                            Toast.makeText(Signup.this, "Sign up Successfull", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            msg.dismiss();

                            if(!pwd.getText().toString().equals(repwd.getText().toString())){
                                Toast.makeText(Signup.this, "Passwords does not match", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                User user = new User(uname.getText().toString(), pwd.getText().toString(),repwd.getText().toString(),phoneNo.getText().toString());
                                usertable.child(uname.getText().toString()).setValue(user);
                                Toast.makeText(Signup.this, "Sign up Successfull", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}