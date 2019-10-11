package view;

import models.Field;
import models.GreenFrog;
import models.Point;
import models.Snake;
import settings.SettingUtil;
import util.direction.SnakeMouseListener;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private GamePanel gamePanel;
    private SnakeMouseListener mouseListener = new SnakeMouseListener();

    public MainWindow() {
        super("Snake Game");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SettingUtil.WIDTH, SettingUtil.HEIGHT);
        gamePanel = new GamePanel(SettingUtil.WIDTH, SettingUtil.HEIGHT, SettingUtil.SCALE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(layout);
        gamePanel.addMouseListener(mouseListener);
        this.add(gamePanel);


        Field field = new Field(mouseListener);
        field.registerDrawObserver(gamePanel);

    }
}
