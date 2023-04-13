import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Game extends JPanel {

    private boolean show;
    private int xDelta = 350;
    private final int yDelta = 420;
    private float xDeltaBall = 390;
    private float yDeltaBall = 400;
    private float xDir = 1.0f;
    private float yDir = 1.0f;
    private final int FIRST_BRICK_LEFT_X_CORNER = 60;
    private final int FIRST_BRICK_LEFT_Y_CORNER = 30;
    private final int NUMBER_OF_BRICK_ROWS = 5;
    private final int NUMBER_OF_BRICK_COL = 10;
    private Bricks brick;
    private ArrayList<Bricks> arrayBricks;
    private Image background;
    private float a, b, c;

    private final int A_TYPE_POINTS=10;
    private final int B_TYPE_POINTS=20;
    private final int C_TYPE_POINTS=30;
    private Color[] colors=new Color[5];

    private int pointsCounter;



    public Game() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("neon.png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        addKeyListener(new KeyboardInputs(this));
        brick = new Bricks(0, 0, null);
        this.arrayBricks = new ArrayList<>();
        int indexX = FIRST_BRICK_LEFT_X_CORNER;
        int indexY = FIRST_BRICK_LEFT_Y_CORNER;//10
        int k=0;
        for (int i = 0; i < NUMBER_OF_BRICK_ROWS; i++) {
            //floatColor();
            for (int t = 0; t < NUMBER_OF_BRICK_COL; t++) {
                Bricks bricks = new Bricks(indexX, indexY,chooseColor(k));//chooseColor(k));
                this.arrayBricks.add(bricks);
                indexX += brick.getWidth();

            }
            k++;
            indexY += brick.getHeight();
            indexX = FIRST_BRICK_LEFT_X_CORNER;
        }
        pointsCounter=0;

    }

//    private void floatColor() {
//        this.a = random.nextFloat();
//        this.b = random.nextFloat();
//        this.c = random.nextFloat();

    private Color chooseColor(int num) {
    Color color=null;
        colors[0]=Color.YELLOW;
        colors[1]=Color.orange;
        colors[2]=Color.RED;
        colors[3]=Color.GREEN;
        colors[4]=Color.BLUE;
    if(num<colors.length){
        color=colors[num];
    }
        return color;
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

//    public void removeTheBrick(Rectangle rectangle) {
//        for (int i = 0; i < arrayBricks.size(); i++) {
//            if (Objects.equals(arrayBricks.get(i), rectangle)) {
//                arrayBricks.remove(i);
//            }
//        }
//    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.WHITE);
        g.fillRect(xDelta, yDelta, 100, 25);//צובע את המלבן
        g.setColor(Color.WHITE);
        g.fillOval((int) xDeltaBall, (int) yDeltaBall, 13, 17);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD,8));
        g.drawString("point: "+pointsCounter,10,10);
//        JLabel points =new JLabel("points: "+pointsCounter);
//        points.setBounds(3,3,200,40);
//        points.setFont(new Font("Arial",Font.BOLD,8));
//        points.setVisible(true);
//        points.setForeground(Color.white);
//        this.add(points);
        if (arrayBricks.size() > 0) {
            if (new Rectangle((int) xDeltaBall, (int) yDeltaBall, 13, 17).intersects(new Rectangle(xDelta, yDelta, 100, 25))) {
                yDir *= -1;
            }
            if (yDeltaBall > yDelta + 25) {
                JOptionPane.showConfirmDialog(this, "Game Over", "Game Over", JOptionPane.CLOSED_OPTION);
            }

            for (int i = 0; i < arrayBricks.size(); i++) {
                if (new Rectangle((int) xDeltaBall, (int) yDeltaBall, 13, 17).intersects(arrayBricks.get(i).getX(), arrayBricks.get(i).getY(), arrayBricks.get(i).getWidth(),
                        arrayBricks.get(i).getHeight())) {
                    System.out.println("HIT THE BRICK" + "i= " + i);
                    calculatePoints(arrayBricks.get(i));
                    arrayBricks.get(i).damage(g);
                    arrayBricks.remove(i);
                    yDir *= -1.02;
                }
            }
//            for (int i = FIRST_BRICK_LEFT_Y_CORNER; i < brick.getHeight() * NUMBER_OF_BRICK_ROWS + FIRST_BRICK_LEFT_Y_CORNER; i += brick.getHeight()) {
//                for (int j = FIRST_BRICK_LEFT_X_CORNER; j < brick.getWidth() * NUMBER_OF_BRICK_COL + FIRST_BRICK_LEFT_X_CORNER; j += brick.getWidth()) {
//                    Rectangle temp = new Rectangle(j, i, brick.getWidth(), brick.getHeight());
//                    if (new Rectangle((int) xDeltaBall, (int) yDeltaBall, 13, 17).intersects(temp)) ;
//                    {
//                        System.out.println("HIT THE BRICK"+"i= "+i+"j= "+j);
//                        removeTheBrick(temp);
//                        yDir *= -1.05;
//                    }
//                }
//            }
            updateBall();
            repaint();
            for (Bricks bricks : this.arrayBricks) {
                bricks.paint(g);
            }
        } else {
            JOptionPane.showConfirmDialog(this, "You Winning!", "Winner!", JOptionPane.PLAIN_MESSAGE);
        }
    }
    public void calculatePoints(Bricks brick){
        pointsCounter+=brick.getPoints();
    }
}


