import view.MainWindow;

public class Application {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
