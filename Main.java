import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int boardLength = 1000;
        int boardWidth = 600;

        JFrame jFrame = new JFrame("Chess");
        jFrame.setSize(boardLength, boardWidth);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.add(new JPanel());

        jFrame.setVisible(true);
    }
}
