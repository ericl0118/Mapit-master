package com.example.chagnoda.mapit;
import android.media.Image;

/**
 * sCreated by David on 22/03/2016.
 */
public class Photos {
    Image Photo;
    float[] location;

    // A photo in mapit contain the picture and the coordinates of the location.
    public Photos(Image Photo, float[] location){
        this.Photo = Photo;
        this.location = location;

    }
}