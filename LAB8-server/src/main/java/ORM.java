import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ORM {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "sundalik";
    static final String PASS = "postgres";
    static Connection conn = null;
    static Statement stmt = null;

    public static void main(String[] args) {
        //createTable(SeaTable.class);
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<SeaTable> l = selectAll(SeaTable.class);
        for (SeaTable element: l){
            System.out.println(element.getName());
        } //aIOFHUEJKLEDJSOIL;dikewomlksjfnawklslidjlk
}

    public static void createTable(Class<?> tableClass) {
        try {
            stmt.executeUpdate(createTableSql(tableClass));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> selectAll(Class<T> tableClass) {
        Table table = tableClass.getDeclaredAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("No @Table annotation found.");
        }

        List<Field> fields = Arrays.stream(tableClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
        String fieldNames = fields.stream()
                .map(field -> field.getAnnotation(Column.class).name())
                .collect(Collectors.joining(", "));
        try {
            ResultSet rs = stmt.executeQuery("SELECT " + fieldNames +" FROM " + table.name());
            List<T> list = new LinkedList<>();
            while (rs.next()){
                T record = tableClass.newInstance();
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    field.setAccessible(true);
                    Object cell = rs.getObject(i + 1);
                    cell = cell.equals("NULL")? null : cell;
                    if (field.getType().isEnum() && cell != null) {
                        cell = Enum.valueOf((Class)field.getType(), cell.toString());
                    }
                    field.set(record, cell);
                }
                list.add(record);
            }
            rs.close();
            return list;
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
    INSERT INTO seas (name, size, power, x, y, color, creation_date)
    VALUES ('name', size, power, x, y, 'color', 'creation_date');
     */
    public static <T> void insertRecord(T record) {
        Table table = record.getClass().getDeclaredAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("No @Table annotation found.");
        }
        String sql = "INSERT INTO " + table.name() + " (";

        List<Field> fields = Arrays.stream(record.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
        String fieldNames = fields.stream()
                .map(field -> field.getAnnotation(Column.class).name())
                .collect(Collectors.joining(", "));
        String fieldValues = fields.stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));
        sql += fieldNames + ")\nVALUES (" + fieldValues + ")";

        try {
            PreparedStatement sqlStatement = conn.prepareStatement(sql);
            for (int i = 0; i < fields.size(); i++) {
                fields.get(i).setAccessible(true); // private
                Object obj = fields.get(i).get(record);
                if (fields.get(i).getType().isEnum() && obj != null) {
                    obj = obj.toString();
                }
                else sqlStatement.setObject(i + 1, obj == null ? "NULL" : obj);
            }
            sqlStatement.execute();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println(sql);
    }

    public static String createTableSql(Class<?> tableClass) {
        Table table = tableClass.getDeclaredAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("No @Table annotation found.");
        }
        String fields = Arrays.stream(tableClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    String name = field.getAnnotation(Column.class).name();
                    String type = null;
                    switch (field.getType().getName()) {
                        case "int":
                        case "java.lang.Integer":
                            type = " INT";
                            break;
                        case "double":
                        case "java.lang.Double":
                            type = " DOUBLE PRECISION";
                            break;
                        case "float":
                        case "java.lang.Float":
                            type = " REAL";
                            break;
                        case "long":
                        case "java.lang.Long":
                            type = " BIGINT";
                            break;
                        case "boolean":
                        case "java.lang.Boolean":
                            type = " BOOL";
                            break;
                        case "java.lang.String":
                            type = " TEXT";
                            break;
                        default:
                            if (field.getType().isEnum()) {
                                type = " TEXT";
                            } else {
                                throw new IllegalArgumentException("Unknown field type");
                            }
                    }
                    if (field.isAnnotationPresent(Id.class)) {
                        return name + " SERIAL PRIMARY KEY";
                    } else return name + type;
                }).collect(Collectors.joining(",\n"));
        return "CREATE TABLE " + table.name() + "(\n" + fields + "\n);";
    }
}
