package com.example.android.shreygarg_spidertask2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class level extends AppCompatActivity {

    static int toughness=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levellay);
    }
    public void easy(View view)
    {
        toughness=1;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.scale);
        linearLayout.setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.display);
        textView.setVisibility(View.VISIBLE);
    }
    public void medium(View view)
    {
        toughness=2;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.scale);
        linearLayout.setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.display);
        textView.setTextColor(Color.parseColor("#03a9f4"));
        textView.setText("Medium");
        textView.setVisibility(View.VISIBLE);
    }
    public void hard(View view)
    {
        toughness=3;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.scale);
        linearLayout.setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.display);
        textView.setTextColor(Color.parseColor("#f44336"));
        textView.setText("Hard");
        textView.setVisibility(View.VISIBLE);
    }
    public void start(View view)
    {
        if(toughness!=0) {
            Intent start = new Intent(this, game.class);
            startActivity(start);
            finish();
        }

    }
}
