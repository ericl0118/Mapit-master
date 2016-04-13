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
import android.widget.Toast;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;




public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
       GPSupdates();

    }


    public void GPSupdates(){
        // si la localisation n'est pas activer sur le telephone, l'application crash!
        // il reste a implementer un Alert.Dialog pour demander a l'usager d'activer la Localisation sur l'appareil si elle n'est pas active, voici un bon exemple(la fonction showAlert()): http://www.androidauthority.com/easy-app-material-design-tips-683092/
        // permission request pour le Gps
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        // LocationManager service de geolocalisation
            locationManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            // Recupere les coordonnees dans location, location.getLatitude() et location.getLongitude()
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // Log.d sert a afficher dans le logcat, si on cherche My App dans la saisi de recherche en bas, on voit le message.(pour voir les coordonnees appuyer sur le bouton mapit(onClick()))
            Log.d("My App:", "latitude: " + location.getLatitude() + "longitude: " + location.getLongitude());
           // locationManager.removeUpdates(locationListener);

    }


    //fonction qui gere si un groupe existe
    public void groupExist(Location location){


    }

    // Fonction qui gere la creation de groupe
    public void createGroup (Location location){
        Groupe new_group = new Groupe("groupName",1,0,location);
        Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit");
        ref.child("Groups").child(new_group.getGroupeName()).setValue(new_group);
    }






}
