import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class Instructions extends JPanel {


    private boolean show;
    private JButton back;
    private Image image;

    public Instructions(Window window) {
        this.show = true;
        back = new JButton("Back to Menu");
        this.add(back);
        back.setBounds(0, 400, 150, 50);
        back.setFont(new Font("Arial", Font.BOLD, 10));
        addImage();
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.openBackgroundMenu();
            }
        });
    }

    public void addImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/data/brick breaker roll.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, getWidth()-10, getHeight()-35, this);

    }

}
