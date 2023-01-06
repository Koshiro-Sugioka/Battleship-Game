package com.example.cs203bw.main;

import com.example.cs203bw.sprite.Sprite;

import java.util.Random;

public enum Size{
    BIG,
    MEDIUM,
    SMALL;

    /**
     * This getRandomSize method returns enum of Size picked randomly.
     * @return Size
     */
    public static Size getRandomSize(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
};
