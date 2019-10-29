package view;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class StatusPanel extends JPanel implements GameStatus{
    private final int width;
    private final int height;
    private final int scale;

    private int score;
    private Graphics2D g2d;

    public StatusPanel(int width, int height, int scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public Color getBackground() {
        return Color.black;
    }

    /**
     * Ensures GUI is painted when the window is moved or hidden.
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        paintHeader();

    }

    public void paintHeader() {
        g2d.setColor(Color.white);
        Font font = new Font("Monospaced", Font.PLAIN, 10);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = font.createGlyphVector(frc,
                "Score: " + score);
        g2d.drawGlyphVector(gv,
                width / 2 - ((int) gv.getVisualBounds().getWidth() / 2),
                height / 2 - ((int) gv.getVisualBounds().getHeight() / 2));
    }

    @Override
    public void setScore(int score) {
        if (this.score != score) {
            this.score = score;
            repaint();
        }
    }


    //    public void update(String difficulty, int highScore, int applesEaten) {
//        this.difficulty = difficulty;
//        this.highScore = String.valueOf(highScore);
//        this.applesEaten = String.valueOf(applesEaten);
//    }
}
