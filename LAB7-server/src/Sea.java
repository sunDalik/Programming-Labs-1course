import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sea implements Comparable<Sea>, Serializable {
    private String name;
    private double size;
    private int powerLimit;
    private double x;
    private double y;
    private Colors color;
    private String creationDate;
    private static final long serialVersionUID = 78L;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    Sea() { }

    public Sea(String name, double size, int powerLimit, double x, double y, Colors color) {
        this.name = name;
        this.size = size;
        this.powerLimit = powerLimit;
        this.x = x;
        this.y = y;
        this.color = color;
        creationDate = sdf.format(new Date());
    }

    public Sea(String name, double size, int powerLimit, double x, double y, Colors color, String creationDate) {
        this.name = name;
        this.size = size;
        this.powerLimit = powerLimit;
        this.x = x;
        this.y = y;
        this.color = color;
        this.creationDate = creationDate;
    }


    public String toCsv() {
        return name + "," + size + "," + powerLimit + "," + x + "," + y + "," + color + "," + creationDate;
    }

    public Object[] toArray() { return new Object[]{name, size, powerLimit, x, y, color, creationDate}; }

    @Override
    public int compareTo(Sea o) {
        return Double.compare(size,o.size);
    }
}
