package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;

import java.time.LocalDateTime;

public class Bomb extends Entity {
    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    private boolean activate = false;
    public static final int EXIT = 1000;

    public LocalDateTime getTimePut() {
        return timePut;
    }

    public void setTimePut(LocalDateTime timePut) {
        this.timePut = timePut;
    }

    public LocalDateTime getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(LocalDateTime timeStop) {
        this.timeStop = timeStop;
    }

    public LocalDateTime getTimeStartFlame() {
        return timeStartFlame;
    }

    public void setTimeStartFlame(LocalDateTime timeStartFlame) {
        this.timeStartFlame = timeStartFlame;
    }

    private LocalDateTime timePut;
    private LocalDateTime timeStop;
    private LocalDateTime timeStartFlame;
    public Flame[] bombFlame = new Flame[30];

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    /**
     * kiem tra cac brick bi pha khi bom no
     */
    public void explosion(int bombRadius, NttGroup levelManage) {
        timeStartFlame = LocalDateTime.now();
        int indexX = x / Sprite.SCALED_SIZE;
        int indexY = y / Sprite.SCALED_SIZE;
        int id = 0;
        bombFlame[id] = new Flame(indexX, indexY,
                Sprite.bomb_exploded1.getFxImage(), timeStartFlame);
        int centerFlame = levelManage.flames.size();
        levelManage.flames.add(bombFlame[id]);
        int lastPos;
        lastPos = EXIT;
        for (int i=1;i<bombRadius;i++) {
            if(levelManage.map[indexX][indexY-i]==' ') {
                id++;
                bombFlame[id] = new Flame(indexX, indexY-i,
                        Sprite.explosion_vertical1.getFxImage(), timeStartFlame);
                lastPos = indexY-i-1;
                levelManage.flames.add(bombFlame[id]);
            }
            else {
                break;
            }
        }
        if(lastPos==EXIT&&levelManage.map[indexX][indexY-1]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX, indexY-1,
                    Sprite.explosion_vertical_top_last1.getFxImage(), timeStartFlame);
            levelManage.flames.add(bombFlame[id]);
        }
        if(lastPos!=EXIT&&levelManage.map[indexX][lastPos]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX, lastPos,
                    Sprite.explosion_vertical_top_last1.getFxImage(), timeStartFlame);
            levelManage.flames.add(bombFlame[id]);
        }
        int check = bombRadius;
        if(id<check) {
            int tmp = levelManage.flames.size();
            check = id;
            if (lastPos==EXIT) {
                levelManage.flames.get(centerFlame).setExploUp(true);
            }
            else {
                levelManage.flames.get(tmp-1).setExploUp(true);
            }
        }
        check += bombRadius;
        lastPos = EXIT;
        for (int i=1;i<bombRadius;i++) {
            if(levelManage.map[indexX][indexY+i]==' ') {
                id++;
                bombFlame[id] = new Flame(indexX, indexY+i,
                        Sprite.explosion_vertical1.getFxImage(), timeStartFlame);
                lastPos = indexY+i+1;
                levelManage.flames.add(bombFlame[id]);
            }
            else {
                break;
            }
        }
        if(lastPos==EXIT&&levelManage.map[indexX][indexY+1]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX, indexY+1,
                    Sprite.explosion_vertical_down_last1.getFxImage(), timeStartFlame);
            levelManage.flames.add(bombFlame[id]);
        }
        if(lastPos!=EXIT&&levelManage.map[indexX][lastPos]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX, lastPos,
                    Sprite.explosion_vertical_down_last1.getFxImage(), timeStartFlame);
            levelManage.flames.add(bombFlame[id]);
        }
        if(id<check) {
            int tmp = levelManage.flames.size();
            check = id;
            if (lastPos==EXIT) {
                levelManage.flames.get(centerFlame).setExploDown(true);
            }
            else {
                levelManage.flames.get(tmp-1).setExploDown(true);
            }
        }
        check += bombRadius;
        lastPos = EXIT;
        for (int i=1;i<bombRadius;i++) {
            if(levelManage.map[indexX-i][indexY]==' ') {
                id++;
                bombFlame[id] = new Flame(indexX-i, indexY,
                        Sprite.explosion_horizontal1.getFxImage(), timeStartFlame);
                lastPos = indexX-i-1;
                levelManage.flames.add(bombFlame[id]);
            }
            else {
                break;
            }
        }
        if(lastPos==EXIT&&levelManage.map[indexX-1][indexY]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX-1, indexY,
                    Sprite.explosion_horizontal_left_last1.getFxImage(), timeStartFlame);
            levelManage.flames.add(bombFlame[id]);
        }
        if(lastPos!=EXIT&&levelManage.map[lastPos][indexY]==' ') {
            id++;
            bombFlame[id] = new Flame(lastPos, indexY,
                    Sprite.explosion_horizontal_left_last1.getFxImage(), timeStartFlame);
            levelManage.flames.add(bombFlame[id]);
        }
        if(id<check) {
            int tmp = levelManage.flames.size();
            check = id;
            if (lastPos==EXIT) {
                levelManage.flames.get(centerFlame).setExploLeft(true);
            }
            else {
                levelManage.flames.get(tmp-1).setExploLeft(true);
            }
        }
        check += bombRadius;
        lastPos = EXIT;
        for (int i=1;i<bombRadius;i++) {
            if(levelManage.map[indexX+i][indexY]==' ') {
                id++;
                bombFlame[id] = new Flame(indexX+i, indexY,
                        Sprite.explosion_horizontal1.getFxImage(), timeStartFlame);
                lastPos = indexX+i+1;
                levelManage.flames.add(bombFlame[id]);
            }
            else
            {
                break;
            }
        }
        if(lastPos==EXIT&&levelManage.map[indexX+1][indexY]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX+1, indexY,
                    Sprite.explosion_horizontal_right_last1.getFxImage(), timeStartFlame);
            levelManage.flames.add(bombFlame[id]);
        }
        if(lastPos!=EXIT&&levelManage.map[lastPos][indexY]==' ') {
            id++;
            bombFlame[id] = new Flame(lastPos, indexY,
                    Sprite.explosion_horizontal_right_last1.getFxImage(), timeStartFlame);
            levelManage.flames.add(bombFlame[id]);
        }
        if(id<check) {
            int tmp = levelManage.flames.size();
            if (lastPos==EXIT) {
                levelManage.flames.get(centerFlame).setExploRight(true);
            }
            else {
                levelManage.flames.get(tmp-1).setExploRight(true);
            }
        }
    }
}
