package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.entities.field.Grass;
import uet.oop.bomberman.graphics.Sprite;

import java.time.Duration;
import java.time.LocalDateTime;

public class Flame extends Entity {

    private LocalDateTime timeStartFlame;
    public boolean exploLeft;
    public boolean exploRight;
    public boolean exploUp;
    public boolean exploDown;
    public Flame(int xUnit, int yUnit, Image img, LocalDateTime timeStartFlame) {
        super(xUnit, yUnit, img);
        this.timeStartFlame = timeStartFlame;
        exploLeft = false;
        exploRight = false;
        exploUp = false;
        exploDown = false;
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
                removeBrick(tmpX, tmpY, j);
                j--;
            }
            else if(tmpX==flameX+1&&tmpY==flameY&&exploRight) {
                removeBrick(tmpX, tmpY, j);
                j--;
            }
            else if(tmpX==flameX&&tmpY==flameY-1&&exploUp) {
                removeBrick(tmpX, tmpY, j);
                j--;
            }
            else if(tmpX==flameX&&tmpY==flameY+1&&exploDown) {
                removeBrick(tmpX, tmpY, j);
                j--;
            }
        }
    }

    public boolean checkEndFlame() {
        LocalDateTime timeEndFlame = LocalDateTime.now();
        if (Duration.between(timeStartFlame, timeEndFlame).toMillis() >= 250) {
            return true;
        }
        return false;
    }
}
