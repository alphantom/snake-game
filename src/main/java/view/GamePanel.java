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
//    private volatile Set<Frog> frogs = Collections.synchronizedSet(new HashSet<>());
//    private volatile List<Point> snakeBody = Collections.synchronizedList(new LinkedList<>());
//    private Set<Point> activePoints = new HashSet<>();
    private Graphics2D g2d;
//    private int[] changes;
    private volatile Point[][] field;

    private boolean snakeRepaint = false;

    private GameStatus statusPanel;

    private final Object lockObject = new Object();

//    private Status
    public GamePanel(int width, int height, int scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        field = new Point[height][width];
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
        Arrays.stream(field).flatMap(Arrays::stream).forEach(point -> {
            if (null != point) paintAct(point);
        });
//        snakeBody.forEach(this::paintAct); // todo ConcurrentModificationException
//        frogs.forEach(frog -> paintAct(frog.getPosition()));
    }

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
    public void update(Point[][] grid, int minx, int maxx, int miny, int maxy) {
        synchronized (field) {
//            changes = new int[]{1,2};
//            changes = grid;
//            Point startPoint = null;
snakeRepaint = true;
//            Point[][] changes = new Point[maxy + 1][maxx + 1];
            for (int i = miny, j = minx; i <= maxy; i++, j++) {
                field[i] = Arrays.copyOfRange(grid[i], minx, maxx + 1);
//            System.out.println("grid i " + Arrays.deepToString(grid[i]));
//            System.out.println("copy grid i " + Arrays.deepToString(Arrays.copyOfRange(grid[i], minx, maxx + 1)));
            }
//
//            for (int i = 0; i < grid[0].length; i++) {
//                for (int j = 0; j < grid.length; j++) {
//                    if (null != grid[j][i]) {
//                        startPoint = grid[j][i];
//                        break;
//                    }
//                }
//                if (null != startPoint) break;
//            }

            Rectangle place = new Rectangle(minx * scale, miny * scale, (maxx - minx) * scale + scale , (maxy - miny) * scale + scale);
//            if (character instanceof Snake) {
////                snakeRepaint=true;
//                Snake snake = (Snake) character;
//                snakeBody = snake.getBody(); //todo update only snake
//    //            activePoints.addAll(snake.getBody());
//                statusPanel.setScore(snake.getXp());
//                place = getRedrawClip((Snake) character);
//            } else if (character instanceof Frog) {
////                snakeRepaint=false;
//                if (character.isAlive()) {
//    //                activePoints.
//                    frogs.add((Frog) character); //todo update only frogs
//                } else {
//                    frogs.remove(character); // todo npex
//                }
//                place = getRedrawClip((Frog) character);
//            }
////                    paintImmediately(place);
////        }
//        repaint(place);
                    paintImmediately(place);
//            repaint(place);
        }
    }

    @Override
    public void clear() {
//        frogs = new HashSet<>();
//        snakeBody = new LinkedList<>();
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
