import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Scoreboard extends JPanel {
    private Game game;
    private static String fileName = "Score";
    private static String[] dataOrder = {"Name ", "Points ", "Time"};
    private static String totalData = "";
    private boolean information;

    public Scoreboard(Window window) {
        this.setBackground(Color.blue);
        this.information = false;
        JButton back = new JButton("Back to Menu");
        this.add(back);
        back.setBounds(0, 0, 100, 50);
        back.setFont(new Font("Arial", Font.BOLD, 10));

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.openBackgroundMenu();
            }
        });
    }

    public static void createFile(String data) throws FileNotFoundException {
        File file = new File(fileName + ".txt");
        totalData += data;
        String[] tempArray = totalData.split(",");
        PrintWriter printWriter = new PrintWriter(file);
        for (int i = 0; i < tempArray.length; i+=3) {
            for(int j = 0 ; j< dataOrder.length;j++){
                printWriter.print(dataOrder[j]+tempArray[i]+". ");
                i++;
            }
            printWriter.println();
        }
        printWriter.close();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (information) {
            graphics.setColor(Color.gray);
            graphics.fillOval(0, 0, 100, 100);
        }


    }

    public void showInformation() {
        information = !information;
    }

}
