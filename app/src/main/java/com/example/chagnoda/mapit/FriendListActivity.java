package com.example.chagnoda.mapit;


import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
        Firebase ref1 = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit");
        final AuthData authData = ref1.getAuth();

        Firebase ref = new Firebase("https://sizzling-inferno-6141.firebaseio.com/Mapit/Profiles");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot profileSnapshot : snapshot.getChildren()) {
                    Profile copy_profile = profileSnapshot.getValue(Profile.class);
                    Log.d("My App: ", copy_profile.getUserName());
                    if(copy_profile.getUserID() == authData.getUid()){
                        ArrayList<String> friendlist = copy_profile.getFriendsList();
                        for(int i=0; i<friendlist.size();i++) {
                            arrayList.add(friendlist.get(i));
                        }
                    }
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
