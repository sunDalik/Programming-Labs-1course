import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sea implements Comparable<Sea>, Serializable {
    private String name;
    private double size;
    private int power;
    private double x;
    private double y;
    private Colors color;
    private String creationDate;
    private static final long serialVersionUID = 78L;

    Sea() { }

    /**
     * Is used to add new element to collection
     */
    Sea(String name, double size, int power, double x, double y, Colors color) {
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
    Sea(String name, double size, int power, double x, double y, Colors color, String creationDate) {
        this.name = name;
        this.size = size;
        this.power = power;
        this.x = x;
        this.y = y;
        this.color = color;
        this.creationDate = creationDate;
    }


    String toCsv() {
        return name + "," + size + "," + power + "," + x + "," + y + "," + color + "," + creationDate; }


    Object[] toArray() { return new Object[]{name, size, power, x, y, color, creationDate}; }


    @Override
    public int compareTo(Sea o) {
        return Double.compare(size,o.size);
    }
}
