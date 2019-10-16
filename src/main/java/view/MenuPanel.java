package view;

import controllers.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MenuPanel extends JMenuBar {

    public MenuPanel(Controller controller) {
        JMenu game = new JMenu("Game");
        JMenuItem newGame = new JMenuItem(new AbstractAction("New game") {
                public void actionPerformed(ActionEvent e) {
                    controller.startGame();
                }
        });
        game.add(newGame);
        JMenuItem exit = new JMenuItem(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        game.add(exit);
        add(game);
        add(createMenu("Settings", "Parameters", "Screen"));
    }

    private static JMenu createMenu(String menuLabel, String... subMenuLabels) {
        JMenu menu = new JMenu(menuLabel);
        for (String subMenuLabel : subMenuLabels) {
            JMenuItem menuItem = new JMenuItem(subMenuLabel);
            menu.add(menuItem);
        }
        return menu;
    }

}
