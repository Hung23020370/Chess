package  piece;

import button.NextEat;
import button.NextMove;
import main.GamePanel;
import pair.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ChessMan{
    GamePanel panel;
    BufferedImage image;
    String name;
    String color;
    public int i, j, x, y;
    public boolean button;
    public boolean firstMove;
    public boolean white;
    public boolean alive;
    public int value;
    public boolean promotionRequired = false;
    ArrayList<Pair> moves = new ArrayList<>();
    ArrayList<Pair> eats = new ArrayList<>();
    ArrayList<NextMove> nextMoves = new ArrayList<>();
    ArrayList<NextEat> nextEats = new ArrayList<>();

    public ChessMan(GamePanel panel, int x, int y, boolean white){
        this.panel = panel;
        this.white = white;
        if(white){
            this.color = "White_";
        }
        else{
            this.color = "Black_";
        }
        this.i = (y / panel.tileSize) - 2;
        this.j = (x / panel.tileSize) - 4;
        this.x = x;
        this.y = y;
        this.button = false;
        this.firstMove = true;
        this.alive = true;
        setValue();
        if (!white && panel.frame.select_white){
            this.value = 0 - this.value;
            panel.turn = 1;
        }
        else if (white && !panel.frame.select_white) {
            this.value = 0 - this.value;
            panel.turn = -1;
        }
        panel.Board[i][j] = this.value;
        setImageName();
        getImage();
    }

    public abstract void setValue();
    public abstract void setImageName();

    public void getImage(){
        try {
            image = ImageIO.read((getClass().getResourceAsStream("/image/" + color + name + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void functionUpdate();

    public void update(){
        if(panel.Board[i][j] * this.value < 0){
            this.alive = false;
        }
        if (this.alive && panel.turn * this.value > 0) {
            if (panel.mouseHandles[i][j].click) {
                this.button = true;
                nextMoves = new ArrayList<>();
                moves = new ArrayList<>();
                nextEats = new ArrayList<>();
                eats = new ArrayList<>();
                functionUpdate();
                ArrayList<Pair> tempMoves = new ArrayList<>(moves);
                ArrayList<Pair> tempEats = new ArrayList<>(eats);
                for (Pair<Integer, Integer> move : tempMoves) {
                    nextMoves.add(new NextMove(this.panel, (move.second + 4) * panel.tileSize, (move.first + 2) * panel.tileSize, panel.tileSize, panel.tileSize));
                }
                for (Pair<Integer, Integer> eat : tempEats) {
                    nextEats.add(new NextEat(this.panel, (eat.second + 4) * panel.tileSize, (eat.first + 2) * panel.tileSize, panel.tileSize, panel.tileSize));
                }
            }

            if (button) {
                for (NextMove move : nextMoves) {
                    move.update();
                    if (move.button) {
                        panel.turn = panel.turn * -1;
                        panel.Board[i][j] = 0;
                        this.x = move.x;
                        this.y = move.y;
                        this.j = (this.x / panel.tileSize) - 4;
                        this.i = (this.y / panel.tileSize) - 2;
                        panel.Board[i][j] = value;
                        panel.mouseHandles[i][j].click = false;
                        this.button = false;
                        this.firstMove = false;
                        break;
                    }
                }
                for (NextEat eat : nextEats) {
                    eat.update();
                    if (eat.button) {
                        panel.turn = panel.turn * -1;
                        panel.Board[i][j] = 0;
                        this.x = eat.x;
                        this.y = eat.y;
                        this.j = (this.x / panel.tileSize) - 4;
                        this.i = (this.y / panel.tileSize) - 2;
                        panel.Board[i][j] = value;
                        panel.mouseHandles[i][j].click = false;
                        this.button = false;
                        this.firstMove = false;
                        break;
                    }
                }
                if (this instanceof Pawn && ((this.i == 0) || (this.i == 7))) {
                    this.promotionRequired = true;
                    panel.chessManArrayList.add(promotePawn());
                }
            }
            for (int a = 0; a < 8; a++) {
                for (int b = 0; b < 8; b++) {
                    if (panel.mouseHandles[a][b].click) {
                        if (a == this.i && b == this.j) {
                            this.button = true;
                        } else {
                            this.button = false;
                        }
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2D) {
        if(this.alive) {
            g2D.drawImage(image, x, y, panel.tileSize, panel.tileSize, null);
            if (button) {
                for (NextMove move : nextMoves) {
                    move.draw(g2D);
                }
                for (NextEat eat : nextEats) {
                    eat.draw(g2D);
                }
            }
        }
    }
    public ChessMan promotePawn() {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};

        int choice = JOptionPane.showOptionDialog(panel.frame,
                "Chọn quân phong:",
                "Thăng cấp quân tốt",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
        ChessMan newPiece = null;
        if (choice >= 0) {
            switch (choice) {
                case 0:
                    newPiece = new Queen(panel, this.x, this.y, white);
                    break;
                case 1:
                    newPiece = new Rook(panel, this.x, this.y, white);
                    break;
                case 2:
                    newPiece = new Bishop(panel, this.x, this.y, white);
                    break;
                case 3:
                    newPiece = new Knight(panel, this.x, this.y, white);
                    break;
            }
        }
        this.alive = false;
        panel.Board[i][j] = newPiece.value;
        return newPiece;
    }

}
