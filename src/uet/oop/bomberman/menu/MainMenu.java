package uet.oop.bomberman.menu;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.NttGroup;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Stack;

import static uet.oop.bomberman.BombermanGame.*;

public class MainMenu {
    public static Text startGame;
    public static Text tutorial;
    public static Text sounds;
    public static Text highScore;
    public static Text exitGame;
    public static Text backButton;
    public static boolean running;
    public static boolean isStart;
    public static boolean showNext;
    public static boolean isSoundOn;
    public static boolean showTutorial;
    public static boolean showHigh;
    public static boolean showEnd;
    public static boolean playerLose;
    public static ImageView authorView;
    public static Prepare prepare;
    public static Group menuRoot;
    public static final int SHOW_SATGE = 0;
    public static final int SHOW_TUTORIAL = 1;
    public static final int SHOW_HIGH = 2;
    public static final int SHOW_END = 3;
    public static final int PAUSE = 4;
    public static int showType;
    public static Stack<Integer> showList = new Stack<>();

    public static void createMenu(Group root) {
        running = true;
        isStart = false;
        menuRoot = root;
        isSoundOn = true;
        prepare = null;
        showTutorial = false;
        showNext = false;
        showType = -1;
        showEnd = false;
        showHigh = false;

        Image author = new Image("menu/GameMenu.png");
        authorView = new ImageView(author);
        authorView.setX(0);
        authorView.setY(0);
        authorView.setScaleX(1);
        authorView.setScaleY(1);
        root.getChildren().add(authorView);

        startGame = new Text("START");
        startGame.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        startGame.setFill(Color.WHITE);
        startGame.setX(180);
        startGame.setY(320);
        root.getChildren().add(startGame);

        highScore = new Text("HIGH SCORE");
        highScore.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        highScore.setFill(Color.WHITE);
        highScore.setX(407);
        highScore.setY(320);
        root.getChildren().add(highScore);

        sounds = new Text("SOUNDS: ON");
        sounds.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        sounds.setFill(Color.WHITE);
        sounds.setX(570);
        sounds.setY(370);
        root.getChildren().add(sounds);

        tutorial = new Text("TUTORIAL");
        tutorial.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        tutorial.setFill(Color.WHITE);
        tutorial.setX(260);
        tutorial.setY(370);
        root.getChildren().add(tutorial);

        exitGame = new Text("EXIT");
        exitGame.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        exitGame.setFill(Color.WHITE);
        exitGame.setX(755);
        exitGame.setY(320);
        root.getChildren().add(exitGame);

        backButton = new Text("BACK");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        backButton.setFill(Color.WHITE);
        backButton.setX(450);
        backButton.setY(350);
    }

    public static void MenuControl(Scene scene, NttGroup levelManage) {
        scene.setOnMouseMoved(event -> {
            //System.out.println(event.getX() + " " + event.getY());
            if (!showTutorial && !showEnd && !showHigh) {
                if (event.getX() - startGame.getX() <= 95 && event.getX() >= startGame.getX()
                        && startGame.getY() - event.getY() <= 28 && event.getY() <= startGame.getY()) {
                    startGame.setFill(Color.YELLOW);
                } else {
                    startGame.setFill(Color.WHITE);
                }

                if (event.getX() - tutorial.getX() <= 142 && event.getX() >= tutorial.getX()
                        && tutorial.getY() - event.getY() <= 28 && event.getY() <= tutorial.getY()) {
                    tutorial.setFill(Color.YELLOW);
                } else {
                    tutorial.setFill(Color.WHITE);
                }

                if (event.getX() - sounds.getX() <= 178 && event.getX() >= sounds.getX()
                        && sounds.getY() - event.getY() <= 28 && event.getY() <= sounds.getY()) {
                    sounds.setFill(Color.YELLOW);
                } else {
                    sounds.setFill(Color.WHITE);
                }

                if (event.getX() - highScore.getX() <= 178 && event.getX() >= highScore.getX()
                        && highScore.getY() - event.getY() <= 28 && event.getY() <= highScore.getY()) {
                    highScore.setFill(Color.YELLOW);
                } else {
                    highScore.setFill(Color.WHITE);
                }

                if (event.getX() - exitGame.getX() <= 65 && event.getX() >= exitGame.getX()
                        && exitGame.getY() - event.getY() <= 28 && event.getY() <= exitGame.getY()) {
                    exitGame.setFill(Color.YELLOW);
                } else {
                    exitGame.setFill(Color.WHITE);
                }
            }

            else {
                if (event.getX() - backButton.getX() <= 80 && event.getX() >= backButton.getX()
                        && backButton.getY() - event.getY() <= 28 && event.getY() <= backButton.getY()) {
                    backButton.setFill(Color.YELLOW);
                }
                else {
                    backButton.setFill(Color.WHITE);
                }
            }
        });

        scene.setOnMouseClicked(event -> {
            //System.out.println(event.getX() + " " + event.getY());
            if (!showTutorial && !showEnd && !showHigh) {
                if (event.getX() - startGame.getX() <= 95 && event.getX() >= startGame.getX()
                        && startGame.getY() - event.getY() <= 28 && event.getY() <= startGame.getY()) {
                    isStart = true;
                    if (levelManage != null) {
                        if (levelManage.bombers == null) {
                            showNext = true;
                            showType = SHOW_SATGE;
                            showList.push(SHOW_SATGE);
                        } else {
                            levelManage.diffTime = (int) Duration.between(levelManage.timePause, LocalDateTime.now()).toMillis();
                        }
                    }
                }

                if (event.getX() - tutorial.getX() <= 142 && event.getX() >= tutorial.getX()
                        && tutorial.getY() - event.getY() <= 28 && event.getY() <= tutorial.getY()) {
                    showTutorial = true;
                    showType = SHOW_TUTORIAL;
                    showList.push(SHOW_TUTORIAL);
                }

                if (event.getX() - sounds.getX() <= 178 && event.getX() >= sounds.getX()
                        && sounds.getY() - event.getY() <= 28 && event.getY() <= sounds.getY()) {
                    if (isSoundOn) {
                        sounds.setText("SOUNDS: OFF");
                        isSoundOn = false;
                        gameSound = false;
                    } else {
                        sounds.setText("SOUNDS: ON");
                        isSoundOn = true;
                        gameSound = true;
                    }
                }

                if (event.getX() - exitGame.getX() <= 65 && event.getX() >= exitGame.getX()
                        && exitGame.getY() - event.getY() <= 28 && event.getY() <= exitGame.getY()) {
                    Platform.exit();
                }
            }

            else {
                if (event.getX() - backButton.getX() <= 80 && event.getX() >= backButton.getX()
                        && backButton.getY() - event.getY() <= 28 && event.getY() <= backButton.getY()) {
                    if(showType == SHOW_TUTORIAL) {
                        showTutorial = false;
                    }
                    if(showType == SHOW_END) {
                        showEnd = false;
                    }
                    if(showType == SHOW_HIGH) {
                        showHigh =false;
                    }
                }
            }

            if (event.getX() - highScore.getX() <= 178 && event.getX() >= highScore.getX()
                    && highScore.getY() - event.getY() <= 28 && event.getY() <= highScore.getY()) {
                showType = SHOW_HIGH;
                showHigh = true;
                showList.push(SHOW_HIGH);
            }
        });
    }

    public static void updateMenu(NttGroup levelManage) {
        if (levelManage != null) {
            if (levelManage.bombers != null) {
                startGame.setText("RESUME");
            } else {
                startGame.setText("START");
            }
        }
        if(isStart) {
            menuRoot.getChildren().remove(authorView);
            menuRoot.getChildren().remove(startGame);
            menuRoot.getChildren().remove(highScore);
            menuRoot.getChildren().remove(exitGame);
            menuRoot.getChildren().remove(sounds);
            menuRoot.getChildren().remove(tutorial);
            if (showList.size() > 0) {
                if (showList.peek() == PAUSE) {
                    running = false;
                    showList.pop();
                    showType = -1;
                }
            }
        }
        if (prepare == null && showNext && showType == SHOW_SATGE) {
            if(BombermanGame.gameSound) {
                Sound.playSound("soundStart");
            }
            prepare = new Prepare(menuRoot);
            menuRoot.getChildren().add(prepare.stage);
            prepare.startPre = LocalDateTime.now();
            if(!levelManage.makeMap) {
                levelManage.makeMap = true;
            }
        }
        else if (prepare != null && showNext && showType == SHOW_SATGE) {
            if (prepare.checkEnd()) {
                menuRoot.getChildren().remove(prepare.prepareView);
                menuRoot.getChildren().remove(prepare.stage);
                running = false;
                prepare = null;
                showNext = false;
                showType = -1;
            }
        }
        if (showTutorial && showType == SHOW_TUTORIAL) {
            if(prepare == null) {
                prepare = new Prepare(menuRoot);
                menuRoot.getChildren().add(prepare.instruct);
                menuRoot.getChildren().add(backButton);
            }
        }
        else if (!showTutorial && showType == SHOW_TUTORIAL) {
            if (prepare != null) {
                menuRoot.getChildren().remove(prepare.prepareView);
                menuRoot.getChildren().remove(backButton);
                menuRoot.getChildren().remove(prepare.instruct);
                prepare = null;
                showType = -1;
                if(showList.size() > 0) {
                    showList.pop();
                }
            }
        }
        if (showType == SHOW_END) {
            if (prepare == null) {
                prepare = new Prepare(menuRoot);
                if (playerLose) {
                    prepare.endGame.setText("YOU LOSE\n\n\n\nYour score\n");
                }
                else {
                    prepare.endGame.setX(340);
                    prepare.endGame.setText("CONGRATULATIONS\n\n\n\n\t Your score\n");
                }
                menuRoot.getChildren().add(prepare.endGame);
                menuRoot.getChildren().add(prepare.point);
                menuRoot.getChildren().add(backButton);
            }
            else {
                if (!showEnd) {
                    menuRoot.getChildren().remove(prepare.prepareView);
                    menuRoot.getChildren().remove(backButton);
                    menuRoot.getChildren().remove(prepare.endGame);
                    menuRoot.getChildren().remove(prepare.point);
                    prepare = null;
                    showType = -1;
                    menuRoot.getChildren().add(authorView);
                    menuRoot.getChildren().add(startGame);
                    menuRoot.getChildren().add(highScore);
                    menuRoot.getChildren().add(exitGame);
                    menuRoot.getChildren().add(sounds);
                    menuRoot.getChildren().add(tutorial);
                    playerLife = 3;
                    playerLose = false;
                    level = 1;
                    playerScore = 0;
                }
            }
        }
        if (showType == SHOW_HIGH) {
            if (prepare == null) {
                prepare = new Prepare(menuRoot);
                prepare.createScore();
                menuRoot.getChildren().add(backButton);
            }
            else {
                if (!showHigh) {
                    menuRoot.getChildren().remove(prepare.prepareView);
                    menuRoot.getChildren().remove(backButton);
                    for (int i =0; i<5;i++) {
                        menuRoot.getChildren().remove(prepare.scoreList[i]);
                    }
                    prepare = null;
                    showType = -1;
                    if(showList.size() > 0) {
                        showList.pop();
                    }
                }
            }
        }
    }
}
