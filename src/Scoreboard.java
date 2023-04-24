import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class Scoreboard extends JPanel {//
    private Game game;
    private Image background;
    private static String fileName = "Score";
    private static String[] dataOrder = {"Name ", "Points ", "Time"};
    private static String totalData = "Scores table \n";
    private boolean information;
    private JTextArea textArea = new JTextArea();

    public Scoreboard(Window window) {
        this.setBackground(Color.blue);
        this.information = false;
        JButton back = new JButton("Back to Menu");
        this.add(back);
        back.setBounds(0, 0, 150, 50);
        back.setFont(new Font("Arial", Font.BOLD, 10));
        addBackgroundImage();

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.openBackgroundMenu();
            }
        });
    }


    private void addBackgroundImage() {
        try {
            this.background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/data/ScoreBoardBG.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void createFile(String data) throws FileNotFoundException {
        File file = new File(fileName + ".txt");
        totalData +="\n"+data;
        String[] tempArray = totalData.split("\\.");
        PrintWriter printWriter = new PrintWriter(file);
        for (int i = 0; i < tempArray.length; i++) {
            printWriter.print(tempArray[i]);
        }
        printWriter.close();
    }
    void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0,0,getHeight()+300,getWidth(),this);
        if (information) {
            g.setColor(Color.gray);
            g.fillOval(0, 0, 100, 100);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        drawString(g,totalData,30,100);


    }

    public void showInformation() {
        information = !information;
    }

}
