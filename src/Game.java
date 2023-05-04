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
    private boolean checkStartBall;
    private boolean canPaint;
    private boolean lose;
    private boolean win;
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
                while (!pause) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    checkIntersectsWithPlate();
                    try {
                        loseGameMassage();
                        if (lose) {
                            break;
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    checkIntersectsWithBricks();
                    if (arrayBricks.size() == 0) {
                        try {
                            winGameMassage();
                            if (win) {
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
    public void pauseTimer() {
        timer.stop();
        System.out.println("Timer is paused");

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
        if (new Rectangle((int) ball.getxDeltaBall(), (int) ball.getyDeltaBall(), ball.getWIDTH_BALL(), ball.getHEIGHT_BALL())
                .intersects(new Rectangle(tray.getxDeltaPlayer(), tray.getyDeltaPlayer(), 100, 25))) {
            intersectsSound();
            System.out.println("Hit the Tray");
            ball.updateBallWhenIntersects();
        }
    }

    private void checkIntersectsWithBricks() {
        for (int i = 0; i < arrayBricks.size(); i++) {
            if (new Rectangle((int) ball.getxDeltaBall(), (int) ball.getyDeltaBall(), ball.getWIDTH_BALL(), ball.getHEIGHT_BALL())
                    .intersects(arrayBricks.get(i).getX(), arrayBricks.get(i).getY(), arrayBricks.get(i).getWidth(),
                            arrayBricks.get(i).getHeight())) {
                System.out.println("Hit the brick" + "i= " + i);
                intersectsSound();
                ball.updateBallWhenIntersects();
                calculatePoints(arrayBricks.get(i));
                arrayBricks.remove(i);
                repaint();
            }
        }
    }

    private void loseGameMassage() throws FileNotFoundException {
        this.lose = false;
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
            this.lose = true;
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

    public void gamePause() {
        this.pause = true;
        timer.stop();
        stopBall();
        int input = JOptionPane.showConfirmDialog(this, "Do you want to exit the game? "
                , "Game stopped", JOptionPane.YES_NO_OPTION);
        if (input == 0) {
            this.window.openBackgroundMenu();
            //gameProcess.interrupt();צריך לסדר שהמהלך של המשחק הנוכחי מסתיים ולא ממשיך אם בחרנו לצאת
        } else {
            System.out.println("paused");
            this.pause = false;
        }
        timer.start();
        ball.resumeUpdateBall(); // צריך לסדר את המשך תנועת הכדור שבחרנו לצאת מפאוז
    }

    private void winGameMassage() throws FileNotFoundException {
        this.win = false;
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
        this.win = true;
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
        if (!checkStartBall) {
            ball.startUpdateBall();
            checkStartBall = true;
        }
    }

    private void stopBall() {
        if (checkStartBall) {
            ball.stopUpdateBall();
            checkStartBall = false;
        }
    }



    private void paintImages(Graphics g) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("Name:   " + playerName + "   Points:   " + pointsCounter + "   Timer:   " + time + "   Bricks left:   " + arrayBricks.size(), 3, 12);
    }

    public void paintFunctions(Graphics g) {
        try {
            for (Bricks bricks : this.arrayBricks) {
                bricks.paint(g);
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
