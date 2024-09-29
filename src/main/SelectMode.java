package main;

import button.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SelectMode extends Panel{
    PlayBotsButton playBotsButton;
    PlayPeopleButton playPeopleButton;
    Menu menu;

    public SelectMode(Frame frame){
        super(frame);
        this.playBotsButton = new PlayBotsButton(this,(13 * tileSize) / 2,4 * tileSize,3 * tileSize, tileSize);
        this.playPeopleButton = new PlayPeopleButton(this,(13 * tileSize)/ 2,  (11 * tileSize) / 2, 3 * tileSize, tileSize);
        menu = new Menu(frame);
    }

    @Override
    public void update(){
        playBotsButton.update();
        playPeopleButton.update();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D =(Graphics2D) g;
        menu.DrawBackground(g2D);
        playBotsButton.draw(g2D);
        playPeopleButton.draw(g2D);
        g2D.dispose();
    }
}
