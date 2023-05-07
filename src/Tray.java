import javax.swing.*;
import java.awt.*;

public class Tray extends JPanel {//

    private int xDeltaPlayer = 350;
    private final int yDeltaPlayer = 420;

    public void paintTray(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(xDeltaPlayer, yDeltaPlayer, 100, 25);//צובע את שחקן
    }

    public void changeXDelta(int value) {
        if (xDeltaPlayer + value > 0 && xDeltaPlayer + value < 685) {// צריך לשנות לגודל קבוע
            xDeltaPlayer += value;
        }
    }

    public int getxDeltaPlayer() {
        return xDeltaPlayer;
    }//

    public int getyDeltaPlayer() {
        return yDeltaPlayer;
    }
}
