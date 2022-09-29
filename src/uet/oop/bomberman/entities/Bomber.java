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
import java.util.ArrayList;

public class Bomber extends Entity {

    private int numBombs = 1;
    private int flameLength = 1;
    private int speed = Sprite.SCALED_SIZE / 8;
    private boolean isAlive = true;
    private int keepMoving = 0;
    private GraphicsContext gc;
    public ArrayList<Bomb> bombs = new ArrayList<>();

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }


    @Override
    public void update() {

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
        setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, keepMoving, 48).getFxImage());
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
        setImg((Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, keepMoving, 48).getFxImage()));
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
        setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, keepMoving, 48).getFxImage());
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
        setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, keepMoving, 48).getFxImage());
    }

    public void createBomb() {
        int tmpX = this.x / Sprite.SCALED_SIZE;
        int tmpY = this.y / Sprite.SCALED_SIZE;
        Bomb bo = new Bomb(tmpX, tmpY, Sprite.bomb_1.getFxImage());
        //System.out.println(this.x + " " + this.y);
        bombs.add(bo);
        NttGroup.bombList.add(bo);
    }

}
