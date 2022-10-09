package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy3 extends Enemy{
    private boolean chaseMode;
    private int countUpDown;
    private int countLeftRight;

    // If trên + dưới != ' ' y -> x
    // If trái + phải != ' ' x -> y
    public Enemy3(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setSpeed(1);
        this.moveCounter = 0;
        chaseMode = false;
        countUpDown = 0;
        countLeftRight = 0;
    }
    @Override
    public void update() {
        moveCounter = (moveCounter+1)%2;
        checkChase();
        if (chaseMode) makeChase();
        if (isAlive() && moveCounter == 0) {
            //System.out.println(countUpDown+ " " + countLeftRight);
            if (this.getSpeedX() == 0) {
                countLeftRight = 0;
                if (checkBoundWall() || checkBoundBomb() || checkBoundBrick()) {
                    int tmp = this.getSpeedY();
                    this.setSpeedY(-1*tmp);
                    countUpDown++;
                }
                if (countUpDown == 2) {
                    this.setSpeedX(this.getSpeed());
                    this.setSpeedY(0);
                }
                else {
                    countUpDown = 0;
                    this.y += this.getSpeedY();
                }
            } else {
                countUpDown = 0;
                if (checkBoundBrick() || checkBoundBomb() || checkBoundWall()) {
                    int tmp = this.getSpeedX();
                    this.setSpeedX(-1*tmp);
                    countLeftRight++;
                }
                if (countLeftRight == 2) {
                    this.setSpeedY(this.getSpeed());
                    this.setSpeedX(0);
                }
                else {
                    countLeftRight = 0;
                    this.x += this.getSpeedX();
                }
            }
        }
        if (isAlive() && moveCounter == 0) {
            if (this.getSpeedX() > 0) {
                this.img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2
                        , Sprite.doll_right3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedX() < 0) {
                this.img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2
                        , Sprite.doll_left3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedY() > 0) {
                this.img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2
                        , Sprite.doll_right3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2
                        , Sprite.doll_left3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            }
        }
        System.out.println(this.getSpeedX() + " " + this.getSpeedY());
    }
    public void setSpecificDead() {
        setImg(Sprite.doll_dead.getFxImage());
    }

    public void checkChase() {
        if (this.x%Sprite.SCALED_SIZE!=0
        ||this.y%Sprite.SCALED_SIZE!=0) {
            return ;
        }
        int idX = this.x/Sprite.SCALED_SIZE;
        int idY = this.y/Sprite.SCALED_SIZE;
        int playerX = NttGroup.bombers.getX()/Sprite.SCALED_SIZE;
        int playerY = NttGroup.bombers.getY()/Sprite.SCALED_SIZE;
        if ((idX==playerX && Math.abs(idY-playerY)<=3)
        || (idY==playerY && Math.abs(idX-playerX)<=3)) {
            this.setSpeed(2);
            chaseMode = true;
        }
    }

    public void makeChase() {
        int idX = this.x/Sprite.SCALED_SIZE;
        int idY = this.y/Sprite.SCALED_SIZE;
        int playerX = NttGroup.bombers.getX()/Sprite.SCALED_SIZE;
        int playerY = NttGroup.bombers.getY()/Sprite.SCALED_SIZE;
        if (idX == playerX) {
            this.setSpeedX(0);
            int tmp = this.getSpeed();
            if (idY > playerY) {
                if(this.getSpeedY()<0) {
                    this.setSpeedY(-1*tmp);
                }
                else {
                    this.setSpeedY(tmp);
                }
            }
            else {
                if(this.getSpeedY()>0) {
                    this.setSpeedY(-1*tmp);
                }
                else {
                    this.setSpeedY(tmp);
                }
            }
        }
        else if (idY == playerY) {
            this.setSpeedY(0);
            int tmp = this.getSpeed();
            if (idX > playerX) {
                if(this.getSpeedX()<0) {
                    this.setSpeedX(-1*tmp);
                }
                else {
                    this.setSpeedX(tmp);
                }
            }
            else {
                if(this.getSpeedX()>0) {
                    this.setSpeedX(-1*tmp);
                }
                else {
                    this.setSpeedX(tmp);
                }
            }
        }
        else {
            chaseMode = false;
            this.setSpeed(1);
        }
    }
}
