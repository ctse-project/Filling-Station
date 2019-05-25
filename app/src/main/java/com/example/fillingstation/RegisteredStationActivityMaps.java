package com.example.fillingstation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fillingstation.Model.Station;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisteredStationActivityMaps extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    int PROXIMITY_RADIUS = 100000;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("station");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            checkLocationPermission();
//
//        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Station station = postSnapshot.getValue(Station.class);
                    String latLng= station.getLatlng();
                    String chainName=station.getChainname();
                    String splitLatLng[] =latLng.split(",");
                    double getLatitude = Double.parseDouble(splitLatLng[0]);
                    double getLongitud = Double.parseDouble(splitLatLng[1]);
                    LatLng mylocation = new LatLng(getLatitude, getLongitud);
                    mMap.addMarker(new MarkerOptions().position(mylocation).snippet(chainName).title("GAS STATION")).showInfoWindow();
//                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation,15));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





    public void onClick(View v)
    {
//        final List<Station> stationList = new ArrayList<>();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                        Station station = postSnapshot.getValue(Station.class);
//                        String latLng= station.getLatlng();
//                        String splitLatLng[] =latLng.split(",");
//                        double getLatitude = Double.parseDouble(splitLatLng[0]);
//                        double getLongitud = Double.parseDouble(splitLatLng[1]);
//                        Object dataTransfer[] = new Object[2];
//                        GetNearbyFillingStation getNearbyPlacesData = new GetNearbyFillingStation();
//                        mMap.clear();
//                        String gasStation = "gas_station";
////                        String url = getUrl(getLatitude, getLongitud);
//                        dataTransfer[0] = mMap;
//                        dataTransfer[1] = url;
//
//                        getNearbyPlacesData.execute(dataTransfer);
//                        Toast.makeText(RegisteredStationActivityMaps.this, latLng, Toast.LENGTH_SHORT).show();
//                    }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        Object dataTransfer[] = new Object[2];
//        GetNearbyFillingStation getNearbyPlacesData = new GetNearbyFillingStation();
//                mMap.clear();
//                String gasStation = "gas_station";
//                String url = getUrl(latitude, longitude);
//                dataTransfer[0] = mMap;
//                dataTransfer[1] = url;
//
//                getNearbyPlacesData.execute(dataTransfer);
//                Toast.makeText(RegisteredStationActivityMaps.this, "Showing Nearby Gas", Toast.LENGTH_SHORT).show();

    }

//
//    private String getUrl(double latitude , double longitude)
//    {
//
//        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//        googlePlaceUrl.append("location="+latitude+","+longitude);
//        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
////        googlePlaceUrl.append("&type="+nearbyPlace);
//        googlePlaceUrl.append("&sensor=true");
//        googlePlaceUrl.append("&key="+"AIzaSyBLEPBRfw7sMb73Mr88L91Jqh3tuE4mKsE");
//
//        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());
//
//        return googlePlaceUrl.toString();
//    }
//
//
//
//    public boolean checkLocationPermission()
//    {
//        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
//        {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
//            {
//                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
//            }
//            else
//            {
//                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
//            }
//            return false;
//
//        }
//        else
//            return true;
//    }
}
