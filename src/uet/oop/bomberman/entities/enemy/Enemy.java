package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import java.util.Random;

public abstract class Enemy extends Entity {

    private int speed = 1;
    private int speedX = this.speed;
    private int speedY = 0;
    private boolean isAlive = true;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public int getSpeed() {
        return speed;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void generateRandomDirection() {
        Random rand = new Random();
        int direction = rand.nextInt(4);
        switch (direction) {
            case UP:
                this.speedY = this.getSpeed();
                this.speedX = 0;
                break;
            case DOWN:
                this.speedY = this.getSpeed() * -1;
                this.speedX = 0;
                break;
            case LEFT:
                this.speedX = this.getSpeed() * -1;
                this.speedY = 0;
                break;
            case RIGHT:
                this.speedX = this.getSpeed();
                this.speedY = 0;
                break;
        }
    }

    public void RandomSpeed() {
        Random rand = new Random();
        this.speed = rand.nextInt(1) + 1;
    }

    public void chaseBomber() {

    }
}