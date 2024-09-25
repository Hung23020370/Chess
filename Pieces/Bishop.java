package Pieces;

import Main.Board;

import javax.swing.*;

public class Bishop extends piece{
    public Bishop(Board board,int col, int row, boolean isWhite) {
        super(board);

        this.col = col;
        this.row = row;
        this.xPos = col * board.square_size;
        this.yPos = row * board.square_size;

        this.isWhite = isWhite;
        this.name = "Queen";
        if(isWhite)
            this.image = new ImageIcon(getClass().getResource("/Image/white_bishop.png")).getImage();
        else
            this.image = new ImageIcon(getClass().getResource("/Image/black_bishop.png")).getImage();

    }
}

