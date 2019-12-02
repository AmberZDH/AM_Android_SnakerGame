package com.example.snake;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.snake.engine.GameEngine;
import com.example.snake.enums.Direction;
import com.example.snake.enums.GameState;
import com.example.snake.views.SnakeView;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    int score;
    //更新速度
    private long updatedelay = 200;
    TextView tv, tv2;
    private float prevX, prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameEngine = new GameEngine();
        gameEngine.intitGame();
        snakeView = (SnakeView) findViewById(R.id.sanke_view);
        tv = (TextView) findViewById(R.id.show_score);
        snakeView.setOnTouchListener(this);
        startUpdateHander();


    }

    //获取当前游戏状态【分数，蛇的运动状态，地图情况】
    public void startUpdateHander() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                score = GameEngine.score[0];
//                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                int lev = sharedPref.getInt("level", 150);
//                System.out.println(lev +" thos");
                tv.setText("Score: " + score);
                gameEngine.Update();
                if (gameEngine.getCurrentGameState() == GameState.Running) {
                    handler.postDelayed(this, updatedelay);
                }
                if (gameEngine.getCurrentGameState() == GameState.Lost) {
                    onGameLost();
                }
                snakeView.setSnakeViewMap(gameEngine.getMap());
                snakeView.invalidate();
            }
        });

    }

    //游戏失败
    private void onGameLost() {
        score = GameEngine.score[0];
        System.out.println(score + " this is your score");
        Toast.makeText(this, "You Lost Score " + score, Toast.LENGTH_LONG).show();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int HS = sharedPref.getInt("High_score", 0);
        tv.setText("Score: " + score);
        System.out.println(HS + " thos");
        if (score > HS) {
            SharedPreferences Pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor piss = Pref.edit();
            piss.putInt("High_score", score);
            piss.commit();
        }
    }

    //创建速度菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.speed, menu);
        return true;
    }

    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //增加handle的更新速度
        if (id == R.id.Decre) {
            updatedelay = 200;
            updatedelay = updatedelay - 50;
//            piss.putInt("level", 500);
//            piss.commit();
            return true;
        }
        //减小handle的更新速度
        else if (id == R.id.Incre) {
            updatedelay = 200;
            updatedelay = updatedelay + 50;
//            piss.putInt("level", 150);
//            piss.commit();
            return true;
        }
        //重置游戏
        else if (id == R.id.Reset) {
            GameEngine.score[0] = 0;
            updatedelay = 200;
            gameEngine = new GameEngine();
            gameEngine.intitGame();
            startUpdateHander();
            return true;
        }
        //退出系统
        else if (id == R.id.Exit) {
            System.exit(1);
            return true;
        }
        //暂停，加大handle的更新速度
        else if (id == R.id.pause) {
            updatedelay = 5200;
            return true;
        }
        //获取分数
        else if (id == R.id.Hscore) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int HS = sharedPref.getInt("High_score", 0);
            System.out.println(HS + " thos");
            Toast.makeText(this, "High Score " + HS, Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //监听手势运动事件
    @Override
    public boolean onTouch(View view, MotionEvent mEvent) {
        updatedelay = 200;
        switch (mEvent.getAction()) {
            //当手触碰到屏幕时获取触碰点的位置
            case MotionEvent.ACTION_DOWN:
                prevX = mEvent.getX();
                prevY = mEvent.getY();
                break;

            //当手提起来的时获取离开位置，计算移动方向
            case MotionEvent.ACTION_UP:
                float newX = mEvent.getX();
                float newY = mEvent.getY();
                //判断左右
                if (Math.abs(newX - prevX) > Math.abs(newY - prevY)) {
                    // Left - Right
                    if (newX > prevX) {
//                  right
                        gameEngine.UpdateDirection(Direction.East);
                    } else {
                        //left
                        gameEngine.UpdateDirection(Direction.West);
                    }
                }
                //判断 Up Down
                else {
                    //Up - Down
                    if (newY > prevY) {
                        //Down
                        gameEngine.UpdateDirection(Direction.South);
                    } else {
                        //UP
                        gameEngine.UpdateDirection(Direction.North);
                    }

                }
                break;


        }
        return true;
    }
}