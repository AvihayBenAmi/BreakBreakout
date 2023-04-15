import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instructions extends JPanel {

    private boolean show;
    private JButton back;

    public Instructions(Window window) {
        this.show = true;
        back = new JButton("Back to Menu");
        this.add(back);
        back.setBounds(0, 0, 100, 50);
        back.setFont(new Font("Arial", Font.BOLD, 10));

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.openBackgroundMenu();
            }
        });
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.setBackground(Color.CYAN);
    }

}
