package Pieces;

import Main.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class piece {
    public int row, col;
    public int xPos, yPos;
    public boolean isWhite;
    public String name;
    public int value;
    public Image image;
    Board board;
    public piece (Board board) {
        this.board = board;
    }

    public void paint (Graphics2D g2d) {
        g2d.drawImage(image, xPos, yPos, null);
    }
}
