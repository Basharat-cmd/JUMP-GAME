import javax.swing.JFrame;

public class Main{
    JFrame frame = new JFrame();

    public Main() {
        frame.setTitle("My JUMP GAME!");
        frame.setSize(750, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        frame.add(new GamePanel(18,5,700, 500));
        frame.setVisible(true);
    }
    public static void main(String args[]){
        new Main();
    }
}