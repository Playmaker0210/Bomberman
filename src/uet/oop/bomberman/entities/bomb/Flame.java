package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.entities.enemy.Enemy3;
import uet.oop.bomberman.entities.field.Brick;
import uet.oop.bomberman.entities.field.Grass;
import uet.oop.bomberman.entities.field.Items;
import uet.oop.bomberman.entities.field.Portal;
import uet.oop.bomberman.graphics.Sprite;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Flame extends Entity {

    private LocalDateTime timeStartFlame;

    public boolean isExploLeft() {
        return exploLeft;
    }

    public void setExploLeft(boolean exploLeft) {
        this.exploLeft = exploLeft;
    }

    public boolean isExploRight() {
        return exploRight;
    }

    public void setExploRight(boolean exploRight) {
        this.exploRight = exploRight;
    }

    public boolean isExploUp() {
        return exploUp;
    }

    public void setExploUp(boolean exploUp) {
        this.exploUp = exploUp;
    }

    public boolean isExploDown() {
        return exploDown;
    }

    public void setExploDown(boolean exploDown) {
        this.exploDown = exploDown;
    }

    private boolean exploLeft;
    private boolean exploRight;
    private boolean exploUp;
    private boolean exploDown;
    private List<Brick> crackList = new ArrayList<>();
    private boolean[][] check = new boolean[100][100];
    private int imgCounter;
    public Flame(int xUnit, int yUnit, Image img, LocalDateTime timeStartFlame) {
        super(xUnit, yUnit, img);
        this.timeStartFlame = timeStartFlame;
        exploLeft = false;
        exploRight = false;
        exploUp = false;
        exploDown = false;
        imgCounter = 0;
    }

    public LocalDateTime getTimeStartFlame() {
        return timeStartFlame;
    }

    /**
     * xoa brick tai vi tri [idX, idY]
     */
    public void removeBrick(int idX, int idY, NttGroup levelManage) {
        for (int i =0 ; i < levelManage.brickList.size(); i++) {
            int posX = levelManage.brickList.get(i).getX() / Sprite.SCALED_SIZE;
            int posY = levelManage.brickList.get(i).getY() / Sprite.SCALED_SIZE;
            if (posY == idY && posX == idX) {
                levelManage.brickList.remove(i);
                break;
            }
        }
        for (int j = 0; j < levelManage.enemyList.size(); j++) {
            if (levelManage.enemyList.get(j) instanceof Enemy3) {
                ((Enemy3) levelManage.enemyList.get(j)).pathFinder.node[idX][idY].setSolid(false);
            }
        }
        levelManage.map[idX][idY] = levelManage.origin[idX][idY];
        switch (levelManage.origin[idX][idY]) {
            case ' ':
                break;
            case '0':
                Items item = new Items(idX, idY, Sprite.powerup_bombpass.getFxImage());
                item.setType(Items.TYPE_BOMBPASS);
                levelManage.itemsList.add(item);
                break;
            case '1':
                item = new Items(idX, idY, Sprite.powerup_bombs.getFxImage());
                item.setType(Items.TYPE_BOMBS);
                levelManage.itemsList.add(item);
                break;
            case '2':
                item = new Items(idX, idY, Sprite.powerup_detonator.getFxImage());
                item.setType(Items.TYPE_DETONATOR);
                levelManage.itemsList.add(item);
                break;
            case '3':
                item = new Items(idX, idY, Sprite.powerup_flamepass.getFxImage());
                item.setType(Items.TYPE_FLAMEPASS);
                levelManage.itemsList.add(item);
                break;
            case '4':
                item = new Items(idX, idY, Sprite.powerup_flames.getFxImage());
                item.setType(Items.TYPE_FLAMES);
                levelManage.itemsList.add(item);
                break;
            case 'p':
                levelManage.gamePortal = new Portal(idX, idY, Sprite.portal.getFxImage());
        }
    }

    /**
     * dua vi tri cac brick bi xoa vao 1 List.
     */
    public void update(NttGroup levelManage) {
        int flameX = x/Sprite.SCALED_SIZE;
        int flameY = y/Sprite.SCALED_SIZE;
        for(int j=0;j<levelManage.brickList.size();j++) {
            int tmpX = levelManage.brickList.get(j).getX()/Sprite.SCALED_SIZE;
            int tmpY = levelManage.brickList.get(j).getY()/Sprite.SCALED_SIZE;
            if(tmpX==flameX-1&&tmpY==flameY&&exploLeft) {
                //removeBrick(tmpX, tmpY, j);
                if(!check[tmpX][tmpY]) {
                    crackList.add(levelManage.brickList.get(j));
                    Grass temp = new Grass(levelManage.brickList.get(j).getX()/Sprite.SCALED_SIZE
                            , levelManage.brickList.get(j).getY()/Sprite.SCALED_SIZE
                            , Sprite.grass.getFxImage());
                    levelManage.grassList.add(temp);
                    int tmp = crackList.size() - 1;
                    crackList.get(tmp).originalPlace = j;
                    check[tmpX][tmpY]=true;
                }
                //j--;
            }
            if(tmpX==flameX+1&&tmpY==flameY&&exploRight) {
                //removeBrick(tmpX, tmpY, j);
                if(!check[tmpX][tmpY]) {
                    crackList.add(levelManage.brickList.get(j));
                    Grass temp = new Grass(levelManage.brickList.get(j).getX()/Sprite.SCALED_SIZE
                            , levelManage.brickList.get(j).getY()/Sprite.SCALED_SIZE
                            , Sprite.grass.getFxImage());
                    levelManage.grassList.add(temp);
                    int tmp = crackList.size() - 1;
                    crackList.get(tmp).originalPlace = j;
                    check[tmpX][tmpY]=true;
                }
                //j--;
            }
            if(tmpX==flameX&&tmpY==flameY-1&&exploUp) {
                //removeBrick(tmpX, tmpY, j);
                if(!check[tmpX][tmpY]) {
                    crackList.add(levelManage.brickList.get(j));
                    Grass temp = new Grass(levelManage.brickList.get(j).getX()/Sprite.SCALED_SIZE
                            , levelManage.brickList.get(j).getY()/Sprite.SCALED_SIZE
                            , Sprite.grass.getFxImage());
                    levelManage.grassList.add(temp);
                    int tmp = crackList.size() - 1;
                    crackList.get(tmp).originalPlace = j;
                    check[tmpX][tmpY]=true;
                }
                //j--;
            }
            if(tmpX==flameX&&tmpY==flameY+1&&exploDown) {
                //removeBrick(tmpX, tmpY, j);
                if(!check[tmpX][tmpY]) {
                    crackList.add(levelManage.brickList.get(j));
                    Grass temp = new Grass(levelManage.brickList.get(j).getX()/Sprite.SCALED_SIZE
                            , levelManage.brickList.get(j).getY()/Sprite.SCALED_SIZE
                            , Sprite.grass.getFxImage());
                    levelManage.grassList.add(temp);
                    int tmp = crackList.size() - 1;
                    crackList.get(tmp).originalPlace = j;
                    check[tmpX][tmpY]=true;
                }
                //j--;
            }
        }
    }

    /**
     * thay doi hinh anh cua cac brick bi xoa va tao anh cho Flame
     */
    public boolean checkEndFlame(NttGroup levelManage) {
        if (levelManage.bombers == null) {
            return false;
        }
        LocalDateTime timeEndFlame = LocalDateTime.now();
        int tmp = (int) Duration.between(timeStartFlame, timeEndFlame).toMillis();
        imgCounter = tmp - levelManage.diffTime;
        int j = 0;
        for (int i=0;i<levelManage.brickList.size();i++) {
            if (j < crackList.size()) {
                int crackX = crackList.get(j).getX() / Sprite.SCALED_SIZE;
                int crackY = crackList.get(j).getY() / Sprite.SCALED_SIZE;
                int posX = levelManage.brickList.get(i).getX() / Sprite.SCALED_SIZE;
                int posY = levelManage.brickList.get(i).getY() / Sprite.SCALED_SIZE;
                if (crackX == posX && crackY == posY) {
                    if (imgCounter >= 100) levelManage.brickList.get(i).setImg(Sprite.brick_exploded.getFxImage());
                    if (imgCounter >= 200) levelManage.brickList.get(i).setImg(Sprite.brick_exploded1.getFxImage());
                    if (imgCounter >= 300) levelManage.brickList.get(i).setImg(Sprite.brick_exploded2.getFxImage());
                    j++;
                }
            }
        }
        if (imgCounter >= 400) {
            for(int i=0;i<crackList.size();i++) {
                int idX = crackList.get(i).getX() / Sprite.SCALED_SIZE;
                int idY = crackList.get(i).getY() / Sprite.SCALED_SIZE;
                removeBrick(idX, idY, levelManage);
            }
            return true;
        }
        return false;
    }
}