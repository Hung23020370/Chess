package Main;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main {
        final static int boardLength = 600;
        final static int boardWidth = 600;
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(boardLength, boardWidth);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setBackground(Color.BLACK);

        jFrame.setLayout(new GridBagLayout());
        Board board = new Board();
        jFrame.add(board);

        jFrame.setVisible(true);
    }
}
