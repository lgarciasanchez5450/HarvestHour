package Applications;

import java.awt.Font;

import lib.Button;
import lib.Label;

public class LoseScreen extends Application{
    private final Label loseLabel;
    private final Button playAgainButton;
    private final Button quitButton;
    private boolean toPlayAgain;
    private final Font font;
    public LoseScreen() {
        super("You Lose! >:) L", 900, 600);
        screen.setResizable(false);
        screen.setLayout(null);
        font = new Font("Roboto",Font.BOLD,50);
        loseLabel = new Label("YOUR BAD!",font);
        loseLabel.setOffset(0,-100);
        toPlayAgain = false;
        playAgainButton = new Button("Play Again, i guess",200,50,() -> {toPlayAgain = true;quit();});
        playAgainButton.setOffset(0,0);
        quitButton  = new Button("Leave Forever",100,50,this::quit);
        quitButton.setOffset(0,55);

        screen.add(loseLabel);
        screen.add(playAgainButton);
        screen.add(quitButton);

    }
    public boolean getPlayAgain() {
        return toPlayAgain;
    }
    protected void onResize() {
        // Recalculate Positions
        loseLabel.recalculatePosition();
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
