import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


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
    private LocalDateTime creationDate;

    transient private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
    private static final long serialVersionUID = 78L;

    Sea() {
    }

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
        creationDate = LocalDateTime.now();
    }

    /**
     * Is used to load element from database to collection
     */
    Sea(int id, String name, double size, int power, int x, int y, Colors color, LocalDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.power = power;
        this.x = x;
        this.y = y;
        this.color = color;
        this.creationDate = creationDate;
    }


    Object[] toArray() {
        return new Object[]{name, size, power, x, y, color, creationDate.format(dtf)};
    }


    @Override
    public int compareTo(Sea o) {
        return Double.compare(size, o.size);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    LocalDateTime getCreationDate() {
        return creationDate;
    }

}