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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengli on 2016-03-21.
 */
public class SuscribeActivity extends AppCompatActivity implements View.OnClickListener{
    Button suscribe;
    TextView username_view,email_view, password_view, confirmed_password_view;
    ImageButton profilepicture_view;
    ImageButton imagePick;
    private final static int SELECT_PHOTO = 12345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_suscribe);
        suscribe = (Button)findViewById(R.id.suscribebutton);
        suscribe.setOnClickListener(this);
        imagePick = (ImageButton)findViewById(R.id.profileimagebutton);
        imagePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            imagePick.setImageBitmap(bitmap);

            // Do something with the bitmap


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    @Override
    public void onClick(View v) {

        username_view = (TextView)findViewById(R.id.usernamebox);
        final String username = username_view.getText().toString();
        email_view = (TextView)findViewById(R.id.emailbox);
        String email = email_view.getText().toString();
        password_view = (TextView)findViewById(R.id.passwordbox);
        final String password = password_view.getText().toString();
        confirmed_password_view = (TextView)findViewById(R.id.confirmedpasswordbox);
        String confirmed_password = confirmed_password_view.getText().toString();
        profilepicture_view = (ImageButton) findViewById(R.id.profileimagebutton);


        final Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit");
        ref.createUser(email, password, new Firebase.ResultHandler() {

            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "User not created!", Toast.LENGTH_SHORT).show();
            }
        });


        ref.authWithPassword(email, password,
                new Firebase.AuthResultHandler() {

                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authentication just completed successfully :)
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("provider", authData.getProvider());
                        map.put("userName", username);
                        map.put("Password", password);
                        if (authData.getProviderData().containsKey("displayName")) {
                            map.put("displayName", authData.getProviderData().get("displayName").toString());
                        }

                        ref.child("Profiles").child(authData.getUid()).setValue(map);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_SHORT).show();
                        switch (error.getCode()) {
                            case FirebaseError.UNKNOWN_ERROR:
                                Toast.makeText(getApplicationContext(), "ERROR 1 !", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.USER_DOES_NOT_EXIST:
                                Toast.makeText(getApplicationContext(), "ERROR 2 !", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.EMAIL_TAKEN:
                                Toast.makeText(getApplicationContext(), "ERROR 3 !", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.NETWORK_ERROR:
                                Toast.makeText(getApplicationContext(), "ERROR 4 !", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.AUTHENTICATION_PROVIDER_DISABLED:
                                Toast.makeText(getApplicationContext(), "ERROR 5 !", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.INVALID_AUTH_ARGUMENTS:
                                Toast.makeText(getApplicationContext(), "ERROR 6 !", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.INVALID_CONFIGURATION:
                                Toast.makeText(getApplicationContext(), "ERROR 7 !", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.INVALID_CREDENTIALS:
                                Toast.makeText(getApplicationContext(), "ERROR 8 !", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.INVALID_EMAIL:
                                Toast.makeText(getApplicationContext(), "ERROR 9 !", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.INVALID_PASSWORD:
                                Toast.makeText(getApplicationContext(), "ERROR 10 !", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.INVALID_PROVIDER:
                                Toast.makeText(getApplicationContext(), "ERROR 11 !", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "ERROR X !", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                });


        /*

        //Image profilepicture = profilepicture_view.get();
        // note: a implementer, condition d'acceptation d'un nouvel acompte
        Profile new_user = new Profile(username,email,password);
        Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit/Profiles");
        ref.setValue();
        // a implementer, ajouter le profil a firebase
        */

        // startLoginActivity au lieu de Main.
        startActivity(new Intent("com.example.chagnoda.mapit.LoginActivity"));

    }


}
