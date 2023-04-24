import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BackgroundMenu extends JPanel {//
    private final int X_RIGHT_BOTTON = 130;
    private final int Y_RIGHT_BOTTON = 330;
    private final int WIDTH_RIGHT_BOTTON_SIZE = 100;
    private final int HIGHT_RIGHT_BOTTON = 50;
    private boolean show;
    private JButton[] jButtons = new JButton[4];
    private Image background;
    MusicThread thread;

    public BackgroundMenu(Window window) {
        this.show = true;
        addBackgroundPicture();
        addAndManageButtons(window);
        addByLine();
        MusicThread.running = true;
        thread=new MusicThread();
    }

    public void addBackgroundPicture() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/data/BreakBreakout.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addAndManageButtons(Window window) {
        jButtons[0] = new JButton("Start Game");
        jButtons[1] = new JButton("Instructions");
        jButtons[2] = new JButton("Scores");
        jButtons[3] = new JButton("Exit Game");
        int place = 0;
        for (int i = 0; i < 4; i++) {
            this.add(jButtons[i]);
            jButtons[i].setBounds(X_RIGHT_BOTTON + place, Y_RIGHT_BOTTON, WIDTH_RIGHT_BOTTON_SIZE, HIGHT_RIGHT_BOTTON);
            place += 1.5 * WIDTH_RIGHT_BOTTON_SIZE;
            if (jButtons[i].getText().length() > 10) {
                jButtons[i].setFont(new Font("Arial", Font.BOLD, 10));
            }
        }
        jButtons[0].addActionListener(e -> {
            buttonSound();
            MusicThread.running = false;
            window.startGame();
        });
        jButtons[1].addActionListener(e -> {
            buttonSound();
            MusicThread.running = false;
            window.openInstructions();
        });
        jButtons[2].addActionListener(e -> {
            buttonSound();
            MusicThread.running = false;
            window.openScoreBoard();
        });
        jButtons[3].addActionListener(e -> {
            buttonSound();
            System.exit(0);
        });
    }

    public void addByLine() {
        JLabel by = new JLabel("@By Avihay Navon, David Even-Haim, Omer Hayoon, Avihay Ben-Ami, AAC-CS 2023");
        by.setBounds(3, 430, 800, 40);
        by.setFont(new Font("Arial", Font.BOLD, 14));
        by.setVisible(true);
        by.setForeground(Color.white);
        this.add(by);
    }
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (this.show) {
            graphics.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void buttonSound(){
        try{
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream=AudioSystem.getAudioInputStream
                    (Objects.requireNonNull(Main.class.getResourceAsStream("/data/button-124476.wav")));
            clip.open(inputStream);
            clip.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
