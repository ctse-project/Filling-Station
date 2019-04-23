package com.example.fillingstation;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Signin extends AppCompatActivity {

    EditText username, password;
    Button signin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.uname);
        password = findViewById(R.id.pass);
        signin = findViewById(R.id.signin);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference usertable = database.getReference("user");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog msg = new ProgressDialog(Signin.this);
                msg.setMessage("Validating");
                msg.show();

                usertable.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(username.getText().toString()).exists()){
                            msg.dismiss();

                            User user = dataSnapshot.child(username.getText().toString()).getValue(User.class);

                            if(user.getPassword().equals(password.getText().toString())){
                                Intent home = new Intent(Signin.this, Home.class);
                                startActivity(home);
                                finish();
                            }
                            else{
                                Toast.makeText(Signin.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            msg.dismiss();
                            Toast.makeText(Signin.this, "User does not exist", Toast.LENGTH_SHORT).show();
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
