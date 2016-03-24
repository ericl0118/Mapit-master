package com.example.chagnoda.mapit;

import android.graphics.Color;
import android.location.Location;

import java.util.ArrayList;

/**
 * Created by chengli on 2016-03-24.
 */
public class Supergroupe extends Groupe {


    private ArrayList<Groupe> channels;


    public Supergroupe(String name , int size , int color , Location location , Profile creator){

    super(name ,size ,color , location ,creator);
    Groupe general = new Groupe("general",50, Color.GREEN,location,creator);
    this.channels.add(this);
    }


    public void addNewGroupeToChannels(Groupe newGroupe){

        this.channels.add(newGroupe);

    }

    public void deleteAGroupeFromChannels(Groupe groupe){
        this.channels.remove(groupe);
    }

}
