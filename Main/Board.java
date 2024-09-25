package Main;
import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    final int cols = 8;
    final int rows = 8;
    public static final int square_size = 53;
    ArrayList <piece> pieceArrayList = new ArrayList<>();
    public Board () {
        // Chi dinh kich thuoc bang hien thi
        this.setPreferredSize(new Dimension(cols * square_size, rows * square_size));
        addPieces();
    }

    public void addPieces() {
        pieceArrayList.add(new Rook(this, 0, 0, false));
        pieceArrayList.add(new Knight(this, 1, 0, false));
        pieceArrayList.add(new Bishop(this, 2, 0, false));
        pieceArrayList.add(new Queen(this, 3, 0, false));
        pieceArrayList.add(new King(this, 4, 0, false));
        pieceArrayList.add(new Bishop(this, 5, 0, false));
        pieceArrayList.add(new Knight(this, 6, 0, false));
        pieceArrayList.add(new Rook(this, 7, 0, false));
        pieceArrayList.add(new Pawn(this, 0, 1, false));
        pieceArrayList.add(new Pawn(this, 1, 1, false));
        pieceArrayList.add(new Pawn(this, 2, 1, false));
        pieceArrayList.add(new Pawn(this, 3, 1, false));
        pieceArrayList.add(new Pawn(this, 4, 1, false));
        pieceArrayList.add(new Pawn(this, 5, 1, false));
        pieceArrayList.add(new Pawn(this, 6, 1, false));
        pieceArrayList.add(new Pawn(this, 7, 1, false));

        pieceArrayList.add(new Rook(this, 0, 7, true));
        pieceArrayList.add(new Knight(this, 1, 7, true));
        pieceArrayList.add(new Bishop(this, 2, 7, true));
        pieceArrayList.add(new Queen(this, 3, 7, true));
        pieceArrayList.add(new King(this, 4, 7, true));
        pieceArrayList.add(new Bishop(this, 5, 7, true));
        pieceArrayList.add(new Knight(this, 6, 7, true));
        pieceArrayList.add(new Rook(this, 7, 7, true));
        pieceArrayList.add(new Pawn(this, 0, 6, true));
        pieceArrayList.add(new Pawn(this, 1, 6, true));
        pieceArrayList.add(new Pawn(this, 2, 6, true));
        pieceArrayList.add(new Pawn(this, 3, 6, true));
        pieceArrayList.add(new Pawn(this, 4, 6, true));
        pieceArrayList.add(new Pawn(this, 5, 6, true));
        pieceArrayList.add(new Pawn(this, 6, 6, true));
        pieceArrayList.add(new Pawn(this, 7, 6, true));

    }
    public void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                g2d.setColor((c + r) % 2 == 0 ? new Color(227, 198, 181) : new Color(157, 105, 53));
                g2d.fillRect(c * square_size,r * square_size, square_size, square_size);
            }
        }
        for (piece Piece : pieceArrayList) {
            g.drawImage(Piece.image, Piece.xPos, Piece.yPos, square_size, square_size, null);
        }
    }
}
