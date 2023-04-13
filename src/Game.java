import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainScene extends JPanel {

    private boolean show;
    private java.util.List<Hero> heroList;

    public MainScene () { //בנאי לסצנה הראשית
        this.setBackground(Color.BLUE); // הגדרת קרע וצבעו
        this.show = true;
        this.heroList = new ArrayList<>(); // הוספת רשימת גיבורים
        this.heroList.add(new Hero(100, 100)); //יצירת גיבורים
        this.heroList.add(new Hero(100, 500));
        this.heroList.add(new Hero(300, 200));
    }

    public void paintComponent (Graphics graphics) {
        super.paintComponent(graphics);
        if (this.show) {
            graphics.setColor(Color.GREEN);
            for (Hero hero : this.heroList) {
                hero.paint(graphics);
            }
        }
    }

    public void disappear () {
        this.show = false;
        repaint();
    }

    public void moveRight () {
        repaint();
    }
}
