package com.example.chagnoda.mapit;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    View mapit_button;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    MyLocationListener locationListener = new MyLocationListener();
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mapit_button = findViewById(R.id.mapitbutton);
        mapit_button.setOnClickListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            startActivity(new Intent("com.example.chagnoda.mapit.MapsActivity"));
        } else if (id == R.id.nav_friends) {
            startActivity(new Intent("com.example.chagnoda.mapit.FriendListActivity"));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent("com.example.chagnoda.mapit.ProfileActivity"));
        } else if (id == R.id.nav_parametre) {
            startActivity(new Intent("com.example.chagnoda.mapit.SettingActivity"));
        } else if (id == R.id.nav_logout) {
            final Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit");
            ref.unauth();
            Toast.makeText(getApplicationContext(), "You have logged out ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent("com.example.chagnoda.mapit.LoginActivity"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit/Profiles");
        final AuthData authData = ref.getAuth();
        Log.d("My App: ", "1");
        findAllGroupeAroundMe();
        Log.d("My App: ", "2");


        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot profileSnapshot : snapshot.getChildren()) {
                    if (profileSnapshot.child("userID").getValue().toString().equals(authData.getUid().toString())) {
                        String username = profileSnapshot.child("userName").getValue().toString();
                        String email = profileSnapshot.child("email").getValue().toString();
                        String password = profileSnapshot.child("password").getValue().toString();
                        String userID = profileSnapshot.child("userID").getValue().toString();
                        Log.d("My App: ", "5");
                        HashMap<String, String> friendlist = new HashMap<String, String>();
                        for (DataSnapshot friendlistSnapshot : profileSnapshot.child("friendsList").getChildren()) {
                            Log.d("My App: ", friendlistSnapshot.getKey().toString()+" , "+ friendlistSnapshot.getValue().toString());
                            friendlist.put(friendlistSnapshot.getKey().toString(), friendlistSnapshot.getValue().toString());
                        }
                        Log.d("My App: ", "6");
                        Profile copy_profile = new Profile(username, email, password, userID, friendlist);
                        Log.d("My App: ", "7");
                        createGroup(copy_profile);
                    } else {
                        Log.d("My App:", "User Not in the data base can't find profile to use it");
                    }

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("My App:", firebaseError.toString());
            }


        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GPSupdates();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "GPS_PERMISSION Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public Location GPSupdates() {//changer type de retour a location pour l'utilise dans la methode create groupe
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return null;
        }
        // LocationManager service de geolocalisation
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        // Recupere les coordonnees dans location, location.getLatitude() et location.getLongitude()
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // Log.d sert a afficher dans le logcat, si on cherche My App dans la saisi de recherche en bas, on voit le message.(pour voir les coordonnees appuyer sur le bouton mapit(onClick()))
        Log.d("My App:", "latitude: " + location.getLatitude() + "longitude: " + location.getLongitude());
        return location;
        // locationManager.removeUpdates(locationListener);

    }

    public void findAllGroupeAroundMe(){
        GeoLocation mylocation = new GeoLocation(GPSupdates().getLatitude(),GPSupdates().getLongitude());
        Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit");
        boolean groupfound;
        GeoFire gf = new GeoFire(ref);
        //Query autour de 'mylocation' avec un rayon de 0.5 kilometers
        GeoQuery geoQuery = gf.queryAtLocation(mylocation,0.5);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {

            public void onDataChange(String key) {
                if(key != null){

                }

            }

            @Override
            public void onKeyEntered(String key, GeoLocation location) {

            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(FirebaseError error) {

            }
        });
        geoQuery.removeAllListeners();
    }


    public void findAllGroupeAroundLocation(GeoLocation location){

        Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit");
        GeoFire gf = new GeoFire(ref);
        //Query autour de 'mylocation' avec un rayon de 0.5 kilometers
        GeoQuery geoQuery = gf.queryAtLocation(location,0.5);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {

            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(FirebaseError error) {

            }
        });
    }

    // Fonction qui gere la creation de groupe

    // On doit metter a jour la location avec GeoFire chaque fois un groupe est cree
    //Geofire est une base de donnee independant (comme la base qui gere le login)

    public void createGroup (Profile creator){
        Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit");
        AuthData authData = ref.getAuth();
        Location location = GPSupdates();//acquérir location quand on cree un groupe
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        ArrayList<String> members = new ArrayList<String>();
        members.add(creator.getUserName());
        String s = "welcome to the groupe : "+"NEW GROUPE"+" , the creator of groupe is : ";
        ArrayList<Discussion> discussions = new ArrayList<Discussion>();
        Discussion firstMessage = new Discussion(creator.getUserName() , s, Calendar.getInstance().getTime());
        discussions.add(firstMessage);
        Date timeoflastmessage = discussions.get(discussions.size()-1).getTimeSend();
        Groupe new_group = new Groupe("groupName",1,0,latitude,longitude,creator.getUserName(),members,discussions,timeoflastmessage);//utilise cette location pour creer le groupe
        ref.child("Groups").child(new_group.getGroupeName()).setValue(new_group);//enregister dans la base
        GeoFire gf = new GeoFire(ref); //creation de geofire qui est la base de location
    /*
        gf.setLocation(new_group.getGroupeName() , new GeoLocation(location.getLatitude() , location.getLongitude()) , new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, FirebaseError error) {
                if (error != null) {
                    Toast.makeText(getApplicationContext(), "An error occur, the group has not been created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "A Group as been successfully created! ", Toast.LENGTH_SHORT).show();
                }
            }
        });;//enregister la location dans geofire , la cle est nom de groupe(optional)

    */
    }



//refs : https://github.com/firebase/geofire-java,
//       https://www.firebase.com/blog/2013-09-25-location-queries-geofire.html
//       https://www.firebase.com/blog/2014-06-23-geofire-two-point-oh.html


}