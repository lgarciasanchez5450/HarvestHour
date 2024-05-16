package Applications;

public class Main {
    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.run();
        if (!launcher.readyToLaunchGame) return;
        Window screen = new Window("GAME");
        GameApp game = new GameApp();
        screen.add(game);
        screen.setVisible(true);
        screen.pack();
        game.startThread();
        screen.setLocation((1920 - screen.getWidth())/2,(1080-screen.getHeight())/2);

    }
}

