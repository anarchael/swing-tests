package fr.funecirce.apprentissageswing.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.funecirce.apprentissageswing.Board;

public class Entity {

    public static final short MOVE_UP = 0;
    public static final short MOVE_DOWN = 1;
    public static final short MOVE_LEFT = 2;
    public static final short MOVE_RIGHT = 3;

    private final String pathname;
    private BufferedImage image;
    protected Point pos;


    public Entity(String pathToSprite, int x, int y) {
        this.pathname = pathToSprite;
        loadImage();

        this.pos = new Point(x, y);
    }

    public Entity(String pathToSprite) {
        this.pathname = pathToSprite;
        loadImage();

        this.pos = new Point(0,0);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            System.out.println("Error opening coin image file: "+e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(
        image, 
        pos.x*Board.TILE_SIZE,
        pos.y*Board.TILE_SIZE, 
        observer);
    }

    public Point getPos() {
        return pos;
    }

}
