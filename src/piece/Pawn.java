package piece;

import main.GamePanel;
import pair.Pair;

public class Pawn extends ChessMan {
    public Pawn(GamePanel panel, int x, int y, boolean isWhite) {
        super(panel, x, y, isWhite);
    }

    @Override
    public void setImageName() {
        if (isWhite) this.name = "White_Pawn";
        else this.name = "Black_Pawn";
    }
    @Override
    public void setValue() {
        this.value = 1;
    }
    @Override
    public void functionUpdate() {
        if (isWhite) {
            if (this.y == 8 * this.panel.tileSize){
                moves.add(new Pair<>(this.x, this.y - this.panel.tileSize));
                moves.add(new Pair<>(this.x, this.y - 2 * this.panel.tileSize));
            }
            else if (this.y > 2 * this.panel.tileSize){
                moves.add(new Pair<>(this.x, this.y - this.panel.tileSize));
            }
        }
        else {
            if (this.y == 3 * this.panel.tileSize){
                moves.add(new Pair<>(this.x, this.y + this.panel.tileSize));
                moves.add(new Pair<>(this.x, this.y + 2 * this.panel.tileSize));
            }
            else if (this.y < 9 * this.panel.tileSize){
                moves.add(new Pair<>(this.x, this.y + this.panel.tileSize));
            }
        }
    }
}