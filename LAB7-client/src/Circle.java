import javax.swing.*;
import java.awt.*;

public class Circle extends JButton {
    private Sea sea;

    Circle(Sea sea) {
        this.sea = sea;
        setBorder(null);
        setEnabled(false);
        setVisible(true);
        setBounds(sea.getX(), sea.getY(), ((Double)sea.getSize()).intValue(), ((Double)sea.getSize()).intValue());
        this.setToolTipText(sea.getName());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(sea.getColor().getRgbColor());
        g.fillOval(0, 0, ((Double)sea.getSize()).intValue(), ((Double)sea.getSize()).intValue());

    }

}