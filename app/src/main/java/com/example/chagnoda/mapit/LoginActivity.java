package com.example.chagnoda.mapit;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.w3c.dom.Text;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengli on 2016-03-21.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    DataSnapshot snapshot;
    Button enterMapit;
    TextView username_view, password_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_login);
        enterMapit = (Button)findViewById(R.id.loginbutton);
        enterMapit.setOnClickListener(this);
    }

//    public void authDataCallback(AuthData authdata) {
//        if (authdata.getAuth()!=null) {
//            Toast.makeText(getApplicationContext(), authdata.getUid(), Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getApplicationContext(), "User is logged out", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public void onClick(View v) {

        username_view = (TextView)findViewById(R.id.loginname);
        final String username = username_view.getText().toString();
        password_view = (TextView)findViewById(R.id.loginpassword);
        final String password = password_view.getText().toString();

        final Firebase ref= new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit");

        ref.authWithPassword(username, password,
                new Firebase.AuthResultHandler() {
                    @Override

                    public void onAuthenticated(AuthData authData) {
                        Toast.makeText(getApplicationContext(), "You have logged in ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent("com.example.chagnoda.mapit.MainActivity"));
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        Toast.makeText(getApplicationContext(), "Login error , verifier your email and/or password ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent("com.example.chagnoda.mapit.LoginActivity"));
                    }
                });
        // a implementer, ajouter le profil a firebase

    }

}