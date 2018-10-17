import javax.swing.*;
import java.awt.*;

public class Circle extends JButton {
    private Color color;
    private int diff = 40;
    private int range = diff;
    private boolean isGoingWhite = true;
    private Sea sea;

    Circle(Sea sea) {
        this.sea = sea;
        color = sea.getColor().getRgbColor();
        setBorder(null);
        setEnabled(false);
        setVisible(true);
        setBounds(sea.getX(), sea.getY(), ((Double) sea.getSize()).intValue(), ((Double) sea.getSize()).intValue());
        this.setToolTipText(sea.getName());

    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillOval(0, 0, ((Double) sea.getSize()).intValue(), ((Double) sea.getSize()).intValue());

    }

    public void transition() {
        double ratio = (double) diff / (double) range;
        if (!isGoingWhite) {
            int red = (int) Math.abs((ratio * Color.WHITE.getRed()) + ((1 - ratio) * sea.getColor().getRgbColor().getRed()));
            int green = (int) Math.abs((ratio * Color.WHITE.getGreen()) + ((1 - ratio) * sea.getColor().getRgbColor().getGreen()));
            int blue = (int) Math.abs((ratio * Color.WHITE.getBlue()) + ((1 - ratio) * sea.getColor().getRgbColor().getBlue()));
            color = new Color(red, green, blue);
            diff--;
            if (diff == 0) {
                setNormalColor();
            }
        } else {
            int red = (int) Math.abs((ratio * sea.getColor().getRgbColor().getRed()) + ((1 - ratio) * Color.WHITE.getRed()));
            int green = (int) Math.abs((ratio * sea.getColor().getRgbColor().getGreen()) + ((1 - ratio) * Color.WHITE.getGreen()));
            int blue = (int) Math.abs((ratio * sea.getColor().getRgbColor().getBlue()) + ((1 - ratio) * Color.WHITE.getBlue()));
            color = new Color(red, green, blue);
            if (diff == 0) {
                isGoingWhite = false;
                range = 30;
                diff = 30;
            } else {
                diff--;
            }
        }
    }

    public void setNormalColor() {
        diff = 40;
        range = 40;
        isGoingWhite = true;
        color = sea.getColor().getRgbColor();
    }

}