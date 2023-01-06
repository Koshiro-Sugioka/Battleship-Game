package com.example.cs203bw.main;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.cs203bw.R;
import com.example.cs203bw.main.FunView;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer soundtrack;
    private FunView fv;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        soundtrack = MediaPlayer.create(this, R.raw.back);
        soundtrack.setLooping(true);
        fv = new FunView(this);
        setContentView(fv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Prefs.soundFX(this)) {
            soundtrack.start();
        }
        fv.time.startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Prefs.soundFX(this)) {
            soundtrack.pause();
        }
        fv.time.stopTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundtrack.release();
    }
}