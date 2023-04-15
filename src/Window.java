import javax.swing.*;

public class Window extends JFrame {

    private static final int WIDTH = 800; //הגדרת קבוע לרוחב החלון
    private static final int HEIGHT = 500; //הגדרת קבוע לגובה החלון
    private BackgroundMenu background;
    private Game game;
    private Scoreboard scoreBoard;
    private Instractions instractions;


    public Window() { //בנאי לחלון
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //פעולה לכפתור האיקס
        this.setResizable(false); //לאפשר שינוי גודל חלון
        this.setSize(WIDTH, HEIGHT); //הגדרת גודל החלון
        this.setVisible(true); // הגדרת נראות חלון
        this.setLayout(null); //הגדרת פריסה בתוך החחלון
        this.setLocationRelativeTo(null);
        this.setTitle("Brick Breaker");
        openBackgroundMenu();

    }

    public void openBackgroundMenu() {

        this.background = new BackgroundMenu(this);
        this.add(background);
        background.setBounds(0, 0, WIDTH, HEIGHT);
        background.requestFocus();
        if (instractions != null)
            this.remove(instractions);
        if (game != null)
            this.remove(game);
        if (scoreBoard != null)
            this.remove(scoreBoard);
    }

    public void startGame() {
        this.remove(background);
        this.game = new Game();
        this.add(game);
        this.game.setBounds(0, 0, WIDTH, HEIGHT);
        game.requestFocus();

    }

    public void openInstructoins() {
        this.remove(background);
        this.instractions = new Instractions(this);
        this.add(instractions);
        this.instractions.setBounds(0, 0, WIDTH, HEIGHT);
        instractions.requestFocus();

    }

    public void openScoreBoard() {
        this.remove(background);
        this.scoreBoard = new Scoreboard(this);
        this.add(scoreBoard);
        this.scoreBoard.setBounds(0, 0, WIDTH, HEIGHT);
        scoreBoard.requestFocus();

    }
}
