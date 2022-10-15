package uet.oop.bomberman.entities.field;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.createMap;
import static uet.oop.bomberman.BombermanGame.level;

public class Portal extends Entity {
    public Portal (int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {

    }
    public void changeLevel(Scene scene) {
        int idX = this.x/ Sprite.SCALED_SIZE;
        int idY = this.y/Sprite.SCALED_SIZE;
        int playerX = NttGroup.bombers.getX()/Sprite.SCALED_SIZE;
        int playerY = NttGroup.bombers.getY()/Sprite.SCALED_SIZE;
        if (idX == playerX && idY == playerY && NttGroup.enemyList.size() == 0) {
            if (level < 3) {
                level++;
                String tmp = "Level" + Integer.toString(level) + ".txt";
                NttGroup.reset();
                createMap(scene, tmp);
            }
        }
    }
}
