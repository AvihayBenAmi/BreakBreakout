import javax.swing.*;

public class Window extends JFrame {//

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private BackgroundMenu background;
    private Game game;
    private Scoreboard scoreBoard;
    private Instructions instructions;


    public Window() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Brick Breaker");
        openBackgroundMenu();
    }

    public void openBackgroundMenu() {
        if (game != null)
            this.remove(game);
        if (instructions != null)
            this.remove(instructions);
        if (scoreBoard != null)
            this.remove(scoreBoard);
        this.background = new BackgroundMenu(this);
        this.add(background);
        background.setBounds(0, 0, WIDTH, HEIGHT);
        background.requestFocus();
    }

    public void startGame() {
        this.remove(background);
        if (game != null)
            this.remove(this.game);
        this.game = new Game(this);
        this.add(game);
        this.game.setBounds(0, 0, WIDTH, HEIGHT);
        game.requestFocus();
    }
    public void openInstructions() {
        this.remove(background);
        this.instructions = new Instructions(this);
        this.add(instructions);
        this.instructions.setBounds(0, 0, WIDTH, HEIGHT);
        instructions.requestFocus();
    }

    public void openScoreBoard() {
        this.remove(background);
        this.scoreBoard = new Scoreboard(this);
        this.add(scoreBoard);
        this.scoreBoard.setBounds(0, 0, WIDTH, HEIGHT);
        scoreBoard.requestFocus();

    }
}
