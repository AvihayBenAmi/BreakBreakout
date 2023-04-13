import javax.imageio.ImageIO;
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

    public BackgroundMenu(Window window) {
        this.show = true;
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("BreakBreakout.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        jButtons[0] = new JButton("Start Game");
        jButtons[1] = new JButton("Instractions");
        jButtons[2] = new JButton("Scores");
        jButtons[3] = new JButton("Exit Game");
        int place = 0;
        for (int i = 0; i < 4; i++) {
            this.add(jButtons[i]);
            jButtons[i].setBounds(X_RIGHT_BOTTON + place, Y_RIGHT_BOTTON, WIDTH_RIGHT_BOTTON_SIZE, HIGHT_RIGHT_BOTTON);
            place += 1.5 * WIDTH_RIGHT_BOTTON_SIZE;
        }
        jButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.startGame();
            }
        });
        jButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.openInstructoins();
            }
        });
        jButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.openScoreBoard();
            }
        });
        jButtons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


//    public void startGameButtonPressed() {
//        this.show = false;
//        for (int i = 0; i < 4; i++) {
//            this.jButtons[i].setVisible(false);
//        }
//        repaint();
//        Game game=new Game();
//        game.setBounds(0, 0, 800, 500);
//        add(game);
//
//       // game.requestFocus();
//        //Window.startGame(Game game);
//
//    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (this.show) {
            graphics.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            JLabel by =new JLabel("@By Avihay Navon, David Ever-Haim, Omer Hayoon, Avihay Ben-Ami, AAC-CS 2023");
            by.setBounds(3,425,800,40);
            by.setFont(new Font("Arial",Font.BOLD,18));
            by.setVisible(true);
            by.setForeground(Color.white);
            this.add(by);

        }
    }
}
