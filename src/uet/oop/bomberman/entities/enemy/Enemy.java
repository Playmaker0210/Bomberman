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

    public abstract void update(NttGroup levelManage) ;

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

    public void setCollisionStart(LocalDateTime tmp) {
        collisionStart = tmp;
    }

    public LocalDateTime getCollisionStart() {
        return collisionStart;
    }

    public abstract void setSpecificDead();

    /**
     * thay doi cac sprites anh cua enemy khi bi tieu diet
     */
    public boolean checkDisappear(NttGroup levelManage) {
        imgCounter++;
        LocalDateTime timeCheck = LocalDateTime.now();
        int tmp = (int) Duration.between(collisionStart, timeCheck).toMillis();
        if (imgCounter == 40) setImg(Sprite.mob_dead1.getFxImage());
        if (imgCounter == 69) setImg(Sprite.mob_dead2.getFxImage());
        if (imgCounter == 98) setImg(Sprite.mob_dead3.getFxImage());
        if (tmp - levelManage.diffTime >= 1300) {
            return true;
        }
        return false;
    }

}