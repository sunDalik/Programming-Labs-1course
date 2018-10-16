import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sea implements Comparable<Sea>, Serializable {
    private String name;
    private double size;
    private int power;
    private int x;
    private int y;
    private Colors color;
    private String creationDate;
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
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

    public String getCreationDate() {
        return creationDate;
    }
}
