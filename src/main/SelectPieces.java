package main;

import button.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SelectPieces extends Panel{
    PlayBlackButton playBlackButton;
    PlayWhiteButton playWhiteButton;
    Menu menu;

    public SelectPieces(Frame frame){
        super(frame);
        this.playBlackButton = new PlayBlackButton(this,(13 * tileSize) / 2,4 * tileSize,3 * tileSize, tileSize);
        this.playWhiteButton = new PlayWhiteButton(this,(13 * tileSize)/ 2,  (11 * tileSize) / 2, 3 * tileSize, tileSize);
        menu = new Menu(frame);
    }

    @Override
    public void update(){
        playBlackButton.update();
        playWhiteButton.update();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D =(Graphics2D) g;
        menu.DrawBackground(g2D);
        playBlackButton.draw(g2D);
        playWhiteButton.draw(g2D);
        g2D.dispose();
    }
}
