package uet.oop.bomberman.entities.field;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.menu.MainMenu;
import uet.oop.bomberman.menu.Sound;

import static uet.oop.bomberman.BombermanGame.*;

public class Portal extends Entity {
    public Portal (int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {

    }
    public void changeLevel() {
        int idX = this.x/ Sprite.SCALED_SIZE;
        int idY = this.y/Sprite.SCALED_SIZE;
        int playerX = NttGroup.bombers.getX()/Sprite.SCALED_SIZE;
        int playerY = NttGroup.bombers.getY()/Sprite.SCALED_SIZE;
        if (idX == playerX && idY == playerY && NttGroup.enemyList.size() == 0) {
            playerScore += (level*200);
            if (level < 3) {
                level++;
                NttGroup.reset();
                MainMenu.running = true;
                MainMenu.showNext = true;
                MainMenu.showType = MainMenu.SHOW_SATGE;
            }
            else {
                BombermanGame.makeEndGame();
                MainMenu.running=true;
                MainMenu.isStart = false;
                NttGroup.reset();
                MainMenu.showType = MainMenu.SHOW_END;
                MainMenu.showEnd = true;
                if (gameSound) {
                    Sound.playSound("soundWin");
                }
            }
        }
    }
}
