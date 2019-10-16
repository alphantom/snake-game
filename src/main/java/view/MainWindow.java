package view;

import controllers.Controller;
import controllers.GameController;
import settings.SettingUtil;
import util.direction.KeyListener;
import util.direction.SnakeMouseListener;
import util.draw.DrawObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame implements View {

    private final int width = SettingUtil.WIDTH;
    private final int height = SettingUtil.HEIGHT;
    private final int scale = SettingUtil.SCALE;

    private JPanel content;
    private final Controller controller;
    private final GamePanel gamePanel;
    private final StatusPanel statusPanel;
    private final SnakeMouseListener mouseListener = new SnakeMouseListener();
    private KeyListener keyListener = new KeyListener();

    public MainWindow() {
        super("Snake Game");
        controller = new GameController(this, keyListener);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
//        addKeyListener(keyListener);
        setJMenuBar(new MenuPanel(controller));
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(scale, scale, scale, scale));
        content.setBackground(Color.black);

        gamePanel = new GamePanel(width, height - 30, scale);
        statusPanel = new StatusPanel(width, 30, scale);

        gamePanel.setStatusPanel(statusPanel);
        gamePanel.addKeyListener(keyListener);
        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "pause");
        gamePanel.getActionMap().put("pause", pauseAct());
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        content.add(statusPanel);
        content.add(gamePanel);

        add(content);

        pack();
    }

    private AbstractAction pauseAct() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("paused");
                controller.pause();
            }
        };
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
