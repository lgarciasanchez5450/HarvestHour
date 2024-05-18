package Applications;

import java.awt.Color;
import java.awt.Font;

import Assets.images.ImageLoader;

import lib.Button;
import lib.ImagePanel;
import lib.Label;

public class Launcher extends Application{
    public enum ReadyToLaunch {
        NONE,GAME,MAP_MAKER
    }
    public ReadyToLaunch toLaunch = ReadyToLaunch.NONE;
    private Button startButton;
    private Label gameTitle;
    private Font titleFont;
    private ImagePanel background;
    private Button quitButton;
    private Button mapMakerButton;
    Launcher() {
        super("Harvest Hour Applications.Launcher",900,600);
        titleFont = new Font("Roboto",Font.BOLD,50);

        screen.setIconImage(ImageLoader.load("CUELLAR.jpg"));
        screen.setResizable(false);
        screen.setLayout(null);

        background = new ImagePanel("titlescreen_background.png");
        background.setBounds(0,0,900,600);


        startButton = new Button("Start",200,50, () -> {toLaunch = ReadyToLaunch.GAME;quit();});
        startButton.setFont(titleFont.deriveFont(15.0f));
        startButton.setBackground(Color.GRAY);

        quitButton = new Button("Quit",200,50, this::quit);
        quitButton.setOffset(0,110);
        quitButton.setFont(titleFont.deriveFont(15.0f));
        quitButton.setBackground(Color.GRAY);


        mapMakerButton = new Button("Map Maker",200,50, () -> {toLaunch = ReadyToLaunch.MAP_MAKER;quit();});
        mapMakerButton.setOffset(0,55);
        mapMakerButton.setFont(titleFont.deriveFont(15.0f));
        mapMakerButton.setBackground(Color.GRAY);


        gameTitle = new Label("Harvest Hour",titleFont);

        gameTitle.setBackground(new Color (153,217,234,0));

        gameTitle.parentYAnchor = 0.3f;

        screen.add(gameTitle);
        screen.add(quitButton);
        screen.add(startButton);
        screen.add(mapMakerButton);
        screen.add(background);

        onResize();


        //screen.pack();

}
    protected void onResize() {
        // Recalculate Positions
        startButton.recalculatePosition();
        gameTitle.recalculatePosition();
        mapMakerButton.recalculatePosition();

        quitButton.recalculatePosition();

    }
    @Override
    protected void init() {
    }

    @Override
    protected void loop() {


        //System.out.print(startButton.getX());
        //System.out.println(", " + startButton.getY());
        //background.paint(graphics);
    }

    @Override
    protected void onQuit() {

    }
}
