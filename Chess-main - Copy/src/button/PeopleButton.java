package button;

import main.Panel;

public class PeopleButton extends Button{

    public PeopleButton(Panel panel, int x, int y, int width, int height) {
        super(panel, x, y, width, height);
    }

    @Override
    public void setImageName(){
        this.name1 = "PeopleButton1";
        this.name2 = "PeopleButton2";
    }

    @Override
    public void functionUpdate(){
        if(button && panel.frame.select_mode) {
            panel.frame.gameState = 2;
            panel.frame.select_mode = false;
            panel.frame.modeChessAI = false;
            panel.frame.update();
            button = false;
        }
    }
}