import java.awt.*;

public class Ball extends Thread {
    private float xDeltaBall = 390;
    private float yDeltaBall = 400;
    private final int WIDTH_BALL = 13;
    private final int HEIGHT_BALL = 20;
    private float xDir = 0.5f;
    private float yDir = 0.5f;

    public void updateBall() {
        new Thread(new Runnable() {
            public void run() {
                xDeltaBall += xDir;
                if (xDeltaBall >= 775 || xDeltaBall <= 0) {
                    xDir *= -1;
                    System.out.println("update ball");
                }
                yDeltaBall -= yDir;
                if (0 > yDeltaBall || yDeltaBall > 500) {
                    yDir *= -1;
                    System.out.println("update ball");
                }

            }
        }).start();
    }

    public void updateBallWhenIntersects() {
        this.yDir *= -1;
        System.out.println("update ball intersects");
    }

    public float getxDeltaBall() {
        return xDeltaBall;
    }

    public float getyDeltaBall() {
        return yDeltaBall;
    }

    public int getWIDTH_BALL() {
        return WIDTH_BALL;
    }

    public int getHEIGHT_BALL() {
        return HEIGHT_BALL;
    }

    public void paintBall(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) this.xDeltaBall, (int) this.yDeltaBall, this.WIDTH_BALL, this.HEIGHT_BALL); //צובע את הכדור
    }
}
