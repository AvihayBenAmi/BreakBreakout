import javax.swing.*;
import java.awt.*;

public class Tray extends JPanel {//

    private int xDeltaPlayer = 350;
    private final int yDeltaPlayer = 420;
    private final int WIDTH_TRAY_SIZE = 100;
    private final int HEIGHT_TRAY_SIZE = 25;
    private final int LEFT_LIMIT = 0;
    private final int RIGHT_LIMIT = 685;

    public void changeXDelta(int value) {
        if (xDeltaPlayer + value > LEFT_LIMIT && xDeltaPlayer + value < RIGHT_LIMIT) {
            xDeltaPlayer += value;
        }
    }
    public int getxDeltaPlayer() {
        return xDeltaPlayer;
    }//

    public int getyDeltaPlayer() {
        return yDeltaPlayer;
    }

    public int getWidthTraySize(){
        return WIDTH_TRAY_SIZE;
    }
    public int getHeightTraySize(){
        return HEIGHT_TRAY_SIZE;
    }
    public void paintTray(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(xDeltaPlayer, yDeltaPlayer, WIDTH_TRAY_SIZE, HEIGHT_TRAY_SIZE);
    }
}
