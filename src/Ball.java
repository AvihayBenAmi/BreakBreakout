import javax.swing.*;
import java.awt.*;

public class Ball extends JPanel implements Runnable {
    private float xDeltaBall = 390;
    private float yDeltaBall = 400;
    private final int WIDTH_BALL = 13;
    private final int HEIGHT_BALL = 20;
    private float xDir = 0.2f;
    private float yDir = 0.2f;
    private boolean running;

    private Thread updateBall;

    public Ball() {
        updateBall = new Thread(this);
        System.out.println("New thread: " + updateBall);
    }

    public void run() {
        while (running) {
            try {
                System.out.println("Ball thread is running");
                Thread.sleep(1);
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startUpdateBall() {
        this.running = true;
        updateBall.start();
    }

    public void stopUpdateBall() {
        this.running = false;
    }

    public void updateBallWhenIntersects() {
        yDeltaBall += yDir * 2;//מונע גלישה של הכדור על המשטח
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
        g.fillOval((int) this.xDeltaBall, (int) this.yDeltaBall, this.WIDTH_BALL, this.HEIGHT_BALL);
    }
}
