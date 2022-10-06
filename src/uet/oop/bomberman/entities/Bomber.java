package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.field.Grass;
import uet.oop.bomberman.graphics.Sprite;
import javafx.event.Event;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import uet.oop.bomberman.graphics.SpriteSheet;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bomber extends Entity {

    private int numBombs = 1;
    private int flameLength = 1;
    private int speed = Sprite.SCALED_SIZE / 8;
    public boolean isAlive = true;
    private int bombRadius = 1;
    private int keepMoving = 0;
    public ArrayList<Bomb> bombs = new ArrayList<>();

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }


    @Override
    public void update() {

    }

    public int getNumBombs() {
        return numBombs;
    }

    public void setNumBombs(int numBombs) {
        this.numBombs= numBombs;
    }

    public void activeBomb() {
        for (int i=0;i<bombs.size();i++) {
            double tmpX = (double) bombs.get(i).getX()/Sprite.SCALED_SIZE;
            double tmpY = (double) bombs.get(i).getY()/Sprite.SCALED_SIZE;
            double vtX = (double) x/Sprite.SCALED_SIZE;
            double vtY = (double) y/Sprite.SCALED_SIZE;
            if((Math.abs(tmpX - vtX) >= 0.9 || Math.abs(tmpY - vtY) >= 0.9) &&
               !bombs.get(i).activate) {
                bombs.get(i).activate=true;
            }
        }
    }

    public void goUp() {
        for (int i = 1; i <= this.speed; i++) {
            this.y -= 1;
            if(checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.y+=1;
                break;
            }
        }
        keepMoving+=this.speed;
        if(keepMoving > 100) {keepMoving = 0;}
        setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1,
                Sprite.player_up_2, keepMoving, 48).getFxImage());
        activeBomb();
    }

    public void goDown() {
        for (int i = 1; i <= this.speed; i++) {
            this.y += 1;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.y -= 1;
            }
        }
        keepMoving+=this.speed;
        if(keepMoving > 100) {keepMoving = 0;}
        setImg((Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1,
                Sprite.player_down_2, keepMoving, 48).getFxImage()));
        activeBomb();
    }

    public void goRight() {
        for (int i = 1; i <= this.speed; i++) {
            this.x += 1;
            if(checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.x-=1;
                break;
            }
        }
        keepMoving+=this.speed;
        if(keepMoving > 100) {keepMoving = 0;}
        setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1,
                Sprite.player_right_2, keepMoving, 48).getFxImage());
        activeBomb();
    }

    public void goLeft() {
        for (int i = 1; i <= this.speed; i++) {
            this.x -= 1;
            if(checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.x+=1;
                break;
            }
        }
        keepMoving+=this.speed;
        if(keepMoving > 100) {keepMoving = 0;}
        setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1,
                Sprite.player_left_2, keepMoving, 48).getFxImage());
        activeBomb();
    }

    public void checkBomb() {
        for(int i=0;i<bombs.size();i++) {
            bombs.get(i).timeStop= LocalDateTime.now();
            int tmp = (int) Duration.between(bombs.get(i).timePut,bombs.get(i).timeStop).toMillis();
            if(tmp%10==0)
            {
                bombs.get(i).setImg(Sprite.movingSprite(Sprite.bomb,Sprite.bomb_1,
                        Sprite.bomb_2,tmp/10,76).getFxImage());
            }
            if(tmp>=2000) {
                bombs.get(i).explosion(bombRadius);
                NttGroup.bombList.remove(i);
                bombs.remove(i);
                i--;
            }
        }
    }

    public void createBomb() {
        int tmpX = this.x / Sprite.SCALED_SIZE;
        int tmpY = this.y / Sprite.SCALED_SIZE;
        Bomb bo = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
        bombs.add(bo);
        bo.timePut= LocalDateTime.now();
        NttGroup.bombList.add(bo);
    }
}
