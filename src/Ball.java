import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ball extends JPanel implements Runnable {//
    private float xDeltaBall = 390;
    private float yDeltaBall = 400;
    private final int WIDTH_BALL = 13;
    private final int HEIGHT_BALL = 20;
    private float xDir;
    private float yDir = 0.25f;
    private final double RAISE_SPEED_BALL=-1.001;
    private boolean running;
    private boolean paused;
    private Thread updateBall;
    private final Object pauseLock = new Object();

    public Ball() {
        Random random = new Random();
        this.xDir = random.nextFloat(-0.2f, 0.2f);
        updateBall = new Thread(this);
        System.out.println("update ball thread: " + updateBall);
    }

    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (!running) {
                    break;
                }
                if (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                    if (!running) {
                        break;
                    }
                }
            }
            try {
                //System.out.println("Ball thread is running");
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

    public synchronized void startUpdateBall() {
        this.running = true;
        updateBall.start();
        System.out.println("Ball started");
    }

    public synchronized void stopUpdateBall() {
        this.running = false;
        System.out.println("Ball stopped");
    }

    public synchronized void pauseUpdateBall() {
        this.paused = true;
        System.out.println("Ball paused");
    }

    public synchronized void resumeUpdateBall() {
        synchronized (pauseLock) {
            this.paused = false;
            pauseLock.notifyAll();
        }
        System.out.println("Ball resumed");
    }

    public void updateBallWhenIntersects(int isCorner) {
        Random random = new Random();
        double temp = random.nextFloat(-1f, 1f);
        yDeltaBall += yDir - 1;
        this.yDir *= RAISE_SPEED_BALL;
        if (isCorner == 1) {
            this.xDir -= 0.2;
        }
        if (isCorner == 2) {
            this.xDir += 0.2;
        }
        if (isCorner == 0) {
            this.xDir *= temp;
        }
        if (isCorner == 3) {
            this.xDir += 0.001;
        }
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
