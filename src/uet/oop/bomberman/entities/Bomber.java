package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.field.Grass;
import uet.oop.bomberman.entities.field.Items;
import uet.oop.bomberman.graphics.Sprite;
import javafx.event.Event;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import uet.oop.bomberman.graphics.SpriteSheet;
import uet.oop.bomberman.menu.MainMenu;

import javax.management.DynamicMBean;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bomber extends Entity {

    private int numBombs = 1;
    private boolean bombPass = false;
    private boolean flamePass = false;
    private int speed = Sprite.SCALED_SIZE / 8;
    private LocalDateTime timeGetBombPass;
    private LocalDateTime timeGetFlamePass;
    private LocalDateTime timeDie;
    private int imgCounter = 0;
    private int numDetonator = 0;
    public boolean isAlive = true;
    private int bombRadius = 1;
    private int keepMoving = 0;
    public ArrayList<Bomb> bombs = new ArrayList<>();
    public ArrayList<Bomb> detonator = new ArrayList<>();

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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
    public boolean isFlamePass() {
        return flamePass;
    }

    public boolean isBombPass() {
        return bombPass;
    }
    public int getNumDetonator() {
        return numDetonator;
    }

    public LocalDateTime getTimeGetBombPass() {
        return timeGetBombPass;
    }

    public LocalDateTime getTimeGetFlamePass() {
        return timeGetFlamePass;
    }

    public LocalDateTime getTimeDie() {
        return timeDie;
    }

    public void activeBomb() {
        for (int i=0;i<bombs.size();i++) {
            double tmpX = (double) bombs.get(i).getX()/Sprite.SCALED_SIZE;
            double tmpY = (double) bombs.get(i).getY()/Sprite.SCALED_SIZE;
            double vtX = (double) x/Sprite.SCALED_SIZE;
            double vtY = (double) y/Sprite.SCALED_SIZE;
            if((Math.abs(tmpX - vtX) >= 0.95 || Math.abs(tmpY - vtY) >= 0.95) &&
                    !bombs.get(i).activate) {
                bombs.get(i).activate=true;
            }
        }
    }

    public void goUp() {
        for (int i = 1; i <= this.speed; i++) {
            this.y -= 1;
            if((checkBoundBomb() && !bombPass) || checkBoundBrick() || checkBoundWall()) {
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
            if ((checkBoundBomb() && !bombPass) || checkBoundBrick() || checkBoundWall()) {
                this.y -= 1;
                break;
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
            if((checkBoundBomb() && !bombPass) || checkBoundBrick() || checkBoundWall()) {
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
            if((checkBoundBomb() && !bombPass) || checkBoundBrick() || checkBoundWall()) {
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
            if((tmp-NttGroup.diffTime)%10==0)
            {
                bombs.get(i).setImg(Sprite.movingSprite(Sprite.bomb,Sprite.bomb_1,
                        Sprite.bomb_2,tmp/10,76).getFxImage());
            }
            if(tmp-NttGroup.diffTime>=2000) {
                bombs.get(i).explosion(bombRadius);
                NttGroup.bombList.remove(i);
                int idX = bombs.get(i).getX()/Sprite.SCALED_SIZE;
                int idY = bombs.get(i).getY()/Sprite.SCALED_SIZE;
                NttGroup.map[idX][idY] = ' ';
                bombs.remove(i);
                i--;
            }
        }
    }

    public void detonate() {
        detonator.get(0).explosion(bombRadius);
        NttGroup.detonatorList.remove(0);
        detonator.remove(0);
    }

    public void createBomb() {
        int tmpX = this.x / Sprite.SCALED_SIZE;
        int tmpY = this.y / Sprite.SCALED_SIZE;
        Bomb bo = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
        NttGroup.map[tmpX][tmpY] = 'b';
        bombs.add(bo);
        bo.timePut= LocalDateTime.now();
        NttGroup.bombList.add(bo);
    }

    public void createDetonator() {
        numDetonator--;
        int tmpX = this.x / Sprite.SCALED_SIZE;
        int tmpY = this.y / Sprite.SCALED_SIZE;
        Bomb bo = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
        NttGroup.map[tmpX][tmpY] = 'b';
        detonator.add(bo);
        NttGroup.detonatorList.add(bo);
    }

    public void getItem(int type) {
        switch (type) {
            case Items.TYPE_BOMBPASS:
                timeGetBombPass = LocalDateTime.now();
                bombPass = true;
                break;
            case Items.TYPE_BOMBS:
                numBombs++;
                break;
            case Items.TYPE_DETONATOR:
                numDetonator += 2;
                break;
            case Items.TYPE_FLAMEPASS:
                timeGetFlamePass = LocalDateTime.now();
                flamePass = true;
                break;
            case Items.TYPE_FLAMES:
                bombRadius++;
                break;
        }
    }

    public void timeOutItem() {
        LocalDateTime checkTime = LocalDateTime.now();
        if (flamePass) {
            if (Duration.between(timeGetFlamePass,checkTime).toSeconds() >= 20 + NttGroup.diffTime/1000) {
                flamePass = false;
            }
        }
        if (bombPass) {
            if(Duration.between(timeGetBombPass,checkTime).toSeconds() >= 20 + NttGroup.diffTime/1000) {
                bombPass = false;
            }
        }
    }

    public void setDie() {
        isAlive = false;
        timeDie = LocalDateTime.now();
    }

    public void reset() {
        LocalDateTime checkTime = LocalDateTime.now();
        int tmp = (int) Duration.between(timeDie, checkTime).toMillis();
        imgCounter++;
        if (imgCounter==10) setImg(Sprite.player_dead1.getFxImage());
        if (imgCounter==75) setImg(Sprite.player_dead2.getFxImage());
        if (imgCounter==150) setImg(Sprite.player_dead3.getFxImage());
        if (imgCounter==200) setImg(Sprite.grass.getFxImage());
        if(tmp - NttGroup.diffTime>=1500) {
            BombermanGame.playerLife--;
            if (BombermanGame.playerLife > 0) {
                this.x = 32;
                this.y = 32;
                isAlive = true;
                imgCounter = 0;
                setImg(Sprite.player_down.getFxImage());
                NttGroup.reset();
                MainMenu.running = true;
                MainMenu.showNext = true;
                MainMenu.showType = MainMenu.SHOW_SATGE;
            }
        }
    }
}