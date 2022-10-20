package uet.oop.bomberman.entities.field;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;


public class Brick extends Entity {

    public int originalPlace;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

}
