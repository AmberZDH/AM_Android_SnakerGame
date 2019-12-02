package com.example.snake.engine;

import com.example.snake.Classes.Cordinate;
import com.example.snake.enums.Direction;
import com.example.snake.enums.GameState;
import com.example.snake.enums.Tiletype;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameEngine {
    public static final int Gamewidth = 32;
    public static final int Gameheight = 32;
    private Random random = new Random();
    public final static int [] score= new int[2];
    private List<Cordinate> walls = new ArrayList<>();
    private List<Cordinate> snake = new ArrayList<>();
    private List<Cordinate> apple = new ArrayList<>();
    private boolean increaseTail = false;
    private Direction direction_current = Direction.East;
    private GameState currentGameState = GameState.Running;

    private Cordinate getSnakeHead() {
        return snake.get(0);
    }

    public GameEngine() {}

    public void intitGame() {

        Addapple();
        Addwalls();
        Addsnake();
    }


    public void UpdateDirection(Direction newDirection) {
        if (Math.abs(newDirection.ordinal() - direction_current.ordinal()) % 2 == 1) {
            direction_current = newDirection;
        }
    }

    public void Update() {
        // update snake
        switch (direction_current) {

            case North:
                UpdateSnake(0, -1);
                break;
            case East:
                UpdateSnake(1, 0);
                break;
            case South:
                UpdateSnake(0, 1);
                break;
            case West:
                UpdateSnake(-1, 0);
                break;
        }
        // checking wall collision
        for (Cordinate w : walls) {
            if (snake.get(0).equals(w)) {
                currentGameState = GameState.Lost;
            }
        }

        // checking self collision
        for (int i = 1; i < snake.size(); i++) {
            if (getSnakeHead().equals(snake.get(i))) {
                currentGameState = GameState.Lost;
                return;

            }
        }
//        checking eaten apple
        Cordinate appleToRemove = null;
        for (Cordinate apples : apple){
            if (getSnakeHead() .equals(apples) ){
                appleToRemove=apples;
                increaseTail=true;

            }
        }
        if (appleToRemove!=null){
            apple.remove(appleToRemove);
            Addapple();
        }

    }


    public Tiletype[][] getMap() {
        Tiletype[][] map = new Tiletype[Gamewidth][Gameheight];
        for (int x = 0; x < Gamewidth; x++) {
            for (int y = 0; y < Gameheight; y++) {
                map[x][y] = Tiletype.Nothing;
            }
        }

        for (Cordinate s : snake) {
            map[s.getX()][s.getY()] = Tiletype.SnakeTail;
        }
        for (Cordinate a : apple) {
            map[a.getX()][a.getY()] = Tiletype.Apple;

        }
        map[snake.get(0).getX()][snake.get(0).getY()] = Tiletype.SnakeHead;


        for (Cordinate wall : walls) {
            map[wall.getX()][wall.getY()] = Tiletype.wall;
        }
        return map;
    }

    private void Addapple() {
        Cordinate cordinate = null;

        boolean added = false;
        while (!added) {
            int x = 1 + random.nextInt(Gamewidth - 2);
            int y = 1 + random.nextInt(Gameheight - 2);
            cordinate = new Cordinate(x, y);
            boolean collision = false;
            for (Cordinate s : snake) {
                if (s.equals(cordinate))
                    collision = true;
            }
//            if (collision = true)
//                continue;
            for (Cordinate a : apple) {
                if (a.equals(cordinate)) {
                    collision = true;
                }

            }
            added = !collision;
        }
        apple.add(cordinate);

    }

    private void Addsnake() {
        snake.clear();
        snake.add(new Cordinate(7, 7));
        snake.add(new Cordinate(6, 7));
        snake.add(new Cordinate(5, 7));
        snake.add(new Cordinate(4, 7));
        snake.add(new Cordinate(3, 7));
        snake.add(new Cordinate(2, 7));

    }

    private void UpdateSnake(int x, int y) {
        int newX = snake.get(snake.size()-1).getX();
        int newY = snake.get(snake.size()-1).getY();
        for (int i = snake.size() - 1; i > -0; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }
        if (increaseTail){
            score[0]=score[0]+2;
            System.out.println(score[0]+" again");
            snake.add(new Cordinate(newX,newY));
                    increaseTail=false;
        }
        snake.get(0).setX(snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);
    }

    private void Addwalls() {
        for (int x = 0; x < Gamewidth; x++) {
            walls.add(new Cordinate(x, 0));
            walls.add(new Cordinate(x, Gameheight - 1));
        }
        //left and right walls
        for (int y = 0; y < Gamewidth; y++) {
            walls.add(new Cordinate(0, y));
            walls.add(new Cordinate(Gamewidth - 1, y));
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}