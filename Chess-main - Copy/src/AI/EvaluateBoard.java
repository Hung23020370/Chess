package AI;

import piece.*;

public class EvaluateBoard {
    public BoardState boardStates; // The current state of the chessboard

    // Position evaluation table for pawns
    private static final int[] PAWN_TABLE =
            {0,  0,  0,  0,  0,  0,  0,  0,
                    50, 50, 50, 50, 50, 50, 50, 50,
                    10, 10, 20, 30, 30, 20, 10, 10,
                    5,  5, 10, 25, 25, 10,  5,  5,
                    0,  0,  0, 20, 20,  0,  0,  0,
                    5, -5,-10,  0,  0,-10, -5,  5,
                    5, 10, 10,-20,-20, 10, 10,  5,
                    0,  0,  0,  0,  0,  0,  0,  0};

    // Position evaluation table for knights
    private static final int[] KNIGHT_TABLE =
            {-50,-40,-30,-30,-30,-30,-40,-50,
                    -40,-20,  0,  0,  0,  0,-20,-40,
                    -30,  0, 10, 15, 15, 10,  0,-30,
                    -30,  5, 15, 20, 20, 15,  5,-30,
                    -30,  0, 15, 20, 20, 15,  0,-30,
                    -30,  5, 10, 15, 15, 10,  5,-30,
                    -40,-20,  0,  5,  5,  0,-20,-40,
                    -50,-40,-30,-30,-30,-30,-40,-50};

    // Position evaluation table for bishops
    private static final int[] BISHOP_TABLE =
            {-20,-10,-10,-10,-10,-10,-10,-20,
                    -10,  0,  0,  0,  0,  0,  0,-10,
                    -10,  0,  5, 10, 10,  5,  0,-10,
                    -10,  5,  5, 10, 10,  5,  5,-10,
                    -10,  0, 10, 10, 10, 10,  0,-10,
                    -10, 10, 10, 10, 10, 10, 10,-10,
                    -10,  5,  0,  0,  0,  0,  5,-10,
                    -20,-10,-10,-10,-10,-10,-10,-20};

    // Position evaluation table for rooks
    private static final int[] ROOK_TABLE =
            {0,  0,  0,  0,  0,  0,  0,  0,
                    5, 10, 10, 10, 10, 10, 10,  5,
                    -5,  0,  0,  0,  0,  0,  0, -5,
                    -5,  0,  0,  0,  0,  0,  0, -5,
                    -5,  0,  0,  0,  0,  0,  0, -5,
                    -5,  0,  0,  0,  0,  0,  0, -5,
                    -5,  0,  0,  0,  0,  0,  0, -5,
                    0,  0,  0,  5,  5,  0,  0,  0};

    // Position evaluation table for queens
    private static final int[] QUEEN_TABLE =
            {-20,-10,-10, -5, -5,-10,-10,-20,
                    -10,  0,  0,  0,  0,  0,  0,-10,
                    -10,  0,  5,  5,  5,  5,  0,-10,
                    -5,  0,  5,  5,  5,  5,  0, -5,
                    0,  0,  5,  5,  5,  5,  0, -5,
                    -10,  5,  5,  5,  5,  5,  0,-10,
                    -10,  0,  5,  0,  0,  0,  0,-10,
                    -20,-10,-10, -5, -5,-10,-10,-20};

    // Position evaluation table for kings in the early game
    private static final int[] KING_TABLE_EARLY =
            {-30,-40,-40,-50,-50,-40,-40,-30,
                    -30,-40,-40,-50,-50,-40,-40,-30,
                    -30,-40,-40,-50,-50,-40,-40,-30,
                    -30,-40,-40,-50,-50,-40,-40,-30,
                    -20,-30,-30,-40,-40,-30,-30,-20,
                    -10,-20,-20,-20,-20,-20,-20,-10,
                    20, 20,  0,  0,  0,  0, 20, 20,
                    20, 30, 10,  0,  0, 10, 30, 20};

    // Constructor that takes the current state of the chessboard
    public EvaluateBoard (BoardState boardStates) {
        this.boardStates = boardStates;
    }

    // Function to evaluate the chessboard and return a score
    public int evaluateBoard() {
        int totalScore = 0;

        // Iterate through each square on the chessboard
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessMan piece = boardStates.boardChess[row][col]; // Access the chess piece at the current position
                if (piece != null) {
                    int piecePosition = row * 8 + col; // Convert 2D coordinates to a 1D index

                    // Add or subtract the positional value for each piece based on its type and color
                    if (piece instanceof Pawn) {
                        totalScore += piece.white ? piece.value + PAWN_TABLE[piecePosition] :
                                piece.value - PAWN_TABLE[63 - piecePosition];
                    }
                    else if(piece instanceof Knight) {
                        totalScore += piece.white ? piece.value + KNIGHT_TABLE[piecePosition] :
                                piece.value - KNIGHT_TABLE[63 - piecePosition];
                    }
                    else if (piece instanceof Bishop) {
                        totalScore += piece.white ? piece.value + BISHOP_TABLE[piecePosition] :
                                piece.value - BISHOP_TABLE[63 - piecePosition];
                    }
                    else if (piece instanceof Rook) {
                        totalScore += piece.white ? piece.value + ROOK_TABLE[piecePosition] :
                                piece.value - ROOK_TABLE[63 - piecePosition];
                    }
                    else if (piece instanceof Queen) {
                        totalScore += piece.white ? piece.value + QUEEN_TABLE[piecePosition] :
                                piece.value - QUEEN_TABLE[63 - piecePosition];
                    }
                    else if (piece instanceof King) {
                        totalScore += piece.white ? piece.value + KING_TABLE_EARLY[piecePosition] :
                                piece.value - KING_TABLE_EARLY[63 - piecePosition];
                    }
                    else {
                        totalScore += piece.value; // Add the basic value of the piece if no specific table exists
                    }

                }
            }
        }

        return totalScore; // Return the total score of the board
    }
}
