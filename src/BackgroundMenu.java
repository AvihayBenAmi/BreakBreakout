import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Background extends JPanel {
    boolean show;
    Image background;
    public Background(){
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("BreakBreakout.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.show=true;

    }
    public void paintComponent (Graphics graphics) {
        super.paintComponent(graphics);
        if (this.show) {
            graphics.drawImage(background,0,0,getWidth(),getHeight(),this);
            }
        }
    }
