import javax.swing.*;
import java.awt.*;

public class Circle extends JButton {
    private int opacity=255; //Непрозрачность
    private Sea sea; //Каждому кружочку соответствует элемент коллекции

    Circle(Sea sea) {
        this.sea = sea;
        setBorder(null);
        setEnabled(false);
        setVisible(true);
        setBounds(sea.getX(), sea.getY(), ((Double)sea.getSize()).intValue(), ((Double)sea.getSize()).intValue());
        this.setToolTipText(sea.getName());
    }

    //Делаем форму круга
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(sea.getColor().getRgbColor());
        g.fillOval(0, 0, ((Double)sea.getSize()).intValue(), ((Double)sea.getSize()).intValue());

    }

    public void rebounds() {
        setBounds(sea.getX(), sea.getY(), ((Double)sea.getSize()).intValue(), ((Double)sea.getSize()).intValue());
    }
    public int getOpacity() {return opacity;}

    public void decrementOpacity(){opacity--;}

    public void incrementOpacity() {opacity++;}

    public void setOpacity(int opacity) {this.opacity=opacity;}
}