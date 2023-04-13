import java.awt.*;

public class Hero {
    private int x;
    private int y;

    public Hero(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint (Graphics graphics) {
        graphics.setColor(Color.GREEN);
        graphics.fillOval(this.x, this.y, 100, 100);
    }
}
