import javax.swing.JPanel;

public class NPC extends JPanel {

    int X;
    int Y;
    int WIDTH;
    int HEIGHT;

    public NPC(int x, int y, int width, int height) {
        this.X = x;
        this.Y = y;
        this.WIDTH = width;
        this.HEIGHT = height;

        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.setBounds(x, y, width, height);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
    }
}
