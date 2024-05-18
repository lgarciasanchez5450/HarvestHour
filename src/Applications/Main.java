package Applications;

public class Main {
    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.run();
        Window screen = new Window("GAME");
        switch (launcher.toLaunch) {
            case GAME -> {
                GameApp game = new GameApp();
                screen.add(game);
                screen.setVisible(true);
                screen.pack();
                game.startThread();
            }
            case MAP_MAKER -> {
                screen.setTitle("Map Maker");
                MapMaker mapMaker = new MapMaker();
                screen.add(mapMaker);
                screen.setVisible(true);
                screen.pack();
                mapMaker.startThread();
            }
        }
        screen.setLocation((1920 - screen.getWidth()) / 2, (1080 - screen.getHeight()) / 2);



    }
}

