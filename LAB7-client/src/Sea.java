import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sea implements Comparable<Sea>, Serializable {
    private String name;
    private double size;
    private int power;
    private int x;
    private int y;
    private Colors color;
    private String creationDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private static final long serialVersionUID = 78L;

    /**
     * Is used to add new element to collection
     */
    Sea(String name, double size, int power, int x, int y, Colors color) {
        this.name = name;
        this.size = size;
        this.power = power;
        this.x = x;
        this.y = y;
        this.color = color;
        creationDate = sdf.format(new Date());
    }

    /**
     * Is used to load element from file to collection
     */
    Sea(String name, double size, int power, int x, int y, Colors color, String creationDate) {
        this.name = name;
        this.size = size;
        this.power = power;
        this.x = x;
        this.y = y;
        this.color = color;
        this.creationDate = creationDate;
    }


    String toCsv() {
        return name + "," + size + "," + power + "," + x + "," + y + "," + color + "," + creationDate;
    }


    Object[] toArray() {
        return new Object[]{name, size, power, x, y, color, creationDate};
    }


    @Override
    public int compareTo(Sea o) {
        return Double.compare(size, o.size);
    }

    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public int getPower() {
        return power;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Colors getColor() {
        return color;
    }

    public String getStringDate() {
        return creationDate;
    }

    public Date getDate() {
        try {
            return sdf.parse(creationDate);
        } catch (ParseException e) {
            return new Date();   //this will simply never happen I guess :^)
        }
    }
}
