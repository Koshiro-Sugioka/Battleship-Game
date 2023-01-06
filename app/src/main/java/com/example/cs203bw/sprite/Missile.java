package com.example.cs203bw.sprite;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import com.example.cs203bw.main.Direction;

public class Missile extends Sprite{
    public Direction direction;

    public Missile(Direction d, float width){
        screenWidth = width;
        direction=d;
        //setting the size and velocity depending on the direction value
        if(direction==Direction.LEFT_FACING) {
            velocity = new PointF(-10, -10);
        }else{
            velocity = new PointF(10, -10);
        }
    }


    /**
     * This method is overridden from Sprite class.
     * It draws a diagonal line as a missile.
     * @param c
     */
    @Override
    public void draw(Canvas c){
        Paint p = new Paint();
        p.setStrokeWidth(5);
        if(direction==Direction.LEFT_FACING){
            c.drawLine(bounds.left,bounds.top,bounds.left+(screenWidth/70),bounds.top+(screenWidth/70), p);
        }else{
            c.drawLine(bounds.left,bounds.top+(screenWidth/70),bounds.left+(screenWidth/70),bounds.top, p);
        }
    }

    @Override
    public void move(){
        super.move();
        if(bounds.top < 0){
            onScreen = false;
        }
    }
}
