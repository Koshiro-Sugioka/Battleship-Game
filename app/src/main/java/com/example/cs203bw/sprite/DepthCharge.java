package com.example.cs203bw.sprite;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.example.cs203bw.R;

public class DepthCharge extends Sprite{

    public DepthCharge(Resources r, float width, float height){
        screenHeight=height;
        image = BitmapFactory.decodeResource(r, R.drawable.depth_charge);
        //setting the size and velocity
        sizeModify = 70;
        size = (int)(width/sizeModify);
        velocity = new PointF(0,10);
    }

    @Override
    public void move(){
        super.move();
        if(bounds.bottom > screenHeight){
            onScreen = false;
        }
    }


}
