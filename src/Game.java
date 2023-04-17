import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Game extends JPanel {
    //    File file = createFile("");
    private boolean show;
    private int xDeltaPlayer = 350;
    private final int yDeltaPlayer = 420;
    private float xDeltaBall = 390;
    private float yDeltaBall = 400;
    private float xDir = 0.05f;
    private float yDir = 0.05f;
    private final int FIRST_BRICK_LEFT_X_CORNER = 40;
    private final int FIRST_BRICK_LEFT_Y_CORNER = 30;
    private final int NUMBER_OF_BRICK_ROWS = 5;
    private final int NUMBER_OF_BRICK_COL = 10;
    private Bricks brick;
    private ArrayList<Bricks> arrayBricks;
    private Image background;
    private Color[] colors = {Color.YELLOW, Color.orange, Color.RED, Color.GREEN, Color.BLUE};
    private int pointsCounter;
    private JButton back;
    private String playerName;
    private boolean stop;
    private Window window;
    private int time = 0;
    private Timer timer;
    private boolean finished;


    public Game(Window window) {
        this.window = window;
        this.stop = false;
        this.show = true;
        addBackgroundImage();
        addKeyListener(new KeyboardInputs(this));
        this.brick = new Bricks(0, 0, null);
        this.arrayBricks = new ArrayList<>();
        //this.colors = new Color[5]; בוצע מערך של צבעים
        this.pointsCounter = 0;
        addTimer();
        createBricks();
        //createBackToMenu();
        insertPlayerName();
    }

    private void addTimer() {
        timer = new Timer(1000, e -> {
            time++;
        });
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
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
        int k = 0;
        for (int i = 0; i < NUMBER_OF_BRICK_ROWS; i++) {
            for (int t = 0; t < NUMBER_OF_BRICK_COL; t++) {
                Bricks bricks = new Bricks(indexX, indexY, colors[i]);
                this.arrayBricks.add(bricks);
                indexX += brick.getWidth();
            }
            k++;
            indexY += brick.getHeight();
            indexX = FIRST_BRICK_LEFT_X_CORNER;
        }
    }

//    private Color chooseColor(int num) {
//        Color color = null;
//        colors[0] = Color.YELLOW;
//        colors[1] = Color.orange;
//        colors[2] = Color.RED;
//        colors[3] = Color.GREEN;
//        colors[4] = Color.BLUE;
//        if (num < colors.length) {
//            color = colors[num];
//        }
//        return color;
//    }

//    private void createBackToMenu() {
//        back = new JButton("Back to Menu");
//        this.add(back);
//        back.setBounds(3, 420, 100, 50);
//        back.setFont(new Font("Arial", Font.BOLD, 10));
//       back.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                window.openBackgroundMenu();
//           }
//        });
//    }

    private String insertPlayerName() {
        this.playerName = "";
        JTextField textField = new JTextField(20);
        JFrame frameOfText = new JFrame("Insert Name");
        //JLabel label=new JLabel("Insert Name");
        JButton okButton = new JButton("Submit");
        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(okButton);
        //panel.add(label);
        frameOfText.add(panel);
        frameOfText.setSize(250, 100);
        //frameOfText.setLocation(585, 400);
        frameOfText.setLocationRelativeTo(null);
        frameOfText.setVisible(true);
        frameOfText.setAlwaysOnTop(true);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scanner scanner = new Scanner(System.in);
                playerName = textField.getText();
                frameOfText.setVisible(false);

            }
        });
        return playerName;
    }

    public void changeXDelta(int value) {
        if (this.xDeltaPlayer + value > 0 && this.xDeltaPlayer + value < 685) {// צריך לשנות לגודל קבוע
            this.xDeltaPlayer += value;
        }
        repaint();
    }

    public void updateBall() {
        xDeltaBall += xDir;
        if (xDeltaBall >= 775 || xDeltaBall <= 0) {
            xDir *= -1;
        }

        yDeltaBall -= yDir;
        if (0 > yDeltaBall || yDeltaBall > 500) {
            yDir *= -1;
        }
    }

    private void paintImages(Graphics g) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.WHITE);
        g.fillRect(xDeltaPlayer, yDeltaPlayer, 100, 25);//צובע את שחקן
        g.setColor(Color.WHITE);
        g.fillOval((int) xDeltaBall, (int) yDeltaBall, 13, 17); //צובע את הכדור
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("Name: --> " + playerName + "   Points: -->   " + pointsCounter + "   Timer: -->   " + time, 3, 12);

    }


    private void checkIntersectsWithPlate() {
        if (new Rectangle((int) xDeltaBall, (int) yDeltaBall, 13, 17)
                .intersects(new Rectangle(xDeltaPlayer, yDeltaPlayer, 100, 25))) {
            intersectsSound();

            yDir *= -1;
        }
    }

    private void checkIntersectsWithBricks() {
        for (int i = 0; i < arrayBricks.size(); i++) {
            if (new Rectangle((int) xDeltaBall, (int) yDeltaBall, 13, 17)
                    .intersects(arrayBricks.get(i).getX(), arrayBricks.get(i).getY(), arrayBricks.get(i).getWidth(),
                            arrayBricks.get(i).getHeight())) {
                intersectsSound();
                System.out.println("HIT THE BRICK" + "i= " + i);
                calculatePoints(arrayBricks.get(i));
                arrayBricks.remove(i);
                yDir *= -1;
            }
        }
    }

    private void loseGameMassage() throws FileNotFoundException {
        if (yDeltaBall > yDeltaPlayer  -5) {//25
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream
                        (Objects.requireNonNull(Main.class.getResourceAsStream("/data/game-over-arcade-6435 (1).wav")));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finished = false;
            showMessage(playerName + ", Game Over!" +
                    " \n Your Score is: " + pointsCounter + " Your time was " + time +
                    " seconds, you "+(finished ? "":"didn't ")+"finish");
            Scoreboard.createFile(collectData());
            SwingUtilities.invokeLater(() -> window.openBackgroundMenu());//the screen didn't update because of the main thread so we added this function.

        }
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void gameStop() {
        this.stop = true;
        timer.stop();
        int input = JOptionPane.showConfirmDialog(this, "Do you want to exit the game? "
                , "Game stopped", JOptionPane.YES_NO_OPTION);// JOptionPane.CLOSED_OPTION
        if (input == 0) {
            this.window.openBackgroundMenu();
        } else {
            System.out.println("paused");
            this.stop = false;
        }
        timer.start();

    }


    private void winGameMassage() throws FileNotFoundException {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("/data/winsquare-6993.wav")));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finished = true;
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
        System.out.println("Name -> " + name + "      Points -> " + points + "      Time -> " + timer + (finished ? "":"didn't")+"finish");
        return "Name -> " + name + "      Points -> " + points + "      Time -> " +
                timer+" Finished     -> "+(finished ? "":"didn't ")+"finish.";
    }

    public void startGame() {
        this.stop = false;
    }
    public void paintFunctions(Graphics g) throws FileNotFoundException {
        checkIntersectsWithPlate();
        timer.start();
        loseGameMassage();
        checkIntersectsWithBricks();
        updateBall();
        repaint();
        for (Bricks bricks : this.arrayBricks) {
            bricks.paint(g);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintImages(g);
        if (this.playerName != "" && !this.stop) {
            if (arrayBricks.size() > 0) {
                try {
                    paintFunctions(g);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    winGameMassage();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}


