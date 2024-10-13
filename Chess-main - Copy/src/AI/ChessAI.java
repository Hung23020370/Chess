package AI;

import main.GamePanel;
import piece.ChessMan;
import pair.Pair;
import piece.King;
import piece.Queen;


public class ChessAI {
    GamePanel panel;
    BoardState boardState;
    int titileSize;

    public ChessAI(GamePanel panel) {
        this.panel = panel;
        titileSize = panel.tileSize;
        boardState = new BoardState(panel.Board, panel.BoardChess);
    }

    // Evaluation function: Calculates the score of the board

    // Minimax algorithm with Alpha-Beta pruning
    public int minimax(int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0 || panel.end) {
            EvaluateBoard evaluateBoard = new EvaluateBoard(boardState);
            int score = evaluateBoard.evaluateBoard();
            return score;
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessMan chessMan = boardState.boardChess[i][j];
                    if (chessMan != null && chessMan.white) { // Chỉ xét nước đi của quân trắng
                        chessMan.update();

                        // Duyệt qua các nước ăn quân
                        for (Pair<Integer, Integer> eat : chessMan.eats) {
                            int x = eat.first;
                            int y = eat.second;

                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            if (eat.special4){
                                panel.promotion = true;
                            }
                            Move move1 = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x, y), eat.special2);

                            makeMove(chessMan, move1);
                            int eval = minimax(depth - 1, alpha, beta, false); // Đối thủ bây giờ là người tối thiểu hóa
                            undoMove(chessMan, capturedPiece, move1);

                                maxEval = Math.max(maxEval, eval);
                                alpha = Math.max(alpha, eval);
                                if (beta <= alpha) { // Cắt tỉa
                                    break;
                                }

                        }

                        // Duyệt qua các nước đi bình thường
                        for (Pair<Integer, Integer> move : chessMan.moves) {
                            int x = move.first;
                            int y = move.second;
                                ChessMan capturedPiece = boardState.boardChess[x][y];
                                if (move.special1){
                                    chessMan.check = true;
                                }
                                if (move.special3) {
                                    panel.castling = true;

                                }
                                if (move.special4){
                                    panel.promotion = true;
                                }
                            Move move1 = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x, y), false);

                            makeMove(chessMan, move1);
                                int eval = minimax(depth - 1, alpha, beta, false); // Đối thủ bây giờ là người tối thiểu hóa
                                undoMove(chessMan, capturedPiece, move1);
                            if (move.special1){
                                chessMan.check = false;
                            }
                                maxEval = Math.max(maxEval, eval);
                                alpha = Math.max(alpha, eval);
                                if (beta <= alpha) { // Cắt tỉa
                                    break;
                                }
                        }
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessMan chessMan = boardState.boardChess[i][j];
                    if (chessMan != null && !chessMan.white) { // Chỉ xét nước đi của quân đen
                        chessMan.update();

                        // Duyệt qua các nước ăn quân
                        for (Pair<Integer, Integer> eat : chessMan.eats) {
                            int x = eat.first;
                            int y = eat.second;
                            if (chessMan.checkMove(x, y, eat.special2)) {
                                ChessMan capturedPiece = boardState.boardChess[x][y];
                                Move move1 = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x, y), eat.special2);
                                if (eat.special4){
                                    panel.promotion = true;
                                }
                                makeMove(chessMan, move1);
                                int eval = minimax(depth - 1, alpha, beta, true); // Người chơi bây giờ là tối đa hóa
                                undoMove(chessMan, capturedPiece, move1);

                                minEval = Math.min(minEval, eval);
                                beta = Math.min(beta, eval);
                                if (beta <= alpha) { // Cắt tỉa
                                    break;
                                }
                            }
                        }

                        // Duyệt qua các nước đi bình thường
                        for (Pair<Integer, Integer> move : chessMan.moves) {
                            int x = move.first;
                            int y = move.second;

                                ChessMan capturedPiece = boardState.boardChess[x][y];
                                Move move1 = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x, y), false);
                                if (move.special1){
                                chessMan.check = true;
                                }
                                if (move.special3) {
                                panel.castling = true;
                                }
                                if (move.special4){
                                panel.promotion = true;
                                }
                                makeMove(chessMan, move1);
                                int eval = minimax(depth - 1, alpha, beta, true); // Người chơi bây giờ là tối đa hóa
                                undoMove(chessMan, capturedPiece, move1);
                            if (move.special1){
                                chessMan.check = false;
                            }
                                minEval = Math.min(minEval, eval);
                                beta = Math.min(beta, eval);
                                if (beta <= alpha) { // Cắt tỉa
                                    break;
                                }

                        }
                    }
                }
            }
            return minEval;
        }
    }

    // Hàm tìm nước đi tốt nhất
    public Move chessAI(int depth) {
        int bestEval = Integer.MAX_VALUE;
        Move bestMove = null;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessMan chessMan = boardState.boardChess[i][j];

                if (chessMan != null && !chessMan.white) { // Chỉ xét nước đi của quân đen
                    chessMan.update();

                    // Duyệt qua các nước ăn quân
                    for (Pair<Integer, Integer> eat : chessMan.eats) {
                        int x = eat.first;
                        int y = eat.second;
                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            Move move1 = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x, y), eat.special2);
                        if (eat.special4){
                            panel.promotion = true;
                        }
                            makeMove(chessMan, move1);
                            int eval = minimax(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                            undoMove(chessMan, capturedPiece, move1);

                            if (eval < bestEval) {
                                bestEval = eval;
                                bestMove = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x, y), false);
                            }

                    }

                    // Duyệt qua các nước đi bình thường
                    for (Pair<Integer, Integer> move : chessMan.moves) {
                        int x = move.first;
                        int y = move.second;

                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            Move move1 = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x, y), false);
                        if (move.special1){
                            chessMan.check = false;
                        }
                        if (move.special3) {
                            panel.castling = false;
                        }
                        if (move.special4){
                            panel.promotion = false;
                        }
                            makeMove(chessMan, move1);
                            int eval = minimax(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                            undoMove(chessMan, capturedPiece, move1);
                        if (move.special1){
                            chessMan.check = false;
                        }
                            if (eval < bestEval) {
                                bestEval = eval;
                                bestMove = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x, y), false);
                            }

                    }
                }
            }
        }

        System.out.println("Best move found with evaluation: " + bestEval);
        return bestMove;
    }

    // Thực hiện nước đi
    public void makeMove(ChessMan chessMan1, Move move1) {
        panel.turn = panel.turn * -1;
        boardState.board[move1.from.first][move1.from.second] = 0;
        boardState.boardChess[move1.from.first][move1.from.second] = null;
        if(boardState.boardChess[move1.to.first][move1.to.second] instanceof King) panel.end = true;
        if(move1.isSpecial) {
            boardState.boardChess[move1.to.first][move1.to.second] = null;
            boardState.board[move1.to.first][move1.to.second] = 0;
            boardState.boardChess[move1.to.first + panel.turn][move1.to.second] = chessMan1;
            boardState.board[move1.to.first + panel.turn][move1.to.second] = chessMan1.value;
        }
        else if(panel.promotion) {
            boardState.boardChess[move1.to.first][move1.to.second] = new Queen(panel, chessMan1.x, chessMan1.y, chessMan1.white);
            boardState.board[move1.to.first][move1.to.second] = 900;
        }
        else {
            boardState.board[move1.to.first][move1.to.second] = chessMan1.value;
            boardState.boardChess[move1.to.first][move1.to.second] = chessMan1;
        }
    }

    // Hoàn tác nước đi
    public void undoMove(ChessMan chessMan1, ChessMan chessMan2, Move move1) {
        panel.turn = panel.turn * -1;


        if (panel.end) panel.end = false;
        if (panel.promotion) panel.promotion = false;

        boardState.boardChess[move1.to.first][move1.to.second] = chessMan2;
        if (chessMan2 != null) {
            boardState.board[move1.to.first][move1.to.second] = chessMan2.value;
        } else {
            boardState.board[move1.to.first][move1.to.second] = 0;
        }
        if(move1.isSpecial) {
            boardState.boardChess[move1.to.first + panel.turn][move1.to.second] = chessMan1;
            boardState.board[move1.to.first + panel.turn][move1.to.second] = chessMan1.value;
        }
        boardState.boardChess[move1.from.first][move1.from.second] = chessMan1;
        boardState.board[move1.from.first][move1.from.second] = chessMan1.value;
    }
}
