package com.example.cs203bw.sprite;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.example.cs203bw.R;

public class Battleship extends Sprite {

    private static Battleship uniqueInstance;

    private Battleship(Resources r, float width){
        super();
        screenWidth = width;
        //setting the image and size value for battleship
        image = BitmapFactory.decodeResource(r, R.drawable.battleship);
        sizeModify = 4;
        size = (int)(width/sizeModify);
        velocity = new PointF(5,0);
    }

    public static Battleship newInstance(Resources r, float width){
        if(uniqueInstance==null){
            uniqueInstance = new Battleship(r,width);
        }
        return  uniqueInstance;
    }

    /**
     * This method is overridden from Sprite class.
     * The battleship object moves right and left on the location between certain x coordinate.
     */
    @Override
    public void move(){
        super.move();
        if(bounds.right>screenWidth/5*4){
            velocity.x = -5;
        }if(bounds.left<screenWidth/5){
            velocity.x = 5;
        }
    }
}
