package piece;

import main.GamePanel;
import pair.Pair;

public class Bishop extends ChessMan {
    public Bishop(GamePanel panel, int x, int y, boolean isWhile) {
        super(panel, x, y, isWhile);
    }

    @Override
    public void setImageName() {
        if (isWhite) this.name = "White_Bishop";
        else this.name = "Black_Bishop";
    }
    @Override
    public void setValue() {
        this.value = 3;
    }

    @Override
    public void functionUpdate() {
        for (int i = 1; this.x + i * this.panel.tileSize < 12 * panel.tileSize && this.y + i * this.panel.tileSize < 10 * panel.tileSize; ++i) {
            moves.add(new Pair<>(this.x + i * this.panel.tileSize, this.y + i * this.panel.tileSize));
        }
        for (int i = 1; this.x - i * this.panel.tileSize > 3 * panel.tileSize && this.y - i * this.panel.tileSize > 1 * panel.tileSize; ++i) {
            moves.add(new Pair<>(this.x - i * this.panel.tileSize, this.y - i * this.panel.tileSize));
        }
        for (int i = 1; this.x + i * this.panel.tileSize < 12 * panel.tileSize && this.y - i * this.panel.tileSize > 1 * panel.tileSize; ++i) {
            moves.add(new Pair<>(this.x + i * this.panel.tileSize, this.y - i * this.panel.tileSize));
        }
        for (int i = 1; this.x - i * this.panel.tileSize > 3 * panel.tileSize && this.y + i * this.panel.tileSize < 10 * panel.tileSize; ++i) {
            moves.add(new Pair<>(this.x - i * this.panel.tileSize, this.y + i * this.panel.tileSize));
        }
    }
}
