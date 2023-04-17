import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Game extends JPanel {

    private boolean show;
    private int xDelta = 350;
    private final int yDelta = 435;
    private float xDeltaBall = 390;
    private float yDeltaBall = 415;
    private float xDir = 0.3f;
    private float yDir = 0.3f;
    private final int FIRST_BRICK_LEFT_X_CORNER = 60;
    private final int FIRST_BRICK_LEFT_Y_CORNER = 30;
    private final int NUMBER_OF_BRICK_ROWS = 5;
    private final int NUMBER_OF_BRICK_COL = 10;
    private Bricks brick;
    private ArrayList<Bricks> arrayBricks;
    private Image background;
    private Color[] colors={Color.YELLOW,Color.orange,Color.RED,Color.GREEN,Color.BLUE};
    private int pointsCounter;
    private JButton back;
    private String playerName;
    private boolean stop;
    private Window window;


    public Game(Window window) {
        this.window=window;
        this.stop=false;
        this.show = true;
        addBackgroundImage();
        addKeyListener(new KeyboardInputs(this));
        this.brick = new Bricks(0, 0, null);
        this.arrayBricks = new ArrayList<>();
        //this.colors = new Color[5]; בוצע מערך של צבעים
        this.pointsCounter = 0;
        createBricks();
        //createBackToMenu();
        insertPlayerName();
    }

    private void addBackgroundImage() {
        try {
            this.background = ImageIO.read(Objects.requireNonNull(getClass().getResource("neon.png")));
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

    private void insertPlayerName() {
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
        frameOfText.setLocation(585, 400);
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
    }

    public void changeXDelta(int value) {
        if (this.xDelta + value > 0 && this.xDelta + value < 685) {// צריך לשנות לגודל קבוע
            this.xDelta += value;
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.WHITE);
        g.fillRect(xDelta, yDelta, 100, 25);//צובע את המלבן
        g.setColor(Color.WHITE);
        g.fillOval((int) xDeltaBall, (int) yDeltaBall, 13, 17); //צובע את הכדור
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 12));

        g.drawString("Name: "+playerName+" points: " + pointsCounter, 3, 12);

        if (this.playerName != "" &&!this.stop) {
            if (arrayBricks.size() > 0) {
                checkIntersectsWithPlate();
                looseGameMassage();
                checkIntersectsWithBricks();
                updateBall();
                repaint();
                for (Bricks bricks : this.arrayBricks) {
                    bricks.paint(g);
                }
            } else {
                winGameMassage();

            }
        }
    }

    private void checkIntersectsWithPlate() {
        if (new Rectangle((int) xDeltaBall, (int) yDeltaBall, 13, 17)
                .intersects(new Rectangle(xDelta, yDelta, 100, 25))) {
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

    private void looseGameMassage() {

        if (yDeltaBall > yDelta + 25) {
            try{
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream=AudioSystem.getAudioInputStream
                        (Objects.requireNonNull(Main.class.getResourceAsStream("game-over-arcade-6435 (1).wav")));
                clip.open(inputStream);
                clip.start();
            }
            catch (Exception e){
                e.printStackTrace();
            }

           int input= JOptionPane.showConfirmDialog(this, playerName+", Game Over!" +
                   " \n Your Score is: "+pointsCounter, "Game Over", JOptionPane.DEFAULT_OPTION);//בעיה שלא חוזר למסך

                this.window.openBackgroundMenu();


        }//

    }
    public void gameStop(){
        this.stop=true;
        int input= JOptionPane.showConfirmDialog(this, "Do you want to exit the game? "
                , "Game stopped", JOptionPane.YES_NO_OPTION);// JOptionPane.CLOSED_OPTION
        if(input==0){
            this.window.openBackgroundMenu();
        }else {
            System.out.println("paused");
            this.stop = false;
        }

    }


    private void winGameMassage() {
        try{
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream=AudioSystem.getAudioInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("winsquare-6993.wav")));
            clip.open(inputStream);
            clip.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        JOptionPane.showConfirmDialog(this, playerName+", You Won! \n Your Score is: "+pointsCounter, "Winner!", JOptionPane.PLAIN_MESSAGE);
        this.window.openBackgroundMenu();


    }

    public void calculatePoints(Bricks brick) {
        pointsCounter += brick.getPoints();
    }
    private void intersectsSound(){
        try{
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream=AudioSystem.getAudioInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("coin-collect-retro-8-bit-sound-effect-145251.wav")));
            clip.open(inputStream);
            clip.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}


