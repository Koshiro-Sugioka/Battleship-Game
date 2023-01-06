package com.example.cs203bw.sprite;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;

import com.example.cs203bw.R;
import com.example.cs203bw.main.Direction;
import com.example.cs203bw.main.Prefs;
import com.example.cs203bw.main.Size;

import java.util.Random;

public class Submarine extends Enemy {
    private Resources resources;
    private int speed;
    private Context c;

    public Submarine(Context con, float width, float height){
        super();
        c = con;
        speed = Prefs.subSpeed(c);
        resources = c.getResources();
        screenWidth = width;
        screenHeight = height;
        this.newObject();
    }

    /**
     * This method is overridden from the Sprite class.
     * It moves a submarine based on the velocity value from left to right,
     * avoiding it going above the water or disappearing from the screen.
     */
    @Override
    public void move(){
        //moving the submarine based on the velocity
        super.move();
        //setting the submarine to the very left when it faded from the screen
        if(bounds.left < -size || bounds.left > screenWidth){
            this.newObject();
        }

        //changing the x velocity so it speeds up or down
        if(direction==Direction.RIGHT_FACING) {
            if (Math.random() < 0.1) {
                if (Math.random() < 0.5 && velocity.x < speed*4) {
                    velocity.x = (float) (velocity.x * 1.5);
                } else if (velocity.x > speed) {
                    velocity.x = (float) (velocity.x * 0.5);
                }
            }
        }else{
            if (Math.random() < 0.5 && velocity.x > -speed*4) {
                velocity.x = (float) (velocity.x * 1.5);
            } else if (velocity.x < -speed) {
                velocity.x = (float) (velocity.x * 0.5);
            }
        }

        if(Prefs.subDepth(c)){
            if(bounds.top<=screenHeight*0.57f){ //when submarine's y coordinate is on the water y coordinate
                if(Math.random()<0.5){
                    velocity.y = 3;
                }else{
                    velocity.y = 0;
                }
            }else if(bounds.bottom>=screenHeight){ //when the bottom of the submarine is on the bottom of the screen
                if(Math.random()<0.5){
                    velocity.y = -3;
                }else{
                    velocity.y = 0;
                }
            }else{
                if(Math.random()<0.5){ //adding or subtracting y velocity
                    if(Math.random()<0.5){
                        velocity.y -= 3;
                    }else{
                        velocity.y += 3;
                    }
                }
            }
        }
    }

    /**
     * This is overridden from Enemy class.
     * It changes the image into explosion one.
     */
    @Override
    public void explode(){
        super.explode();
        image = BitmapFactory.decodeResource(resources, R.drawable.submarine_explosion);
    }


    /**
     * This is overridden from Enemy class.
     * It returns the point for submarines;
     * Big one has 25, Medium one has 40, and Small one has 150 points.
     * @return int
     */
    @Override
    public int getPointValue(){
        if(enemySize==Size.BIG){
            return 25;
        }else if(enemySize==Size.MEDIUM){
            return 40;
        }else{
            return 150;
        }
    }

    /**
     * This method resets the object's condition: size, direction, and the image.
     * This randomly assigns the size, the initial y coordinate, and direction.
     */
    @Override
    public void newObject(){
        if(Prefs.subDirection(c).equals("left")){
            direction = Direction.RIGHT_FACING;
        }else if(Prefs.subDirection(c).equals("right")){
            direction = Direction.LEFT_FACING;
        }else{
            direction = Direction.getRandomDirection();
        }
        super.newObject();
        Random random = new Random();
        float x = random.nextInt(3);
        //setting the image and sizeModify value for submarine depending on the Size
        if(direction== Direction.RIGHT_FACING) {
            velocity = new PointF((speed)*(x+1),0);
            if (this.enemySize == Size.BIG) {
                image = BitmapFactory.decodeResource(resources, R.drawable.big_submarine);
                sizeModify = 8;
                size = (int) (screenWidth / sizeModify);
            } else if (this.enemySize == Size.MEDIUM) {
                image = BitmapFactory.decodeResource(resources, R.drawable.medium_submarine);
                sizeModify = 16;
                size = (int) (screenWidth / sizeModify);
            } else {
                image = BitmapFactory.decodeResource(resources, R.drawable.little_submarine);
                sizeModify = 32;
                size = (int) (screenWidth / sizeModify);
            }
            //Setting the initial y coordinate and set the bounds to the left side of the screen.
            float top =(float) (screenHeight - (screenHeight * 0.2 * Math.random())) - size;
            bounds.offsetTo(-size, top);
            bounds.right=0;
            bounds.bottom=top+size;
        }else{
            velocity = new PointF((-speed)*(x+1),0);
            if (this.enemySize == Size.BIG) {
                image = BitmapFactory.decodeResource(resources, R.drawable.big_submarine_flip);
                sizeModify = 8;
                size = (int) (screenWidth / sizeModify);
            } else if (this.enemySize == Size.MEDIUM) {
                image = BitmapFactory.decodeResource(resources, R.drawable.medium_submarine_flip);
                sizeModify = 16;
                size = (int) (screenWidth / sizeModify);
            } else {
                image = BitmapFactory.decodeResource(resources, R.drawable.little_submarine_flip);
                sizeModify = 32;
                size = (int) (screenWidth / sizeModify);
            }
            //Setting the initial y coordinate and set the bounds to the right side of the screen.
            float top =(float) (screenHeight - (screenHeight * 0.2 * Math.random())) - size;
            bounds.offsetTo(screenWidth, top);
            bounds.right=screenWidth+size;
            bounds.bottom=top+size;
        }
    }


}
