package com.example.cs203bw.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import androidx.lifecycle.ViewModelProvider;

import com.example.cs203bw.main.TickListener;
import com.example.cs203bw.main.Timer;

import java.util.ArrayList;
import java.util.Random;

public abstract class Sprite implements TickListener {

    protected int sizeModify;
    protected int size;
    protected PointF velocity;
    public RectF bounds;
    protected Bitmap image;
    protected Paint paint = new Paint();
    protected float screenWidth;
    protected float screenHeight;
    protected boolean onScreen;

    public Sprite(){
        onScreen=true;
        bounds = new RectF();
    }


    /**
     * This setPosition method set the position for a Sprite object.
     * The first parameter if the x coordinate,
     * and the second parameter is the y coordinate.
     */
    public void setPosition(float x, float y){
        bounds.right = size;
        bounds.bottom = size;
        bounds.offsetTo(x,y);
    }

    /**
     * This method moves the object based on the velocity value.
     */
    public void move() {
        bounds.offset(velocity.x,velocity.y);
    }


    /**
     * This draw method scale the bitmap image so it can be drawn,
     * and draw it on canvas.
     */
    public void draw(Canvas c){
        image = Bitmap.createScaledBitmap(image, (int)bounds.width(), (int)bounds.height(), true);
        c.drawBitmap(image, bounds.left, bounds.top, paint);
    }

    /**
     * This method is overridden from TickListener interface which imply call move method.
     */
    @Override
    public void tick(){
        this.move();
    }

    /**
     * This method returns the condition if the object is on the screen or not.
     * @return boolean
     */
    public boolean isOnScreen(){
        return onScreen;
    }

    /**
     * This method changes the condition of the object into off screen.
     */
    public void removeFromScreen(){
        onScreen = false;
    }


    /**
     * This method is to check if the object's bounds is overlapping the other one.
     * @param s
     * @return boolean
     */
    public boolean overlaps(Sprite s){
        if(RectF.intersects(this.bounds, s.bounds)){
            return true;
        }else{
            return false;
        }
    }


}
