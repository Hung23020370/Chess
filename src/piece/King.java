package piece;

import main.GamePanel;
import main.Panel;
import pair.Pair;

public class King extends ChessMan{
    public King(GamePanel panel, int x, int y, boolean isWhite) {
        super(panel, x, y, isWhite);
    }

    @Override
    public void setImageName() {
        if(isWhite) this.name = "White_King";
        else this.name = "Black_King";
    }

    @Override
    public void setValue() {
        this.value = 1000;
    }

    @Override
    public void functionUpdate() {
        if (this.x + this.panel.tileSize < 12 * panel.tileSize) {
            moves.add(new Pair<>(this.x + this.panel.tileSize, this.y));
        }
        if(this.y + this.panel.tileSize < 10 * panel.tileSize) {
            moves.add(new Pair<>(this.x, this.y + this.panel.tileSize));
        }
        if(this.x + this.panel.tileSize < 12 * panel.tileSize && this.y + this.panel.tileSize < 10 * panel.tileSize) {
            moves.add(new Pair<>(this.x + this.panel.tileSize, this.y + this.panel.tileSize));
        }
        if(this.x - this.panel.tileSize > 3 * panel.tileSize){
            moves.add(new Pair<>(this.x - this.panel.tileSize, this.y));
        }
        if(this.y - this.panel.tileSize > 1 * panel.tileSize){
            moves.add(new Pair<>(this.x, this.y - this.panel.tileSize));
        }
        if(this.x - this.panel.tileSize > 3 * panel.tileSize && this.y - this.panel.tileSize > 1 * panel.tileSize){
            moves.add(new Pair<>(this.x - this.panel.tileSize, this.y - this.panel.tileSize));
        }
        if(this.x + this.panel.tileSize < 12 * panel.tileSize  && this.y - this.panel.tileSize > 1 * panel.tileSize){
            moves.add(new Pair<>(this.x + this.panel.tileSize, this.y - this.panel.tileSize));
        }
        if(this.x - this.panel.tileSize > 3 * panel.tileSize && this.y + this.panel.tileSize < 10 * panel.tileSize){
            moves.add(new Pair<>(this.x - this.panel.tileSize, this.y + this.panel.tileSize));
        }
    }
}
