package com.example.cs203bw.main;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import com.example.cs203bw.sprite.Airplane;
import com.example.cs203bw.sprite.Battleship;
import com.example.cs203bw.R;
import com.example.cs203bw.sprite.DepthCharge;
import com.example.cs203bw.sprite.Explosion;
import com.example.cs203bw.sprite.Missile;
import com.example.cs203bw.sprite.Submarine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FunView extends View implements  TickListener {

    private Paint paint;
    private int score;
    private PointF waterPos;
    protected Battleship battleship;
    protected Airplane airplane1, airplane2, airplane3;
    protected Submarine submarine1, submarine2, submarine3;
    private Bitmap water;
    private boolean initialized;
    protected Timer time;
    protected ArrayList<Airplane> airplanes = new ArrayList<>();
    protected ArrayList<Submarine> submarines = new ArrayList<>();
    protected ArrayList<DepthCharge> depthCharges = new ArrayList<>();
    protected ArrayList<Missile> missiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private MediaPlayer depth, leftGun, rightGun, planeEx, subEx;
    private Context con;


    /**
     * The constructor for this Class.
     */
    public FunView(Context c) {
        super(c);
        con = c;
        paint = new Paint();
        paint.setTextSize(100);
        score = 0;
        initialized = false;
        time = new Timer();
        time.register(this);
        depth = MediaPlayer.create(c,R.raw.depth);
        leftGun = MediaPlayer.create(c, R.raw.gun);
        rightGun = MediaPlayer.create(c, R.raw.gun2);
        planeEx = MediaPlayer.create(c, R.raw.plane_explosion);
        subEx = MediaPlayer.create(c, R.raw.sub_explosion);
    }

    /**
     * This onDraw method instantiates the Sprites objects, and defines the size and the location for
     * all Sprite objects and water bitmap.
     *
     */
    @Override
    public void onDraw(Canvas c) {
        Resources r = getResources();
        float w = c.getWidth();
        float h = c.getHeight();
        //Here will be run when positions and sizes have not initialized.
        if (!initialized) {
            waterPos = new PointF();

            //instantiating all Sprite objects
            battleship = Battleship.newInstance(r,w);
            airplane1 = new Airplane(con,w,h);
            airplane2 = new Airplane(con,w,h);
            airplane3 = new Airplane(con,w,h);
            submarine1 = new Submarine(con,w,h);
            submarine2 = new Submarine(con,w,h);
            submarine3 = new Submarine(con,w,h);

            airplanes.add(airplane1);
            airplanes.add(airplane2);
            airplanes.add(airplane3);
            submarines.add(submarine1);
            submarines.add(submarine2);
            submarines.add(submarine3);

            for(Airplane a: airplanes){
                time.register(a);
            }

            for(Submarine s: submarines){
                time.register(s);
            }

            time.register(battleship);

            //instantiating the water
            water = BitmapFactory.decodeResource(r, R.drawable.water);
            int waterSize = (int)(w/60);
            waterPos.set(w*0f, h*0.55f+waterSize/2.8f);
            water = Bitmap.createScaledBitmap(water, waterSize, waterSize, true);

            //setting the positions for the Sprite objects
            battleship.setPosition(w*0.33f, h*0.55f-((int)((w/3)/2.8)));
            initialized = true;
        }

        //displaying te score and the time left
        c.drawColor(Color.WHITE);
        paint.setTextSize(w/30);
        c.drawText(getResources().getString(R.string.score)+": "+score, w/50, (float)(h/1.5), paint);
        c.drawText(getResources().getString(R.string.time)+": "+time.getMinutes()+":"+String.format("%02d", time.getSeconds()), (w/15)*12, (float)(h/1.5), paint);

        //drawing water
        for(int i =0; i<65; i++){
            c.drawBitmap(water, waterPos.x+i*(int)(getWidth()/60), waterPos.y, paint);
        }


        //drawing all sprites
        for(Airplane a: airplanes){
            a.draw(c);
            if(!a.isOnScreen()){
                a.newObject();
            }
        }

        for(Submarine s: submarines){
            s.draw(c);
            if(!s.isOnScreen()){
                s.newObject();
            }
        }
        battleship.draw(c);
        for(DepthCharge d: depthCharges){
            d.draw(c);
            if(!d.isOnScreen()){
                time.deregister(d);
            }
        }
        for(Missile m: missiles){
            m.draw(c);
            if(!m.isOnScreen()){
                time.deregister(m);
            }
        }
        for(Explosion e: explosions){
            e.draw(c);
            if(!e.isOnScreen()){
                time.deregister(e);
            }
        }

        //removing sprite objects which are unnecessary.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            missiles.removeIf(m -> (!m.isOnScreen()));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            depthCharges.removeIf(d -> (!d.isOnScreen()));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            explosions.removeIf(e -> (!e.isOnScreen()));
        }

        //showing the alertdialog when the time is 0.
        if (time.getSeconds()+time.getMinutes()==0) {
            String message = "Do you wanna play again?";
            if(getHighestScore()<score){
                message = "Congratulations! You got the highest score! Do you wanna play again?";
                try(var fos = getContext().openFileOutput("score.txt", Context.MODE_PRIVATE);) {
                    fos.write((""+score).getBytes());
                } catch (IOException e) {
                }
            }
            AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
            ab.setTitle("GAME OVER!")
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Yes!", (d,i) -> resetGame())
                    .setNegativeButton("No.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int i) {
                            ((Activity)getContext()).finish();
                        }
                    });
            AlertDialog box = ab.create();
            box.show();
        }

    }


    /**
     * Method called when the screen is touched. It is used to shoot the depth charges and missiles.
     * A depth charge is dropped when the bottom half of screen is touched,
     * and a missile is shot toward right when the top right of the screen is touched,
     * and a missile is hot toward left when the top left of the screen is touched.
     * @param m
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent m) {
        float x = m.getX();
        float y = m.getY();
        float w = getWidth();
        float h = getHeight();
        if (m.getAction() == MotionEvent.ACTION_DOWN) {
           if(y>getHeight()/2){
               //instantiating and setting the position for depthCharge object
               if(!Prefs.rapidDepth(con)){
                   if(depthCharges.size()==0){
                       dropDepthCharge(w,h);
                   }
               }else{
                   dropDepthCharge(w,h);
               }
           }else if(x<w/2){
               if(!Prefs.rapidMissile(con)){
                   if(missiles.size()==0){
                       shootLeftMissile(w,h);
                   }
               }else{
                   shootLeftMissile(w,h);
               }
           }else{
               if(!Prefs.rapidMissile(con)){
                   if(missiles.size()==0){
                       shootRightMissile(w,h);
                   }
               }else{
                   shootRightMissile(w,h);
               }
           }
        }
        return true;
    }


    /**
     * This method detects collisions between enemies and missiles or depthCharges.
     */
    public void detectCollisions(){
        //Checking the collisions between Airplanes and Missiles.
        for(Missile m: missiles){
            for(Airplane a: airplanes){
                if(a.overlaps(m)){
                    a.explode();
                    score += a.getPointValue();
                    m.removeFromScreen();
                    if(Prefs.soundsFX(con)){
                        planeEx.start();
                    }

                }
            }
        }
        //Checking the collisions between Submarines and DepthCharges.
        for(DepthCharge d: depthCharges){
            for(Submarine s: submarines){
                if(s.overlaps(d)){
                    s.explode();
                    score += s.getPointValue();
                    d.removeFromScreen();
                    if(Prefs.soundsFX(con)){
                        subEx.start();
                    }

                }
            }
        }
    }


    /**
     * This method is overridden from TickListener interface which imply call invalidate method.
     */
    @Override
    public void tick(){
        detectCollisions();
        invalidate();
    }

    //method for resetting the game
    private void resetGame(){
        score = 0;
        time.resetTimer();
        for(Airplane a: airplanes){
            a.newObject();
        }
        for(Submarine s: submarines){
            s.newObject();
        }
        missiles = new ArrayList<>();
        depthCharges = new ArrayList<>();
    }

    //This reads the file and returns the highest score stored on the file.
    private int getHighestScore(){
        int highScore=0;
        try (var file = getContext().openFileInput("score.txt");
             Scanner s = new Scanner(file);){
            highScore=s.nextInt();
        } catch (IOException e) {
        }
        return highScore;
    }

    //method for dropping a depth charge
    private void dropDepthCharge(float w, float h){
        DepthCharge d = new DepthCharge(getResources(),w,h);
        d.setPosition((float)((battleship.bounds.left)+(w/8)),(float)(h*0.58));
        depthCharges.add(d);
        time.register(d);
        if(Prefs.soundsFX(con)){
            depth.start();
        }
        if(Prefs.frugality(con)){
            score -= 1;
        }
    }

    //method for shooting a missile from left gun
    private void shootLeftMissile(float w, float h){
        //instantiating and setting the position for missile object which moves left
        Missile missile = new Missile(Direction.LEFT_FACING,w);
        ((Missile)missile).setPosition((float)(battleship.bounds.left+(w/16.4)),battleship.bounds.top+(w/20));
        missiles.add(missile);
        time.register(missile);
        if(Prefs.soundsFX(con)){
            leftGun.start();
        }

        //instantiating explosion object for the effect
        Explosion e = new Explosion(getResources(),w);
        e.setPosition((float)((battleship.bounds.left)+(w/16.4)),(float)(battleship.bounds.top+(w/20)));
        explosions.add(e);
        time.register(e);
        if(Prefs.frugality(con)){
            score -= 1;
        }
    }

    //method for shooting missile from right gun
    private void shootRightMissile(float w, float h){
        //instantiating and setting the position for missile object which moves right
        Missile missile = new Missile(Direction.RIGHT_FACING,w);
        ((Missile)missile).setPosition((float)(battleship.bounds.left+(w/4.4)),battleship.bounds.top+(w/20));
        missiles.add(missile);
        time.register(missile);
        if(Prefs.soundsFX(con)){
            rightGun.start();
        }
        //instantiating explosion object for the effect
        Explosion e = new Explosion(getResources(),w);
        e.setPosition((float)((battleship.bounds.left)+(w/4.4)),(float)(battleship.bounds.top+(w/20)));
        explosions.add(e);
        time.register(e);
        if(Prefs.frugality(con)){
            score -= 1;
        }
    }


}

