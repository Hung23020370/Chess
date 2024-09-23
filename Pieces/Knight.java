package Pieces;

import Main.Board;

import java.awt.image.BufferedImage;

public class Knight extends piece{
    public Knight(Board board,int col, int row, boolean isWhite) {
        super(board);

        this.col = col;
        this.row = row;
        this.xPos = col * board.square_size;
        this.yPos = row * board.square_size;

        this.isWhite = isWhite;
        this.name = "Knight";
        this.sprite = sheet.getSubimage(2 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.square_size, board.square_size, BufferedImage.SCALE_SMOOTH);
    }
}
