package com.example.cs203bw.main;

import java.util.Random;

public enum Direction{
    LEFT_FACING,
    RIGHT_FACING;

    /**
     * This returns a random direction
     * @return Direction
     */
    public static Direction getRandomDirection(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

};
