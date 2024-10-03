package piece;

import main.GamePanel;
import pair.Pair;

import javax.swing.*;

public class Pawn extends ChessMan {
    public boolean promotionRequired = false;
    public Pawn(GamePanel panel, int x, int y, boolean white) {
        super(panel, x, y, white);
    }

    @Override
    public void setValue() {
        this.value = 1;
    }

    @Override
    public void setImageName() {
        this.name = "Pawn";
    }

    @Override
    public void functionUpdate() {
        if ((white && panel.frame.select_white) || (!white && !panel.frame.select_white)) {
            if (this.firstMove) {
                if (panel.Board[i - 2][j] == 0 && panel.Board[i - 1][j] == 0) {
                    moves.add(new Pair<>(this.i - 2, this.j));
                }
            }
            if (this.i - 1 > -1) {
                if (panel.Board[i - 1][j] == 0) {
                    moves.add(new Pair<>(this.i - 1, this.j));
                }
            }
            if (this.i - 1 > -1 && this.j - 1 > -1) {
                if (panel.Board[i - 1][j - 1] * value < 0) {
                    eats.add(new Pair<>(this.i - 1, this.j - 1));
                }
            }
            if (this.i - 1 > -1 && this.j + 1 < 8) {
                if (panel.Board[i - 1][j + 1] * value < 0) {
                    eats.add(new Pair<>(this.i - 1, this.j + 1));
                }
            }
        } else if ((!white && panel.frame.select_white) || (white && !panel.frame.select_white)) {
            {
                if (this.firstMove) {
                    if (panel.Board[i + 2][j] == 0 && panel.Board[i + 1][j] == 0) {
                        moves.add(new Pair<>(this.i + 2, this.j));
                    }
                }
                if (this.i + 1 < 8) {
                    if (panel.Board[i + 1][j] == 0) {
                        moves.add(new Pair<>(this.i + 1, this.j));
                    }
                }
                if (this.i + 1 < 8 && this.j - 1 > -1) {
                    if (panel.Board[i + 1][j - 1] * value < 0) {
                        eats.add(new Pair<>(this.i + 1, this.j - 1));
                    }
                }
                if (this.i + 1 < 8 && this.j + 1 < 8) {
                    if (panel.Board[i + 1][j + 1] * value < 0) {
                        eats.add(new Pair<>(this.i + 1, this.j + 1));
                    }
                }
            }
        }
    }
    public ChessMan promotePawn() {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};

        int choice = JOptionPane.showOptionDialog(panel.frame,
                "Chọn quân phong:",
                "Thăng cấp quân tốt",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
        ChessMan newPiece = null;
        if (choice >= 0) {
            switch (choice) {
                case 0:
                    newPiece = new Queen(panel, this.x, this.y, white);
                    break;
                case 1:
                    newPiece = new Rook(panel, this.x, this.y, white);
                    break;
                case 2:
                    newPiece = new Bishop(panel, this.x, this.y, white);
                    break;
                case 3:
                    newPiece = new Knight(panel, this.x, this.y, white);
                    break;
            }
        }
        Pawn.this.alive = false;
        assert newPiece != null;
        panel.Board[i][j] = newPiece.value;
        return newPiece;
    }

}
