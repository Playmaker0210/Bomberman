package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.field.*;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

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
    }

}