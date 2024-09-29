package button;

import main.GamePanel;
import main.Panel;

public class PlayBlackButton extends Button{

    public PlayBlackButton(Panel panel, int x, int y, int width, int height) {
        super(panel, x, y, width, height);
    }

    @Override
    public void setImageName(){
        this.name1 = "Black_Pawn";
        this.name2 = "Black_Pawn";
    }

    @Override
    public void functionUpdate(){
        if(button && panel.frame.select_mode && panel.frame.select_pieces) {
            panel.frame.select_white = false;
            panel.frame.update();
            button = false;
            panel.frame.gameState = 2;
        }
    }
}