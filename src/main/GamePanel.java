package main;

import button.MenuButton;
import piece.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Math.abs;

public class GamePanel extends Panel {
    MenuButton menuButton;
    BufferedImage board, queue;
    double boardScale = 1;
    public int[][] Board = new int[8][8];

    public MouseHandle[][] mouseHandles = new MouseHandle[8][8];
    public ArrayList<ChessMan> chessManArrayList;

    int test_while;
    public int turn;

    public GamePanel(Frame frame) {
        super(frame);
        this.menuButton = new MenuButton(this, 0, 0, 2 * tileSize, tileSize);
        this.setBackground(Color.BLACK);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mouseHandles[i][j] = new MouseHandle((j + 4) * tileSize, (i + 2) * tileSize, tileSize, tileSize);
                this.addMouseListener(mouseHandles[i][j]);
                this.addMouseMotionListener(mouseHandles[i][j]);
            }
        }
        chessManArrayList = new ArrayList<>();

        if (frame.select_white) {
            test_while = 0;
        } else {
            test_while = 11;
        }

        // Thêm các quân cờ vào danh sách
        chessManArrayList.add(new King(this, 8 * tileSize, abs(test_while - 9) * tileSize, true));
        chessManArrayList.add(new Queen(this, 7 * tileSize, abs(test_while - 9) * tileSize, true));
        chessManArrayList.add(new Rook(this, 4 * tileSize, abs(test_while - 9) * tileSize, true));
        chessManArrayList.add(new Rook(this, 11 * tileSize, abs(test_while - 9) * tileSize, true));
        chessManArrayList.add(new Bishop(this, 6 * tileSize, abs(test_while - 9) * tileSize, true));
        chessManArrayList.add(new Bishop(this, 9 * tileSize, abs(test_while - 9) * tileSize, true));
        chessManArrayList.add(new Knight(this, 5 * tileSize, abs(test_while - 9) * tileSize, true));
        chessManArrayList.add(new Knight(this, 10 * tileSize, abs(test_while - 9) * tileSize, true));
        chessManArrayList.add(new Pawn(this, 4 * tileSize, abs(test_while - 8) * tileSize, true));
        chessManArrayList.add(new Pawn(this, 5 * tileSize, abs(test_while - 8) * tileSize, true));
        chessManArrayList.add(new Pawn(this, 6 * tileSize, abs(test_while - 8) * tileSize, true));
        chessManArrayList.add(new Pawn(this, 7 * tileSize, abs(test_while - 8) * tileSize, true));
        chessManArrayList.add(new Pawn(this, 8 * tileSize, abs(test_while - 8) * tileSize, true));
        chessManArrayList.add(new Pawn(this, 9 * tileSize, abs(test_while - 8) * tileSize, true));
        chessManArrayList.add(new Pawn(this, 10 * tileSize, abs(test_while - 8) * tileSize, true));
        chessManArrayList.add(new Pawn(this, 11 * tileSize, abs(test_while - 8) * tileSize, true));

        chessManArrayList.add(new King(this, 8 * tileSize, abs(test_while - 2) * tileSize, false));
        chessManArrayList.add(new Queen(this, 7 * tileSize, abs(test_while - 2) * tileSize, false));
        chessManArrayList.add(new Rook(this, 4 * tileSize, abs(test_while - 2) * tileSize, false));
        chessManArrayList.add(new Rook(this, 11 * tileSize, abs(test_while - 2) * tileSize, false));
        chessManArrayList.add(new Bishop(this, 6 * tileSize, abs(test_while - 2) * tileSize, false));
        chessManArrayList.add(new Bishop(this, 9 * tileSize, abs(test_while - 2) * tileSize, false));
        chessManArrayList.add(new Knight(this, 5 * tileSize, abs(test_while - 2) * tileSize, false));
        chessManArrayList.add(new Knight(this, 10 * tileSize, abs(test_while - 2) * tileSize, false));
        chessManArrayList.add(new Pawn(this, 4 * tileSize, abs(test_while - 3) * tileSize, false));
        chessManArrayList.add(new Pawn(this, 5 * tileSize, abs(test_while - 3) * tileSize, false));
        chessManArrayList.add(new Pawn(this, 6 * tileSize, abs(test_while - 3) * tileSize, false));
        chessManArrayList.add(new Pawn(this, 7 * tileSize, abs(test_while - 3) * tileSize, false));
        chessManArrayList.add(new Pawn(this, 8 * tileSize, abs(test_while - 3) * tileSize, false));
        chessManArrayList.add(new Pawn(this, 9 * tileSize, abs(test_while - 3) * tileSize, false));
        chessManArrayList.add(new Pawn(this, 10 * tileSize, abs(test_while - 3) * tileSize, false));
        chessManArrayList.add(new Pawn(this, 11 * tileSize, abs(test_while - 3) * tileSize, false));

        getImage();
    }

    public void getImage() {
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
        ArrayList <ChessMan> newChessMen = new ArrayList<>();

        for (int index = 0; index < chessManArrayList.size(); index++) {
            ChessMan chessMan = chessManArrayList.get(index);
            chessMan.update();

            if (chessMan instanceof Pawn && ((Pawn) chessMan).promotionRequired) {
                ChessMan promotedPiece = ((Pawn) chessMan).promotePawn();
                if (promotedPiece != null) {
                    newChessMen.add(promotedPiece);
                    ((Pawn) chessMan).promotionRequired = false;
                }
            }
        }
        chessManArrayList.addAll(newChessMen);

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        menuButton.draw(g2D);
        ArrayList <ChessMan> copy = new ArrayList<>(chessManArrayList);
        g2D.drawImage(board, 4 * tileSize, 2 * tileSize, (int) (8 * boardScale * tileSize), (int) (8 * boardScale * tileSize), null);
        for (int i = 2; i < 10; i++) {
            for (int j = 1; j <= 2; j++) {
                g2D.drawImage(queue, j * tileSize, i * tileSize, tileSize, tileSize, null);
                g2D.drawImage(queue, (j + 12) * tileSize, i * tileSize, tileSize, tileSize, null);
            }
        }
        for (ChessMan chessMan : copy) {
            chessMan.draw(g2D);
        }
        g2D.dispose();
    }
}
