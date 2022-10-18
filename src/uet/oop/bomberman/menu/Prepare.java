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
import static uet.oop.bomberman.BombermanGame.playerScore;

public class Prepare {
    public Group prepareRoot;
    public Text stage;
    public ImageView prepareView;
    public LocalDateTime startPre;
    public Text instruct;
    public Text endGame;
    public Text point;

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

        String res = "Dùng các phím mũi tên để di chuyển, phím SPACE để đặt bom.\n\n" +
                "Dùng phím D để đặt bom hẹn giờ, phím F để cho nổ bom hẹn giờ đã đặt sớm nhất.\n\n" +
                "Dùng phím ESC để tạm dừng game.";
        instruct = new Text(res);
        instruct.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        instruct.setFill(Color.ORANGERED);
        instruct.setX(100);
        instruct.setY(100);

        String res1 = "CONGRATULATIONS\n\n\n\nYour score\n";
        endGame = new Text(res1);
        endGame.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        endGame.setFill(Color.CRIMSON);
        endGame.setX(410);
        endGame.setY(50);

        point = new Text(Integer.toString(playerScore));
        point.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        point.setFill(Color.AQUA);
        if (playerScore < 1000 && playerScore > 0) {
            point.setX(460);
        }
        else if (playerScore >= 1000) {
            point.setX(450);
        }
        else {
            point.setX(480);
        }
        point.setY(230);
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
