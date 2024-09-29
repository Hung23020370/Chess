package button;

import main.Panel;

public class PlayWhiteButton extends Button{

    public PlayWhiteButton(Panel panel, int x, int y, int width, int height) {
        super(panel, x, y, width, height);
    }

    @Override
    public void setImageName(){
        this.name1 = "White_Pawn";
        this.name2 = "White_Pawn";
    }

    @Override
    public void functionUpdate(){
        if(button && panel.frame.select_mode && panel.frame.select_pieces) {
            panel.frame.select_white = true;
            panel.frame.update();
            button = false;
            panel.frame.gameState = 2;
        }
    }
}