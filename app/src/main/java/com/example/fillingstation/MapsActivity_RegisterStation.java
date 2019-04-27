package com.example.fillingstation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity_RegisterStation extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastloc;
    private Marker currentUserMarker;

    private static  final  int request_user_location_code = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps__register_station);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkUserLocationPerm();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button regloc = findViewById(R.id.register);

        regloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String uname = intent.getStringExtra("USERNAME");

                Log.e("LAST LOCATION", lastloc.toString());

                Intent regi = new Intent(MapsActivity_RegisterStation.this, RegisterStation.class);
                regi.putExtra("USERNAME", uname);
                regi.putExtra("LOCATION", lastloc.toString());
                startActivity(regi);

            }
        });
    }


    public void onClick(View v){
        switch(v.getId()){
            case R.id.search_loc:
                EditText loc = findViewById(R.id.location_search);
                String locString = loc.getText().toString();

                List<Address> locationList = null;
                MarkerOptions markerOptions  = new MarkerOptions();

                if(!TextUtils.isEmpty(locString)){
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        locationList = geocoder.getFromLocationName(locString, 6);
                        if(locationList != null){
                            for(int i=0; i<locationList.size(); i++){
                                Address userloc = locationList.get(i);
                                LatLng latLng = new LatLng(userloc.getLatitude(), userloc.getLongitude());

                                Log.e("LOCATION ", latLng.toString());

                                markerOptions.position(latLng);
                                markerOptions.title(locString);
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                            }
                        }
                        else{
                            Toast.makeText(this,"Location not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(this, "Please type the location", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){

            buildingGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }

    }

    public boolean checkUserLocationPerm(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_user_location_code);

            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_user_location_code);
            }
            return false;
        }

        else{
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case request_user_location_code:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(googleApiClient == null){
                            buildingGoogleApiClient();
                        }

                        mMap.setMyLocationEnabled(true);
                    }
                }

                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT);
                }
                return;

        }
    }

    protected  synchronized void buildingGoogleApiClient(){

        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        googleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {

        lastloc = location;
        if (currentUserMarker != null){
            currentUserMarker.remove();

        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        Log.e("LOCATION 1 ", latLng.toString());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current User Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        currentUserMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(14));

        if(googleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
