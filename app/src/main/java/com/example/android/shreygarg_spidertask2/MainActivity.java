package com.example.android.shreygarg_spidertask2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static int mode=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
    }
    public void gamemode(View view)
    {
        mode=1;
        Intent set = new Intent(MainActivity.this,game.class);
        startActivity(set);
    }
    public void gamemodecomp(View view)
    {
        mode=2;
        Intent set = new Intent(this,level.class);
        startActivity(set);
        finish();
    }


    public void leader(View view)
    {
        Intent intent = new Intent(MainActivity.this,Leaderboard.class);
            startActivity(intent);
    }
}
