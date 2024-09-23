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
    Image sprite;

    BufferedImage sheet;

    {
        try {
            // Đọc file hình ảnh bằng ImageIO
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("Image/Chess_Pieces_Sprite.svg (1).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Board board;
    protected int sheetScale = sheet.getWidth()/6;
    public piece (Board board) {
        this.board = board;
    }

    public void paint (Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}
