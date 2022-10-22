package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Enemy2 extends Enemy{
    public Enemy2(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setSpeedX(1);
    }
    @Override
    public void update(NttGroup levelManage) {
        moveCounter = (moveCounter+1)%2;
        if (isAlive() && moveCounter == 0) {
            if (this.getSpeedX() == 0) {
                if (checkBoundWall(levelManage) || checkBoundBomb(levelManage) || checkBoundBrick(levelManage)) {
                    int tmp = this.getSpeedY();
                    this.setSpeedY(-1*tmp);
                }
                this.y += this.getSpeedY();
            } else {
                if (checkBoundBrick(levelManage) || checkBoundBomb(levelManage) || checkBoundWall(levelManage)) {
                    int tmp = this.getSpeedX();
                    this.setSpeedX(-1*tmp);
                }
                this.x += this.getSpeedX();
            }
            if (this.x%32==0&&this.y%32==0 ) {
                int idX = this.x / Sprite.SCALED_SIZE;
                int idY = this.y / Sprite.SCALED_SIZE;
                if (countDirection(idX, idY, levelManage) >= 3) {
                    randomSpeed();
                    generateRandomDirection(levelManage);
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

    /**
     * random 1 huong co the di
     */
    public void generateRandomDirection(NttGroup levelManage) {
        int currentDirection = 5;
        if (getSpeedY() > 0) {
            currentDirection = DOWN;
        }
        if (getSpeedY() < 0) {
            currentDirection = UP;
        }
        if (getSpeedX() > 0) {
            currentDirection = RIGHT;
        }
        if (getSpeedX() < 0) {
            currentDirection = LEFT;
        }
        Random rand = new Random();
        int direction = rand.nextInt(4);
        int idX = x/Sprite.SCALED_SIZE;
        int idY = y/Sprite.SCALED_SIZE;
        while (true) {
            if (direction == UP && levelManage.map[idX][idY-1] == ' '
                    && currentDirection != DOWN) {
                break;
            }
            if (direction == DOWN && levelManage.map[idX][idY+1] == ' '
                    && currentDirection != UP) {
                break;
            }
            if (direction == LEFT && levelManage.map[idX-1][idY] == ' '
                    && currentDirection != RIGHT) {
                break;
            }
            if (direction == RIGHT && levelManage.map[idX+1][idY] == ' '
                    && currentDirection != LEFT) {
                break;
            }
            direction = (direction + 1) % 4;
        }
        switch (direction) {
            case UP:
                setSpeedY(getSpeed() * -1);
                setSpeedX(0);
                break;
            case DOWN:
                setSpeedY(getSpeed());
                setSpeedX(0);
                break;
            case LEFT:
                setSpeedX(getSpeed() * -1);
                setSpeedY(0);
                break;
            case RIGHT:
                setSpeedX(getSpeed());
                setSpeedY(0);
                break;
        }
        //System.out.println(direction+ " "+ currentDirection);
    }

    public void randomSpeed() {
        Random rand = new Random();
        setSpeed(rand.nextInt(2) + 1);
    }

    /**
     * dem cac huong co the di duoc
     */
    public int countDirection(int idX, int idY, NttGroup levelManage) {
        int cnt = 0;
        if (levelManage.map[idX-1][idY] == ' ') {
            cnt++;
        }
        if (levelManage.map[idX+1][idY] == ' ') {
            cnt++;
        }
        if (levelManage.map[idX][idY-1] == ' ') {
            cnt++;
        }
        if (levelManage.map[idX][idY+1] == ' ') {
            cnt++;
        }
        return cnt;
    }
}
