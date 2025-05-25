import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

public class GamePanel extends JPanel implements java.awt.event.KeyListener {

    int WIDTH;
    int HEIGHT;
    int X;
    int Y;
    NPC player;
    NPC enemy1;
    int enemy1X = 2000;
    boolean isJumping = false;
    JLabel GameOverLabel = new JLabel("Game Over Press Space to Restart");
    JLabel HS = new JLabel("High Score: "); // high score label
    JLabel S = new JLabel("Score: "); // score label
    float score = 0; // score variable
    float highScore; // high score variable
    Timer timer;
    MYFILES file = new MYFILES("highscore.dat");


    public GamePanel(int x, int y, int width, int height) {
        this.setDoubleBuffered(true);
        this.WIDTH = width;
        this.HEIGHT = height;
        this.X = x;
        this.Y = y;
        this.setLayout(null);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.setBounds(x, y, width, height);
        this.setBackground(Color.WHITE);

        player = new NPC(10, 500 - 50, 50, 50);
        player.setBackground(Color.GREEN);
        enemy1 = new NPC(enemy1X, 500 - 50, 50, 50);
        enemy1.setBackground(Color.RED);

        HS.setBounds(10, 10, 500, 30);
        S.setBounds(10, 40, 500, 30);


        try { HS.setText("High Score: " + file.read()); } catch (IOException e) {}

        try { highScore = file.read().isEmpty() ? 0 : Float.parseFloat(file.read()); } catch (NumberFormatException | IOException e) {}

        this.add(player);
        this.add(enemy1);
        this.add(HS);
        this.add(S);

        MainLoop();
        
    }

    private boolean isCollidingWithEnemy() {
        int px = player.getX();
        int py = player.getY();
        int ex = enemy1.getX();
        int ey = enemy1.getY();

        return px < ex + 50 &&
               px + 50 > ex &&
               py < ey + 50 &&
               py + 50 > ey;
    }

    private void MainLoop(){
        // Main game loop
        timer = new Timer(10, e -> {
            enemy1X -= 5;
            if (enemy1X < -50) {
                enemy1X = new Random().nextInt(1000) + 750;
            }

            // Check collision
            if (isCollidingWithEnemy()) {
                ((Timer) e.getSource()).stop();

                GameOverLabel.setBounds(300, 250, 200, 50);
                this.add(GameOverLabel);
                if (score > highScore) {
                    highScore = score;
                    try { file.write(String.valueOf(highScore)); } catch (IOException e1) {}
                    HS.setText("High Score: " + highScore); // Update high score label
                }
            }

            enemy1.setLocation(enemy1X, 500 - 50);
            score += 0.1; // Increment score over time
            S.setText("Score: " + score); // Update score label
            repaint();
        });

        timer.start();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isJumping && !isCollidingWithEnemy()) {
            isJumping = true;

            Timer jumpTimer = new Timer(10, new ActionListener() {
                double velocity = -10;
                final int groundY = 500 - 50;

                @Override
                public void actionPerformed(ActionEvent e) {
                    int newY = (int)(player.getY() + velocity);
                    velocity += 0.5;

                    if (newY >= groundY) {
                        newY = groundY;
                        ((Timer) e.getSource()).stop();
                        isJumping = false;
                    }

                    player.setLocation(player.getX(), newY);
                }
            });

            jumpTimer.start();
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE && isCollidingWithEnemy()) {
            // Restart the game
            enemy1X = 2000; // Reset enemy position
            player.setLocation(10, 500 - 50); // Reset player position
            score = 0; // Reset score
            S.setText("Score: " + score); // Update score label
            this.remove(GameOverLabel); // Remove Game Over label
            isJumping = false; // Reset jumping state
            enemy1.setLocation(enemy1X, 500 - 50); // Reset enemy position
            this.repaint(); // Repaint the panel to reflect changes
            this.requestFocusInWindow(); // Request focus for key events
            this.revalidate(); // Revalidate the panel to update layout
            MainLoop();
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
