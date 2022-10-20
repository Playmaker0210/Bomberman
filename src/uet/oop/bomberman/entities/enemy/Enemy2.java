package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy2 extends Enemy{
    public Enemy2(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setSpeedX(1);
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
            if (this.x%32==0&&this.y%32==0 ) {
                int idX = this.x / Sprite.SCALED_SIZE;
                int idY = this.y / Sprite.SCALED_SIZE;
                if (countDirection(idX, idY) >= 3) {
                    randomSpeed();
                    generateRandomDirection();
                }
            }
        }
        if (isAlive() && moveCounter == 0) {
            if (this.getSpeedX() > 0) {
                this.img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2
                        , Sprite.oneal_right3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedX() < 0) {
                this.img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2
                        , Sprite.oneal_left3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedY() > 0) {
                this.img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2
                        , Sprite.oneal_right3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2
                        , Sprite.oneal_left3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            }
        }
    }

    public void setSpecificDead() {
        setImg(Sprite.oneal_dead.getFxImage());
    }

}
