package com.example.cs203bw.sprite;

import android.content.res.Resources;

import com.example.cs203bw.main.Direction;
import com.example.cs203bw.main.Size;

public abstract class Enemy extends Sprite {

    public Direction direction;
    protected Size enemySize;

    public Enemy(){
        super();
    }

    /**
     * This resets the enemy object's initial position, size, and direction.
     */
    public void newObject(){
        onScreen = true;
        enemySize = Size.getRandomSize();
    }

    /**
     * This method is called when there is a collision
     * between the enemy and a missile or a depth charge.
     */
    public void explode(){
        onScreen = false;
        velocity.x = 0;
        velocity.y = 0;
    }

    /**
     * This method will be overridden in Airplane and Submarine classes.
     * It returns the point depending on the size of the enemy.
     * @return int
     */
    public abstract int getPointValue();

}
