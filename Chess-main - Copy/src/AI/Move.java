package AI;

import pair.Pair;

public class Move {
    public Pair<Integer, Integer> from; // The starting position of the piece (row, column)
    public Pair<Integer, Integer> to;   // The ending position of the piece (row, column)
    public boolean isSpecial;    // Indicates if the move is special (e.g., en passant)
    public boolean isPromotion;  // Indicates if the move involves a pawn promotion
    public boolean isCastling;   // Indicates if the move is a castling move

    // Constructor to initialize the move details
    public Move(Pair<Integer, Integer> from, Pair<Integer, Integer> to, boolean isSpecial, boolean isPromotion, boolean isCastling) {
        this.from = from;             // Set the starting position
        this.to = to;                 // Set the ending position
        this.isSpecial = isSpecial;   // Mark whether the move is special
        this.isPromotion = isPromotion; // Mark whether the move is a promotion
        this.isCastling = isCastling;   // Mark whether the move is castling
    }
}
