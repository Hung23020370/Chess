package AI;

import piece.ChessMan;

import java.util.ArrayList;

public class BoardState {
    public int[][] board; // Lưu trạng thái bàn cờ
    public ChessMan [][] boardChess ;

    public BoardState(int[][] board, ChessMan[][] boardChess) {
        this.board = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = board[i].clone(); // Sao chép từng hàng
        }

        this.boardChess = new ChessMan[boardChess.length][];
        for (int i = 0; i < boardChess.length; i++) {
            this.boardChess[i] = boardChess[i].clone(); // Sao chép từng hàng
        }
    }

}
