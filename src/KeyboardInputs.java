import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs extends Thread implements KeyListener {//
    private Game game;
    private Tray tray;
    private Ball ball;

    public KeyboardInputs(Game game, Tray tray ) {
        this.game = game;
        this.tray=tray;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case (KeyEvent.VK_LEFT):
            case (KeyEvent.VK_A):
                tray.changeXDelta(-15);//מהירות מגש
                System.out.println("Left");
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                tray.changeXDelta(15);
                System.out.println("Right");
                break;
            case KeyEvent.VK_ESCAPE: //לסדר פאוז לפי לחיצה על P ושחרור בלחיצה על P
                game.gamePause(true);
                break;
            case KeyEvent.VK_R: //לסדר פאוז לפי לחיצה על P ושחרור בלחיצה על R
                game.gamePause(false);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {


    }
}
