package button;

import main.GamePanel;
import main.Panel;

public class PieceButton extends Button{
    String name;

    public PieceButton(Panel panel, int x, int y, int width, int height, String name) {
        super(panel, x, y, width, height);
        this.name = name;
    }

    @Override
    public void setImageName(){
        this.name1 = name;
        this.name2 = name;
    }

    @Override
    public void functionUpdate(){
        if(button) {
            panel.frame.update();
            button = false;
        }
    }
}