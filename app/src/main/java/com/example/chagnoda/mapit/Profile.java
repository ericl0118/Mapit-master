package com.example.chagnoda.mapit;
import android.media.Image;

/**
 * Created by David on 22/03/2016.
 */
public class Profile {
    private String username;
    private String email;
    private String password;
    private Image profilepicture;
    private Profile[] friends;
    private Photo[] photos;
    private String Password;
   // public Groupe[] actualGroups;
   // public Groupe[] groupsHistory;


    public Profile(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        //this.profilepicture = profilePicture;
    }
    public String getUserName(){
        return this.username;
    }
    public Image getProfilePicture(){return this.profilepicture;}
    public void setUserName(String newUserName) {this.username = newUserName;}
    public void setProfilePicture(Image newProfilePicture){this.profilepicture = newProfilePicture;}

    }




