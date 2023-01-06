package com.example.cs203bw.main;

import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.example.cs203bw.sprite.DepthCharge;
import com.example.cs203bw.sprite.Explosion;
import com.example.cs203bw.sprite.Missile;
import com.example.cs203bw.sprite.Sprite;

import java.util.ArrayList;

public class Timer extends Handler {

    public ArrayList<TickListener> list;
    private int timeLeft =180;
    private int cycle=0;
    private boolean running;

    public Timer() {
        running = true;
        list = new ArrayList<>();
        sendMessageDelayed(obtainMessage(), 1000);
    }

    /**
     * This Method is called in certain interval to move the Sprite objects.
     */
    @Override
    public void handleMessage(Message m) {

        if(!(timeLeft <=0) && running){
            for(TickListener t: list){
                t.tick();
            }
            if(countCycle()==20){
                timeLeft-=1;
            }
        }
        sendMessageDelayed(obtainMessage(), 50);
    }

    /**
     * This method put the TickListener parameter into the list of TickListener.
     * @param t
     */
    public void register(TickListener t){
        list.add(t);
    }

    /**
     * This method put the TickListener parameter into the list of TickListener.
     * @param t
     */
    public void deregister(TickListener t){
        list.remove(t);
    }

    //method for doing action every second
    private int countCycle(){
        cycle+=1;
        if(cycle==21){
            cycle=0;
        }
        return cycle;
    }

    /**
     * This method returns how minutes left.
     * @return int
     */
    public int getMinutes(){
        return (int)(timeLeft/60);
    }

    /**
     * This method returns the seconds left.
     * @return int
     */
    public int getSeconds(){
        return timeLeft%60;
    }

    /**
     * This method resets the timer.
     */
    public void resetTimer(){
        timeLeft=180;
    }

    /**
     * This method is used to stop the timer.
     */
    public void stopTimer(){
        running=false;
    }

    /**
     * This method is used to start the timer.
     */
    public void startTimer(){
        running=true;
    }
}
