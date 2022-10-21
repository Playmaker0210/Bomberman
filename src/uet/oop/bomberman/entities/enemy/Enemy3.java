package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;

public class Enemy3 extends Enemy{
    private boolean chaseMode;
    private int currentIndex;
    private int goalX;
    private int goalY;

    public Enemy3(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setSpeedX(1);
        this.moveCounter = 0;
        chaseMode = false;
    }
    @Override
    public void update() {
        moveCounter = (moveCounter+1)%2;
        if(!chaseMode) {
            checkChase();
        }
        if (chaseMode) {
            makeChase();
        }
        else {
            if (isAlive() && moveCounter == 0) {
                if (this.getSpeedX() == 0) {
                    if (checkBoundWall() || checkBoundBomb() || checkBoundBrick()) {
                        int tmp = this.getSpeedY();
                        this.setSpeedY(-1 * tmp);
                    }
                    this.y += this.getSpeedY();
                } else {
                    if (checkBoundBrick() || checkBoundBomb() || checkBoundWall()) {
                        int tmp = this.getSpeedX();
                        this.setSpeedX(-1 * tmp);
                    }
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
            goalX = playerX;
            goalY = playerY;
            BombermanGame.pathFinder.setNode(idX, idY, playerX, playerY);
            BombermanGame.pathFinder.search();
            currentIndex = BombermanGame.pathFinder.pathList.size()-1;
        }
    }

    public void makeChase() {
        if (!NttGroup.bombers.isAlive) {
            chaseMode = false;
            return;
        }
        int idX = this.x / Sprite.SCALED_SIZE;
        int idY = this.y / Sprite.SCALED_SIZE;
        int playerX = NttGroup.bombers.getX() / Sprite.SCALED_SIZE;
        int playerY = NttGroup.bombers.getY() / Sprite.SCALED_SIZE;
        if (currentIndex == -1) {
            BombermanGame.pathFinder.setNode(idX, idY, playerX, playerY);
            BombermanGame.pathFinder.search();
            currentIndex = BombermanGame.pathFinder.pathList.size()-1;
            goalX = playerX;
            goalY = playerY;
        }
        if (currentIndex >= 0) {
            if (idX == BombermanGame.pathFinder.pathList.get(currentIndex).col &&
                    idY == BombermanGame.pathFinder.pathList.get(currentIndex).row &&
                    this.x % Sprite.SCALED_SIZE == 0 && this.y % Sprite.SCALED_SIZE == 0) {
                currentIndex--;
            }
        }
        if(moveCounter == 0 && currentIndex >= 0) {
            int tmpX = BombermanGame.pathFinder.pathList.get(currentIndex).col;
            int tmpY = BombermanGame.pathFinder.pathList.get(currentIndex).row;
            if (BombermanGame.pathFinder.node[tmpX][tmpY].isSolid()) {
                currentIndex = -1;
            }
            else {
                makeMove(currentIndex);
            }
        }
    }

    public void makeMove(int index) {
        int tmp = this.getSpeed();
        if (this.x == BombermanGame.pathFinder.pathList.get(index).col * 32) {
            this.setSpeedX(0);
            if (this.y < BombermanGame.pathFinder.pathList.get(index).row * 32) {
                this.setSpeedY(tmp);
            }
            else {
                this.setSpeedY(-1*tmp);
            }
            this.y += this.getSpeedY();
        }
        if (this.y == BombermanGame.pathFinder.pathList.get(index).row * 32) {
            this.setSpeedY(0);
            if (this.x < BombermanGame.pathFinder.pathList.get(index).col * 32) {
                this.setSpeedX(tmp);
            }
            else {
                this.setSpeedX(-1*tmp);
            }
            this.x += this.getSpeedX();
        }
    }
}
