package GameEngine.Entities;


import static GameEngine.GameConstants.LOAN_SHARK_NEED_TO_WIN;
import static GameEngine.GameConstants.loanSharkTag;

import Assets.images.ImageLoader;
import GameEngine.Animation;

public class LoanShark extends Living {
    private int weetLeft = LOAN_SHARK_NEED_TO_WIN;
    public LoanShark(float x, float y) {
        super(loanSharkTag,x, y, 0.8f,1.0f);
        currentAnimation = new Animation(ImageLoader.loadFolder("src/Assets/images/LoanShark",40*2,2*68),5);
        setAnchorY(1);
        setAnchorX(0.25f);
    }
    public void acceptWeet(int x) {
        System.out.println("ACCEPTING :" + x + " amount of weet");
        weetLeft -= x;
        System.out.println( x + " amount needed left");

    }
    public boolean isHappy() {
        return weetLeft <= 0;
    }

}
