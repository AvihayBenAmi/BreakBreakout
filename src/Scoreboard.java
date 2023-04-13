import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scoreboard extends JPanel {
        private boolean information;
        public Scoreboard(Window window){
            this.setBackground(Color.blue);
            this.information=false;
            JButton back = new JButton("Back to Menu");
            this.add(back);
            back.setBounds(0, 0, 100, 50);

            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    window.openBackgroundMenu();
                }
            });
        }
        public void paintComponent(Graphics graphics){
            super.paintComponent(graphics);
            if(information) {
                graphics.setColor(Color.gray);
                graphics.fillOval(0, 0, 100, 100);
            }


        }
        public void showInformation(){
            information= !information;
        }

    }
