import java.awt.*;

public class Bricks {//
    private int x;
    private int y;
    private final int width=70;
    private final int height=30;//
    private Color color;
    private int points;

    public Bricks(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        if(this.color!=null){
        setPoints();}
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public void paint(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(Color.white);
        g.drawRect(this.x, this.y, this.width, this.height);

    }

    public int getPoints() {
        return points;
    }

    public void setPoints() {
        this.points = 10;
        if (this.color.getRGB() == (Color.blue.getRGB())) {
            this.points = 10;
        }
        if (this.color.getRGB() == (Color.green.getRGB())) {
            this.points = 15;
        }
        if (this.color.getRGB() == (Color.red.getRGB())) {
            this.points = 20;
        }
        if (this.color.getRGB() == (Color.orange.getRGB())) {
            this.points = 30;
        }
        if (this.color.getRGB() == (Color.yellow.getRGB())) {
            this.points = 35;
        }
    }
}

