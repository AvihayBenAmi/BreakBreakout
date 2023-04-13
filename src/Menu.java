import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    public Menu (Game scene) {
        this.setBackground(Color.CYAN);
        int buttons = 4;
        GridLayout gridLayout = new GridLayout(1, buttons);
        this.setLayout(null);
        JButton jButton = new JButton("1");
        this.add(jButton);
        jButton.setBounds(0, 0, 100, 100);
        jButton.addActionListener((event) -> {
          //  scene.moveRight();
        });
//        this.setLayout(gridLayout);
//        for (int i = 0; i < buttons; i++) {
//            this.add(new JButton(String.valueOf(i)));
//        }
    }

}
