package uet.oop.bomberman.entities.field;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;

public class Items extends Entity {
    private int type;
    public static final int TYPE_BOMBPASS = 0;
    public static final int TYPE_BOMBS = 1;
    public static final int TYPE_DETONATOR = 2;
    public static final int TYPE_FLAMEPASS = 3;
    public static final int TYPE_FLAMES = 4;

    public Items (int x, int y, Image img) {
        super(x, y, img);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void update() {

    }

    public void checkPlayerGet(int index) {
        int idX = this.x/ Sprite.SCALED_SIZE;
        int idY = this.y/Sprite.SCALED_SIZE;
        int playerX = NttGroup.bombers.getX()/Sprite.SCALED_SIZE;
        int playerY = NttGroup.bombers.getY()/Sprite.SCALED_SIZE;
        if (idX == playerX && idY == playerY) {
            NttGroup.bombers.getItem(type);
            NttGroup.itemsList.remove(index);
            NttGroup.map[idX][idY] = ' ';
            Grass tmp = new Grass(idX, idY, Sprite.grass.getFxImage());
            NttGroup.grassList.add(tmp);
        }
    }

}
