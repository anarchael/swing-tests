/*
 * This source file was generated by the Gradle 'init' task
 */
package fr.funecirce.apprentissageswing;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    
    private static void initWindow() {

        JFrame f = new JFrame("Apprentissage Swing");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Board board = new Board();
        f.add(board);
        f.addKeyListener(board);

        f.setLocationRelativeTo(null);
        f.setResizable(false);

        f.pack();
        
        f.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { initWindow(); });
    }
}
