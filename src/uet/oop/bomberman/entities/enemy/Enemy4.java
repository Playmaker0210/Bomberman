package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy4 extends Enemy {

    private boolean chaseMode;
    private int imgCondition;
    public Enemy4(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setSpeedX(1);
        chaseMode = false;
        imgCondition = 4;
    }
    @Override
    public void update() {
        moveCounter = (moveCounter+1)%imgCondition;
        if(!chaseMode || !NttGroup.bombers.isAlive) {
            checkChase();
        }
        if (isAlive() && moveCounter == 0 && !chaseMode) {
            if (this.getSpeedX() == 0) {
                if (this.y + this.getSpeedY() < Sprite.SCALED_SIZE
                        || this.y + this.getSpeedY() > 11*Sprite.SCALED_SIZE) {
                    int tmp = this.getSpeedY();
                    this.setSpeedY(-1*tmp);
                }
                this.y += this.getSpeedY();
            } else {
                if (this.x + this.getSpeedX() < Sprite.SCALED_SIZE
                        || this.x + this.getSpeedX() > 29*Sprite.SCALED_SIZE) {
                    int tmp = this.getSpeedX();
                    this.setSpeedX(-1*tmp);
                }
                this.x += this.getSpeedX();
            }
            //System.out.println(this.x + " " + this.y);
        }
        if (isAlive() && moveCounter == 0 && chaseMode) {
            int tmp = this.getSpeed();
            if (NttGroup.bombers.getX() != this.x) {
                this.setSpeedY(0);
                if (NttGroup.bombers.getX() > this.x) {
                    this.setSpeedX(tmp);
                }
                else {
                    this.setSpeedX(-1*tmp);
                }
                this.x += this.getSpeedX();
            }
            if (NttGroup.bombers.getY() != this.y && NttGroup.bombers.getX() == this.x) {
                this.setSpeedX(0);
                if (NttGroup.bombers.getY() > this.y) {
                    this.setSpeedY(tmp);
                }
                else {
                    this.setSpeedY(-1*tmp);
                }
                this.y += this.getSpeedY();
            }
        }
        if (isAlive() && moveCounter == 0) {
            if (this.getSpeedX() > 0) {
                this.img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2
                        , Sprite.kondoria_right3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedX() < 0) {
                this.img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2
                        , Sprite.kondoria_left3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedY() > 0) {
                this.img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2
                        , Sprite.kondoria_right3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2
                        , Sprite.kondoria_left3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            }
        }
    }

    public void setSpecificDead() {
        setImg(Sprite.kondoria_dead.getFxImage());
    }

    public void checkChase() {
        if (!NttGroup.bombers.isAlive) {
            chaseMode = false;
            return;
        }
        int idX = this.x/Sprite.SCALED_SIZE;
        int idY = this.y/Sprite.SCALED_SIZE;
        int playerX = NttGroup.bombers.getX()/Sprite.SCALED_SIZE;
        int playerY = NttGroup.bombers.getY()/Sprite.SCALED_SIZE;
        if ((idX==playerX && Math.abs(idY-playerY)<=3)
                || (idY==playerY && Math.abs(idX-playerX)<=3)) {
            chaseMode = true;
            imgCondition = 2;
        }
    }
}
