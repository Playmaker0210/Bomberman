package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.field.*;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NttGroup {

    public static List<Bomb> bombList = new ArrayList<>();
    public static List<Wall> wallList = new ArrayList<>();
    public static List<Grass> grassList = new ArrayList<>();
    public static List<Enemy> enemyList = new ArrayList<>();
    public static List<Brick> brickList = new ArrayList<>();
    public static Bomber bombers ;
    public static Portal gamePortal;
    public static List<Flame> flames = new ArrayList<>();
    public static List<Items> itemsList = new ArrayList<>();
    public static List<Bomb> detonatorList = new ArrayList<>();
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static char[][] map = new char[100][100];
    public static char[][] origin = new char[100][100];
    public static LocalDateTime timePause;
    public static int diffTime;

    public static void reset() {
        while (NttGroup.wallList.size()>0) {
            NttGroup.wallList.remove(0);
        }
        while (NttGroup.itemsList.size()>0) {
            NttGroup.itemsList.remove(0);
        }
        while (NttGroup.grassList.size()>0) {
            NttGroup.grassList.remove(0);
        }
        while (NttGroup.bombList.size()>0) {
            NttGroup.bombList.remove(0);
        }
        while (NttGroup.brickList.size()>0) {
            NttGroup.brickList.remove(0);
        }
        NttGroup.bombers = null;
        NttGroup.gamePortal = null;
        diffTime = 0;
        timePause = null;
    }

    public static boolean resetDiff() {
        if (timePause == null) {
            return true;
        }
        for (Bomb bomb : NttGroup.bombList) {
            if (bomb.timeStartFlame != null) {
                if (bomb.timeStartFlame.compareTo(NttGroup.timePause) < 0) {
                    return false;
                }
            }
            if (bomb.timePut != null) {
                //System.out.println(bomb.timePut.compareTo(NttGroup.timePause));
                if (bomb.timePut.compareTo(NttGroup.timePause) < 0) {
                    return false;
                }
            }
            if (bomb.timeStop != null) {
                if (bomb.timeStop.compareTo(NttGroup.timePause) < 0) {
                    return false;
                }
            }
        }

        for (Enemy enemy : NttGroup.enemyList) {
            if (enemy.getCollisionStart() != null) {
                if (enemy.getCollisionStart().compareTo(NttGroup.timePause) < 0) {
                    return false;
                }
            }
        }

        for (Flame flame : NttGroup.flames) {
            if (flame.getTimeStartFlame() != null) {
                if (flame.getTimeStartFlame().compareTo(NttGroup.timePause) < 0) {
                    return false;
                }
            }
        }
        if (NttGroup.bombers.getTimeDie() != null) {
            if (NttGroup.bombers.getTimeDie().compareTo(NttGroup.timePause) < 0) {
                return false;
            }
        }
        if (NttGroup.bombers.getTimeGetBombPass() != null) {
            if (NttGroup.bombers.getTimeGetBombPass().compareTo(NttGroup.timePause) < 0) {
                return false;
            }
        }
        if (NttGroup.bombers.getTimeGetFlamePass() != null) {
            if (NttGroup.bombers.getTimeGetFlamePass().compareTo(NttGroup.timePause) < 0) {
                return false;
            }
        }
        return true;
    }

}