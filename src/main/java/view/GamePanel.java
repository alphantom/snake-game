package view;

import models.*;
import models.Character;
import models.Point;
import util.draw.DrawObserver;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel implements DrawObserver {
    private final int width, height, scale;
    private volatile Set<Frog> frogs = Collections.synchronizedSet(new HashSet<>());
    private volatile List<Point> snakeBody = Collections.synchronizedList(new LinkedList<>());
//    private Set<Point> activePoints = new HashSet<>();
    private Graphics2D g2d;

//    private boolean snakeRepaint = false;

    private GameStatus statusPanel;

    private final Object lockObject = new Object();

//    private Status
    public GamePanel(int width, int height, int scale) {
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
        paintDots();
        snakeBody.forEach(this::paintAct); // todo ConcurrentModificationException
        frogs.forEach(frog -> paintAct(frog.getPosition()));
    }

//    @Override
//    public void update(Graphics graphics) {
//        paint(graphics);
//    }

    public void paintDots() {
        g2d.setStroke(new BasicStroke(0.25f));
        g2d.setColor(Color.gray);
//        int width = g2d.getClip().getBounds().width;
//        int height = g2d.getClip().getBounds().height;
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
    public void update(Character character) {
        Rectangle place = null;
//        synchronized (lockObject) {
            if (character instanceof Snake) {
//                snakeRepaint=true;
                Snake snake = (Snake) character;
                snakeBody = snake.getBody(); //todo update only snake
    //            activePoints.addAll(snake.getBody());
                statusPanel.setScore(snake.getXp());
                place = getRedrawClip((Snake) character);
            } else if (character instanceof Frog) {
//                snakeRepaint=false;
                if (character.isAlive()) {
    //                activePoints.
                    frogs.add((Frog) character); //todo update only frogs
                } else {
                    frogs.remove(character); // todo npex
                }
                place = getRedrawClip((Frog) character);
            }
//        }
        repaint(place);
//        paintImmediately(place);
    }

    @Override
    public void clear() {
        frogs = Collections.synchronizedSet(new HashSet<>());
        snakeBody = new LinkedList<>();
        repaint();
    }

    public void setStatusPanel(GameStatus statusPanel) {
        this.statusPanel = statusPanel;
    }

    private Rectangle getRedrawClip(Snake snake) {
        int minx = width, miny = height, maxx = 0, maxy = 0;

        for (Point point: snake.getBody()) {
//            System.out.println("Snake point " + point.getX() + " " + point.getY());
            if (point.getX() > maxx) maxx = point.getX();
            if (point.getX() < minx) minx = point.getX();
            if (point.getY() > maxy) maxy = point.getY();
            if (point.getY() < miny) miny = point.getY();
        }
        if (minx - scale < 0 || maxx + scale*2 > width) {
            minx = 0;
            maxx = width;
        }
        if (miny - scale < 0 || maxy + scale*2 > height) {
            miny = 0;
            maxy = height;
        }
//        System.out.println("snake rect x " + (minx - scale) + " y " + (miny - scale) + " w " + (maxx - minx + scale  * 3) + " h " +(maxy - miny + scale  * 3));
        return new Rectangle(minx - scale, miny - scale, maxx - minx + scale * 3, maxy - miny + scale * 3);
    }

    private Rectangle getRedrawClip(Frog frog) {
        Point point = frog.getPosition();
        int x = 0, y = 0, clipWidth = width, clipHeight = height;

        if (point.getX() - scale < 0 || point.getX() + scale*2 >= width) {
            x = point.getX() - scale;
            clipWidth = scale * 3;
        }

        if (point.getY() - scale < 0 || point.getY() + scale*2 >= height) {
            y = point.getY() - scale;
            clipHeight = scale * 3;
        }

        return new Rectangle(x, y, clipWidth, clipHeight);
    }


}
