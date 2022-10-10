package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy3 extends Enemy{
    private boolean chaseMode;
    private boolean bombEscape;

    public Enemy3(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setSpeed(1);
        this.moveCounter = 0;
        chaseMode = false;
        bombEscape = false;
    }
    @Override
    public void update() {
        moveCounter = (moveCounter+1)%2;
        if(!chaseMode) {
            checkChase();
        }
        if (checkBoundBomb() && !bombEscape && moveCounter==0) {
            chaseMode = false;
            for(Bomb tmp : NttGroup.bombList) {
                int bombX = tmp.getX()/Sprite.SCALED_SIZE;
                int bombY = tmp.getY()/Sprite.SCALED_SIZE;
                int idX = this.x/Sprite.SCALED_SIZE;
                int idY = this.y/Sprite.SCALED_SIZE;
                if (Math.abs(bombY - idY)<=1 || Math.abs(bombX - idX)<=1) {
                    System.out.println(this.getSpeedX()+ " "+this.getSpeedY() + " current");
                    if (this.getSpeedX()>0) {
                        this.setSpeedX(-1*this.getSpeedX());
                        this.setSpeedY(0);
                    }
                    if (this.getSpeedX()<0) {
                        this.setSpeedX(-1*this.getSpeedX());
                        this.setSpeedY(0);
                    }
                    if (this.getSpeedY()<0) {
                        this.setSpeedY(-1*this.getSpeedY());
                        this.setSpeedX(0);
                    }
                    if (this.getSpeedY()>0) {
                        this.setSpeedY(-1*this.getSpeedY());
                        this.setSpeedX(0);
                    }
                    System.out.println(this.getSpeedX()+ " "+this.getSpeedY());
                }
            }
            if(this.getSpeedX()!=0) {
                this.x += this.getSpeedX();
            }
            else {
                this.y += this.getSpeedY();
            }
            bombEscape = true;
        }
        if (chaseMode) makeChase();
        checkDirection();
        if (isAlive() && moveCounter == 0) {
            if (this.getSpeedX() == 0) {
                if (checkBoundWall() || checkBoundBrick()) {
                    int tmp = this.getSpeedY();
                    this.setSpeedY(-1*tmp);
                    bombEscape = false;
                }
                this.y += this.getSpeedY();
            } else {
                if (checkBoundBrick() || checkBoundWall()) {
                    int tmp = this.getSpeedX();
                    this.setSpeedX(-1*tmp);
                    bombEscape = false;
                }
                this.x += this.getSpeedX();
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
        //System.out.println(this.getSpeedX() + " " + this.getSpeedY());
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
            chaseMode = true;
        }
        else {
            chaseMode = false;
        }
    }

    public void makeChase() {
        if (this.x%Sprite.SCALED_SIZE!=0 || this.y%Sprite.SCALED_SIZE!=0) {
            return;
        }
        int idX = this.x/Sprite.SCALED_SIZE;
        int idY = this.y/Sprite.SCALED_SIZE;
        int playerX = NttGroup.bombers.getX()/Sprite.SCALED_SIZE;
        int playerY = NttGroup.bombers.getY()/Sprite.SCALED_SIZE;
        if (idX == playerX) {
            this.setSpeedX(0);
            int tmp = this.getSpeed();
            if (idY > playerY) {
                this.setSpeedY(-1*tmp);
            }
            else {
                this.setSpeedY(tmp);
            }
        }
        else if (idY == playerY) {
            this.setSpeedY(0);
            int tmp = this.getSpeed();
            if (idX > playerX) {
                this.setSpeedX(-1*tmp);
            }
            else {
                this.setSpeedX(tmp);
            }
        }
        else {
            if (this.x%Sprite.SCALED_SIZE!=0||this.y%Sprite.SCALED_SIZE!=0) {
                int tmpX = this.x/Sprite.SCALED_SIZE;
                int tmpY = this.y/Sprite.SCALED_SIZE;
                this.x = tmpX * Sprite.SCALED_SIZE;
                this.y = tmpY * Sprite.SCALED_SIZE;
            }
            chaseMode = false;
        }
    }

    public void checkDirection() {
        int idX = this.x/Sprite.SCALED_SIZE;
        int idY = this.y/Sprite.SCALED_SIZE;
        if (NttGroup.map[idX][idY-1]!= ' ' && NttGroup.map[idX][idY+1]!= ' '
                && this.getSpeedX()==0) {
            this.setSpeedY(0);
            this.setSpeedX(this.getSpeed());
        }
        if (NttGroup.map[idX-1][idY]!= ' ' && NttGroup.map[idX+1][idY]!= ' '
                && this.getSpeedY()==0) {
            this.setSpeedX(0);
            this.setSpeedY(this.getSpeed());
        }
    }
}
