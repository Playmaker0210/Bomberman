package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.*;
import uet.oop.bomberman.entities.field.Brick;
import uet.oop.bomberman.entities.field.Grass;
import uet.oop.bomberman.entities.field.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.Player.PlayerController;
import uet.oop.bomberman.menu.MainMenu;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.PriorityQueue;
import java.util.Scanner;

import static uet.oop.bomberman.menu.MainMenu.playerLose;
import static uet.oop.bomberman.menu.MainMenu.running;

public class BombermanGame extends Application  {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static int level = 1;
    public static int playerScore = 0;
    public static int playerLife = 3;
    public static PriorityQueue<Integer> highscore = new PriorityQueue<>();

    private GraphicsContext gc;
    public static Canvas canvas;

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

        MainMenu.createMenu(root);
        root.getChildren().add(canvas);
        readHighScore();

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman");
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running) {
                    MainMenu.MenuControl(scene);
                    MainMenu.updateMenu(scene);
                }
                else {
                    render();
                    update(scene);
                }
            }
        };
        timer.start();
    }

    public static void createMap(Scene scene, String last) {
        File url = new File("res/levels/" + last);
        // Đọc dữ liệu từ File với Scanner
        Scanner scanner = null;
        try {
            scanner = new Scanner(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        level = scanner.nextInt();
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
                    NttGroup.origin[j][i] = '#';
                }
                else {
                    char k = X.charAt(j);
                    if(k=='*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        NttGroup.brickList.add((Brick) object);
                        NttGroup.map[j][i] = '*';
                        NttGroup.origin[j][i] = ' ';
                    }
                    else if(k=='#'){
                        object = new Wall(j,i,Sprite.wall.getFxImage());
                        NttGroup.wallList.add((Wall) object);
                        NttGroup.map[j][i] = '#';
                        NttGroup.origin[j][i] = '#';
                    }
                    else if(k==' '){
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        NttGroup.grassList.add((Grass) object);
                        NttGroup.map[j][i] = ' ';
                        NttGroup.origin[j][i] = ' ';
                    }
                    else {
                        //System.out.println(k);
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        NttGroup.brickList.add((Brick) object);
                        NttGroup.map[j][i] = '*';
                        NttGroup.origin[j][i] = k;
                    }
                }
            }
        }

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        NttGroup.bombers = (Bomber) bomberman;
        //Entity en = new Enemy2(25, 5, Sprite.doll_right1.getFxImage());
        //NttGroup.enemyList.add((Enemy) en);
        PlayerController.bomberController(scene, NttGroup.bombers);
    }

    public void update(Scene scene) {
        if(playerLife == 0) {
            makeEndGame();
            return;
        }
        if (NttGroup.bombers != null && !running) {
            if (NttGroup.resetDiff()) {
                NttGroup.diffTime = 0;
            }
            if (NttGroup.bombers.bombs.size() > 0) {
                NttGroup.bombers.checkBomb();
            }
            if (NttGroup.gamePortal != null) {
                NttGroup.gamePortal.changeLevel(scene);
            }
            if (NttGroup.bombers == null) {
                return;
            }
            for (int i = 0; i < NttGroup.itemsList.size(); i++) {
                NttGroup.itemsList.get(i).checkPlayerGet(i);
            }
            if (NttGroup.bombers.isFlamePass() || NttGroup.bombers.isBombPass()) {
                NttGroup.bombers.timeOutItem();
            }
            for (int i = 0; i < NttGroup.enemyList.size(); i++) {
                if (NttGroup.enemyList.get(i).checkBoundFlame()
                        && NttGroup.enemyList.get(i).isAlive()) {
                    NttGroup.enemyList.get(i).setSpeed(0);
                    NttGroup.enemyList.get(i).setAlive(false);
                    NttGroup.enemyList.get(i).setCollisionStart(LocalDateTime.now());
                    NttGroup.enemyList.get(i).setSpecificDead();
                    scoreGet(NttGroup.enemyList.get(i));
                }
                if (!NttGroup.enemyList.get(i).isAlive()
                        && NttGroup.enemyList.get(i).checkDisappear()) {
                    NttGroup.enemyList.remove(i);
                    i--;
                }
            }
            if ((NttGroup.bombers.checkBoundFlame() && !NttGroup.bombers.isFlamePass())
                    || NttGroup.bombers.checkBoundEnemy()) {
                NttGroup.bombers.setDie();
            }
            if (!NttGroup.bombers.isAlive) {
                NttGroup.bombers.reset();
            }
            if (NttGroup.flames.size() > 0) {
                NttGroup.flames.forEach(Flame::update);
                for (int i = 0; i < NttGroup.flames.size(); i++) {
                    if (NttGroup.flames.get(i).checkEndFlame()) {
                        NttGroup.flames.remove(i);
                        i--;
                    }
                }
            }
        }
        NttGroup.enemyList.forEach(Enemy::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        NttGroup.grassList.forEach(g -> g.render(gc));
        NttGroup.brickList.forEach(g -> g.render(gc));
        NttGroup.wallList.forEach(g -> g.render(gc));
        NttGroup.itemsList.forEach(g -> g.render(gc));
        NttGroup.enemyList.forEach(g -> g.render(gc));
        NttGroup.bombList.forEach(g -> g.render(gc));
        NttGroup.detonatorList.forEach(g -> g.render(gc));
        NttGroup.flames.forEach(g -> g.render(gc));
        if(NttGroup.gamePortal != null) {
            NttGroup.gamePortal.render(gc);
        }
        if(NttGroup.bombers != null){
            NttGroup.bombers.render(gc);
        }
    }

    public void scoreGet(Enemy temp) {
        if (temp instanceof Enemy1) {
            playerScore+=100;
        }
        if (temp instanceof Enemy2) {
            playerScore+=200;
        }
        if (temp instanceof Enemy3) {
            playerScore+=200;
        }
        if (temp instanceof Enemy4) {
            playerScore+=250;
        }
    }

    public static void makeEndGame() {
        MainMenu.running=true;
        MainMenu.isStart = false;
        NttGroup.reset();
        MainMenu.showType = MainMenu.SHOW_END;
        MainMenu.showEnd = true;
        MainMenu.playerLose = true;
        updateHighScore();
    }

    public void readHighScore() {
        File url = new File("res/highscore.txt");
        // Đọc dữ liệu từ File với Scanner
        Scanner scanner = null;
        try {
            scanner = new Scanner(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int u;
        for (int i =0; i<5;i++) {
            u = scanner.nextInt();
            highscore.add(u);
        }
        scanner.close();
    }

    public static void updateHighScore() {
        if (highscore.size() > 0) {
            if (playerScore > highscore.peek()) {
                highscore.add(playerScore);
                highscore.remove();
            }
        }
        int[] hi = new int[5];
        try (FileWriter fileWriter = new FileWriter("res/highscore.txt")) {
            for (int i =0 ; i < 5 ; i++) {
                hi[i] = highscore.remove();
            }
            for (int i = 4; i >= 0; i--) {
                highscore.add(hi[i]);
                fileWriter.write(Integer.toString(hi[i])+'\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}