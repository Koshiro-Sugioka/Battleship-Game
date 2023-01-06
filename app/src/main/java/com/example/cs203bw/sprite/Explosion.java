package com.example.cs203bw.sprite;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.example.cs203bw.R;

public class Explosion extends Sprite{

    private int count = 0;

    public Explosion(Resources r, float width){
        screenWidth= width;
        image = BitmapFactory.decodeResource(r, R.drawable.star);
        sizeModify = 70;
        size = (int)(width/sizeModify);
        velocity = new PointF(0,0);
    }


    /**
     * This method is the overridden move method from Sprite class to check how long it has shown on the screen.
     * It is called everytime when handleMessage method in FunView class is called.
     */
    @Override
    public void move(){
        count += 1;
        if(count==3){
            onScreen = false;
        }
    }
}
