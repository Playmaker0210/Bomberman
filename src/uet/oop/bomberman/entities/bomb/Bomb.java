package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;

import java.time.LocalDateTime;

public class Bomb extends Entity {
    public boolean activate = false;
    public static final int EXIT = 1000;
    public LocalDateTime timePut;
    public LocalDateTime timeStop;
    public LocalDateTime timeStartFlame;
    public Flame[] bombFlame = new Flame[30];

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void update() {

    }

    public void explosion(int bombRadius) {
        timeStartFlame = LocalDateTime.now();
        int indexX = x / Sprite.SCALED_SIZE;
        int indexY = y / Sprite.SCALED_SIZE;
        int id = 0;
        bombFlame[id] = new Flame(indexX, indexY,
                Sprite.bomb_exploded1.getFxImage(), timeStartFlame);
        int centerFlame = NttGroup.flames.size();
        NttGroup.flames.add(bombFlame[id]);
        int lastPos;
        lastPos = EXIT;
        for (int i=1;i<bombRadius;i++) {
            if(NttGroup.map[indexX][indexY-i]==' ') {
                id++;
                bombFlame[id] = new Flame(indexX, indexY-i,
                        Sprite.explosion_vertical1.getFxImage(), timeStartFlame);
                lastPos = indexY-i-1;
                NttGroup.flames.add(bombFlame[id]);
            }
            else {
                break;
            }
        }
        if(lastPos==EXIT&&NttGroup.map[indexX][indexY-1]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX, indexY-1,
                    Sprite.explosion_vertical_top_last1.getFxImage(), timeStartFlame);
            NttGroup.flames.add(bombFlame[id]);
        }
        if(lastPos!=EXIT&&NttGroup.map[indexX][lastPos]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX, lastPos,
                    Sprite.explosion_vertical_top_last1.getFxImage(), timeStartFlame);
            NttGroup.flames.add(bombFlame[id]);
        }
        int check = bombRadius;
        if(id<check) {
            int tmp = NttGroup.flames.size();
            check = id;
            if (lastPos==EXIT) {
                NttGroup.flames.get(centerFlame).exploUp = true;
            }
            else {
                NttGroup.flames.get(tmp-1).exploUp = true;
            }
        }
        check += bombRadius;
        lastPos = EXIT;
        for (int i=1;i<bombRadius;i++) {
            if(NttGroup.map[indexX][indexY+i]==' ') {
                id++;
                bombFlame[id] = new Flame(indexX, indexY+i,
                        Sprite.explosion_vertical1.getFxImage(), timeStartFlame);
                lastPos = indexY+i+1;
                NttGroup.flames.add(bombFlame[id]);
            }
            else {
                break;
            }
        }
        if(lastPos==EXIT&&NttGroup.map[indexX][indexY+1]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX, indexY+1,
                    Sprite.explosion_vertical_down_last1.getFxImage(), timeStartFlame);
            NttGroup.flames.add(bombFlame[id]);
        }
        if(lastPos!=EXIT&&NttGroup.map[indexX][lastPos]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX, lastPos,
                    Sprite.explosion_vertical_down_last1.getFxImage(), timeStartFlame);
            NttGroup.flames.add(bombFlame[id]);
        }
        if(id<check) {
            int tmp = NttGroup.flames.size();
            check = id;
            if (lastPos==EXIT) {
                NttGroup.flames.get(centerFlame).exploDown = true;
            }
            else {
                NttGroup.flames.get(tmp-1).exploDown = true;
            }
        }
        check += bombRadius;
        lastPos = EXIT;
        for (int i=1;i<bombRadius;i++) {
            if(NttGroup.map[indexX-i][indexY]==' ') {
                id++;
                bombFlame[id] = new Flame(indexX-i, indexY,
                        Sprite.explosion_horizontal1.getFxImage(), timeStartFlame);
                lastPos = indexX-i-1;
                NttGroup.flames.add(bombFlame[id]);
            }
            else {
                break;
            }
        }
        if(lastPos==EXIT&&NttGroup.map[indexX-1][indexY]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX-1, indexY,
                    Sprite.explosion_horizontal_left_last1.getFxImage(), timeStartFlame);
            NttGroup.flames.add(bombFlame[id]);
        }
        if(lastPos!=EXIT&&NttGroup.map[lastPos][indexY]==' ') {
            id++;
            bombFlame[id] = new Flame(lastPos, indexY,
                    Sprite.explosion_horizontal_left_last1.getFxImage(), timeStartFlame);
            NttGroup.flames.add(bombFlame[id]);
        }
        if(id<check) {
            int tmp = NttGroup.flames.size();
            check = id;
            if (lastPos==EXIT) {
                NttGroup.flames.get(centerFlame).exploLeft = true;
            }
            else {
                NttGroup.flames.get(tmp-1).exploLeft = true;
            }
        }
        check += bombRadius;
        lastPos = EXIT;
        for (int i=1;i<bombRadius;i++) {
            if(NttGroup.map[indexX+i][indexY]==' ') {
                id++;
                bombFlame[id] = new Flame(indexX+i, indexY,
                        Sprite.explosion_horizontal1.getFxImage(), timeStartFlame);
                lastPos = indexX+i+1;
                NttGroup.flames.add(bombFlame[id]);
            }
            else
            {
                break;
            }
        }
        if(lastPos==EXIT&&NttGroup.map[indexX+1][indexY]==' ') {
            id++;
            bombFlame[id] = new Flame(indexX+1, indexY,
                    Sprite.explosion_horizontal_right_last1.getFxImage(), timeStartFlame);
            NttGroup.flames.add(bombFlame[id]);
        }
        if(lastPos!=EXIT&&NttGroup.map[lastPos][indexY]==' ') {
            id++;
            bombFlame[id] = new Flame(lastPos, indexY,
                    Sprite.explosion_horizontal_right_last1.getFxImage(), timeStartFlame);
            NttGroup.flames.add(bombFlame[id]);
        }
        if(id<check) {
            int tmp = NttGroup.flames.size();
            if (lastPos==EXIT) {
                NttGroup.flames.get(centerFlame).exploRight = true;
            }
            else {
                NttGroup.flames.get(tmp-1).exploRight = true;
            }
        }
    }
}
