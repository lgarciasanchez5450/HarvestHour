
import org.w3c.dom.css.Rect;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import Assets.images.ImageLoader;

import lib.Button;
import lib.ImagePanel;
import lib.Label;

import Assets.fonts.FontLoader;

public class Launcher extends Application{
    public boolean readyToLaunchGame;
    private Button startButton;
    private Label gameTitle;
    private Font titleFont;
    private ImagePanel background;
    private Button quitButton;
    Launcher() {
        super("Harvest Hour Launcher",900,600);
        titleFont = new Font("Roboto",Font.BOLD,50);

        screen.setIconImage(ImageLoader.load("CUELLAR.jpg"));
        screen.setResizable(false);
        screen.setLayout(null);

        background = new ImagePanel("titlescreen_background.png");
        background.setBounds(0,0,900,600);


        startButton = new Button("Start",200,50, () -> {readyToLaunchGame = true;quit();});
        startButton.setFont(titleFont.deriveFont(15.0f));
        startButton.setBackground(Color.GRAY);

        quitButton = new Button("Quit",200,50, this::quit);
        quitButton.setOffset(0,55);
        quitButton.setFont(titleFont.deriveFont(15.0f));
        quitButton.setBackground(Color.GRAY);

        gameTitle = new Label("Harvest Hour",titleFont);

        gameTitle.setBackground(new Color (153,217,234,0));

        gameTitle.parentYAnchor = 0.3f;

        screen.add(gameTitle);
        screen.add(quitButton);
        screen.add(startButton);
        screen.add(background);

        onResize();


        //screen.pack();

}
    protected void onResize() {
        // Recalculate Positions
        startButton.recalculatePosition();
        gameTitle.recalculatePosition();
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
