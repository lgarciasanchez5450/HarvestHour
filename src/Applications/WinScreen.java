package Applications;

import java.awt.Font;

import lib.Button;
import lib.Label;

public class WinScreen extends Application{
    private final Label winLabel;
    private final Button playAgainButton;
    private final Button quitButton;
    private boolean toPlayAgain;
    private final Font font;
    public WinScreen() {
        super("You Win!", 900, 600);
        screen.setResizable(false);
        screen.setLayout(null);
        font = new Font("Roboto",Font.BOLD,50);
        winLabel = new Label("YOU WIN! YAY!",font);
        winLabel.setOffset(0,-100);
        toPlayAgain = false;
        playAgainButton = new Button("Play Again",100,50,() -> {toPlayAgain = true;quit();});
        playAgainButton.setOffset(0,0);
        quitButton  = new Button("Quit",100,50,this::quit);
        quitButton.setOffset(0,55);

        screen.add(winLabel);
        screen.add(playAgainButton);
        screen.add(quitButton);

    }
    public boolean getPlayAgain() {
        return toPlayAgain;
    }
    protected void onResize() {
        // Recalculate Positions
        winLabel.recalculatePosition();
        playAgainButton.recalculatePosition();
        quitButton.recalculatePosition();

    }
    @Override
    protected void init() {

    }
    @Override
    protected void onQuit() {

    }
}
