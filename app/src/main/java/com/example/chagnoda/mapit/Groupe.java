package com.example.chagnoda.mapit;

import android.location.Location;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.graphics.Color;

/**
 * Created by chengli on 2016-03-23.
 */
public class Groupe {

  

    private int groupesize;

    private Color groupecolor;

    private Location location;

    private Profile creator;

    private ArrayList<Profile> members;

    private Date timeoflastmessage;

    private ArrayList<Discussion> discussions;

    private String groupename;

    public Groupe(){}

    public Groupe(String name , int size , int color , Location location , Profile creator){

        this.groupename = name;
        this.groupesize=size;
        this.location=location;
        this.creator=creator;
        this.members.add(creator);
        String s = "welcome to the groupe : "+this.groupename+" , the creator of groupe is : ";
        this.discussions.add(new Discussion(this.creator , s, Calendar.getInstance().getTime()));
        this.timeoflastmessage = discussions.get(discussions.size()-1).getTimeSend();

    }

    public String getGroupeName(){
        return this.groupename;
    }

    public int getGroupeSize(){
        return this.groupesize;
    }

    public Color getGroupeColor(){
        return this.groupecolor;
    }

    public ArrayList<Profile> getMemberList(){
        return this.members;
    }

    public Location getGroupeLocation(){
        return this.location;
    }

    public Profile getCreator(){
        return this.creator;
    }

    public Date getTimeOfLastMessage(){
        return this.timeoflastmessage;
    }

    public void addDiscussionToGroupe(Discussion d){
        this.discussions.add(d);
    }

    public void addMemberToGroupe(Profile p){
        this.members.add(p);
    }

    public void removeMemberFromGroupe(Profile p){
        if(this.members.contains(p)){
            this.members.remove(p);}
    }

    public void extendGroupeSize(int newsize){
        this.groupesize+=newsize;
    }








}
