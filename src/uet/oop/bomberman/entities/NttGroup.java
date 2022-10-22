package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.field.*;
import uet.oop.bomberman.entities.enemy.Enemy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * lop quan li cac doi tuong trong 1 man choi
 */
public class NttGroup {

    public List<Bomb> bombList;
    public List<Wall> wallList;
    public List<Grass> grassList;
    public List<Enemy> enemyList;
    public List<Brick> brickList;
    public Bomber bombers;
    public Portal gamePortal;
    public List<Flame> flames;
    public List<Items> itemsList;
    public List<Bomb> detonatorList;
    public char[][] map;
    public char[][] origin;
    public LocalDateTime timePause;
    public int diffTime;
    public boolean makeMap;
    public NttGroup() {
        bombList = new ArrayList<>();
        wallList = new ArrayList<>();
        brickList = new ArrayList<>();
        grassList = new ArrayList<>();
        enemyList = new ArrayList<>();
        flames = new ArrayList<>();
        itemsList = new ArrayList<>();
        detonatorList = new ArrayList<>();
        map = new char[100][100];
        origin = new char[100][100];
        gamePortal = null;
        diffTime = 0;
        bombers = null;
        makeMap = false;
    }

    public boolean resetDiff() {
        if (timePause == null) {
            return true;
        }
        for (Bomb bomb : bombList) {
            if (bomb.getTimeStartFlame() != null) {
                if (bomb.getTimeStartFlame().compareTo(timePause) < 0) {
                    return false;
                }
            }
            if (bomb.getTimePut() != null) {
                //System.out.println(bomb.timePut.compareTo(NttGroup.timePause));
                if (bomb.getTimePut().compareTo(timePause) < 0) {
                    return false;
                }
            }
            if (bomb.getTimeStop() != null) {
                if (bomb.getTimeStop().compareTo(timePause) < 0) {
                    return false;
                }
            }
        }

        for (Enemy enemy : enemyList) {
            if (enemy.getCollisionStart() != null) {
                if (enemy.getCollisionStart().compareTo(timePause) < 0) {
                    return false;
                }
            }
        }

        for (Flame flame : flames) {
            if (flame.getTimeStartFlame() != null) {
                if (flame.getTimeStartFlame().compareTo(timePause) < 0) {
                    return false;
                }
            }
        }
        if (bombers.getTimeDie() != null) {
            if (bombers.getTimeDie().compareTo(timePause) < 0) {
                return false;
            }
        }
        if (bombers.getTimeGetBombPass() != null) {
            if (bombers.getTimeGetBombPass().compareTo(timePause) < 0) {
                return false;
            }
        }
        if (bombers.getTimeGetFlamePass() != null) {
            if (bombers.getTimeGetFlamePass().compareTo(timePause) < 0) {
                return false;
            }
        }
        return true;
    }

}