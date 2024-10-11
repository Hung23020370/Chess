package AI;

import button.NextEat;
import button.NextMove;
import main.GamePanel;
import piece.ChessMan;
import pair.Pair;

public class ChessAI {
    GamePanel panel;
    BoardState boardState;
    int titileSize ;

    public ChessAI(GamePanel panel) {
        this.panel = panel;
        titileSize = panel.tileSize;
        boardState = new BoardState(panel.Board, panel.BoardChess);
    }

    // Evaluation function: Calculates the score of the board
    public int evaluateBoard() {
        int totalScore = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessMan chessMan = boardState.boardChess[i][j]; // Thay đổi từ panel.BoardChess
                if (chessMan != null) {
                    totalScore += chessMan.value;
                }
            }
        }

        System.out.println(totalScore);
        return totalScore;
    }

    // Minimax algorithm with Alpha-Beta pruning
    public int minimax(int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0 || panel.end) {
            return evaluateBoard();
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessMan chessMan = boardState.boardChess[i][j]; // Thay đổi từ panel.BoardChess
                    if (chessMan != null && chessMan.white) {
                        chessMan.update();
                        for (Pair<Integer, Integer> move : chessMan.moves) {
                            ChessMan capturedPiece;
                            if (boardState.boardChess[move.first][move.second] != null) {
                                capturedPiece = boardState.boardChess[move.first][move.second].copy();
                            }
                            else capturedPiece = null;
                            Move move1 = new Move(new Pair(chessMan.i, chessMan.j), new Pair(move.first, move.second), false);
                            makeMove(chessMan, move1);
                            if (move.special1) {
                                chessMan.check = true;
                            }
                            int eval = minimax(depth - 1, alpha, beta, false);
                            undoMove(chessMan, capturedPiece, move1);
                            chessMan.check = false;
                            maxEval = Math.max(maxEval, eval);
                            alpha = Math.max(alpha, eval);
                            if (beta <= alpha) {
                                break;
                            }
                        }
                        for (Pair <Integer, Integer> eat : chessMan.eats) {
                            ChessMan capturedPiece;
                            if (boardState.boardChess[eat.first][eat.second] != null) {
                                capturedPiece = boardState.boardChess[eat.first][eat.second].copy();
                            }
                            else capturedPiece = null;
                            Move move1 = new Move(new Pair(chessMan.i, chessMan.j), new Pair(eat.first, eat.second), false);
                            makeMove(chessMan, move1);
                            int eval = minimax(depth - 1, alpha, beta, false);
                            undoMove(chessMan, capturedPiece, move1);
                            maxEval = Math.max(maxEval, eval);
                            alpha = Math.max(alpha, eval);
                            if (beta <= alpha) {
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
                    ChessMan chessMan = boardState.boardChess[i][j]; // Thay đổi từ panel.BoardChess
                    if (chessMan != null && !chessMan.white) {
                        chessMan.update();
                        for (Pair<Integer, Integer> move : chessMan.moves) {
                            ChessMan capturedPiece;
                            if (boardState.boardChess[move.first][move.second] != null) {
                                capturedPiece = boardState.boardChess[move.first][move.second].copy();
                            }
                            else capturedPiece = null;
                            Move move1 = new Move(new Pair(chessMan.i, chessMan.j), new Pair(move.first, move.second), false);
                            makeMove(chessMan, move1);
                            if (move.special1) {
                                chessMan.check = true;
                            }
                            int eval = minimax(depth - 1, alpha, beta, true);
                            undoMove(chessMan, capturedPiece, move1);
                            chessMan.check = false;
                            minEval = Math.min(minEval, eval);
                            beta = Math.min(beta, eval);
                            if (beta <= alpha) {
                                break;
                            }
                        }
                        for (Pair <Integer, Integer> eat : chessMan.eats) {
                            ChessMan capturedPiece;
                            if (boardState.boardChess[eat.first][eat.second] != null) {
                                capturedPiece = boardState.boardChess[eat.first][eat.second].copy();
                            }
                            else capturedPiece = null;
                            Move move1 = new Move(new Pair(chessMan.i, chessMan.j), new Pair(eat.first, eat.second), false);
                            makeMove(chessMan, move1);
                            int eval = minimax(depth - 1, alpha, beta, true);
                            undoMove(chessMan, capturedPiece, move1);
                            minEval = Math.min(minEval, eval);
                            beta = Math.min(beta, eval);
                            if (beta <= alpha) {
                                break;
                            }
                        }
                    }
                }
            }
            return minEval;
        }
    }

    public void makeMove(ChessMan chessMan1, Move move1) {
        boardState.board[move1.from.first][move1.from.second] = 0;
        boardState.boardChess[move1.from.first][move1.from.second] = null;
        boardState.board[move1.to.first][move1.to.second] = chessMan1.value;
        boardState.boardChess[move1.to.first][move1.to.second] = chessMan1;
    }

    public void undoMove(ChessMan chessMan1, ChessMan chessMan2, Move move1) {
        boardState.boardChess[move1.to.first][move1.to.second] = chessMan2;
        if (chessMan2 != null) {
            boardState.board[move1.to.first][move1.to.second] = chessMan2.value;
        } else {
            boardState.board[move1.to.first][move1.to.second] = 0;
        }
        boardState.boardChess[move1.from.first][move1.from.second] = chessMan1;
        boardState.board[move1.from.first][move1.from.second] = chessMan1.value;

    }

    public Move chessAI(int depth) {
        int bestEval = Integer.MIN_VALUE;
        Move bestMove = null;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessMan chessMan = boardState.boardChess[i][j];
                if (chessMan != null && !chessMan.white) { // Kiểm tra không phải null
                    chessMan.update();
                    for (Pair<Integer, Integer> move : chessMan.moves) {
                        ChessMan capturedPiece;
                        if (boardState.boardChess[move.first][move.second] != null) {
                            capturedPiece = boardState.boardChess[move.first][move.second].copy();
                        }
                        else capturedPiece = null;
                        Move move1 = new Move(new Pair(chessMan.i, chessMan.j), new Pair(move.first, move.second), false);
                        makeMove(chessMan, move1);
                        if (move.special1) {
                            chessMan.check = true;
                        }
                        int eval = minimax(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                        undoMove(chessMan, capturedPiece, move1);
                        chessMan.check = false;
                        if (eval > bestEval) {
                            bestEval = eval;
                            bestMove = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(move.first, move.second), false);
                        }
                    }
                    for (Pair <Integer, Integer> eat : chessMan.eats) {
                        ChessMan capturedPiece;
                        if (boardState.boardChess[eat.first][eat.second] != null) {
                            capturedPiece = boardState.boardChess[eat.first][eat.second].copy();
                        }
                        else capturedPiece = null;
                        Move move1 = new Move(new Pair(chessMan.i, chessMan.j), new Pair(eat.first, eat.second), false);
                        makeMove(chessMan, move1);
                        int eval = minimax(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                        undoMove(chessMan, capturedPiece, move1);
                        if (eval > bestEval) {
                            bestEval = eval;
                            bestMove = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(eat.first, eat.second), false);
                        }
                    }
                }
            }
        }
        System.out.println("Nuoc di ngon nhat");
        System.out.println(bestEval);
        System.out.println(bestMove.from.first + " " + bestMove.from.second);
        System.out.println(bestMove.to.first + " " + bestMove.to.second);
        return bestMove;
    }

}
