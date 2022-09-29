package uet.oop.bomberman.Player;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class PlayerController {
    public static void bomberController(Scene scene, Bomber bomberman) {
        scene.setOnKeyPressed(event -> {
                    if (event.getCode().toString().equals("RIGHT")) {
                        bomberman.goRight();
                    } else if (event.getCode().toString().equals("LEFT")) {
                        bomberman.goLeft();
                    } else if (event.getCode().toString().equals("UP")) {
                        bomberman.goUp();
                    } else if (event.getCode().toString().equals("DOWN")) {
                        bomberman.goDown();
                    } else if (event.getCode().toString().equals("SPACE")) {
                        if(bomberman.bombs.size()<bomberman.getNumBombs()) {
                            bomberman.createBomb();
                        }
                    }
                }
        );
        scene.setOnKeyReleased(event -> {
            if(event.getCode().toString().equals("UP")) {
                bomberman.setImg(Sprite.player_up.getFxImage());
            } else {
                bomberman.setImg(Sprite.player_down.getFxImage());
            }
        });
    }
}
