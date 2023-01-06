package com.example.cs203bw.sprite;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.example.cs203bw.R;
import com.example.cs203bw.main.Direction;
import com.example.cs203bw.main.Prefs;
import com.example.cs203bw.main.Size;

import java.util.Random;

public class Airplane extends Enemy {

    private Resources resources;
    private int speed;
    private Context c;

    public Airplane(Context con, float width, float height){
        super();
        c = con;
        speed = Prefs.planeSpeed(c);
        resources = c.getResources();
        screenWidth = width;
        screenHeight = height;
        this.newObject();
    }

    /**
     * This method is overridden from the Sprite class.
     * It moves an airplane based on the velocity value from right to left,
     * avoiding it clashes into the battleship or disappearing from the screen.
     */
    @Override
    public void move(){
        //moving the plane based on the velocity
        super.move();
        //setting the plane to the very right when it faded from the screen
        if(bounds.left < -size || bounds.left > screenWidth){
            this.newObject();
        }
        //changing the x velocity so it speeds up or down
        if(Math.random()<0.1){
            if(direction==Direction.LEFT_FACING) {
                if (Math.random() < 0.5 && velocity.x > -speed*4) {
                    velocity.x = (float) (velocity.x * 1.5);
                } else if (velocity.x < -speed) {
                    velocity.x = (float) (velocity.x * 0.5);
                }
            }else{
                if(Math.random()<0.5 && velocity.x<speed*4){
                    velocity.x = (float)(velocity.x*1.5);
                }else if(velocity.x>speed){
                    velocity.x = (float)(velocity.x*0.5);
                }
            }
        }

        if(Prefs.planeAltitude(c)){
            if(bounds.top<=0){ //when plane's y coordinate is 0
                if(Math.random()<0.5){
                    velocity.y = 3;
                }else{
                    velocity.y = 0;
                }
            }else if(bounds.bottom>=screenHeight*0.55f-((int)((screenWidth/3)/2.8))){ //before the bottom of plane get ander the top of battleship
                if(Math.random()<0.5){
                    velocity.y = -3;
                }else{
                    velocity.y = 0;
                }
            }else{ //adding or subtracting y velocity
                if(Math.random()<0.5){
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
        image = BitmapFactory.decodeResource(resources, R.drawable.airplane_explosion);
    }

    /**
     * This is overridden from Enemy class.
     * It returns the point for planes;
     * Big one has 15, Medium one has 20, and Small one has 75 points.
     * @return int
     */
    @Override
    public int getPointValue(){
        if(enemySize==Size.BIG){
            return 15;
        }else if(enemySize==Size.MEDIUM){
            return 20;
        }else{
            return 75;
        }
    }

    /**
     * This method resets the object's condition: size, direction, and the image.
     * This randomly assigns the size, the initial y coordinate, and direction.
     */
    @Override
    public void newObject(){
        if(Prefs.planeDirection(c).equals("left")){
            direction = Direction.RIGHT_FACING;
        }else if(Prefs.planeDirection(c).equals("right")){
            direction = Direction.LEFT_FACING;
        }else{
            direction = Direction.getRandomDirection();
        }
        super.newObject();
        Random random = new Random();
        float x = random.nextInt(3);
        //setting the image and sizeModify value for airplane depending on the Size
        if(direction== Direction.LEFT_FACING) {
            velocity = new PointF((-speed)*(x+1),0);
            if (this.enemySize == Size.BIG) {
                image = BitmapFactory.decodeResource(resources, R.drawable.big_airplane);
                sizeModify = 8;
                size = (int) (screenWidth / sizeModify);
            } else if (this.enemySize == Size.MEDIUM) {
                image = BitmapFactory.decodeResource(resources, R.drawable.medium_airplane);
                sizeModify = 16;
                size = (int) (screenWidth / sizeModify);
            } else {
                image = BitmapFactory.decodeResource(resources, R.drawable.little_airplane);
                sizeModify = 32;
                size = (int) (screenWidth / sizeModify);
            }
            //Setting the initial y coordinate and set the bounds to the right side of the screen.
            float top =(float) ((screenHeight * 0.3 * Math.random()));
            bounds.offsetTo(screenWidth, top);
            bounds.right=screenWidth+size;
            bounds.bottom=top+size;
        }else{
            velocity = new PointF((speed)*(x+1),0);
            if (this.enemySize == Size.BIG) {
                image = BitmapFactory.decodeResource(resources, R.drawable.big_airplane_flip);
                sizeModify = 8;
                size = (int) (screenWidth / sizeModify);
            } else if (this.enemySize == Size.MEDIUM) {
                image = BitmapFactory.decodeResource(resources, R.drawable.medium_airplane_flip);
                sizeModify = 16;
                size = (int) (screenWidth / sizeModify);
            } else {
                image = BitmapFactory.decodeResource(resources, R.drawable.little_airplane_flip);
                sizeModify = 32;
                size = (int) (screenWidth / sizeModify);
            }
            //Setting the initial y coordinate and set the bounds to the left side of the screen.
            float top =(float) ((screenHeight * 0.3 * Math.random()));
            bounds.offsetTo(-size, top);
            bounds.right=0;
            bounds.bottom=top+size;
        }
    }

}
