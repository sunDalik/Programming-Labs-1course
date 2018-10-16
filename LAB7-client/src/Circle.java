import javax.swing.*;
import java.awt.*;


public class Circle extends JComponent {
    private int x;
    private int y;
    private int diameter;
    private Color color;

    Circle(int x, int y, int diameter, Color color) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.color = color;
        setVisible(true);
    }

    public void paintComponent(Graphics gr) {
        super.paintComponents(gr);
        Graphics2D g2d = (Graphics2D) gr;
        g2d.setPaint(this.color);
        g2d.fillOval(this.x, this.y, this.diameter, this.diameter);
    }
}