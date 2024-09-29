package button;

import main.Panel;

public class PlayPeopleButton extends Button{

    public PlayPeopleButton(Panel panel, int x, int y, int width, int height) {
        super(panel, x, y, width, height);
    }

    @Override
    public void setImageName(){
        this.name1 = "PlayPeopleButton1";
        this.name2 = "PlayPeopleButton2";
    }

    @Override
    public void functionUpdate(){
        if(button && panel.frame.select_mode) {
            panel.frame.update();
            panel.frame.select_pieces = true;
            button = false;
        }
    }
}