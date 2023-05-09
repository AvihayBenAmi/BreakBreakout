import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Game extends JPanel {//
    private Window window;
    private Image background;
    private int pointsCounter;
    private String playerName;
    private boolean pause;
    private Timer timer;
    private int time;
    private boolean finished;
    private Bricks brick;
    private ArrayList<Bricks> arrayBricks;
    private final int FIRST_BRICK_LEFT_X_CORNER = 40;
    private final int FIRST_BRICK_LEFT_Y_CORNER = 30;
    private final int NUMBER_OF_BRICK_ROWS = 5;
    private final int NUMBER_OF_BRICK_COL = 10;
    private final Color[] colors = {Color.YELLOW, Color.orange, Color.RED, Color.GREEN, Color.BLUE};
    private Ball ball;
    private Tray tray;
    private JFrame frameOfText;
    private boolean canPaint;
    private boolean ifLose;
    private boolean ifWin;
    private String gameData;

    public Game(Window window) {
        this.window = window;
        this.tray = new Tray();
        addBackgroundImage();
        this.time = 0;
        this.pointsCounter = 0;
        addTimer();
        this.brick = new Bricks(0, 0, null);
        this.arrayBricks = new ArrayList<>();
        createBricks();
        this.pause = false;
        addKeyListener(new KeyboardInputs(this, this.tray));
        this.playerName = insertPlayerName();
        this.ball = new Ball();
    }

    Thread gameProcess = new Thread(new Runnable() {
        public void run() {
            System.out.println("Game process Thread " + gameProcess);
            if (playerName != null && !pause) {
                if (arrayBricks.size() > 0) {
                    startTimer();
                    startBall();
                    canPaint = true;
                }
                while (true) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    checkIntersectsWithPlate();
                    try {
                        loseGameMassage();
                        if (ifLose) {
                            break;
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    checkIntersectsWithBricks();
                    if (arrayBricks.size() == 0) {
                        try {
                            winGameMassage();
                            if (ifWin) {
                                break;
                            }
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    });

    private void addTimer() {
        timer = new Timer(1000, e -> {
            time++;
        });
    }

    public void startTimer() {
        timer.start();
        System.out.println("Timer is started");
    }

    public void stopTimer() {
        timer.stop();
        System.out.println("Timer is stopped");

    }

    private void addBackgroundImage() {
        try {
            this.background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/data/neon.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void createBricks() {
        int indexX = FIRST_BRICK_LEFT_X_CORNER;
        int indexY = FIRST_BRICK_LEFT_Y_CORNER;//10
        for (int i = 0; i < NUMBER_OF_BRICK_ROWS; i++) {
            for (int t = 0; t < NUMBER_OF_BRICK_COL; t++) {
                Bricks bricks = new Bricks(indexX, indexY, colors[i]);
                this.arrayBricks.add(bricks);
                indexX += brick.getWidth();
            }
            indexY += brick.getHeight();
            indexX = FIRST_BRICK_LEFT_X_CORNER;
        }
    }

    private String insertPlayerName() {
        this.playerName = null;
        JTextField textField = new JTextField(20);
        this.frameOfText = new JFrame("Insert Name");
        JButton submitButton = new JButton("Submit");
        frameOfText.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(submitButton);
        frameOfText.add(panel);
        frameOfText.setSize(250, 100);
        frameOfText.setLocationRelativeTo(null);
        frameOfText.setVisible(true);
        frameOfText.setAlwaysOnTop(true);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = textField.getText();
                frameOfText.removeNotify();
                gameProcess.start();
            }
        });
        return playerName;
    }

    private void checkIntersectsWithPlate() {
        final int IS_CENTER_TRAY = 0;
        final int IS_LEFT_CORNER_TRAY = 1;
        final int IS_RIGHT_CORNER_TRAY = 2;
        int parameter;
        Rectangle ballPosition = new Rectangle((int) ball.getxDeltaBall(), (int) ball.getyDeltaBall(), ball.getWIDTH_BALL(), ball.getHEIGHT_BALL());
        Rectangle trayPosition = new Rectangle(tray.getxDeltaPlayer(), tray.getyDeltaPlayer(), tray.getWidthTraySize(), tray.getHeightTraySize());
        Rectangle trayCornerPositionLeft = new Rectangle(tray.getxDeltaPlayer(), tray.getyDeltaPlayer(), 5, tray.getHeightTraySize());
        Rectangle trayCornerPositionRight = new Rectangle(tray.getxDeltaPlayer() + tray.getWidthTraySize() - 5, tray.getyDeltaPlayer(), 5, tray.getHeightTraySize());
        if (ballPosition.intersects(trayPosition)) {
            if (ballPosition.intersects(trayCornerPositionLeft)) {
                parameter = IS_LEFT_CORNER_TRAY;
                System.out.println("Hit the left corner Tray");
            } else if (ballPosition.intersects(trayCornerPositionRight)) {
                System.out.println("Hit the right corner Tray");
                parameter = IS_RIGHT_CORNER_TRAY;
            } else {
                System.out.println("Hit the center Tray");
                parameter = IS_CENTER_TRAY;
            }
            intersectsSound();
            ball.updateBallWhenIntersects(parameter);
        }

    }


    private void checkIntersectsWithBricks() {
        final int IS_BRICK_CORNER = 3;
        for (int i = 0; i < arrayBricks.size(); i++) {
            if (new Rectangle((int) ball.getxDeltaBall(), (int) ball.getyDeltaBall(), ball.getWIDTH_BALL(), ball.getHEIGHT_BALL())
                    .intersects(arrayBricks.get(i).getX(), arrayBricks.get(i).getY(), arrayBricks.get(i).getWidth(),
                            arrayBricks.get(i).getHeight())) {
                System.out.println("Hit the brick" + "i= " + i);
                intersectsSound();
                ball.updateBallWhenIntersects(IS_BRICK_CORNER);
                calculatePoints(arrayBricks.get(i));
                arrayBricks.remove(i);
                repaint();
            }
        }
    }

    private void loseGameMassage() throws FileNotFoundException {
        this.ifLose = false;
        if (ball.getyDeltaBall() > tray.getyDeltaPlayer() - 1) {//25
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream
                        (Objects.requireNonNull(Main.class.getResourceAsStream("/data/game-over-arcade-6435 (1).wav")));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stopBall();
            finished = false;
            stopTimer();
            this.ifLose = true;
            showMessage(playerName + ", Game Over!" +
                    " \n Your Score is: " + pointsCounter + " Your time was " + time +
                    " seconds, you " + (finished ? "" : "didn't ") + " finish");
            Scoreboard.createFile(collectData());
            SwingUtilities.invokeLater(() -> window.openBackgroundMenu());//the screen didn't update because of the main thread so we added this function.

        }
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void gamePause(Boolean pauseStatus) {
        if (pauseStatus) {
            this.pause = true;
            timer.stop();
            pauseBall();
            int input = JOptionPane.showConfirmDialog(this, "Do you want to exit the game? "
                    , "Game stopped", JOptionPane.YES_NO_OPTION);
            if (input == 0) {
                this.window.openBackgroundMenu();
            } else {
                System.out.println("paused, enter R to continue");
            }
        } else {
            this.pause = false;
            System.out.println("game resumed");
            timer.start();
            resumeBall();
        }
    }

    private void winGameMassage() throws FileNotFoundException {
        this.ifWin = false;
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("/data/winsquare-6993.wav")));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stopBall();
        finished = true;
        this.ifWin = true;
        stopTimer();
        JOptionPane.showConfirmDialog(this, playerName + ", You Won! \n Your Score is: " + pointsCounter + " Your time was " + time
                , "Winner!", JOptionPane.PLAIN_MESSAGE);
        Scoreboard.createFile(collectData());
        SwingUtilities.invokeLater(() -> window.openBackgroundMenu());//the screen didn't update because of the main thread so we added this function.
    }

    public void calculatePoints(Bricks brick) {
        pointsCounter += brick.getPoints();
    }

    private void intersectsSound() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("/data/coin-collect-retro-8-bit-sound-effect-145251.wav")));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String collectData() {
        String name = this.playerName;
        int points = this.pointsCounter;
        int timer = this.time;
        this.gameData = "Name:  " + name + "      Points:  " + points + "      Time:  " +
                timer + " Finished:     " + (finished ? "" : " didn't ") + " finish.";
        System.out.println(gameData);
        return gameData;
    }

    private void startBall() {
        ball.startUpdateBall();
    }

    private void stopBall() {
        ball.stopUpdateBall();
    }

    private void pauseBall() {
        this.ball.pauseUpdateBall();
    }

    private void resumeBall() {
        this.ball.resumeUpdateBall();
    }

    private void paintImages(Graphics g) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("Name:   " + playerName + "   Points:   " + pointsCounter + "   Timer:   " + time + "   Bricks left:   " + arrayBricks.size(), 3, 12);
    }

    public void paintFunctions(Graphics g) {
        try {
            for (int i = 0; i < this.arrayBricks.size(); i++) {
                if (arrayBricks.get(i) != null)
                    arrayBricks.get(i).paint(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ball.paintBall(g);
        tray.paintTray(g);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintImages(g);
        if (canPaint) {
            paintFunctions(g);
        }
        repaint();
    }
}
