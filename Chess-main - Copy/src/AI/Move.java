package AI;

import piece.ChessMan;
import pair.Pair;

public class Move {
    public Pair<Integer, Integer> from; // Vị trí bắt đầu
    public Pair<Integer, Integer> to;   // Vị trí kết thúc
    public boolean isSpecial;
    public boolean isPromotion;
    public boolean isCastling;

    public Move(Pair<Integer, Integer> from, Pair<Integer, Integer> to, boolean isSpecial, boolean isPromotion, boolean isCastling) {
        this.from = from;
        this.to = to;
        this.isSpecial = isSpecial;
        this.isPromotion = isPromotion;
        this.isCastling = isCastling;
    }
}
