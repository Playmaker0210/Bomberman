package uet.oop.bomberman.menu;

import javafx.application.Application;
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

import static uet.oop.bomberman.BombermanGame.createMap;

public class MainMenu {
    private static Text startGame;
    public static Text level, bomb, time;
    public static boolean running;
    private static boolean isStart;
    public static ImageView authorView;
    public static ImageView startButton;
    public static Group menuRoot;

    public static void createMenu(Group root) {
        running = true;
        isStart = false;
        menuRoot = root;

        Image author = new Image("menu/GameMenu.png");
        authorView = new ImageView(author);
        authorView.setX(0);
        authorView.setY(0);
        authorView.setScaleX(1);
        authorView.setScaleY(1);
        root.getChildren().add(authorView);

        level = new Text("Level: 1");
        level.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        level.setFill(Color.WHITE);
        level.setX(416);
        level.setY(20);
        time = new Text("Times: 360");
        time.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        time.setFill(Color.WHITE);
        time.setX(608);
        time.setY(20);

        startGame = new Text("START");
        startGame.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        startGame.setFill(Color.WHITE);
        startGame.setX(300);
        startGame.setY(320);
        root.getChildren().add(startGame);

    }

    public static void MenuControl(Scene scene) {
        scene.setOnMouseMoved(event -> {
            //System.out.println(event.getX() + " " + event.getY());
            if (event.getX() - startGame.getX() <= 140 && event.getX() >= startGame.getX()
                    && startGame.getY() - event.getY() <= 28 && event.getY() <= startGame.getY()) {
                startGame.setFill(Color.YELLOW);
            }
            else {
                startGame.setFill(Color.WHITE);
            }
        });

        scene.setOnMouseClicked(event -> {
            //System.out.println(event.getX() + " " + event.getY());
            if (event.getX() - startGame.getX() <= 140 && event.getX() >= startGame.getX()
                    && startGame.getY() - event.getY() <= 28 && event.getY() <= startGame.getY()) {
                isStart = true;
            }
        });
    }

    public static void updateMenu(Scene scene) {
        if(isStart) {
            menuRoot.getChildren().remove(authorView);
            menuRoot.getChildren().remove(startButton);
            createMap(scene, "Level1.txt");
            running = false;
        }
    }
}
