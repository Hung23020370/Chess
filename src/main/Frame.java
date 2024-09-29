package main;

import javax.swing.*;

public class Frame extends JFrame {
    public GamePanel gamePanel;
    Menu menu;
    public int gameState = 1;
    public boolean started = false;
    public boolean select_mode = false;
    public boolean select_pieces = false;
    SelectMode selectMode;
    SelectPieces selectPieces;
    public boolean select_white = true;


    public Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Chess");
        gamePanel = new GamePanel(this);
        menu = new Menu(this);
        selectMode = new SelectMode(this);
        selectPieces = new SelectPieces(this);
        this.add(menu);
        menu.startGameThread();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void switchToPanel(JPanel panel) {
        this.getContentPane().removeAll();  // Xóa tất cả các thành phần
        this.add(panel);                    // Thêm panel mới
        this.revalidate();                  // Cập nhật giao diện
        this.repaint();                     // Vẽ lại
        panel.requestFocusInWindow();       // Đặt focus cho panel
    }

    private void stopGamePanelThread() {
        if (started && gamePanel.thread != null) {
            gamePanel.thread = null;        // Dừng thread game
        }
    }

    public void update(){
        stopGamePanelThread();  // Dừng thread game nếu cần thiết

        if (gameState == 1) {
            if (!select_mode && !select_pieces) {
                switchToPanel(menu);
                if (!started) menu.startGameThread();
            } else if (select_mode && !select_pieces) {
                switchToPanel(selectMode);
                if (!started) selectMode.startGameThread();
            } else if (select_mode && select_pieces) {
                switchToPanel(selectPieces);
                if (!started) selectPieces.startGameThread();
            }
        } else if (gameState == 2) {
            gamePanel = new GamePanel(this);  // Khởi tạo lại gamePanel
            switchToPanel(gamePanel);
            gamePanel.startGameThread();
            started = true;
        } else if (gameState == 3) {
            switchToPanel(gamePanel);
            gamePanel.startGameThread();
        }
    }
}
