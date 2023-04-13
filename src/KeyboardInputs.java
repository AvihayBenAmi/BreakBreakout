import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
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
            case KeyEvent.VK_A:
                game.changeXDelta(-10);
                System.out.println("A");
                break;
            case KeyEvent.VK_D:
                game.changeXDelta(10);
                System.out.println("D");
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
