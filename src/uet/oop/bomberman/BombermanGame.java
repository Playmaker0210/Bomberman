package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemy.Enemy1;
import uet.oop.bomberman.entities.field.Brick;
import uet.oop.bomberman.entities.field.Grass;
import uet.oop.bomberman.entities.field.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.Player.PlayerController;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class BombermanGame extends Application  {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        NttGroup.bombers.add((Bomber) bomberman);
        Entity en = new Enemy1(26, 11, Sprite.minvo_right1.getFxImage());
        entities.add(en);
        PlayerController.bomberController(scene, (Bomber) bomberman);
    }

    public void createMap() {
        File url = new File("res/levels/Level1.txt");
        // Đọc dữ liệu từ File với Scanner
        Scanner scanner = null;
        try {
            scanner = new Scanner(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int level = scanner.nextInt();
        int hang = scanner.nextInt();
        int cot = scanner.nextInt();

        for (int i = 0; i < hang; i++) {
            String X= scanner.next();
            X += scanner.nextLine();
            for (int j = 0; j < cot; j++) {
                Entity object;
                if (j == 0 || j == cot - 1 || i == 0 || i == hang - 1) {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    NttGroup.wallList.add((Wall) object);
                }
                else {
                    char k = X.charAt(j);
                    if(k=='*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        NttGroup.brickList.add((Brick) object);
                    }
                    else if(k=='#'){
                        object = new Wall(j,i,Sprite.wall.getFxImage());
                        NttGroup.wallList.add((Wall) object);
                    }
                    else{
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        NttGroup.grassList.add((Grass) object);
                    }
                }
                stillObjects.add(object);
            }
            //System.out.println(i+ " " + X);
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        NttGroup.bombList.forEach(g -> g.render(gc));
    }
}