package uet.oop.bomberman.Player;

import javafx.scene.Scene;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.menu.MainMenu;
import uet.oop.bomberman.menu.Sound;

import java.time.LocalDateTime;

import static uet.oop.bomberman.menu.MainMenu.*;

public class PlayerController {
    public static void bomberController(Scene scene, Bomber bomberman, NttGroup levelManage) {
        scene.setOnKeyPressed(event -> {
                    if (!running) {
                        if (event.getCode().toString().equals("RIGHT") && bomberman.isAlive) {
                            bomberman.goRight(levelManage);
                        } else if (event.getCode().toString().equals("LEFT") && bomberman.isAlive) {
                            bomberman.goLeft(levelManage);
                        } else if (event.getCode().toString().equals("UP") && bomberman.isAlive) {
                            bomberman.goUp(levelManage);
                        } else if (event.getCode().toString().equals("DOWN") && bomberman.isAlive) {
                            bomberman.goDown(levelManage);
                        } else if (event.getCode().toString().equals("SPACE") && bomberman.isAlive) {
                            if (bomberman.bombs.size() < bomberman.getNumBombs()) {
                                if (BombermanGame.gameSound) {
                                    Sound.playSound("soundSet");
                                }
                                bomberman.createBomb(levelManage);
                            }
                        } else if (event.getCode().toString().equals("D") && bomberman.isAlive) {
                            if (bomberman.getNumDetonator() > 0) {
                                if (BombermanGame.gameSound) {
                                    Sound.playSound("soundSet");
                                }
                                bomberman.createDetonator(levelManage);
                            }
                        } else if (event.getCode().toString().equals("F") && bomberman.isAlive) {
                            if (bomberman.detonator.size() > 0) {
                                bomberman.detonate(levelManage);
                            }
                        } else if (event.getCode().toString().equals("P")) {
                            levelManage.timePause = LocalDateTime.now();
                            MainMenu.running = true;
                            MainMenu.isStart = false;
                            showType = PAUSE;
                            showList.push(PAUSE);
                            menuRoot.getChildren().add(authorView);
                            menuRoot.getChildren().add(startGame);
                            menuRoot.getChildren().add(highScore);
                            menuRoot.getChildren().add(exitGame);
                            menuRoot.getChildren().add(sounds);
                            menuRoot.getChildren().add(tutorial);
                        } else if (event.getCode().toString().equals("L")) {
                            while (levelManage.enemyList.size() > 0) {
                                levelManage.enemyList.remove(0);
                            }
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
