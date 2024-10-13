package main;

import AI.ChessAI;
import AI.Move;
import button.MenuButton;
import piece.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends Panel {
    MenuButton menuButton;
    BufferedImage board, queue;
    double boardScale = 1;
    public int score;
    public boolean end;
    public boolean moving;
    public boolean castling;
    public boolean promotion;
    public int [][] Board = new int[8][8];
    public ChessMan [][] BoardChess = new ChessMan[8][8];

    public MouseHandle [][] mouseHandles = new MouseHandle[8][8];

    public King whiteKing;
    public King blackKing;
    Knight whiteKnight1;
    Knight whiteKnight2;
    Knight blackKnight1;
    Knight blackKnight2;
    public Pawn [] whitePawn = new Pawn[8];
    public Pawn [] blackPawn = new Pawn[8];
    public Rook whiteRook1;
    public Rook whiteRook2;
    public Rook blackRook1;
    public Rook blackRook2;
    Bishop whiteBishop1;
    Bishop whiteBishop2;
    Bishop blackBishop1;
    Bishop blackBishop2;
    Queen whiteQueen;
    Queen blackQueen;
    Sound sound = new Sound();
    public boolean runAI = false;
    public ArrayList<ChessMan> chessMans;
    public ArrayList<ChessMan> removeWhite;
    public ArrayList<ChessMan> removeBlack;


    public int turn;

    public GamePanel(Frame frame) {
        super(frame);
        this.menuButton = new MenuButton(this,0,0,2 * tileSize,tileSize);
        this.setBackground(Color.BLACK);
        this.turn = 1;
        this.score = 0;
        this.end = false;
        this.moving = false;
        this.castling = false;
        this.promotion = false;

        for (int i = 0; i < 8; i++ ) {
            for (int j = 0; j < 8; j++) {
                mouseHandles[i][j] = new MouseHandle((j + 4) * tileSize, (i + 2) * tileSize, tileSize, tileSize);
                this.addMouseListener(mouseHandles[i][j]);
                this.addMouseMotionListener(mouseHandles[i][j]);
            }
        }
        chessMans = new ArrayList<>();
        removeWhite = new ArrayList<>();
        removeBlack = new ArrayList<>();

        this.whiteKing = new King(this, 8 * tileSize,  9 * tileSize, true);
        chessMans.add(whiteKing);
        for (int i = 0; i< 8; i++) {
            whitePawn[i] = new Pawn(this, (i + 4) * tileSize, 8 * tileSize, true);
            chessMans.add(whitePawn[i]);
        }
        this.whiteRook1 = new Rook(this, 4 * tileSize, 9 * tileSize, true);
        chessMans.add(whiteRook1);
        this.whiteRook2 = new Rook(this, 11 * tileSize, 9 * tileSize, true);
        chessMans.add(whiteRook2);
        this.whiteBishop1 = new Bishop(this, 6 * tileSize, 9 * tileSize, true);
        chessMans.add(whiteBishop1);
        this.whiteBishop2 = new Bishop(this, 9 * tileSize, 9 * tileSize, true);
        chessMans.add(whiteBishop2);
        this.whiteQueen = new Queen(this, 7 * tileSize, 9 * tileSize, true);
        chessMans.add(whiteQueen);
        this.whiteKnight1 = new Knight(this, 5 * tileSize,  9 * tileSize, true);
        chessMans.add(whiteKnight1);
        this.whiteKnight2 = new Knight(this, 10 * tileSize,  9 * tileSize, true);
        chessMans.add(whiteKnight2);

        this.blackKing = new King(this, 8 * tileSize,  2 * tileSize, false);
        chessMans.add(blackKing);
        for (int i = 0; i< 8; i++) {
            blackPawn[i] = new Pawn(this, (i + 4) * tileSize, 3 * tileSize, false);
            chessMans.add(blackPawn[i]);
        }
        this.blackRook1 = new Rook(this, 4 * tileSize, 2 * tileSize, false);
        chessMans.add(blackRook1);
        this.blackRook2 = new Rook(this, 11 * tileSize, 2 * tileSize, false);
        chessMans.add(blackRook2);
        this.blackBishop1 = new Bishop(this, 6 * tileSize, 2 * tileSize, false);
        chessMans.add(blackBishop1);
        this.blackBishop2 = new Bishop(this, 9 * tileSize, 2 * tileSize, false);
        chessMans.add(blackBishop2);
        this.blackQueen = new Queen(this, 7 * tileSize, 2 * tileSize, false);
        chessMans.add(blackQueen);
        this.blackKnight1 = new Knight(this, 5 * tileSize,  2 * tileSize, false);
        chessMans.add(blackKnight1);
        this.blackKnight2 = new Knight(this, 10 * tileSize,  2 * tileSize, false);
        chessMans.add(blackKnight2);
        getImage();
    }

    public void eventSound(int i){
        sound.setFile(i);
        sound.play();
    }

    public void getImage(){
        try {
            board = ImageIO.read((getClass().getResourceAsStream("/image/Board.png")));
            queue = ImageIO.read((getClass().getResourceAsStream("/image/Queue.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        menuButton.update();
            if (!end){
                for (int i = 0; i < chessMans.size(); i++) {
                    chessMans.get(i).update();
                    if (!chessMans.get(i).alive) {
                        if(chessMans.get(i).white) removeWhite.add(chessMans.get(i));
                        else removeBlack.add(chessMans.get(i));
                    }
                    if (chessMans.get(i).button && i != chessMans.size() - 1){
                        chessMans.add(chessMans.get(i));
                        chessMans.remove(i);

                        if (i !=0) {
                            i--;
                        }
                    }
                    if (!chessMans.get(i).alive) {
                        chessMans.remove(i);
                        if (i != 0) {
                            i--;
                        }
                    }
                }
                if (turn == -1 && frame.modeChessAI) {
                    runAI = true;
                    ChessAI chessAI = new ChessAI(this);
                    Move bestMove = chessAI.chessAI(3);
                    if(BoardChess[bestMove.from.first][bestMove.from.second] instanceof King) {
                        end = true;
                    }

                    // Thực hiện nước đi tốt nhất
                    if (bestMove != null) {
                        int x = (bestMove.to.second + 4) * tileSize;
                        int y = (bestMove.to.first + 2) * tileSize;
                        BoardChess[bestMove.from.first][bestMove.from.second].Move(x, y, false);
                    }
                    runAI = false;
                }

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        mouseHandles[i][j].click = false;
                    }
                }
            }

    }

    public void AI () {

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D =(Graphics2D) g;
        menuButton.draw(g2D);
        g2D.drawImage(board, 4 * tileSize, 2 * tileSize, (int) (8 * boardScale * tileSize), (int) (8 * boardScale *tileSize), null);
        for (int i = 2; i < 10; i ++){
            for (int j = 1; j <= 2; j++){
                g2D.drawImage(queue, j * tileSize, i * tileSize, tileSize, tileSize, null);
                g2D.drawImage(queue, (j + 12) * tileSize, i * tileSize, tileSize, tileSize, null);
            }
        }
        for (int i = 0; i < chessMans.size(); ++i){
            chessMans.get(i).draw(g2D);
        }
        for (int i = 0; i < removeWhite.size(); i++) {
            g2D.drawImage(removeWhite.get(i).image, (i % 2 + 13) * tileSize, (i / 2 + 2) * tileSize, tileSize, tileSize, null);
        }
        for (int i = 0; i < removeBlack.size(); i++) {
            g2D.drawImage(removeBlack.get(i).image, (i % 2 + 1) * tileSize, (i / 2 + 2) * tileSize, tileSize, tileSize, null);
        }

        g2D.dispose();
    }
}
