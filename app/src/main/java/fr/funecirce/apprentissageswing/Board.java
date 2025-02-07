package fr.funecirce.apprentissageswing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import fr.funecirce.apprentissageswing.entities.Coin;
import fr.funecirce.apprentissageswing.entities.Player;

public class Board extends JPanel implements ActionListener, KeyListener{

    //Délai pour la gestion du timer en ms
    private final int DELAY = 20;

    //gestion de la taille de ma grille
    public static final int TILE_SIZE = 50;
    public static final int COLUMNS = 18;
    public static final int ROWS = 12;

    public static int nb_coins;

    private final Player player;
    private ArrayList<Coin> coins;
    private final SoundPlayer coinCollectedSound;
    
    //Création du timer: Référence extérieure en cas de besoin en dehors de actionPerformed()
    private final Timer timer;
    private int counter;
    
    public Board() {
            setPreferredSize(new Dimension(COLUMNS*TILE_SIZE, ROWS*TILE_SIZE));
            setBackground(new Color(232,232,232));
            coinCollectedSound = new SoundPlayer("assets/sounds/coin-received.wav");
            coinCollectedSound.setVolume(0.5f);


            player = new Player();
            nb_coins = 6;
            coins = populateCoins();
    
            timer = new Timer(DELAY, this);
    
            timer.start();
        }
    
    @Override
    public void paintComponent(Graphics g) {
            super.paintComponent(g);
    
            drawScore(g);
            for(Coin coin : coins) {
                coin.draw(g, this);
            }
    
            player.draw(g, this);
        }
    
    @Override
    public void keyTyped(KeyEvent e) {
             //Handle numerous key press
             
        }
    
    @Override
    public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

        }
    
    @Override
    public void keyReleased(KeyEvent e) {
            //Handle for the key release
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
            if (counter >= DELAY*250) {
                if(!coins.isEmpty() && nb_coins >= 3) nb_coins--;
                player.addScore(-(100*coins.size()));
                coins.clear();
                
                coins = populateCoins();
                counter = 0;
            }
            if (coins.isEmpty()) {
                if(nb_coins <= 10) nb_coins++;
                coins = populateCoins();
                counter = 0;
            }

        //Where to put the timer in
        player.tick();

        collectCoins();

        repaint();
        counter+=DELAY;

    }

    private void drawScore(Graphics g) {
        Color c;
        String text = player.getScore()+"€";

        //Cast en Graphics2D pour avoir un meilleur rendu pour le texte
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        //Définition de la couleur du texte et de la police
        if (player.getScore() < 0) {
            c = Color.RED;
        } else {
            c = Color.GREEN;
        }
        g2d.setColor(c);
        g2d.setFont(new Font("Lato", Font.BOLD, 25));
        // Dessiner le score dans un rectangle en bas au milieu de l'écran.
        //FontMetrics permet de pouvoir mettre en forme le texte sur un écran en particulier
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        Rectangle rect = new Rectangle(0, TILE_SIZE*(ROWS-1), TILE_SIZE*(COLUMNS-1), TILE_SIZE);
        int x = rect.x + (rect.width - metrics.stringWidth(text))/2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.drawString(text, x, y);
    }

    private void collectCoins() {
        ArrayList<Coin> collectedCoins = new ArrayList<>();
        for(Coin coin : coins) {
            if(player.getPos().equals(coin.getPos())) {
                player.addScore(100);
                collectedCoins.add(coin);
            }
        }

        if(!collectedCoins.isEmpty()) coinCollectedSound.start();
        coins.removeAll(collectedCoins);
    }

    private ArrayList<Coin> populateCoins() {
        ArrayList<Coin> coinList = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < nb_coins; i++) {
            int coinX = rand.nextInt(COLUMNS);
            int coinY = rand.nextInt(ROWS);
            coinList.add(new Coin(coinX, coinY));
        }

        return coinList;
    }

}
