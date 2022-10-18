package uet.oop.bomberman.menu;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Tutorial {
    public Group tutorialRoot;
    public Text instruct;
    public ImageView tutorialView;

    public Tutorial(Group root) {
        tutorialRoot = root;

        Image author = new Image("menu/prepare.png");
        tutorialView = new ImageView(author);
        tutorialView.setX(0);
        tutorialView.setY(0);
        tutorialView.setScaleX(1);
        tutorialView.setScaleY(1);
        root.getChildren().add(tutorialView);

        String res = "Dùng các phím mũi tên để di chuyển, phím SPACE để đặt bom.\n\n" +
                     "Dùng phím D để đặt bom hẹn giờ, phím F để cho nổ bom hẹn giờ đã đặt sớm nhất.\n\n" +
                     "Dùng phím ESC để tạm dừng game.";
        instruct = new Text(res);
        instruct.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        instruct.setFill(Color.ORANGERED);
        instruct.setX(100);
        instruct.setY(100);
        root.getChildren().add(instruct);
    }
}
