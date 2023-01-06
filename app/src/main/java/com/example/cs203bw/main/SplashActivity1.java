package com.example.cs203bw.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.cs203bw.R;

public class SplashActivity1 extends Activity {

    private ImageView iv;
    private Bundle b;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        this.b=b;
        setContentView(R.layout.activity_splash1);
        iv = new ImageView(this);
        iv.setImageResource(R.drawable.background);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(iv);
    }

    @Override
    public boolean onTouchEvent(MotionEvent m){
        var w = iv.getWidth();
        var h = iv.getHeight();
        if (m.getAction() == MotionEvent.ACTION_DOWN) {
            var x = m.getX();
            var y = m.getY();
            if(x<w/10 && y<h/5){
                Intent tent = new Intent(this, PrefsActivity.class);
                startActivity(tent);
            }else if(x<w/10 && y>(h/5)*4){
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("?")
                        .setMessage("You shoot the airplanes and submarines to get the points!")
                        .setCancelable(false)
                        .setPositiveButton("Yes!", (d,i) -> onCreate(this.b));
                AlertDialog box = ab.create();
                box.show();
            }else{
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        }
        return true;
    }
}