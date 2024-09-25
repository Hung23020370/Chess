package Pieces;

import Main.Board;

import javax.swing.*;

public class King extends piece{
    public King(Board board,int col, int row, boolean isWhite) {
        super(board);

        this.col = col;
        this.row = row;
        this.xPos = col * board.square_size;
        this.yPos = row * board.square_size;

        this.isWhite = isWhite;
        this.name = "King";
        if(isWhite)
            this.image = new ImageIcon(getClass().getResource("/Image/white_king.png")).getImage();
        else
            this.image = new ImageIcon(getClass().getResource("/Image/black_king.png")).getImage();

    }
}

