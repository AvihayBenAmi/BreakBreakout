import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class BackgroundMenu extends JPanel {
    private final int X_RIGHT_BOTTON = 130;
    private final int Y_RIGHT_BOTTON = 330;
    private final int WIDTH_RIGHT_BOTTON_SIZE = 100;
    private final int HIGHT_RIGHT_BOTTON = 50;
    private boolean show;
    private JButton[] jButtons = new JButton[4];
    private Image background;
    private boolean interrupted;

    public BackgroundMenu(Window window) {
        this.show = true;
        addBackgroundPicture();
        addAndManageButtons(window);
        addByLine();
        //this.interrupted=false;
        //thread.start();
    }

    public void addBackgroundPicture() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("BreakBreakout.jpg")));
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
                jButtons[i].setFont(new Font("Arial", Font.BOLD, 11));
            }
        }
        jButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSound();
                //interrupted=true;
                window.startGame();
            }
        });
        jButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSound();
                window.openInstructions();
            }
        });
        jButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSound();
                window.openScoreBoard();
            }
        });
        jButtons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSound();
                System.exit(0);
            }
        });
    }

    public void addByLine() {
        JLabel by = new JLabel("@By Avihay Navon, David Ever-Haim, Omer Hayoon, Avihay Ben-Ami, AAC-CS 2023");
        by.setBounds(3, 430, 800, 40);
        by.setFont(new Font("Arial", Font.BOLD, 14));
        by.setVisible(true);
        by.setForeground(Color.white);
        this.add(by);
    }

    private Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        Clip clip = AudioSystem.getClip();
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("8bit-music-for-game-68698.wav")));
                        clip.open(inputStream);
                        clip.start();
                        System.out.println("Clip Started");
//                        if(interrupted){
//                            clip.stop();
//                            thread.interrupt();
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    }

        });

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (this.show) {
            graphics.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static synchronized void buttonSound(){
        try{
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream=AudioSystem.getAudioInputStream
                    (Objects.requireNonNull(Main.class.getResourceAsStream("button-124476.wav")));
            clip.open(inputStream);
            clip.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
