package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemy.*;
import uet.oop.bomberman.entities.field.Brick;
import uet.oop.bomberman.entities.field.Grass;
import uet.oop.bomberman.entities.field.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.NttGroup;
import uet.oop.bomberman.Player.PlayerController;
import uet.oop.bomberman.menu.MainMenu;
import uet.oop.bomberman.menu.Sound;
import uet.oop.bomberman.pathFinding.PathFinder;

import java.io.*;
import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.Scanner;

import static uet.oop.bomberman.menu.MainMenu.*;

public class BombermanGame extends Application  {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static int level = 1;
    public static int playerScore = 0;
    public static int playerLife = 3;
    public static boolean gameSound = true;
    public static PathFinder pathFinder = new PathFinder();
    public static PriorityQueue<Integer> highscore = new PriorityQueue<>();
    private GraphicsContext gc;
    public static Canvas canvas;
    public NttGroup levelManage;

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
        levelManage = new NttGroup();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (levelManage != null) {
                    if (levelManage.makeMap) {
                        createMap(scene, "Level" + Integer.toString(level) + ".txt");
                    }
                }
                if (running) {
                    MainMenu.MenuControl(scene, levelManage);
                    MainMenu.updateMenu(levelManage);
                }
                else {
                    render();
                    update(levelManage);
                    stage.setTitle("Bomberman     Score: " + Integer.toString(playerScore)
                            + "     Left: " + Integer.toString(playerLife));
                }
            }
        };
        timer.start();
    }

    public void createMap(Scene scene, String last) {
        levelManage = new NttGroup();
        pathFinder.instantiateNode();
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
        int numEnemy = scanner.nextInt();
        for (int i = 0; i < hang; i++) {
            String X= scanner.next();
            X += scanner.nextLine();
            for (int j = 0; j < cot; j++) {
                Entity object;
                if (j == 0 || j == cot - 1 || i == 0 || i == hang - 1) {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    levelManage.wallList.add((Wall) object);
                    levelManage.map[j][i] = '#';
                    levelManage.origin[j][i] = '#';
                }
                else {
                    char k = X.charAt(j);
                    if(k=='*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        levelManage.brickList.add((Brick) object);
                        levelManage.map[j][i] = '*';
                        levelManage.origin[j][i] = ' ';
                        pathFinder.node[j][i].setSolid(true);
                    }
                    else if(k=='#'){
                        object = new Wall(j,i,Sprite.wall.getFxImage());
                        levelManage.wallList.add((Wall) object);
                        levelManage.map[j][i] = '#';
                        levelManage.origin[j][i] = '#';
                        pathFinder.node[j][i].setSolid(true);
                    }
                    else if(k==' '){
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        levelManage.grassList.add((Grass) object);
                        levelManage.map[j][i] = ' ';
                        levelManage.origin[j][i] = ' ';
                    }
                    else {
                        //System.out.println(k);
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        levelManage.brickList.add((Brick) object);
                        levelManage.map[j][i] = '*';
                        levelManage.origin[j][i] = k;
                        pathFinder.node[j][i].setSolid(true);
                    }
                }
            }
        }
        for (int i = 0; i < numEnemy; i++) {
            int type = scanner.nextInt();
            if (type == 1) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int direct = scanner.nextInt();
                Enemy enemy = new Enemy1(x, y, Sprite.balloom_left2.getFxImage());
                if (direct == 1) {
                    enemy.setSpeedX(enemy.getSpeed());
                }
                else {
                    enemy.setSpeedY(enemy.getSpeed());
                }
                levelManage.enemyList.add(enemy);
            }
            else {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                Enemy enemy;
                if (type == 2) {
                    enemy = new Enemy2(x, y, Sprite.oneal_right2.getFxImage());
                }
                else if (type == 3) {
                    enemy = new Enemy3(x, y, Sprite.doll_left2.getFxImage());
                }
                else {
                    enemy = new Enemy4(x, y, Sprite.kondoria_right1.getFxImage());
                }
                levelManage.enemyList.add(enemy);
            }
        }
        scanner.close();
        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        levelManage.bombers = (Bomber) bomberman;
        PlayerController.bomberController(scene, levelManage.bombers, levelManage);
    }

    public void update(NttGroup levelManage) {
        if(playerLife == 0) {
            if (gameSound) {
                Sound.playSound("soundLose");
            }
            playerLose = true;
            makeEndGame();
            return;
        }
        if (levelManage.bombers != null && !running) {
            if (levelManage.resetDiff()) {
                levelManage.diffTime = 0;
            }
            if (levelManage.bombers.bombs.size() > 0) {
                levelManage.bombers.checkBomb(levelManage);
            }
            if (levelManage.gamePortal != null) {
                levelManage.gamePortal.changeLevel(levelManage);
            }
            if (levelManage.bombers == null) {
                return;
            }
            for (int i = 0; i < levelManage.itemsList.size(); i++) {
                levelManage.itemsList.get(i).checkPlayerGet(i, levelManage);
            }
            if (levelManage.bombers.isFlamePass() || levelManage.bombers.isBombPass()) {
                levelManage.bombers.timeOutItem(levelManage);
            }
            for (int i = 0; i < levelManage.enemyList.size(); i++) {
                if (levelManage.enemyList.get(i).checkBoundFlame(levelManage)
                        && levelManage.enemyList.get(i).isAlive()) {
                    levelManage.enemyList.get(i).setSpeed(0);
                    levelManage.enemyList.get(i).setAlive(false);
                    levelManage.enemyList.get(i).setCollisionStart(LocalDateTime.now());
                    levelManage.enemyList.get(i).setSpecificDead();
                    scoreGet(levelManage.enemyList.get(i));
                }
                if (!levelManage.enemyList.get(i).isAlive()
                        && levelManage.enemyList.get(i).checkDisappear(levelManage)) {
                    levelManage.enemyList.remove(i);
                    i--;
                }
            }
            if ((levelManage.bombers.checkBoundFlame(levelManage) && !levelManage.bombers.isFlamePass())
                    || levelManage.bombers.checkBoundEnemy(levelManage)) {
                if (levelManage.bombers.isAlive) {
                    levelManage.bombers.setDie();
                }
            }
            if (!levelManage.bombers.isAlive) {
                levelManage.bombers.reset(levelManage);
            }
            if (levelManage.flames.size() > 0) {
                for (int i = 0; i < levelManage.flames.size(); i++) {
                    levelManage.flames.get(i).update(levelManage);
                }
                for (int i = 0; i < levelManage.flames.size(); i++) {
                    if (levelManage.flames.get(i).checkEndFlame(levelManage)) {
                        levelManage.flames.remove(i);
                        i--;
                    }
                }
            }
        }
        for (int i = 0; i < levelManage.enemyList.size(); i++){
            levelManage.enemyList.get(i).update(levelManage);
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        levelManage.grassList.forEach(g -> g.render(gc));
        levelManage.brickList.forEach(g -> g.render(gc));
        levelManage.wallList.forEach(g -> g.render(gc));
        levelManage.itemsList.forEach(g -> g.render(gc));
        levelManage.bombList.forEach(g -> g.render(gc));
        levelManage.detonatorList.forEach(g -> g.render(gc));
        levelManage.flames.forEach(g -> g.render(gc));
        if(levelManage.gamePortal != null) {
            levelManage.gamePortal.render(gc);
        }
        if(levelManage.bombers != null){
            levelManage.bombers.render(gc);
        }
        levelManage.enemyList.forEach(g -> g.render(gc));
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

    /**
     * ket thuc tro choi
     */
    public void makeEndGame() {
        MainMenu.running=true;
        MainMenu.isStart = false;
        MainMenu.showType = MainMenu.SHOW_END;
        MainMenu.showEnd = true;
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