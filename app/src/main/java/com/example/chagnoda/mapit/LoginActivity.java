package com.example.chagnoda.mapit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

/**
 * Created by chengli on 2016-03-21.
 */
public class LoginActivity extends AppCompatActivity {
    Firebase ref;
    DataSnapshot snapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit/Profiles");
        //snapshot = new DataSnapshot(ref,)
    }
}
