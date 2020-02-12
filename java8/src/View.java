import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Sierpinski Triangles (Fractal)
 * 
 * - AND Operator method
 * - Chaos Game method
 * 
 * @author Leonardo Ono (ono.leo@gmail.com);
 */
public class View extends JPanel {
    
    private int k = 0;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLUE);
        drawAndOperatorMethod(g2d);
        
        g2d.setColor(Color.WHITE);
        drawCaosGameMethod(g2d);
        
        k++;
        
        // please implement a better game loop later xD ...
        try {
            Thread.sleep(1000 / 30);
        } catch (InterruptedException ex) {
        }
        repaint();
    }
    
    // ref.: https://github.com/kfox/sierpinski-c64
    private void drawAndOperatorMethod(Graphics2D g) {
        for (int row = 0; row < 600; row++) {
          for (int column = 0; column < 800; column++) {
            if ((((row + k) % 128) & ((column + k) % 128)) == 0) {
              //g.setColor(new Color(((column & 0xc0) ^ (row & 0xc0))));
              g.drawLine(column, row, column, row);
            }
          }
        }        
    }

    // https://www.youtube.com/watch?v=kbKtFN71Lfs&t=327s
    // https://www.youtube.com/watch?v=IGlGvSXkRGI
    private void drawCaosGameMethod(Graphics2D g) {
        double s1 = Math.sin(k * 0.025);
        double c1 = Math.cos(k * 0.025);
        double s2 = Math.sin(k * 0.025 + Math.toRadians(120));
        double c2 = Math.cos(k * 0.025 + Math.toRadians(120));
        double s3 = Math.sin(k * 0.025 + Math.toRadians(240));
        double c3 = Math.cos(k * 0.025 + Math.toRadians(240));

        final int[] ps = {
            400 + (int) (400 * c1), 300 + (int) (300 * s1), 
            400 + (int) (400 * c2), 300 + (int) (300 * s2), 
            400 + (int) (400 * c3), 300 + (int) (300 * s3)}; 
        
        int random, px = ps[0], py = ps[1];
        for (int i = 0; i < 0x4000; i++) {
            random = (int) (Integer.MAX_VALUE * Math.random());
            px += (ps[2 * (random % 3)] - px) / 2;
            py += (ps[2 * (random % 3) + 1] - py) / 2;
            g.fillOval(px - 2, py - 2, 4, 4);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                view.setPreferredSize(new Dimension(800, 600));
                view.setBackground(Color.BLACK);
                JFrame frame = new JFrame();
                frame.setTitle("Sierpinski Triangles (Fractal)");
                frame.getContentPane().add(view);
                frame.setResizable(false);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
    
}
