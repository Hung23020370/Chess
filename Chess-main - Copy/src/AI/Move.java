package AI;

import piece.ChessMan;
import pair.Pair;

public class Move {
    public Pair<Integer, Integer> from; // Vị trí bắt đầu
    public Pair<Integer, Integer> to;   // Vị trí kết thúc
    public boolean isSpecial;

    public Move(Pair<Integer, Integer> from, Pair<Integer, Integer> to, boolean isSpecial) {
        this.from = from;
        this.to = to;
        this.isSpecial = isSpecial;
    }
}
