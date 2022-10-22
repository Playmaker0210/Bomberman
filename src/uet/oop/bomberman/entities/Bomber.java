package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.field.Items;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;
import uet.oop.bomberman.menu.MainMenu;
import uet.oop.bomberman.menu.Sound;

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

    public int getNumBombs() {
        return numBombs;
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

    /**
     * kiem tra xem da di ra khoi bomb chua
     */
    public void activeBomb() {
        for (int i=0;i<bombs.size();i++) {
            double tmpX = (double) bombs.get(i).getX()/Sprite.SCALED_SIZE;
            double tmpY = (double) bombs.get(i).getY()/Sprite.SCALED_SIZE;
            double vtX = (double) x/Sprite.SCALED_SIZE;
            double vtY = (double) y/Sprite.SCALED_SIZE;
            if((Math.abs(tmpX - vtX) >= 0.95 || Math.abs(tmpY - vtY) >= 0.95) &&
                    !bombs.get(i).isActivate()) {
                bombs.get(i).setActivate(true);
            }
        }

        for (int i=0;i<detonator.size();i++) {
            double tmpX = (double) detonator.get(i).getX()/Sprite.SCALED_SIZE;
            double tmpY = (double) detonator.get(i).getY()/Sprite.SCALED_SIZE;
            double vtX = (double) x/Sprite.SCALED_SIZE;
            double vtY = (double) y/Sprite.SCALED_SIZE;
            if((Math.abs(tmpX - vtX) >= 0.95 || Math.abs(tmpY - vtY) >= 0.95) &&
                    !detonator.get(i).isActivate()) {
                detonator.get(i).setActivate(true);
            }
        }
    }

    public void goUp(NttGroup levelManage) {
        for (int i = 1; i <= this.speed; i++) {
            this.y -= 1;
            if((checkBoundBomb(levelManage) && !bombPass) || checkBoundBrick(levelManage) || checkBoundWall(levelManage)) {
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

    public void goDown(NttGroup levelManage) {
        for (int i = 1; i <= this.speed; i++) {
            this.y += 1;
            if ((checkBoundBomb(levelManage) && !bombPass) || checkBoundBrick(levelManage) || checkBoundWall(levelManage)) {
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

    public void goRight(NttGroup levelManage) {
        for (int i = 1; i <= this.speed; i++) {
            this.x += 1;
            if((checkBoundBomb(levelManage) && !bombPass) || checkBoundBrick(levelManage) || checkBoundWall(levelManage)) {
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

    public void goLeft(NttGroup levelManage) {
        for (int i = 1; i <= this.speed; i++) {
            this.x -= 1;
            if((checkBoundBomb(levelManage) && !bombPass) || checkBoundBrick(levelManage) || checkBoundWall(levelManage)) {
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

    /**
     * kiem tra bom no
     */
    public void checkBomb(NttGroup levelManage) {
        for(int i=0;i<bombs.size();i++) {
            bombs.get(i).setTimeStop(LocalDateTime.now());
            int tmp = (int) Duration.between(bombs.get(i).getTimePut(),bombs.get(i).getTimeStop()).toMillis();
            if((tmp-levelManage.diffTime)%10==0)
            {
                bombs.get(i).setImg(Sprite.movingSprite(Sprite.bomb,Sprite.bomb_1,
                        Sprite.bomb_2,tmp/10,76).getFxImage());
            }
            if(tmp-levelManage.diffTime>=2000) {
                if (BombermanGame.gameSound) {
                    Sound.playSound("soundBomb");
                }
                int idX = bombs.get(i).getX()/Sprite.SCALED_SIZE;
                int idY = bombs.get(i).getY()/Sprite.SCALED_SIZE;
                BombermanGame.pathFinder.node[idX][idY].setSolid(false);
                bombs.get(i).explosion(bombRadius, levelManage);
                levelManage.bombList.remove(i);
                levelManage.map[idX][idY] = ' ';
                bombs.remove(i);
                i--;
            }
        }
    }

    public void detonate(NttGroup levelManage) {
        int idX = detonator.get(0).getX()/Sprite.SCALED_SIZE;
        int idY = detonator.get(0).getY()/Sprite.SCALED_SIZE;
        BombermanGame.pathFinder.node[idX][idY].setSolid(false);
        detonator.get(0).explosion(bombRadius, levelManage);
        levelManage.detonatorList.remove(0);
        detonator.remove(0);
    }

    public void createBomb(NttGroup levelManage) {
        int tmpX = this.x / Sprite.SCALED_SIZE;
        int tmpY = this.y / Sprite.SCALED_SIZE;
        Bomb bo = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
        levelManage.map[tmpX][tmpY] = 'b';
        BombermanGame.pathFinder.node[tmpX][tmpY].setSolid(true);
        bombs.add(bo);
        bo.setTimePut(LocalDateTime.now()) ;
        levelManage.bombList.add(bo);
    }

    public void createDetonator(NttGroup levelManage) {
        numDetonator--;
        int tmpX = this.x / Sprite.SCALED_SIZE;
        int tmpY = this.y / Sprite.SCALED_SIZE;
        Bomb bo = new Bomb(tmpX, tmpY, Sprite.bomb.getFxImage());
        levelManage.map[tmpX][tmpY] = 'b';
        BombermanGame.pathFinder.node[tmpX][tmpY].setSolid(true);
        detonator.add(bo);
        levelManage.detonatorList.add(bo);
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

    /**
     * thoi gian het item
     */
    public void timeOutItem(NttGroup levelManage) {
        LocalDateTime checkTime = LocalDateTime.now();
        if (flamePass) {
            if (Duration.between(timeGetFlamePass,checkTime).toSeconds() >= 20 + levelManage.diffTime/1000) {
                flamePass = false;
            }
        }
        if (bombPass) {
            if(Duration.between(timeGetBombPass,checkTime).toSeconds() >= 20 + levelManage.diffTime/1000) {
                bombPass = false;
            }
        }
    }

    public void setDie() {
        isAlive = false;
        timeDie = LocalDateTime.now();
    }

    /**
     * thay doi sprite cua Bomber khi bi chet
     */
    public void reset(NttGroup levelManage) {
        LocalDateTime checkTime = LocalDateTime.now();
        int tmp = (int) Duration.between(timeDie, checkTime).toMillis();
        imgCounter++;
        if (imgCounter == 10 && BombermanGame.gameSound) {
            Sound.playSound("soundDie");
        }
        if (imgCounter==10) setImg(Sprite.player_dead1.getFxImage());
        if (imgCounter==75) setImg(Sprite.player_dead2.getFxImage());
        if (imgCounter==150) setImg(Sprite.player_dead3.getFxImage());
        if (imgCounter==200) setImg(Sprite.grass.getFxImage());
        if(tmp - levelManage.diffTime>=1500) {
            BombermanGame.playerLife--;
            if (BombermanGame.playerLife > 0) {
                this.x = 32;
                this.y = 32;
                isAlive = true;
                imgCounter = 0;
                setImg(Sprite.player_down.getFxImage());
                MainMenu.running = true;
                MainMenu.showNext = true;
                MainMenu.showType = MainMenu.SHOW_SATGE;
            }
        }
    }
}