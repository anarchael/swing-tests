package fr.funecirce.apprentissageswing.entities;

import java.awt.event.KeyEvent;

import fr.funecirce.apprentissageswing.Board;

public class Player extends Entity{

    private int score;

    public Player() {
        super("assets/images/player.png");
        score = 0;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_Z) {
            move(MOVE_UP);
        }
        if(key == KeyEvent.VK_S) {
            move(MOVE_DOWN);
        }
        if(key == KeyEvent.VK_Q) {
            move(MOVE_LEFT);
        }
        if(key == KeyEvent.VK_D) {
            move(MOVE_RIGHT);
        }
    }

    public void move(short dir) {
        switch(dir) {
            case MOVE_UP -> pos.translate(0, -1);
            case MOVE_DOWN -> pos.translate(0, 1);
            case MOVE_LEFT -> pos.translate(-1, 0);
            case MOVE_RIGHT -> pos.translate(1, 0);
            default -> System.err.println("Direction not valid.");
        }
    }

    public void tick() {

        if (pos.x < 0) {
            pos.x = 0;
        } else if (pos.x >= Board.COLUMNS) {
            pos.x = Board.COLUMNS-1;
        }

        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= Board.ROWS) {
            pos.y = Board.ROWS-1;
        }
    }

    public void addScore(int n) {
        score+=n;
    }

    public int getScore() {
        return score;
    }

}
