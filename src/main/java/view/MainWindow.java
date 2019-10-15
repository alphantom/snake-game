package view;

import controllers.Controller;
import controllers.GameController;
import settings.SettingUtil;
import util.direction.KeyListener;
import util.direction.SnakeMouseListener;
import util.draw.DrawObserver;

import javax.swing.*;

public class MainWindow extends JFrame implements View {

    Controller controller;
    private GamePanel gamePanel;
    private SnakeMouseListener mouseListener = new SnakeMouseListener();
    private KeyListener keyListener = new KeyListener();

    public MainWindow() {
        super("Snake Game");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SettingUtil.WIDTH, SettingUtil.HEIGHT);
        gamePanel = new GamePanel(SettingUtil.WIDTH, SettingUtil.HEIGHT, SettingUtil.SCALE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(layout);
//        gamePanel.addMouseListener(mouseListener);
        gamePanel.addKeyListener(keyListener);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        this.add(gamePanel);

        controller = new GameController(this, keyListener);
        controller.startGame();

    }


    @Override
    public DrawObserver getDrawPanel() {
        return gamePanel;
    }

    @Override
    public void launch() {
        setVisible(true);
    }
}
