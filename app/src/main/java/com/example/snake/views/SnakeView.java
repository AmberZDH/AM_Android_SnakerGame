package com.example.snake.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.snake.enums.Tiletype;

public class SnakeView extends View {
    private Paint mpaint = new Paint();
    private Tiletype snakeViewMap[][];
    public SnakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setSnakeViewMap(Tiletype[][]map){
        this.snakeViewMap=map;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (snakeViewMap!=null){
            float tilesizeX= canvas.getWidth()/snakeViewMap.length;
            float tilesizeY= canvas.getHeight()/snakeViewMap[0].length;

            float circlesize = Math.min(tilesizeX,tilesizeY)/2;
            //循环设置 地图 和 snake
            for (int x = 0;x<snakeViewMap.length;x++){
                for(int y =0; y<snakeViewMap[x].length;y++){
                    switch (snakeViewMap[x][y]){

                        case Nothing://非蛇区域
                            mpaint.setColor(Color.WHITE);
                            break;
                        case wall:
                            mpaint.setColor(Color.BLUE);
                            break;
                        case SnakeHead:
                            mpaint.setColor(Color.RED);
                            break;
                        case SnakeTail:
                            mpaint.setColor(Color.GREEN);
                            break;
                        case Apple:
                            mpaint.setColor(Color.RED);
                            break;
                    }

                    canvas.drawCircle(
                            x * tilesizeX + tilesizeX / 2f + circlesize / 2,
                            y * tilesizeY + tilesizeY / 2f + circlesize / 2,
                            circlesize,
                            mpaint
                    );

                }
            }


        }
    }
}