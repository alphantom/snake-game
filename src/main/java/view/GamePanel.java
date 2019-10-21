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

    private Graphics2D g2d;
    private Point[][] field;

    private GameStatus statusPanel;

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
            for (int i = miny, j = minx; i <= maxy; i++, j++) {
                field[i] = Arrays.copyOfRange(grid[i], minx, maxx + 1);
            }

            Rectangle place = new Rectangle(minx * scale, miny * scale, (maxx - minx) * scale + scale , (maxy - miny) * scale + scale);
            paintImmediately(place);
//            repaint(place);
        }
    }

    @Override
    public void clear() {
        field = new Point[height][width];
        repaint();
    }

    public void setStatusPanel(GameStatus statusPanel) {
        this.statusPanel = statusPanel;
    }

}
