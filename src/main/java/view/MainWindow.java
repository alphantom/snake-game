package view;

import controllers.Controller;
import controllers.GameController;
import settings.SettingUtil;
import util.direction.KeyListener;
import util.direction.SnakeMouseListener;
import util.draw.DrawObserver;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements View {

    private final int width = SettingUtil.WIDTH;
    private final int height = SettingUtil.HEIGHT;
    private final int scale = SettingUtil.SCALE;

    private JPanel content;
    private Controller controller;
    private GamePanel gamePanel;
    private StatusPanel statusPanel;
    private SnakeMouseListener mouseListener = new SnakeMouseListener();
    private KeyListener keyListener = new KeyListener();

    public MainWindow() {
        super("Snake Game");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(scale, scale, scale, scale));
        content.setBackground(Color.black);

        gamePanel = new GamePanel(width, height - 30, scale);
        statusPanel = new StatusPanel(width, 30, scale);

//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(gamePanel);
//        gamePanel.setLayout(layout);
//        gamePanel.addMouseListener(mouseListener);
        addKeyListener(keyListener);
//        gamePanel.setFocusable(true);
//        gamePanel.requestFocusInWindow();
        gamePanel.setStatusPanel(statusPanel);

        content.add(statusPanel);
        content.add(gamePanel);

        this.add(content);
        pack();
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
