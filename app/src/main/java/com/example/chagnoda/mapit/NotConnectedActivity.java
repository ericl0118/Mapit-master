package com.example.chagnoda.mapit;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by David on 27/03/2016.
 */
public class NotConnectedActivity extends AppCompatActivity implements View.OnClickListener{
    Button signin;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_connected);
        signin = (Button)findViewById(R.id.sign_in_button);
        signup = (Button)findViewById(R.id.sign_up_button);
        signup.setOnClickListener(this);
        signin.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_up_button:
                startActivity(new Intent("com.example.chagnoda.mapit.SuscribeActivity"));
                break;
            case R.id.sign_in_button:
                startActivity(new Intent("com.example.chagnoda.mapit.LoginActivity"));
                break;
        }
    }
}
