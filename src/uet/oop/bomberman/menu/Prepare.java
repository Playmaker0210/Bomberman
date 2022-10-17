package uet.oop.bomberman.menu;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.Duration;
import java.time.LocalDateTime;

import static uet.oop.bomberman.BombermanGame.level;

public class Prepare {
    public Group prepareRoot;
    public Text stage;
    public ImageView prepareView;
    public LocalDateTime startPre;

    public Prepare(Group root) {
        prepareRoot = root;

        Image author = new Image("menu/prepare.png");
        prepareView = new ImageView(author);
        prepareView.setX(0);
        prepareView.setY(0);
        prepareView.setScaleX(1);
        prepareView.setScaleY(1);
        root.getChildren().add(prepareView);

        stage = new Text("STAGE " + Integer.toString(level));
        stage.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        stage.setFill(Color.WHITE);
        stage.setX(400);
        stage.setY(200);
        root.getChildren().add(stage);
    }

    public boolean checkEnd() {
        LocalDateTime tmp = LocalDateTime.now();
        int time = (int) Duration.between(startPre, tmp).toMillis();
        if(time >= 1000) {
            return true;
        }
        return false;
    }
}
