package AI;

import button.NextEat;
import button.NextMove;
import main.GamePanel;
import piece.ChessMan;
import pair.Pair;
import piece.King;
import piece.Queen;
import piece.Rook;


public class ChessAI {
    GamePanel panel;
    BoardState boardState;
    int titileSize;

    public ChessAI(GamePanel panel) {
        this.panel = panel;
        titileSize = panel.tileSize;
        boardState = new BoardState(panel.Board, panel.BoardChess);
    }

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
                        chessMan.functionUpdate();

                        // Duyệt qua các nước ăn quân
                        for (Pair <Integer, Integer> eat : chessMan.eats) {
                            int x = eat.first;
                            int y = eat.second;

                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            Move move1 = new Move(new Pair<>(i, j), new Pair<>(x, y), eat.special2, eat.special4, false);

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
                        for (Pair <Integer, Integer> move : chessMan.moves) {
                            int x = move.first;
                            int y = move.second;
                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            Move move1 = new Move(new Pair<>(i, j), new Pair<>(x, y), false, move.special4, move.special3);
                            makeMove(chessMan, move1);
                            int eval = minimax(depth - 1, alpha, beta, false); // Đối thủ bây giờ là người tối thiểu hóa
                            undoMove(chessMan, capturedPiece, move1);
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
                        chessMan.functionUpdate();

                        // Duyệt qua các nước ăn quân
                        for (Pair <Integer, Integer> eat : chessMan.eats) {
                            int x = eat.first;
                            int y = eat.second;

                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            Move move1 = new Move(new Pair<>(i, j), new Pair<>(x, y), eat.special2, eat.special4, false);
                            makeMove(chessMan, move1);
                                int eval = minimax(depth - 1, alpha, beta, true); // Người chơi bây giờ là tối đa hóa

                            undoMove(chessMan, capturedPiece, move1);

                                minEval = Math.min(minEval, eval);
                                beta = Math.min(beta, eval);
                                if (beta <= alpha) { // Cắt tỉa
                                    break;
                                }

                        }

                        // Duyệt qua các nước đi bình thường
                        for (Pair <Integer, Integer> move : chessMan.moves) {
                            int x = move.first;
                            int y = move.second;
                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            Move move1 = new Move(new Pair<>(i, j), new Pair<>(x, y), false, move.special4, move.special3);
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
                    chessMan.functionUpdate();

                    // Duyệt qua các nước ăn quân
                    for (Pair <Integer, Integer> eat : chessMan.eats) {
                        int x = eat.first;
                        int y = eat.second;
                        ChessMan capturedPiece = boardState.boardChess[x][y];
                        Move move1 = new Move(new Pair<>(i, j), new Pair<>(x, y), eat.special2, eat.special4, false);
                        makeMove(chessMan, move1);
                        int eval = minimax(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                        undoMove(chessMan, capturedPiece, move1);
                        if (eval < bestEval) {
                            bestEval = eval;
                            bestMove = new Move(new Pair<>(i, j), new Pair<>(x, y), eat.special2, eat.special4, false);
                        }

                    }

                    // Duyệt qua các nước đi bình thường
                    for (Pair <Integer, Integer> move : chessMan.moves) {
                        int x = move.first;
                        int y = move.second;
                        ChessMan capturedPiece = boardState.boardChess[x][y];
                        Move move1 = new Move(new Pair<>(i, j), new Pair<>(x, y), false, move.special4, move.special3);
                        makeMove(chessMan, move1);
                        int eval = minimax(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                        undoMove(chessMan, capturedPiece, move1);

                        if (eval < bestEval) {
                            bestEval = eval;
                            bestMove = new Move(new Pair<>(i, j), new Pair<>(x, y), false, move.special4, move.special3);
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
        else if(move1.isPromotion) {
            boardState.boardChess[move1.to.first][move1.to.second] = new Queen(panel, chessMan1.x, chessMan1.y, chessMan1.white);
            boardState.board[move1.to.first][move1.to.second] = 900;
        }
        else if (move1.isCastling) {
            boardState.board[move1.to.first][move1.to.second] = chessMan1.value;
            boardState.boardChess[move1.to.first][move1.to.second] = chessMan1;
            if (chessMan1.white && move1.to.second == 2 && boardState.boardChess[7][0] instanceof Rook && chessMan1.moveTurn == 0) {
                ChessMan rook = boardState.boardChess[7][0];
                boardState.boardChess[7][0] = null;
                boardState.board[7][0] = 0;
                boardState.boardChess[7][3] = rook;
                boardState.board[7][3] = rook.value;
            }
            else if (chessMan1.white && move1.to.second == 6 && boardState.boardChess[7][7] instanceof Rook && chessMan1.moveTurn == 0) {
                ChessMan rook = boardState.boardChess[7][7];
                boardState.boardChess[7][7] = null;
                boardState.board[7][7] = 0;
                boardState.boardChess[7][5] = rook;
                boardState.board[7][5] = rook.value;
            }
            else if (!chessMan1.white && move1.to.second == 2 && boardState.boardChess[0][0] instanceof Rook && chessMan1.moveTurn == 0) {
                ChessMan rook = boardState.boardChess[0][0];
                boardState.boardChess[0][0] = null;
                boardState.board[0][0] = 0;
                boardState.boardChess[0][3] = rook;
                boardState.board[0][3] = rook.value;
            }
            else if (!chessMan1.white && move1.to.second == 6 && boardState.boardChess[0][7] instanceof Rook && chessMan1.moveTurn == 0) {
                ChessMan rook = boardState.boardChess[0][7];
                boardState.boardChess[0][7] = null;
                boardState.board[0][7] = 0;
                boardState.boardChess[0][6] = rook;
                boardState.board[0][6] = rook.value;
            }
        }
        else {
            boardState.board[move1.to.first][move1.to.second] = chessMan1.value;
            boardState.boardChess[move1.to.first][move1.to.second] = chessMan1;
        }
        chessMan1.moveTurn ++;
    }

    // Hoàn tác nước đi
    public void undoMove(ChessMan chessMan1, ChessMan chessMan2, Move move1) {
        panel.turn = panel.turn * -1;

        boardState.boardChess[move1.from.first][move1.from.second] = chessMan1;
        boardState.board[move1.from.first][move1.from.second] = chessMan1.value;
        boardState.boardChess[move1.to.first][move1.to.second] = chessMan2;
        boardState.board[move1.to.first][move1.to.second] = (chessMan2 == null) ? 0 : chessMan2.value;
        if(move1.isSpecial) {
            boardState.boardChess[move1.to.first - panel.turn][move1.to.second] = null;
            boardState.board[move1.to.first - panel.turn][move1.to.second] = 0;
            }
        else if (move1.isCastling) {
            if (chessMan1.white && move1.to.second == 2 && boardState.boardChess[7][3] instanceof Rook && chessMan1.moveTurn == 1) {
                ChessMan rook = boardState.boardChess[7][3];
                boardState.boardChess[7][3] = null;
                boardState.board[7][3] = 0;
                boardState.boardChess[7][0] = rook;
                boardState.board[7][0] = rook.value;
            }
            else if (chessMan1.white && move1.to.second == 6 && boardState.boardChess[7][5] instanceof Rook && chessMan1.moveTurn == 1) {
                ChessMan rook = boardState.boardChess[7][5];
                boardState.boardChess[7][5] = null;
                boardState.board[7][5] = 0;
                boardState.boardChess[7][7] = rook;
                boardState.board[7][7] = rook.value;
            }
            else if (!chessMan1.white && move1.to.second == 2 && boardState.boardChess[0][3] instanceof Rook && chessMan1.moveTurn == 1) {
                ChessMan rook = boardState.boardChess[0][3];
                boardState.boardChess[0][3] = null;
                boardState.board[0][3] = 0;
                boardState.boardChess[0][0] = rook;
                boardState.board[0][0] = rook.value;
            }
            else if (!chessMan1.white && move1.to.second == 6 && boardState.boardChess[0][5] instanceof Rook && chessMan1.moveTurn == 1) {
                ChessMan rook = boardState.boardChess[0][5];
                boardState.boardChess[0][5] = null;
                boardState.board[0][5] = 0;
                boardState.boardChess[0][7] = rook;
                boardState.board[0][7] = rook.value;
            }
        }
        if (panel.end) panel.end = false;
        chessMan1.moveTurn --;
    }
}