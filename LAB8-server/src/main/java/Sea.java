import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Retention(RetentionPolicy.RUNTIME)
@interface Table{
    String name();
}

@Retention(RetentionPolicy.RUNTIME)
@interface  Id{

}

@Retention(RetentionPolicy.RUNTIME)
@interface Column{
    String name();
}


@Table(name = "seas")
public class Sea implements Serializable, Comparable<Sea> {
    @Id
    @Column(name = "sea_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private Double size;

    @Column(name = "power")
    private Integer power;

    @Column(name = "x")
    private Integer x;

    @Column(name = "y")
    private Integer y;

    @Column(name = "color")
    private Colors color;

    @Column(name = "creation_date")
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
     * Is used to load element from database to collection
     */
    Sea(int id, String name, double size, int power, int x, int y, Colors color, String creationDate) {
        this.id = id;
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

    String getName() {
        return name;
    }

    double getSize() {
        return size;
    }

    int getPower() {
        return power;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    Colors getColor() {
        return color;
    }

    String getStringDate() {
        return creationDate;
    }

    Date getDate() {
        try {
            return sdf.parse(creationDate);
        } catch (ParseException e) {
            return new Date();   //this will simply never happen I guess :^)
        }
    }
}