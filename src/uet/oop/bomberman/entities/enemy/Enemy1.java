package uet.oop.bomberman.entities.enemy;

import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class Enemy1 extends Enemy {

    public Enemy1(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setSpeed(1);
    }
    @Override
    public void update() {
        moveCounter = (moveCounter+1)%2;
        if (isAlive() && moveCounter == 0) {
            if (this.getSpeedX() == 0) {
                if (checkBoundWall() || checkBoundBomb() || checkBoundBrick()) {
                    int tmp = this.getSpeedY();
                    this.setSpeedY(-1*tmp);
                }
                this.y += this.getSpeedY();
            } else {
                if (checkBoundBrick() || checkBoundBomb() || checkBoundWall()) {
                    int tmp = this.getSpeedX();
                    this.setSpeedX(-1*tmp);
                }
                this.x += this.getSpeedX();
            }
        }
        if (isAlive() && moveCounter == 0) {
            if (this.getSpeedX() > 0) {
                this.img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2
                        , Sprite.balloom_right3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedX() < 0) {
                this.img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2
                        , Sprite.balloom_left3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedY() > 0) {
                this.img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2
                        , Sprite.balloom_right3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2
                        , Sprite.balloom_left3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            }
        }
    }

    public void setSpecificDead() {
        setImg(Sprite.balloom_dead.getFxImage());
    }
}

