package com.example.chagnoda.mapit;


import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;


/**
 * Created by chengli on 2016-03-21.
 */
public class FriendListActivity extends AppCompatActivity{
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_friend_list);
        listView = (ListView)findViewById(R.id.FriendList);
        arrayList = new ArrayList<>();
        //adapter = new ArrayAdapter<String>(this,R.layout.activity_friend_list,R.id.FriendList,arrayList);
        //listView.setAdapter(adapter);
        Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit/Profiles");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Toast.makeText(getApplicationContext(), snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                // snapshot.getValue() retrieve toutes les profiles (Dans les photos en piece jointe c'est le plus gros "Toast")

                for (DataSnapshot profileSnapshot : snapshot.getChildren()) {
                    Profile copy_profile = profileSnapshot.getValue(Profile.class);
                    String username = copy_profile.getUserName();
                    arrayList.add(username);
                    Log.d("My App: ", copy_profile.getUserName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FriendListActivity.this, android.R.layout.select_dialog_multichoice, arrayList);
                listView.setAdapter(adapter);
            }


            // snapshot.getValue().toString()
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });

    }

}
