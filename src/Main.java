import Applications.GameApp;
import Applications.Launcher;
import Applications.LoseScreen;
import Applications.MapMaker;
import Applications.WinScreen;
import Applications.Window;
import GameEngine.MapGenerator;

public class Main {
    public static void main(String[] args) {
        boolean running = true;
        do {
            Launcher launcher = new Launcher();
            launcher.run();
            Window screen = new Window("GAME");
            switch (launcher.toLaunch) {
                case GAME -> {
                    MapGenerator.loadMap("main");
                    GameApp game = new GameApp();
                    screen.add(game);
                    screen.setVisible(true);
                    screen.pack();
                    game.startThread();
                    screen.setLocation((1920 - screen.getWidth()) / 2, (1080 - screen.getHeight()) / 2);
                    while (!game.isDone()) {
                        try {
                            Thread.sleep(15);
                        } catch (InterruptedException ignored) {
                        }
                    }
                    screen.dispose();
                    if (game.isWon()) {
                        WinScreen win = new WinScreen();
                        win.run();
                        if (!win.getPlayAgain()) {
                            running = false;
                        }
                    } else {
                        LoseScreen los = new LoseScreen();
                        los.run();
                        if (!los.getPlayAgain()) {
                            running = false;
                        }
                    }

                    System.out.println(game.isWon());
                }
                case MAP_MAKER -> {
                    screen.setTitle("Map Maker");

                    MapMaker mapMaker = new MapMaker();
                    screen.add(mapMaker);
                    screen.setVisible(true);
                    screen.pack();
                    mapMaker.startThread();
                    screen.setLocation((1920 - screen.getWidth()) / 2, (1080 - screen.getHeight()) / 2);

                }
                default -> running = false;
            }
        } while (running);

    }
}

