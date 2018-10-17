import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Circle extends JButton {
    private Color color;
    private int diff = 40;
    private int range = diff;
    private boolean isGoingWhite = true;
    private Sea sea;
    private GUI frame;

    Circle(Sea sea, GUI frame) {
        this.sea = sea;
        this.frame = frame;
        color = sea.getColor().getRgbColor();
        setBorder(null);
        setEnabled(false);
        setBounds(0, 0, ((Double) sea.getSize()).intValue(), ((Double) sea.getSize()).intValue());
        this.setToolTipText(sea.getName());
        this.addMouseListener(new setInfoOnClick());
    }

    @Override
    public void paintComponent(Graphics g) {
        rebound();
        g.setColor(color);
        g.fillOval(0, 0, ((Double) sea.getSize()).intValue(), ((Double) sea.getSize()).intValue());
    }

    private void rebound(){
        double k = 0.295;
        double k2 = 0.295;
        double x = frame.p3.getWidth()/2.08 + sea.getX()*k;
        double y = frame.p3.getHeight()/2.08 - sea.getY()*k2;
        this.setBounds((int) x, (int) y, ((Double) sea.getSize()).intValue(), ((Double) sea.getSize()).intValue());
    }

    void transition() {
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

    void setNormalColor() {
        diff = 40;
        range = 40;
        isGoingWhite = true;
        color = sea.getColor().getRgbColor();
    }

    class setInfoOnClick extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            GUI.setTopPanelInfo(sea);
        }
    }
}