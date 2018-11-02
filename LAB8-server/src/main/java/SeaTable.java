import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
public class SeaTable implements Serializable {
    @Id
    @Column(name = "sea_id")
    private int id;

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

    public SeaTable() {}

    public String getName() {
        return name;
    }
}