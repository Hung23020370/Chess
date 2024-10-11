package main;

import button.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SelectMode extends Panel{
    BotButton botButton;
    PeopleButton peopleButton;
    BufferedImage background, whiteKing, blackKing;

    public SelectMode(Frame frame){
        super(frame);
        this.botButton = new BotButton(this,(13 * tileSize) / 2,(9 * tileSize)/2,3 * tileSize, tileSize);
        this.peopleButton = new PeopleButton(this, (13 * tileSize)/ 2, (13 * tileSize)/2, 3 * tileSize, tileSize);
        getImage();
    }

    public void getImage(){
        try {
            background = ImageIO.read((getClass().getResourceAsStream("/image/BackGround.png")));
            whiteKing = ImageIO.read((getClass().getResourceAsStream("/image/White_King.png")));
            blackKing = ImageIO.read((getClass().getResourceAsStream("/image/Black_King.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(){
        botButton.update();
        peopleButton.update();

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D =(Graphics2D) g;
        BackGround(g2D);
        botButton.draw(g2D);
        peopleButton.draw(g2D);
        g2D.dispose();
    }
    public void BackGround (Graphics2D g2D) {
        g2D.drawImage(background,0, 0, screenWidth, screenHeight, null);
        g2D.drawImage(whiteKing,15,125,6 * tileSize, 6 * tileSize, null);
        g2D.drawImage(blackKing,465,125,6 * tileSize, 6 * tileSize, null);

    }
}
