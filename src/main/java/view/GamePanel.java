package view;

import models.*;
import models.Character;
import models.Point;
import util.draw.DrawObserver;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GamePanel extends JPanel implements DrawObserver {
    private final int width, height, scale;
    private Set<Frog> frogs = new HashSet<>();
    private List<Point> snakeBody = new LinkedList<>();
    private Graphics2D g2d;

    private GameStatus statusPanel;

//    private Status
    public GamePanel(int width, int height, int scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
//        addMouseListener(new SnakeMouseListener());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public Color getBackground() {
        return Color.BLACK;
    }

    @Override
    public boolean isOpaque() {
        return true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setPaintMode();
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        if (isNewGame) {
//        if (currentPoint!= null) paintAct(g2d);
            paintDots();
//            activePoints.forEach(this::paintAct);
            snakeBody.forEach(this::paintAct); // todo ConcurrentModificationException
            frogs.forEach(frog -> paintAct(frog.getPosition()));
//            isNewGame = false;
//        } else paintAct(g2d);
    }

//    @Override
//    public void update(Graphics graphics) {
//        paint(graphics);
//    }

    public void paintDots() {
        g2d.setStroke(new BasicStroke(0.25f));
        g2d.setColor(Color.gray);
        for (int i = 0; i <= width / scale; i++) {
            for (int j = 0; j <= height / scale; j++) {
                if (i * scale == width) {
                    g2d.fillRect(i * scale - 1, j * scale, 1, 1);
                } else if (j * scale == height) {
                    g2d.fillRect(i * scale, j * scale - 1, 1, 1);
                } else {
                    g2d.fillRect(i * scale, j * scale, 1, 1);
                }
            }
        }
        g2d.fillRect(width - 1, height - 1, 1, 1);

    }

    private void paintAct(Point currentPoint) {
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.setColor(new Color(currentPoint.getR(), currentPoint.getG(), currentPoint.getB()));
        g2d.fillOval(currentPoint.getX() + 2, currentPoint.getY() + 2, scale - 4, scale - 3);
    }
// update snake update frog
    @Override
    public synchronized void update(Character character) {
        if (character instanceof Snake) {
            snakeBody = ((Snake) character).getBody(); //todo update only snake
            statusPanel.setScore(((Snake) character).getXp());
        } else if (character instanceof Frog) {
            if (character.getPosition() != null)
                frogs.add((Frog)character); //todo update only frogs
            else {
                frogs.remove(character);
            }
        }
        repaint(200, 200, 10, 10); // todo repaint concrete place
//        paintImmediately(100, 100, 10, 10);
    }

    public void setStatusPanel(GameStatus statusPanel) {
        this.statusPanel = statusPanel;
    }

}
