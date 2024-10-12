package AI;


import button.NextEat;
import button.NextMove;
import main.GamePanel;
import piece.ChessMan;
import pair.Pair;

import javax.swing.*;

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

    // Negamax algorithm with Alpha-Beta pruning
    public int negamax(int depth, int alpha, int beta, boolean isMaximizingPlayer, int color) {
        if (depth == 0 || panel.end) {
            EvaluateBoard evaluateBoard = new EvaluateBoard(boardState);
            int score = evaluateBoard.evaluateBoard() * color;
            System.out.println(score);
            return score ;
        }

        int bestValue = Integer.MIN_VALUE;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessMan chessMan = boardState.boardChess[i][j];
                if (chessMan != null && (chessMan.white == isMaximizingPlayer)) { // Check for the current player's pieces
                    chessMan.update();
                    // Handle capturing moves
                    for (Pair <Integer, Integer> eat : chessMan.eats) {
                        int x = eat.first;
                        int y = eat.second;
                        if(chessMan.checkMove(x, y, eat.special2)) {
                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            Move move1 = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x,y), false);
                            System.out.print(chessMan.name + " " + move1.from.first + " " + move1.from.second + " " + move1.to.first + " " + move1.to.second + " ");
                            makeMove(chessMan, move1);
                            int eval = -negamax(depth - 1, -beta, -alpha, !isMaximizingPlayer, -color);
                            undoMove(chessMan, capturedPiece, move1);

                            bestValue = Math.max(bestValue, eval);
                            alpha = Math.max(alpha, eval);
                            if (beta <= alpha) {
                                break;
                            }
                        }
                    }

                    for (Pair <Integer, Integer> move : chessMan.moves) {
                        int x = move.first;
                        int y = move.second;
                        if(chessMan.checkMove(x, y, false)) {
                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            Move move1 = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x,y), false);
                            System.out.print(chessMan.name + " " + move1.from.first + " " + move1.from.second + " " + move1.to.first + " " + move1.to.second + " ");

                            makeMove(chessMan, move1);
                            int eval = -negamax(depth - 1, -beta, -alpha, !isMaximizingPlayer, -color);
                            undoMove(chessMan, capturedPiece, move1);
                            bestValue = Math.max(bestValue, eval);
                            alpha = Math.max(alpha, eval);
                            if (beta <= alpha) {
                                break;
                            }
                        }
                    }


                }
            }
        }
        return bestValue;
    }

    public Move chessAI(int depth) {
        int bestEval = Integer.MIN_VALUE;
        Move bestMove = null;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessMan chessMan = boardState.boardChess[i][j];

                if (chessMan != null && !chessMan.white) { // Check for white pieces
                    chessMan.update();
                    // Handle capturing moves
                    for (Pair <Integer, Integer> eat : chessMan.eats) {
                        int x = eat.first;
                        int y = eat.second;
                        if(chessMan.checkMove(x, y, eat.special2)) {
                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            Move move1 = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x,y), false);
                            System.out.print(chessMan.name + " " + move1.from.first + " " + move1.from.second + " " + move1.to.first + " " + move1.to.second + " ");
                            makeMove(chessMan, move1);

                            int eval = -negamax(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 1);
                            undoMove(chessMan, capturedPiece, move1);

                            if (eval > bestEval) {
                                bestEval = eval;
                                bestMove = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x, y), false);
                            }
                        }
                    }

                    // Iterate through possible moves
                    for (Pair <Integer, Integer> move : chessMan.moves) {
                        int x = move.first;
                        int y = move.second;
                        if(chessMan.checkMove(x,y,false)) {
                            ChessMan capturedPiece = boardState.boardChess[x][y];
                            Move move1 = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x,y), false);
                            System.out.print(chessMan.name + " " + move1.from.first + " " + move1.from.second + " " + move1.to.first + " " + move1.to.second + " ");
                            makeMove(chessMan, move1);
                            int eval = -negamax(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 1);
                            undoMove(chessMan, capturedPiece, move1);
                            if (eval > bestEval) {
                                bestEval = eval;
                                bestMove = new Move(new Pair<>(chessMan.i, chessMan.j), new Pair<>(x, y), false);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Best move found with evaluation: " + bestEval);
        return bestMove;
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
}
