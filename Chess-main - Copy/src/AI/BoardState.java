package AI;

import piece.ChessMan;

import java.util.ArrayList;

public class BoardState {
    public int[][] board; // Lưu trạng thái bàn cờ
    public ChessMan [][] boardChess ;

    public BoardState(int[][] board, ChessMan[][] boardChess) {
        this.board = board;

        this.boardChess = boardChess;
    }

}
