package GameEngine.Entities;


import static GameEngine.GameConstants.loanSharkTag;

import Assets.images.ImageLoader;
import GameEngine.Animation;

public class LoanShark extends Living {
    public LoanShark(float x, float y) {
        super(loanSharkTag,x, y, 0.8f,1.0f);
        currentAnimation = new Animation(ImageLoader.loadFolder("src/Assets/images/LoanShark",40*2,2*68),5);
        setAnchorY(1);
        setAnchorX(0.5f);
    }
}
