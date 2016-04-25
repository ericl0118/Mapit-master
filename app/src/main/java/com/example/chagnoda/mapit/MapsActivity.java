package com.example.chagnoda.mapit;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_maps);
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
        Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit/Groups");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()){
                    Log.d("My App: ", "1");
                    Log.d("My App: ", groupSnapshot.toString());
                    double latitude = (double)groupSnapshot.child("groupeLatitude").getValue();
                    double longitude = (double)groupSnapshot.child("groupeLongitude").getValue();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)));
                    CircleOptions circleOptions = new CircleOptions()
                            .center(new LatLng(latitude, longitude))
                            .radius(10000).fillColor(Color.BLUE).strokeColor(Color.BLUE);
                    Circle circle = mMap.addCircle(circleOptions);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }
}
