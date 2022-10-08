package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.entities.field.Brick;
import uet.oop.bomberman.entities.field.Grass;
import uet.oop.bomberman.graphics.Sprite;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Flame extends Entity {

    private LocalDateTime timeStartFlame;
    public boolean exploLeft;
    public boolean exploRight;
    public boolean exploUp;
    public boolean exploDown;
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

    public void removeBrick(int idX, int idY, int vt) {
        NttGroup.brickList.remove(vt);
        NttGroup.map[idX][idY] = ' ';
        Grass temp = new Grass(idX, idY, Sprite.grass.getFxImage());
        NttGroup.grassList.add(temp);
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
                    int tmp = crackList.size() - 1;
                    crackList.get(tmp).originalPlace = j;
                    check[tmpX][tmpY]=true;
                }
                //j--;
            }
        }
    }

    public boolean checkEndFlame() {
        LocalDateTime timeEndFlame = LocalDateTime.now();
        int tmp = (int) Duration.between(timeStartFlame, timeEndFlame).toMillis();
        imgCounter++;
        for (int i=0;i<crackList.size();i++) {
            int vt = crackList.get(i).originalPlace;
            if(imgCounter==9) NttGroup.brickList.get(vt).setImg(Sprite.brick_exploded.getFxImage());
            if(imgCounter==18) NttGroup.brickList.get(vt).setImg(Sprite.brick_exploded1.getFxImage());
            if(imgCounter==27) NttGroup.brickList.get(vt).setImg(Sprite.brick_exploded2.getFxImage());
        }
        if (tmp >= 240) {
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