package piece;

import main.GamePanel;
import pair.Pair;

public class Pawn extends ChessMan{
    public Pawn(GamePanel panel, int x, int y, boolean white) {
        super(panel, x, y, white);
    }

    @Override
    public void setValue() {
        this.value = 100;
    }

    @Override
    public void setImageName() {
        this.name = "Pawn";
    }

    @Override
    public void functionUpdate() {
        if (white) {
            if (this.moveTurn == 0) {
                if (panel.Board[i - 2][j] == 0 && panel.Board[i - 1][j] == 0) {
                    Pair<Integer,Integer> pair = new Pair<>(this.i - 2, this.j);
                    pair.special1 = true;
                    moves.add(pair);
                }
            }
            if (this.i - 1 > -1) {
                if (panel.Board[i - 1][j] == 0) {
                    Pair<Integer, Integer> pair = new Pair<>(this.i - 1, this.j);
                    if (i - 1 == 0){
                        pair.special4 = true;
                    }
                    moves.add(pair);
                }
            }

            if(this.j - 1 > -1){
                if (panel.Board[i][j - 1] == -1){
                    if (panel.blackPawn[j - 1].check){
                        Pair<Integer,Integer> pair = new Pair<>(this.i, this.j - 1);
                        pair.special2 = true;
                        eats.add(pair);
                    }
                }
            }
            if(this.j + 1 < 8){
                if (panel.Board[i][j + 1] == -1){
                    if (panel.blackPawn[j + 1].check){
                        Pair<Integer,Integer> pair = new Pair<>(this.i, this.j + 1);
                        pair.special2 = true;
                        eats.add(pair);
                    }
                }
            }

            if (this.i - 1 > -1 && this.j - 1 > -1) {
                if (panel.Board[i - 1][j - 1] * value < 0) {
                    Pair<Integer, Integer> pair = new Pair<>(this.i - 1, this.j - 1);
                    if (i - 1 == 0){
                        pair.special4 = true;
                    }
                    eats.add(pair);
                }
            }
            if (this.i - 1 > -1 && this.j + 1 < 8) {
                if (panel.Board[i - 1][j + 1] * value < 0) {
                    Pair<Integer, Integer> pair = new Pair<>(this.i - 1, this.j + 1);
                    if (i - 1 == 0){
                        pair.special4 = true;
                    }
                    eats.add(pair);
                }
            }
        }
        else{
            if (this.moveTurn == 0) {
                if (panel.Board[i + 2][j] == 0  && panel.Board[i + 1][j] == 0) {
                    Pair<Integer,Integer> pair = new Pair<>(this.i + 2, this.j);
                    pair.special1 = true;
                    moves.add(pair);
                }
            }
            if (this.i + 1 < 8) {
                if (panel.Board[i + 1][j] == 0) {
                    Pair<Integer, Integer> pair = new Pair<>(this.i + 1, this.j);
                    if (i + 1 == 7){
                        pair.special4 = true;
                    }
                    moves.add(pair);
                }
            }

            if(this.j - 1 > -1){
                if (panel.Board[i][j - 1] == 1){
                    if (panel.whitePawn[j - 1].check){
                        Pair<Integer,Integer> pair = new Pair<>(this.i, this.j - 1);
                        pair.special2 = true;
                        eats.add(pair);
                    }
                }
            }
            if(this.j + 1 < 8){
                if (panel.Board[i][j + 1] == 1){
                    if (panel.whitePawn[j + 1].check){
                        Pair<Integer,Integer> pair = new Pair<>(this.i, this.j + 1);
                        pair.special2 = true;
                        eats.add(pair);
                    }
                }
            }

            if (this.i + 1 < 8 && this.j - 1 > -1) {
                if (panel.Board[i + 1][j - 1] * value < 0) {
                    Pair<Integer, Integer> pair = new Pair<>(this.i + 1, this.j - 1);
                    if (i + 1 == 7){
                        pair.special4 = true;
                    }
                    eats.add(pair);
                }
            }
            if (this.i + 1 < 8 && this.j + 1 < 8) {
                if (panel.Board[i + 1][j + 1] * value < 0) {
                    Pair<Integer, Integer> pair = new Pair<>(this.i + 1, this.j + 1);
                    if (i + 1 == 7){
                        pair.special4 = true;
                    }
                    eats.add(pair);
                }
            }
        }
        if (panel.promotion){
            if (panel.turn == 1){
                for (int a = 0; a < 4; a++){
                    promotionButtons[a].update();
                    if (promotionButtons[a].button && this.i == 0){
                        this.alive = false;
                        panel.promotion = false;
                        promotionButtons[a].button = false;
                        if (a == 0) {
                            panel.Board[this.i][this.j] = 9;
                            panel.chessMans.add(new Queen(panel, this.x, this.y, true));
                            panel.BoardChess[this.i][this.j] = new Queen(panel, this.x, this.y, true); // Thay thế bằng quân Hậu
                        }
                        else if (a == 1){
                            panel.Board[this.i][this.j] = 5;
                            panel.chessMans.add(new Rook(panel, this.x, this.y, true));
                            panel.BoardChess[this.i][this.j] = new Rook(panel, this.x, this.y, true); // Thay thế bằng quân Xe

                        }
                        else if (a == 2){
                            panel.Board[this.i][this.j] = 3;
                            panel.chessMans.add(new Knight(panel, this.x, this.y, true));
                            panel.BoardChess[this.i][this.j] = new Knight(panel, this.x, this.y, true); // Thay thế bằng quân Mã

                        }
                        else if (a == 3){
                            panel.Board[this.i][this.j] = 3;
                            panel.chessMans.add(new Bishop(panel, this.x, this.y, true));
                            panel.BoardChess[this.i][this.j] = new Bishop(panel, this.x, this.y, true); // Thay thế bằng quân Tượng

                        }
                        panel.turn = panel.turn * -1;
                    }
                }
            }
            else if (panel.turn == -1){
                for (int a = 4; a < 8; a++){
                    promotionButtons[a].update();
                    if (promotionButtons[a].button && this.i == 7){
                        this.alive = false;
                        panel.promotion = false;
                        promotionButtons[a].button = false;
                        if (a == 4) {
                            panel.Board[this.i][this.j] = -9;
                            panel.chessMans.add(new Queen(panel, this.x, this.y, false));
                            panel.BoardChess[this.i][this.j] = new Queen(panel, this.x, this.y, false); // Thay thế bằng quân Hậu
                        }
                        else if (a == 5){
                            panel.Board[this.i][this.j] = -5;
                            panel.chessMans.add(new Rook(panel, this.x, this.y, false));
                            panel.BoardChess[this.i][this.j] = new Rook(panel, this.x, this.y, false); // Thay thế bằng quân Xe

                        }
                        else if (a == 6){
                            panel.Board[this.i][this.j] = -3;
                            panel.chessMans.add(new Knight(panel, this.x, this.y, false));
                            panel.BoardChess[this.i][this.j] = new Knight(panel, this.x, this.y, false); // Thay thế bằng quân Mã

                        }
                        else if (a == 7){
                            panel.Board[this.i][this.j] = -3;
                            panel.chessMans.add(new Bishop(panel, this.x, this.y, false));
                            panel.BoardChess[this.i][this.j] = new Bishop(panel, this.x, this.y, false); // Thay thế bằng quân Tượng

                        }
                        panel.turn = panel.turn * -1;
                    }
                }
            }
        }
    }
}
