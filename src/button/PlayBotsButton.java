package button;

import main.Panel;

public class PlayBotsButton extends Button{

    public PlayBotsButton(Panel panel, int x, int y, int width, int height) {
        super(panel, x, y, width, height);
    }

    @Override
    public void setImageName(){
        this.name1 = "PlayBotButton1";
        this.name2 = "PlayBotButton2";
    }

    @Override
    public void functionUpdate(){
        if(button && panel.frame.select_mode) {
            panel.frame.update();
            button = false;
            panel.frame.select_pieces = true;
        }
    }
}