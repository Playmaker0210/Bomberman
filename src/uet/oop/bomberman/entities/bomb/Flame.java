package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
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

    public void removeBrick(int idX, int idY, int vt) {
        NttGroup.brickList.remove(vt);
        BombermanGame.pathFinder.node[idX][idY].setSolid(false);
        NttGroup.map[idX][idY] = NttGroup.origin[idX][idY];
        switch (NttGroup.origin[idX][idY]) {
            case ' ':
                break;
            case '0':
                Items item = new Items(idX, idY, Sprite.powerup_bombpass.getFxImage());
                item.setType(Items.TYPE_BOMBPASS);
                NttGroup.itemsList.add(item);
                break;
            case '1':
                item = new Items(idX, idY, Sprite.powerup_bombs.getFxImage());
                item.setType(Items.TYPE_BOMBS);
                NttGroup.itemsList.add(item);
                break;
            case '2':
                item = new Items(idX, idY, Sprite.powerup_detonator.getFxImage());
                item.setType(Items.TYPE_DETONATOR);
                NttGroup.itemsList.add(item);
                break;
            case '3':
                item = new Items(idX, idY, Sprite.powerup_flamepass.getFxImage());
                item.setType(Items.TYPE_FLAMEPASS);
                NttGroup.itemsList.add(item);
                break;
            case '4':
                item = new Items(idX, idY, Sprite.powerup_flames.getFxImage());
                item.setType(Items.TYPE_FLAMES);
                NttGroup.itemsList.add(item);
                break;
            case 'p':
                NttGroup.gamePortal = new Portal(idX, idY, Sprite.portal.getFxImage());
        }
    }

    public void update() {
        int flameX = x/Sprite.SCALED_SIZE;
        int flameY = y/Sprite.SCALED_SIZE;
        for(int j=0;j<NttGroup.brickList.size();j++) {
            int tmpX = NttGroup.brickList.get(j).getX()/Sprite.SCALED_SIZE;
            int tmpY = NttGroup.brickList.get(j).getY()/Sprite.SCALED_SIZE;
            if(tmpX==flameX-1&&tmpY==flameY&&exploLeft) {
                //removeBrick(tmpX, tmpY, j);
                if(!check[tmpX][tmpY]) {
                    crackList.add(NttGroup.brickList.get(j));
                    Grass temp = new Grass(NttGroup.brickList.get(j).getX()/Sprite.SCALED_SIZE
                            , NttGroup.brickList.get(j).getY()/Sprite.SCALED_SIZE
                            , Sprite.grass.getFxImage());
                    NttGroup.grassList.add(temp);
                    int tmp = crackList.size() - 1;
                    crackList.get(tmp).originalPlace = j;
                    check[tmpX][tmpY]=true;
                }
                //j--;
            }
            if(tmpX==flameX+1&&tmpY==flameY&&exploRight) {
                //removeBrick(tmpX, tmpY, j);
                if(!check[tmpX][tmpY]) {
                    crackList.add(NttGroup.brickList.get(j));
                    Grass temp = new Grass(NttGroup.brickList.get(j).getX()/Sprite.SCALED_SIZE
                            , NttGroup.brickList.get(j).getY()/Sprite.SCALED_SIZE
                            , Sprite.grass.getFxImage());
                    NttGroup.grassList.add(temp);
                    int tmp = crackList.size() - 1;
                    crackList.get(tmp).originalPlace = j;
                    check[tmpX][tmpY]=true;
                }
                //j--;
            }
            if(tmpX==flameX&&tmpY==flameY-1&&exploUp) {
                //removeBrick(tmpX, tmpY, j);
                if(!check[tmpX][tmpY]) {
                    crackList.add(NttGroup.brickList.get(j));
                    Grass temp = new Grass(NttGroup.brickList.get(j).getX()/Sprite.SCALED_SIZE
                            , NttGroup.brickList.get(j).getY()/Sprite.SCALED_SIZE
                            , Sprite.grass.getFxImage());
                    NttGroup.grassList.add(temp);
                    int tmp = crackList.size() - 1;
                    crackList.get(tmp).originalPlace = j;
                    check[tmpX][tmpY]=true;
                }
                //j--;
            }
            if(tmpX==flameX&&tmpY==flameY+1&&exploDown) {
                //removeBrick(tmpX, tmpY, j);
                if(!check[tmpX][tmpY]) {
                    crackList.add(NttGroup.brickList.get(j));
                    Grass temp = new Grass(NttGroup.brickList.get(j).getX()/Sprite.SCALED_SIZE
                            , NttGroup.brickList.get(j).getY()/Sprite.SCALED_SIZE
                            , Sprite.grass.getFxImage());
                    NttGroup.grassList.add(temp);
                    int tmp = crackList.size() - 1;
                    crackList.get(tmp).originalPlace = j;
                    check[tmpX][tmpY]=true;
                }
                //j--;
            }
        }
    }

    public boolean checkEndFlame() {
        if (NttGroup.bombers == null) {
            return false;
        }
        LocalDateTime timeEndFlame = LocalDateTime.now();
        int tmp = (int) Duration.between(timeStartFlame, timeEndFlame).toMillis();
        System.out.println(imgCounter);
        for (int i=0;i<crackList.size();i++) {
            int vt = crackList.get(i).originalPlace;
            if(imgCounter==13) NttGroup.brickList.get(vt).setImg(Sprite.brick_exploded.getFxImage());
            if(imgCounter==26) NttGroup.brickList.get(vt).setImg(Sprite.brick_exploded1.getFxImage());
            if(imgCounter==39) NttGroup.brickList.get(vt).setImg(Sprite.brick_exploded2.getFxImage());
        }
        if (tmp - NttGroup.diffTime >= 500) {
            for(int i=0;i<crackList.size();i++) {
                int vt = crackList.get(i).originalPlace-i;
                int idX = crackList.get(i).getX() / Sprite.SCALED_SIZE;
                int idY = crackList.get(i).getY() / Sprite.SCALED_SIZE;
                removeBrick(idX, idY, vt);
            }
            return true;
        }
        return false;
    }
}