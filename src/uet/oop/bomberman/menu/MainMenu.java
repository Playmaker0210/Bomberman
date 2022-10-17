package uet.oop.bomberman.menu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.entities.NttGroup;

import java.time.LocalDateTime;

import static uet.oop.bomberman.BombermanGame.createMap;

public class MainMenu {
    private static Text startGame;
    private static Text tutorial;
    private static Text sounds;
    private static Text highScore;
    private static Text exitGame;
    public static boolean running;
    private static boolean isStart;
    private static boolean isSoundOn;
    public static ImageView authorView;
    private static Prepare prepare;
    public static Group menuRoot;

    public static void createMenu(Group root) {
        running = true;
        isStart = false;
        menuRoot = root;
        isSoundOn = true;
        prepare = null;

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
    }

    public static void MenuControl(Scene scene) {
        scene.setOnMouseMoved(event -> {
            //System.out.println(event.getX() + " " + event.getY());
            if (event.getX() - startGame.getX() <= 95 && event.getX() >= startGame.getX()
                    && startGame.getY() - event.getY() <= 28 && event.getY() <= startGame.getY()) {
                startGame.setFill(Color.YELLOW);
            }
            else {
                startGame.setFill(Color.WHITE);
            }
            if (event.getX() - tutorial.getX() <= 142 && event.getX() >= tutorial.getX()
                    && tutorial.getY() - event.getY() <= 28 && event.getY() <= tutorial.getY()) {
                tutorial.setFill(Color.YELLOW);
            }
            else {
                tutorial.setFill(Color.WHITE);
            }
            if (event.getX() - sounds.getX() <= 178 && event.getX() >= sounds.getX()
                    && sounds.getY() - event.getY() <= 28 && event.getY() <= sounds.getY()) {
                sounds.setFill(Color.YELLOW);
            }
            else {
                sounds.setFill(Color.WHITE);
            }
            if (event.getX() - highScore.getX() <= 178 && event.getX() >= highScore.getX()
                    && highScore.getY() - event.getY() <= 28 && event.getY() <= highScore.getY()) {
                highScore.setFill(Color.YELLOW);
            }
            else {
                highScore.setFill(Color.WHITE);
            }
            if (event.getX() - exitGame.getX() <= 65 && event.getX() >= exitGame.getX()
                    && exitGame.getY() - event.getY() <= 28 && event.getY() <= exitGame.getY()) {
                exitGame.setFill(Color.YELLOW);
            }
            else {
                exitGame.setFill(Color.WHITE);
            }
        });

        scene.setOnMouseClicked(event -> {
            //System.out.println(event.getX() + " " + event.getY());
            if (event.getX() - startGame.getX() <= 95 && event.getX() >= startGame.getX()
                    && startGame.getY() - event.getY() <= 28 && event.getY() <= startGame.getY()) {
                isStart = true;
            }
            if (event.getX() - sounds.getX() <= 178 && event.getX() >= sounds.getX()
                    && sounds.getY() - event.getY() <= 28 && event.getY() <= sounds.getY()) {
                if(isSoundOn) {
                    sounds.setText("SOUNDS: OFF");
                    isSoundOn = false;
                }
                else {
                    sounds.setText("SOUNDS: ON");
                    isSoundOn = true;
                }
            }
            if (event.getX() - exitGame.getX() <= 65 && event.getX() >= exitGame.getX()
                    && exitGame.getY() - event.getY() <= 28 && event.getY() <= exitGame.getY()) {
                Platform.exit();
            }
        });
    }

    public static void updateMenu(Scene scene) {
        if(isStart) {
            menuRoot.getChildren().remove(authorView);
            if (prepare == null) {
                prepare = new Prepare(menuRoot);
                prepare.startPre = LocalDateTime.now();
            }
            if (prepare != null) {
                if (prepare.checkEnd()) {
                    menuRoot.getChildren().remove(prepare.prepareView);
                    menuRoot.getChildren().remove(prepare.stage);
                    createMap(scene, "Level1.txt");
                    prepare = null;
                    running = false;
                }
            }
        }
    }
}
