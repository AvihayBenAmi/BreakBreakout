import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs extends Thread implements KeyListener {
    private Game game;

    public KeyboardInputs(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case (KeyEvent.VK_LEFT):
            case (KeyEvent.VK_A):
                game.changeXDelta(-10);
                System.out.println("Left");
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                game.changeXDelta(10);
                System.out.println("Right");
                break;
            case KeyEvent.VK_P: //לסדר פאוז לפי לחיצה על P ושחרור בלחיצה על P
                game.gameStop();
                break;///////
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {


    }
}
