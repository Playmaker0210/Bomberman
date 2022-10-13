package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.field.Brick;
import uet.oop.bomberman.entities.field.Grass;
import uet.oop.bomberman.entities.field.Items;
import uet.oop.bomberman.entities.field.Wall;
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
    public static List<Flame> flames = new ArrayList<>();
    public static List<Items> itemsList = new ArrayList<>();
    public static List<Bomb> detonatorList = new ArrayList<>();
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static char[][] map = new char[100][100];
    public static char[][] origin = new char[100][100];
//    public static Bomber bomber = new Bomber(1, 1, Sprite.player_right.getFxImage());

}