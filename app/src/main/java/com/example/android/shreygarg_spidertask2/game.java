package com.example.android.shreygarg_spidertask2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class game extends AppCompatActivity {
    cellcanvas cell;
    int mode = MainActivity.mode;
    int toughness=level.toughness;
    Database mydb;
    public SharedPreferences mPreferances;
    public SharedPreferences .Editor mEditor;
    int besttime=0;
    String bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setframe();
        mydb= new Database(this);
        if(mode==2)
        {
            TextView textView = (TextView) findViewById(R.id.best);
            timer();
            mPreferances = PreferenceManager.getDefaultSharedPreferences(this);
            mEditor = mPreferances.edit();
            if(toughness==1)
            {
                bt = mPreferances.getString(getString(R.string.teasy),"-");
            }
            else if(toughness==2)
            {
                bt = mPreferances.getString(getString(R.string.tmedium),"-");
            }
            else if(toughness==3)
            {
                bt =mPreferances.getString(getString(R.string.thard),"-");
            }
            textView.setText("Best : "+bt);
            if(!bt.equals("-"))
                besttime=Integer.parseInt(bt);
        }
        else
        {
            TextView textView = (TextView) findViewById(R.id.timer);
            textView.setVisibility(View.INVISIBLE);
        }

    }
    float h,w;
    float tx,ty;
    float cx,cy;
    int x,y;
    int[][] frame = {{0,0,0},{0,0,0},{0,0,0}};
    int turn=0;
    int done=0;
    int[][] row={{3,0,0},{3,0,0},{3,0,0}};
    int[][] column={{3,0,0},{3,0,0},{3,0,0}};
    int[] diagonalp={3,0,0};
    int[] diagonalnp={3,0,0};
    int ms=0;
    public class cellcanvas extends View
    {  Paint paint;

        public cellcanvas(Context context) {
            super(context);
            paint= new Paint();
            paint.setColor(Color.parseColor("#ffa000"));
            paint.setStrokeWidth(20);
            paint.setStrokeCap(Paint.Cap.ROUND);
        }

        @Override
        public void onDraw(Canvas canvas)
        {
            h=getHeight();
            w=getWidth();
            float margin = 20*getResources().getDisplayMetrics().density;
            super.onDraw(canvas);
            canvas.drawLine(w/3, (h-w)/2+margin, w/3, (h+w)/2-margin, paint);
            canvas.drawLine(2*w/3, (h-w)/2+margin, 2*w/3, (h+w)/2-margin, paint);
            canvas.drawLine(margin, (h-w)/2+w/3, w-margin, (h-w)/2+w/3, paint);
            canvas.drawLine(margin, (h-w)/2+2*w/3, w-margin, (h-w)/2+2*w/3, paint);

        }
        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            if(event.getAction()==MotionEvent.ACTION_DOWN)
            {
                tx=event.getX();
                ty=event.getY();
                if(ty<(h+w)/2&&ty>(h-w)/2)
                {
                    if(mode==1)
                    function();
                    else if(mode==2&&cm==1)
                        function();
                }
            }
            return true;
        }
    }
    public class crosscanvas extends View
    {
        Paint paint;

        public crosscanvas(Context context) {
            super(context);
            paint=new Paint();
            paint.setColor(Color.WHITE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(20);
        }

        @Override
        public void onDraw(Canvas canvas)
        {
            canvas.drawLine(cx-w/12,cy-w/12,cx+w/12,cy+w/12,paint);
            canvas.drawLine(cx-w/12,cy+w/12,cx+w/12,cy-w/12,paint);
        }

    }
    public class circlecanvas extends View
    {
        Paint paint;

        public circlecanvas(Context context) {
            super(context);
            paint=new Paint();
            paint.setColor(Color.WHITE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(20);
        }

        @Override
        public void onDraw(Canvas canvas)
        {
            canvas.drawCircle(cx,cy,w/(6*(float)Math.sqrt(3)),paint);
            paint.setColor(Color.parseColor("#212121"));
            canvas.drawCircle(cx,cy,w/(6*(float)Math.sqrt(3))-20,paint);
        }

    }
    public void setframe()
    {
        cell = new cellcanvas(this);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.framelayout);
        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        cell.setLayoutParams(rp);
        relativeLayout.addView(cell);
    }
    int t=0;
    public void timer()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ms++;
                if(ms%10==0) {
                    TextView textView = (TextView) findViewById(R.id.timer);
                    t++;
                    textView.setText("Time : " + t);
                }

                if (done == 0) {
                    timer();
                }
                else
                {

                    if(win==1)
                    {
                        if(toughness==1) {
                            mydb.insertData("Easy", t);
                            if(t<besttime||besttime==0)
                            {
                                TextView textView = (TextView) findViewById(R.id.best);
                                mEditor.putString(getString(R.string.teasy),""+t);
                                mEditor.commit();
                                textView.setText("Best : "+t);
                            }
                        }
                        if(toughness==2) {
                            mydb.insertData("Medium", t);
                            if(t<besttime||besttime==0)
                            {
                                TextView textView = (TextView) findViewById(R.id.best);
                                mEditor.putString(getString(R.string.tmedium),""+t);
                                mEditor.commit();
                                textView.setText("Best : "+t);
                            }
                        }
                        if(toughness==3) {
                            mydb.insertData("Hard", t);
                            if(t<besttime||besttime==0)
                            {
                                TextView textView = (TextView) findViewById(R.id.best);
                                mEditor.putString(getString(R.string.thard),""+t);
                                mEditor.commit();
                                textView.setText("Best : "+t);
                            }
                        }

                        Toast.makeText(game.this,"Data Inserted : "+t,Toast.LENGTH_LONG).show();
                    }
                }
            }
        },100);
    }
    public void function()
    {   if(done==0) {
        if (tx < w / 3) {
            cx = w / 6;
            x = 0;
        } else if (tx < 2 * w / 3) {
            cx = w / 2;
            x = 1;
        } else {
            cx = 5 * w / 6;
            x = 2;
        }

        if (ty > (h - w) / 2 && ty < (h - w) / 2 + w / 3) {
            cy = (h - w) / 2 + w / 6;
            y = 0;
        } else if (ty > (h - w) / 2 + w / 3 && ty < (h - w) / 2 + 2 * w / 3) {
            cy = (h - w) / 2 + w / 2;
            y = 1;
        } else if (ty < (h + w) / 2 && ty > (h - w) / 2 + 2 * w / 3) {
            cy = (h - w) / 2 + 5 * w / 6;
            y = 2;
        }
        if (frame[x][y] == 0) {
            place();
        }
    }
    }
    int cm=1;
    public void place()
    {
        turn++;
        if (turn % 2 == 1&&done==0) {
            crosscanvas cross = new crosscanvas(this);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.framelayout);
            RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            cross.setLayoutParams(rp);
            relativeLayout.addView(cross);
            row[x][1]++;
            row[x][0]--;
            column[y][1]++;
            column[y][0]--;
            if(x==y)
            {
                diagonalp[1]++;
                diagonalp[0]--;
            }
            if(x+y==2)
            {
                diagonalnp[1]++;
                diagonalnp[0]--;
            }
            frame[x][y] = 1;
            cm=0;

            check();
        } else if(turn%2==0&&done==0){
            circlecanvas circle = new circlecanvas(this);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.framelayout);
            RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            circle.setLayoutParams(rp);
            relativeLayout.addView(circle);
            row[x][2]++;
            row[x][0]--;
            column[y][2]++;
            column[y][0]--;
            if(x==y)
            {
                diagonalp[2]++;
                diagonalp[0]--;
            }
            if(x+y==2)
            {
                diagonalnp[2]++;
                diagonalnp[0]--;
            }
            frame[x][y] = 2;
            ia=0;
            cm=1;
            check();

        }

    }
    int win=0;
    public void check() {
        for (int i = 0; i < 3; i++)
            if (frame[i][0] == frame[i][1] && frame[i][0] == frame[i][2] && frame[i][0] != 0) {
                TextView textView = (TextView) findViewById(R.id.res);
                if (mode == 1)
                    textView.setText("Player " + frame[i][0] + " Won");
                else if (mode == 2 && turn % 2 == 1) {
                    textView.setTextColor(Color.parseColor("#4CAF50"));
                    textView.setText("You Win");
                    win=1;
                } else {
                    textView.setTextColor(Color.parseColor("#F44336"));
                    textView.setText("You Lose");
                }
                textView.setVisibility(View.VISIBLE);
                done = 1;
            }
        for (int i = 0; i < 3; i++)
            if (frame[0][i] == frame[1][i] && frame[0][i] == frame[2][i] && frame[0][i] != 0) {
                TextView textView = (TextView) findViewById(R.id.res);
                if (mode == 1)
                    textView.setText("Player " + frame[0][i] + " Won");
                else if (mode == 2 && turn % 2 == 1) {
                    textView.setTextColor(Color.parseColor("#4CAF50"));
                    textView.setText("You Win");
                    win=1;
                } else {
                    textView.setTextColor(Color.parseColor("#F44336"));
                    textView.setText("You Lose");
                }
                textView.setVisibility(View.VISIBLE);
                done = 1;
            }
        if (frame[0][0] == frame[1][1] && frame[0][0] == frame[2][2] && frame[0][0] != 0) {
            TextView textView = (TextView) findViewById(R.id.res);
            if (mode == 1)
                textView.setText("Player " + frame[1][1] + " Won");
            else if (mode == 2 && turn % 2 == 1) {
                textView.setTextColor(Color.parseColor("#4CAF50"));
                textView.setText("You Win");
                win=1;
            } else {
                textView.setTextColor(Color.parseColor("#F44336"));
                textView.setText("You Lose");
            }
            textView.setVisibility(View.VISIBLE);
            done = 1;
        }
        if (frame[0][2] == frame[1][1] && frame[1][1] == frame[2][0] && frame[1][1] != 0) {
            TextView textView = (TextView) findViewById(R.id.res);
            if (mode == 1)
                textView.setText("Player " + frame[1][1] + " Won");
            else if (mode == 2 && turn % 2 == 1) {
                textView.setTextColor(Color.parseColor("#4CAF50"));
                textView.setText("You Win");
                win=1;
            } else {
                textView.setTextColor(Color.parseColor("#F44336"));
                textView.setText("You Lose");
            }
            textView.setVisibility(View.VISIBLE);
            done = 1;
        }
        if (turn == 9&&done==0) {
            TextView textView = (TextView) findViewById(R.id.res);
            textView.setText("Draw");
            textView.setVisibility(View.VISIBLE);
            done = 1;

        }

        if (mode == 2&&turn%2==1&&done==0) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (toughness == 1) {
                        computermoveeasy();
                    } else if (toughness == 2) {
                        computermovemedium();
                    } else {
                        computermovehard();
                    }
                }
            }, 500);
    }

    }
    int ia=0;
    public int checktwomine()
    {
        for(int i=0;i<3;i++)
        {
            if(row[i][2]==2&&row[i][0]==1)
            {
                for(int j=0;j<3;j++)
                {
                    if(frame[i][j]==0)
                    {
                        x=i;
                        y=j;
                        return 1;
                    }
                }
            }

        }

        for(int i=0;i<3;i++)
        {
            if(column[i][2]==2&&column[i][0]==1)
            {
                for(int j=0;j<3;j++)
                {
                    if(frame[j][i]==0)
                    {
                        x=j;
                        y=i;
                        return 1;
                    }
                }
            }

        }

        if(diagonalp[2]==2&&diagonalp[0]==1)
        {
            for(int i=0;i<3;i++)
            {
                if(frame[i][i]==0)
                {
                    x=i;
                    y=i;
                    return 1;
                }
            }
        }
        if(diagonalnp[2]==2&&diagonalnp[0]==1)
        {
            for(int i=0;i<3;i++)
            {
                if(frame[i][2-i]==0)
                {
                    x=i;
                    y=2-i;
                    return 1;
                }
            }
        }

        return 0;
    }
    public int checktwo()
    {
        for(int i=0;i<3;i++)
        {
            if(row[i][1]==2&&row[i][0]==1)
            {
                for(int j=0;j<3;j++)
                {
                    if(frame[i][j]==0)
                    {
                        x=i;
                        y=j;
                        return 1;
                    }
                }
            }

        }

        for(int i=0;i<3;i++)
        {
            if(column[i][1]==2&&column[i][0]==1)
            {
                for(int j=0;j<3;j++)
                {
                    if(frame[j][i]==0)
                    {
                        x=j;
                        y=i;
                        return 1;
                    }
                }
            }

        }

        if(diagonalp[1]==2&&diagonalp[0]==1)
        {
            for(int i=0;i<3;i++)
            {
                if(frame[i][i]==0)
                {
                    x=i;
                    y=i;
                    return 1;
                }
            }
        }
        if(diagonalnp[1]==2&&diagonalnp[0]==1)
        {
            for(int i=0;i<3;i++)
            {
                if(frame[i][2-i]==0)
                {
                    x=i;
                    y=2-i;
                    return 1;
                }
            }
        }

        return 0;
    }
    public int checkones()
    {
        for(int i=0;i<3;i++)
        {
            if(row[i][0]==2&&row[i][2]==1)
            {
                for(int j=0;j<3;j++)
                {
                    if(column[j][0]==0&&column[j][2]==1&&frame[i][j]==0)
                    {
                        x=i;
                        y=j;
                        return 1;
                    }
                }
                if(diagonalp[0]==2&&frame[i][i]==0)
                {
                    x=i;
                    y=i;
                    return 1;
                }
                if(diagonalnp[0]==2&&frame[i][2-i]==0)
                {
                    x=i;
                    y=2-i;
                    return 1;
                }
            }
        }
        for(int i=0;i<3;i++)
        {
            if(column[i][0]==2&&column[i][2]==1)
            {
                for(int j=0;j<3;j++)
                {
                    if(row[j][0]==0&&row[j][2]==1&&frame[j][i]==0)
                    {
                        x=j;
                        y=i;
                        return 1;
                    }
                }
                if(diagonalp[0]==2&&frame[i][i]==0)
                {
                    x=i;
                    y=i;
                    return 1;
                }
                if(diagonalnp[0]==2&&frame[i][2-i]==0)
                {
                    x=2-i;
                    y=i;
                    return 1;
                }
            }
        }
        if(diagonalp[0]==2&&diagonalnp[0]==2&&frame[1][1]==0)
        {
            x=1;
            y=1;
            return 1;
        }
        return 0;
    }
    public int checkonesopp()
    {
        if(frame[0][0]==1&&frame[2][2]==1||frame[0][2]==1&&frame[2][0]==1)
        {
            if(frame[0][0]!=2&&frame[2][2]!=2&&frame[0][2]!=2&&frame[2][0]!=2)
            {
                if(frame[0][1]==0)
                {
                    x=0;
                    y=1;
                }
                else if(frame[1][0]==0)
                {
                    x=1;
                    y=0;
                }
                else if(frame[1][2]==0)
                {
                    x=1;
                    y=2;
                }
                else if(frame[2][0]==0)
                {
                    x=2;
                    y=0;
                }
                return 1;
            }
        }
        for(int i=0;i<3;i++)
        {
            if(row[i][0]==2&&row[i][1]==1)
            {
                for(int j=0;j<3;j++)
                {
                    if(column[j][0]==0&&column[j][1]==1&&frame[i][j]==0)
                    {
                        x=i;
                        y=j;
                        return 1;
                    }
                }
                if(diagonalp[0]==2&&frame[i][i]==0)
                {
                    x=i;
                    y=i;
                    return 1;
                }
                if(diagonalnp[0]==2&&frame[i][2-i]==0)
                {
                    x=i;
                    y=2-i;
                    return 1;
                }
            }
        }
        for(int i=0;i<3;i++)
        {
            if(column[i][0]==2&&column[i][1]==1)
            {
                for(int j=0;j<3;j++)
                {
                    if(row[j][0]==0&&row[j][1]==1&&frame[j][i]==0)
                    {
                        x=j;
                        y=i;
                        return 1;
                    }
                }
                if(diagonalp[0]==2&&frame[i][i]==0)
                {
                    x=i;
                    y=i;
                    return 1;
                }
                if(diagonalnp[0]==2&&frame[2-i][i]==0)
                {
                    x=2-i;
                    y=i;
                    return 1;
                }
            }
        }
        if(diagonalp[0]==2&&diagonalnp[0]==2&&frame[1][1]==0)
        {
            x=1;
            y=1;
            return 1;
        }
        return 0;
    }
    public void computermoveeasy()
    {
        Random random = new Random();
        int t = random.nextInt(9);
        while (frame[t/3][t%3]!=0)
        {
            t=random.nextInt(9);
        }
        x=t/3;
        y=t%3;
        cx=(w/6)+x*(w/3);
        cy=(h-w)/2+w/6+y*(w/3);
        place();
    }
    public void computermovemedium()
    {
        ia=checktwomine();
        if(ia==1)
        {
            cx=(w/6)+x*(w/3);
            cy=(h-w)/2+w/6+y*(w/3);
            place();
        }
        else
        {   ia=checktwo();
            if(ia==1)
            {
                cx=(w/6)+x*(w/3);
                cy=(h-w)/2+w/6+y*(w/3);
                place();
            }
            else
            computermoveeasy();
        }
    }
    public void computermovehard()
    {
        ia=checktwomine();
        if(ia==1)
        {
            cx=(w/6)+x*(w/3);
            cy=(h-w)/2+w/6+y*(w/3);
            place();
        }

        else
        {   ia=checktwo();
            if(ia==1)
            {
                cx=(w/6)+x*(w/3);
                cy=(h-w)/2+w/6+y*(w/3);
                place();
            }
            else
            {
                ia=checkones();
                if(ia==1)
                {
                    cx=(w/6)+x*(w/3);
                    cy=(h-w)/2+w/6+y*(w/3);
                    place();
                }
                else
                {

                    if(checkonesopp()==1)
                    {
                        cx=(w/6)+x*(w/3);
                        cy=(h-w)/2+w/6+y*(w/3);
                        place();
                    }
                    else
                    {
                        if(frame[1][1]==0)
                        {
                            x=1;
                            y=1;
                            cx=(w/6)+x*(w/3);
                            cy=(h-w)/2+w/6+y*(w/3);
                            place();
                        }
                        else
                        {
                            if(frame[0][0]==0)
                            {
                                x=0;
                                y=0;
                                cx=(w/6)+x*(w/3);
                                cy=(h-w)/2+w/6+y*(w/3);
                                place();
                            }
                            else if(frame[0][2]==0)
                            {
                                x=0;
                                y=2;
                                cx=(w/6)+x*(w/3);
                                cy=(h-w)/2+w/6+y*(w/3);
                                place();
                            }
                            else if(frame[2][0]==0)
                            {
                                x=2;
                                y=0;
                                cx=(w/6)+x*(w/3);
                                cy=(h-w)/2+w/6+y*(w/3);
                                place();
                            }
                            else if(frame[2][2]==0)
                            {
                                x=2;
                                y=2;
                                cx=(w/6)+x*(w/3);
                                cy=(h-w)/2+w/6+y*(w/3);
                                place();
                            }
                            else
                                computermoveeasy();
                        }
                    }
                }
            }
        }
    }
    public void refreshact(View view)
    {
        Intent refresh = new Intent(this,game.class);
        startActivity(refresh);
        finish();
    }
    public void mainmenu(View view)
    {Intent set = new Intent(this,MainActivity.class);
        startActivity(set);
        finish();

    }
}

