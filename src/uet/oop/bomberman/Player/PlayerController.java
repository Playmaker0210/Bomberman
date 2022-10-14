package uet.oop.bomberman.Player;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class PlayerController {
    public static void bomberController(Scene scene, Bomber bomberman) {
        scene.setOnKeyPressed(event -> {
                    if (event.getCode().toString().equals("RIGHT") && bomberman.isAlive) {
                        bomberman.goRight();
                    } else if (event.getCode().toString().equals("LEFT")&& bomberman.isAlive) {
                        bomberman.goLeft();
                    } else if (event.getCode().toString().equals("UP")&& bomberman.isAlive) {
                        bomberman.goUp();
                    } else if (event.getCode().toString().equals("DOWN")&& bomberman.isAlive) {
                        bomberman.goDown();
                    } else if (event.getCode().toString().equals("SPACE")&& bomberman.isAlive) {
                        if(bomberman.bombs.size()<bomberman.getNumBombs()) {
                            bomberman.createBomb();
                        }
                    } else if (event.getCode().toString().equals("D")&& bomberman.isAlive) {
                        if (bomberman.getNumDetonator() > 0) {
                            bomberman.createDetonator();
                        }
                    } else if (event.getCode().toString().equals("F")&& bomberman.isAlive) {
                        if (bomberman.detonator.size()>0) {
                            bomberman.detonate();
                        }
                    }
                }
        );
        scene.setOnKeyReleased(event -> {
            if(event.getCode().toString().equals("UP")&& bomberman.isAlive) {
                bomberman.setImg(Sprite.player_up.getFxImage());
            } else if (event.getCode().toString().equals("DOWN")&& bomberman.isAlive) {
                bomberman.setImg(Sprite.player_down.getFxImage());
            }
        });
    }
}
