package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import uet.oop.bomberman.entities.bomb.Bomb;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.field.Brick;
import uet.oop.bomberman.entities.field.Wall;
import uet.oop.bomberman.graphics.Sprite;


public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Rectangle2D getBoundary() {
        if (this instanceof Bomber || this instanceof Enemy)
        {
            return new Rectangle2D(x, y, Sprite.SCALED_SIZE-8, Sprite.SCALED_SIZE-1);
        }
        else{
            return new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        }
    }

    public boolean intersect(Entity other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    public boolean checkBoundBrick(NttGroup levelManage) {
        for(Brick brick : levelManage.brickList) {
            if(this.intersect(brick)) {return true;}
        }
        return false;
    }

    public boolean checkBoundWall(NttGroup levelManage) {
        for(Wall wall : levelManage.wallList) {
            if(this.intersect(wall)) {return true;}
        }
        return false;
    }

    public boolean checkBoundBomb(NttGroup levelManage) {
        for(Bomb bomb : levelManage.bombList) {
            if(this instanceof Bomber) {
                if(this.intersect(bomb)&&bomb.isActivate()) {return true;}
            }
            else {
                if(this.intersect(bomb)) {return true;}
            }
        }

        for(Bomb bomb : levelManage.detonatorList) {
            if(this instanceof Bomber) {
                if(this.intersect(bomb)&&bomb.isActivate()) {return true;}
            }
            else {
                if(this.intersect(bomb)) {return true;}
            }
        }
        return false;
    }

    public boolean checkBoundFlame(NttGroup levelManage) {
        for(Flame flame : levelManage.flames) {
            if(this.intersect(flame)) {return true;}
        }
        return false;
    }

    public boolean checkBoundEnemy(NttGroup levelManage) {
        for(Enemy enemy : levelManage.enemyList) {
            if(this.intersect(enemy)) {return true;}
        }
        return false;
    }

}
