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
    }

    public static <T> void updateRecord(T record) {
        Table table = record.getClass().getDeclaredAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("No @Table annotation found.");
        }
        Field idField = null;
        for (Field field : record.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(Column.class)) {
                idField = field;
                break;
            }
        }
        if (idField == null) {
            throw new IllegalArgumentException("No @Id && @Column annotation found");
        }
        String sql = "UPDATE " + table.name() + " SET ";
        sql += Arrays.stream(record.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class))
                .map(field -> field.getDeclaredAnnotation(Column.class).name() + " = ?")
                .collect(Collectors.joining(", "));
        idField.setAccessible(true);
        try {
            sql += " WHERE " + idField.getDeclaredAnnotation(Column.class).name() + " = " + idField.get(record) + ";";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        executePrepStmt(sql, record);
    }


    public static <T> void deleteRecord(T record) {
        Table table = record.getClass().getDeclaredAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("No @Table annotation found.");
        }
        String sql = "DELETE FROM " + table.name() + " WHERE ";
        Field idField = null;
        for (Field field : record.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(Column.class)) {
                idField = field;
                break;
            }
        }
        if (idField == null) {
            throw new IllegalArgumentException("No @Id && @Column annotation found");
        } else {
            idField.setAccessible(true);
            try {
                sql += idField.getDeclaredAnnotation(Column.class).name() + " = " + idField.get(record) + ";";
                stmt.executeUpdate(sql);
            } catch (SQLException | IllegalAccessException e) {
                e.printStackTrace();
            }
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
            ResultSet rs = stmt.executeQuery("SELECT " + fieldNames + " FROM " + table.name() + ";");
            List<T> list = new LinkedList<>();
            while (rs.next()) {
                T record = tableClass.newInstance();
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    field.setAccessible(true);
                    Object cell = rs.getObject(i + 1);
                    cell = cell.equals("NULL") ? null : cell;
                    if (field.getType().isEnum() && cell != null) {
                        cell = Enum.valueOf((Class) field.getType(), cell.toString());
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


    public static <T> void insertRecord(T record) {
        Table table = record.getClass().getDeclaredAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("No @Table annotation found.");
        }
        String sql = "INSERT INTO " + table.name() + " (";
        sql += Arrays.stream(record.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class))
                .map(field -> field.getAnnotation(Column.class).name())
                .collect(Collectors.joining(", "));
        sql += ")\nVALUES (";
        sql += Arrays.stream(record.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class))
                .map(field -> "?")
                .collect(Collectors.joining(", "));
        sql += ");";
        executePrepStmt(sql, record);
    }


    public static void createTableSql(Class<?> tableClass) {
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
        try {
            stmt.executeUpdate("CREATE TABLE " + table.name() + "(\n" + fields + "\n);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public <T> void executePrepStmt(String sql, T record){
        try {
            List<Field> fields = Arrays.stream(record.getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class))
                    .collect(Collectors.toList());
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            for (int i = 0; i < fields.size(); i++) {
                fields.get(i).setAccessible(true);
                Object obj = fields.get(i).get(record);
                if (fields.get(i).getType().isEnum() && obj != null) {
                    obj = obj.toString();
                }
                prepStmt.setObject(i + 1, obj);
            }
            prepStmt.execute();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
