package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.Enemy1;
import uet.oop.bomberman.entities.enemy.Enemy2;
import uet.oop.bomberman.entities.field.Brick;
import uet.oop.bomberman.entities.field.Grass;
import uet.oop.bomberman.entities.field.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.Player.PlayerController;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class BombermanGame extends Application  {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    private int vt ;

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
        createMap(scene);
    }

    public void createMap(Scene scene) {
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
                    NttGroup.map[j][i] = '#';
                }
                else {
                    char k = X.charAt(j);
                    if(k=='*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        NttGroup.brickList.add((Brick) object);
                        NttGroup.map[j][i] = '*';
                        //System.out.println(j+ " "+i);
                    }
                    else if(k=='#'){
                        object = new Wall(j,i,Sprite.wall.getFxImage());
                        NttGroup.wallList.add((Wall) object);
                        NttGroup.map[j][i] = '#';
                    }
                    else{
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        NttGroup.grassList.add((Grass) object);
                        NttGroup.map[j][i] = ' ';
                    }
                }
                stillObjects.add(object);
            }
            //System.out.println(i+ " " + X);
        }

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        vt= entities.size()-1;
        NttGroup.bombers.add((Bomber) bomberman);
        Entity en = new Enemy2(25, 10, Sprite.oneal_right1.getFxImage());
        NttGroup.enemyList.add((Enemy) en);
        PlayerController.bomberController(scene, (Bomber) bomberman);
    }

    public void update() {
        Bomber tmp = (Bomber) entities.get(vt);
        if(tmp.bombs.size()>0) {
            tmp.checkBomb();
        }
        for(int i=0;i<NttGroup.enemyList.size();i++) {
            if(NttGroup.enemyList.get(i).checkBoundFlame()
                    && NttGroup.enemyList.get(i).isAlive()) {
                NttGroup.enemyList.get(i).setSpeed(0);
                NttGroup.enemyList.get(i).setAlive(false);
                NttGroup.enemyList.get(i).setCollisionStart(LocalDateTime.now());
                NttGroup.enemyList.get(i).setSpecificDead();
            }
            if(!NttGroup.enemyList.get(i).isAlive()
                    && NttGroup.enemyList.get(i).checkDisappear()) {
                NttGroup.enemyList.remove(i);
                i--;
            }
        }
        if(NttGroup.flames.size()>0) {
            if(tmp.checkBoundFlame()) {
                tmp.setX(Sprite.SCALED_SIZE);
                tmp.setY(Sprite.SCALED_SIZE);
            }
            NttGroup.flames.forEach(Flame::update);
            for (int i=0;i<NttGroup.flames.size();i++) {
                if(NttGroup.flames.get(i).checkEndFlame()) {
                    NttGroup.flames.remove(i);
                    i--;
                }
            }
        }
        NttGroup.enemyList.forEach(Enemy::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        NttGroup.grassList.forEach(g -> g.render(gc));
        NttGroup.enemyList.forEach(g -> g.render(gc));
        NttGroup.bombList.forEach(g -> g.render(gc));
        NttGroup.flames.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}