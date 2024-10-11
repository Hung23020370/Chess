package main;

import javax.swing.*;

public class Frame extends JFrame {
    GamePanel gamePanel;
    Menu menu;
    SelectMode selectMode;
    public int gameState = 1;
    public boolean started = false;
    public boolean select_mode = false;
    public boolean modeChessAI = false;

    public Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Chess");
        gamePanel = new GamePanel(this);
        menu = new Menu(this);
        selectMode = new SelectMode(this);
        this.add(menu);
        menu.startGameThread();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void update(){
        if(gameState == 1 && !select_mode){
            if(started) {
                gamePanel.thread = null;
                this.remove(gamePanel);
            }
            this.add(menu);
            if (!started) {
                menu.startGameThread();
            }
            this.revalidate();
            this.repaint();
            menu.requestFocusInWindow();
        }
        else if(gameState == 1 && select_mode){
            this.remove(menu);
            if(started) {
                gamePanel.thread = null;
                this.remove(gamePanel);
            }
            this.add(selectMode);
            if (!started) {
                selectMode.startGameThread();
            }
            this.revalidate();
            this.repaint();
            selectMode.requestFocusInWindow();
        }
        else if (gameState == 2){
            this.remove(selectMode);
            started = true;
            gamePanel.removeAll();
            gamePanel = new GamePanel(this);
            this.add(gamePanel);
            gamePanel.startGameThread();
            this.revalidate();
            this.repaint();
            gamePanel.requestFocusInWindow();
        }
        else if (gameState == 3){
            this.remove(selectMode);
            this.remove(menu);
            this.add(gamePanel);
            gamePanel.startGameThread();
            this.revalidate();
            this.repaint();
            gamePanel.requestFocusInWindow();
        }
    }
}
