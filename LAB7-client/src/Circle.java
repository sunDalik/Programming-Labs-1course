import javax.swing.*;
import java.awt.*;


public class Circle extends JPanel {
    private int x;
    private int y;
    private int diameter;
    private Color color;

    Circle(int x, int y, int diameter, Color color) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.color = color;
        //this.setBackground(color);
        //this.setBounds(x, y, diameter, diameter);
        this.repaint();
        //setVisible(true);
    }

    void dood(){
        this.repaint();
    }
    @Override
    public void paint(Graphics g) {
        //super.paintComponent (g);
        //Graphics2D g2 = (Graphics2D) g;
        g.setColor(this.color);
        System.out.println("dssd");
        g.fillOval(this.x, this.y, this.diameter, this.diameter);
    }
}