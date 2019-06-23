package com.example.android.shreygarg_spidertask2;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Leaderboard extends AppCompatActivity {
    Database mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboardlayout);
        mydb = new Database(this);
        LinearLayout c1 = (LinearLayout) findViewById(R.id.col1);
        LinearLayout c2 = (LinearLayout) findViewById(R.id.col2);
        LinearLayout c3 = (LinearLayout) findViewById(R.id.col3);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        Cursor res = mydb.getAllData();
        if(res.getCount()!=0)
        {
            while(res.moveToNext())
            {
                TextView t1= new TextView(getApplicationContext());
                TextView t2= new TextView(getApplicationContext());
                TextView t3= new TextView(getApplicationContext());
                t1.setLayoutParams(lp);
                t2.setLayoutParams(lp);
                t3.setLayoutParams(lp);
                t1.setText(res.getString(0));
                t2.setText(res.getString(1));
                t3.setText(res.getString(2));
                t1.setTextColor(Color.WHITE);
                t2.setTextColor(Color.WHITE);
                t3.setTextColor(Color.WHITE);
                t1.setGravity(Gravity.CENTER);
                t2.setGravity(Gravity.CENTER);
                t3.setGravity(Gravity.CENTER);
                c1.addView(t1);
                c2.addView(t2);
                c3.addView(t3);
            }
        }
    }
}
