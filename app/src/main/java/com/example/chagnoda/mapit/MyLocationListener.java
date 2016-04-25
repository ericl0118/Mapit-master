package com.example.chagnoda.mapit;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


/**
 * Created by David on 01/04/2016.
 */
public class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {

        location.getLongitude();
        location.getLatitude();


    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
