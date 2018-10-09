import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sea implements Comparable<Sea>, Serializable {
    private String name;
    private double size;
    private int powerLimit;
    private double latitude;
    private double longitude;
    private Date creationTimeDate;
    private String creationTime;
    private static final long serialVersionUID = 78L;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);

    {
        creationTimeDate = new Date();
        creationTime = sdf.format(creationTimeDate);
    }

    Sea() {
    }

    public Sea(String name, double size, int powerLimit, double latitude, double longitude) {
        this.name = name;
        this.size = size;
        this.powerLimit = powerLimit;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Sea(String name, double size, int powerLimit, double latitude, double longitude, String creationTime) {
        this.name = name;
        this.size = size;
        this.powerLimit = powerLimit;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creationTime = creationTime;
        try {
            creationTimeDate = sdf.parse(creationTime);
        } catch (java.text.ParseException e) {
            System.out.println(e.getMessage());
        }
    }


    public String toCsv() {
        return name + "," + size + "," + powerLimit + "," + latitude + "," + longitude + "," + creationTime;
    }

    public String toJson() {
        return "{\n" +
                "\"name\": " + name + ",\n" +
                "\"size\": " + size + ",\n" +
                "\"powerLimit\": " + powerLimit + ",\n" +
                "\"latitude\": " + latitude + ",\n" +
                "\"longitude\": " + longitude + ",\n" +
                "\"creationTime\": " + creationTime + ",\n" +
                "}";
    }

    @Override
    public int compareTo(Sea o) {
        return Double.compare(size,o.size);
    }
}
