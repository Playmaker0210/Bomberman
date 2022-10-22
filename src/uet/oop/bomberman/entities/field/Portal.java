package uet.oop.bomberman.entities.field;

import javafx.scene.image.Image;
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

    public void changeLevel(NttGroup levelManage) {
        int idX = this.x/ Sprite.SCALED_SIZE;
        int idY = this.y/Sprite.SCALED_SIZE;
        int playerX = levelManage.bombers.getX()/Sprite.SCALED_SIZE;
        int playerY = levelManage.bombers.getY()/Sprite.SCALED_SIZE;
        if (idX == playerX && idY == playerY && levelManage.enemyList.size() == 0) {
            playerScore += (level*200);
            if (level < 3) {
                level++;
                MainMenu.running = true;
                MainMenu.showNext = true;
                MainMenu.showType = MainMenu.SHOW_SATGE;
            }
            else {
                playerScore += playerLife * 1500;
                makeEndGame();
                MainMenu.running=true;
                MainMenu.isStart = false;
                MainMenu.showType = MainMenu.SHOW_END;
                MainMenu.showEnd = true;
                if (gameSound) {
                    Sound.playSound("soundWin");
                }
            }
        }
    }

    public void makeEndGame() {
        MainMenu.running=true;
        MainMenu.isStart = false;
        MainMenu.showType = MainMenu.SHOW_END;
        MainMenu.showEnd = true;
        updateHighScore();
    }
}
