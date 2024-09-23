package Main;
import Pieces.Knight;
import Pieces.piece;

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
        pieceArrayList.add(new Knight(this, 2, 0, false));
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
            Piece.paint(g2d);
        }
    }
}
