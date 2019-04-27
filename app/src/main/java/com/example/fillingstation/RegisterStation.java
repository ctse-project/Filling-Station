package com.example.fillingstation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fillingstation.Model.Station;
import com.example.fillingstation.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterStation extends AppCompatActivity {

    Button sub;
    EditText chainName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference stationtable = database.getReference("station");

        sub = findViewById(R.id.submit);
        chainName = findViewById(R.id.chain);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String uname = intent.getStringExtra("USERNAME");

                String latlng = intent.getStringExtra("LOCATION");

                final String loc=latlng.substring(15, 33);

                Log.e("USERNAME_____________", uname);
                Log.e("LATLNG_____________", latlng);
                Log.e("LATLNG_____________", loc);

                String cname = chainName.getText().toString();

                Log.e("CHAINNNNNNNNN ", cname);

                if (!TextUtils.isEmpty(cname)){

                    Log.e("Not empty ", cname);

                    final ProgressDialog msg = new ProgressDialog(RegisterStation.this);
                    msg.setMessage("Processing");
                    msg.show();

                    stationtable.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Station station = new Station(chainName.getText().toString(), loc, uname);
                            stationtable.child(chainName.getText().toString()).setValue(station);
                            msg.dismiss();
                            Toast.makeText(RegisterStation.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please Enter the Chain Name",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}
