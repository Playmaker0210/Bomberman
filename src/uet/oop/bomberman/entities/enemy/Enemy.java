package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public abstract class Enemy extends Entity {

    private int speed = 1;
    private int speedX = 0;
    private int speedY = 0;
    private int imgCounter = 0;

    private LocalDateTime collisionStart;
    private boolean isAlive = true;
    protected int moveCounter = 0;
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
        int currentDirection = 5;
        if (speedY > 0) {
            currentDirection = DOWN;
        }
        if (speedY < 0) {
            currentDirection = UP;
        }
        if (speedX > 0) {
            currentDirection = RIGHT;
        }
        if (speedX < 0) {
            currentDirection = LEFT;
        }
        Random rand = new Random();
        int direction = rand.nextInt(4);
        int idX = x/Sprite.SCALED_SIZE;
        int idY = y/Sprite.SCALED_SIZE;
        while (true) {
            if (direction == UP && NttGroup.map[idX][idY-1] == ' '
                    && currentDirection != DOWN) {
                break;
            }
            if (direction == DOWN && NttGroup.map[idX][idY+1] == ' '
                    && currentDirection != UP) {
                break;
            }
            if (direction == LEFT && NttGroup.map[idX-1][idY] == ' '
                    && currentDirection != RIGHT) {
                break;
            }
            if (direction == RIGHT && NttGroup.map[idX+1][idY] == ' '
                    && currentDirection != LEFT) {
                break;
            }
            direction = (direction + 1) % 4;
        }
        switch (direction) {
            case UP:
                this.speedY = this.getSpeed() * -1;
                this.speedX = 0;
                break;
            case DOWN:
                this.speedY = this.getSpeed();
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
        //System.out.println(direction+ " "+ currentDirection);
    }

    public void setCollisionStart(LocalDateTime tmp) {
        collisionStart = tmp;
    }

    public LocalDateTime getCollisionStart() {
        return collisionStart;
    }

    public abstract void setSpecificDead();
    public boolean checkDisappear() {
        imgCounter++;
        LocalDateTime timeCheck = LocalDateTime.now();
        int tmp = (int) Duration.between(collisionStart, timeCheck).toMillis();
        if (imgCounter == 40) setImg(Sprite.mob_dead1.getFxImage());
        if (imgCounter == 69) setImg(Sprite.mob_dead2.getFxImage());
        if (imgCounter == 98) setImg(Sprite.mob_dead3.getFxImage());
        if (tmp - NttGroup.diffTime >= 1300) {
            return true;
        }
        return false;
    }

    public void randomSpeed() {
        Random rand = new Random();
        this.speed = rand.nextInt(2) + 1;
    }

    public int countDirection(int idX, int idY) {
        int cnt = 0;
        if (NttGroup.map[idX-1][idY] == ' ') {
            cnt++;
        }
        if (NttGroup.map[idX+1][idY] == ' ') {
            cnt++;
        }
        if (NttGroup.map[idX][idY-1] == ' ') {
            cnt++;
        }
        if (NttGroup.map[idX][idY+1] == ' ') {
            cnt++;
        }
        return cnt;
    }
}