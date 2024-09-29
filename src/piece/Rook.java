package piece;

import main.GamePanel;
import pair.Pair;

public class Rook extends ChessMan {
    public Rook(GamePanel panel, int x, int y, boolean isWhite) {
        super(panel, x, y, isWhite);
    }

    @Override
    public void setImageName() {
        if (isWhite) this.name = "White_Rook";
        else this.name = "Black_Rook";
    }
    @Override
    public void setValue() {
        this.value = 5;
    }

    @Override
    public void functionUpdate() {
        for (int i = 1; this.x + i * this.panel.tileSize < 12 * panel.tileSize; i++) {
            moves.add(new Pair<>(this.x + i * this.panel.tileSize, this.y));
        }
        for(int i = 1; this.y + i * this.panel.tileSize < 10 * panel.tileSize; ++i) {
            moves.add(new Pair<>(this.x, this.y + i * this.panel.tileSize));
        }
        for(int i = 1; this.x - i * this.panel.tileSize > 3 * panel.tileSize; ++i){
            moves.add(new Pair<>(this.x - i * this.panel.tileSize, this.y));
        }
        for(int i = 1; this.y - i * this.panel.tileSize > 1 * panel.tileSize; ++i){
            moves.add(new Pair<>(this.x, this.y - i * this.panel.tileSize));
        }

    }
}
