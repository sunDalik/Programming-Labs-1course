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
        //this.setBounds(x, y, diameter, diameter); //удаление этой строчки привело к появлению кругов
        //this.repaint();
        //setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent (g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(this.color);
        System.out.println("dssd");
        g2.fillOval(this.x, this.y, this.diameter, this.diameter);
    }
}