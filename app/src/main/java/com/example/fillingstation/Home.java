package com.example.fillingstation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_logout) {
            Intent SignIn = new Intent(Home.this, Signin.class);
            SignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(SignIn);
        }
        if (id == R.id.nav_home){

        }
        if (id == R.id.nav_register){
            Intent intent = getIntent();
            String uname = intent.getStringExtra("USERNAME");

            Intent register = new Intent(Home.this, MapsActivity_RegisterStation.class);

            register.putExtra("USERNAME", uname);

            startActivity(register);
        }

        if(id == R.id.edit_user){

            Intent editUser = new Intent(Home.this, EditUser.class);
            startActivity(editUser);


        }

        if(id == R.id.find_filling_station){

            Intent fillingStation = new Intent(Home.this,MapsActivity.class);
            startActivity(fillingStation);
        }

        if(id == R.id.deactivate_user){

//            Intent deactivateUser = new Intent(Home.this,)
            DeactivateUser deactivateUser = new DeactivateUser();
            deactivateUser.deleteUser();
            Intent goToSingIn = new Intent(Home.this,Signin.class);
            Toast.makeText(getApplicationContext(), "User Deactivated", Toast.LENGTH_LONG).show();
            startActivity(goToSingIn);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
