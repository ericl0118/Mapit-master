package com.example.chagnoda.mapit;
import android.media.Image;

/**
 * Created by David on 22/03/2016.
 */
public class Profile {
    private String username;
    private Image profilepicture;
    private Profile[] friends;
    private Photo[] photos;
   // public Groupe[] actualGroups;
   // public Groupe[] groupsHistory;


    public Profile(String userName, Image profilePicture) {
        this.username = userName;
        this.profilepicture = profilePicture;
    }
    public String getUserName(){
        return this.username;
    }
    public Image getProfilePicture(){return this.profilepicture;}
    public void setUserName(String newUserName) {this.username = newUserName;}
    public void setProfilePicture(Image newProfilePicture){this.profilepicture = newProfilePicture;}

    }




