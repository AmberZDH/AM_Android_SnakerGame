package com.example.snake.Classes;


public class Cordinate {
    private int x;
    private int y;

    public Cordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cordinate cordinate = (Cordinate) o;

        if (getX() != cordinate.getX()) return false;
        return getY() == cordinate.getY();

    }


}
