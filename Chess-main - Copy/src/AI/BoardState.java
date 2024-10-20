package AI;

import piece.ChessMan;

public class BoardState implements Cloneable {  // The BoardState class holds the state of the chessboard and allows cloning
    public int[][] board;      // Stores the state of the board (positions of pieces)
    public ChessMan[][] boardChess; // Stores the actual chess pieces in their positions

    // Constructor to initialize the board state
    public BoardState(int[][] board, ChessMan[][] boardChess) {
        this.board = board;        // Initialize the board state
        this.boardChess = boardChess;  // Initialize the chess pieces on the board
    }
}
