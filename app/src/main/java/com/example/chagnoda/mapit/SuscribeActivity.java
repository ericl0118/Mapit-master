package com.example.chagnoda.mapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.w3c.dom.Text;

/**
 * Created by chengli on 2016-03-21.
 */
public class SuscribeActivity extends AppCompatActivity implements View.OnClickListener{
    Button suscribe;
    TextView username_view,email_view, password_view, confirmed_password_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_suscribe);
        suscribe = (Button)findViewById(R.id.suscribebutton);
        suscribe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        username_view = (TextView)findViewById(R.id.usernamebox);
        String username = username_view.getText().toString();
        email_view = (TextView)findViewById(R.id.emailbox);
        String email = email_view.getText().toString();
        password_view = (TextView)findViewById(R.id.passwordbox);
        String password = password_view.getText().toString();
        confirmed_password_view = (TextView)findViewById(R.id.confirmedpasswordbox);
        String confirmed_password = confirmed_password_view.getText().toString();

        // note: a implementer, condition d'acceptation d'un nouvel acompte
        Profile new_user = new Profile(username,email,password);
        Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit/Profiles");
        ref.child(email).setValue(new_user);
        // a implementer, ajouter le profil a firebase
        startActivity(new Intent("com.example.chagnoda.mapit.MainActivity"));

    }
}
