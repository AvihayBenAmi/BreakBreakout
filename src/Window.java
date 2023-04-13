import javax.swing.*;

public class Window extends JFrame {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 750;


    public Window () {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.setLayout(null);
        MainScene scene = new MainScene();
        this.add(scene);
        scene.setBounds(WIDTH / 5, 0, WIDTH / 5 * 4, HEIGHT);
        Menu menu = new Menu(scene);
        this.add(menu);
        menu.setBounds(0, 0, WIDTH / 5, HEIGHT);
    }
}
