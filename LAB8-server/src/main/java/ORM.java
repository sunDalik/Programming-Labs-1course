import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ORM {
    static private final String JDBC_DRIVER = "org.postgresql.Driver";
    //static private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static private final String USER = "sundalik";
    static private final String PASS = "postgres";
    static private Connection conn = null;
    static private Statement stmt = null;
    static private boolean connected = false;

    public static boolean connect(String DB_URL, String USER, String PASS) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            connected = true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            connected = false;
        }
        return connected;
    }

    public static <T> void updateRecord(T record) {
        if (connected) {
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
            List<Field> fields = Arrays.stream(record.getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class))
                    .collect(Collectors.toList());
            sql += fields.stream()
                    .map(field -> field.getDeclaredAnnotation(Column.class).name() + " = ?")
                    .collect(Collectors.joining(", "));
            idField.setAccessible(true);
            try {
                sql += " WHERE " + idField.getDeclaredAnnotation(Column.class).name() + " = " + idField.get(record) + ";";
                makePrepStmt(sql, record, fields).executeUpdate();
            } catch (IllegalAccessException | SQLException e) {
                e.printStackTrace();
            }
        } else System.out.println("No DB connected");
    }


    public static <T> void deleteRecord(T record) {
        if (connected) {
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
        } else System.out.println("No DB connected");
    }


    public static <T> List<T> selectAll(Class<T> tableClass) {
        if (connected) {
            Table table = tableClass.getDeclaredAnnotation(Table.class);
            if (table == null) {
                throw new IllegalArgumentException("No @Table annotation found.");
            }

            List<Field> fields = Arrays.stream(tableClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Column.class))
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
            } catch (SQLException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            } catch (InstantiationException e) {
                System.out.println("Table class must have an empty constructor");
                return null;
            }
        } else {
            System.out.println("No DB connected");
            return null;
        }
    }


    public static <T> Integer insertRecord(T record) {
        if (connected) {
            Table table = record.getClass().getDeclaredAnnotation(Table.class);
            if (table == null) {
                throw new IllegalArgumentException("No @Table annotation found.");
            }
            String sql = "INSERT INTO " + table.name() + " (";
            List<Field> fields = Arrays.stream(record.getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class))
                    .collect(Collectors.toList());
            sql += fields.stream()
                    .map(field -> field.getAnnotation(Column.class).name())
                    .collect(Collectors.joining(", "));
            sql += ")\nVALUES (";
            sql += fields.stream()
                    .map(field -> "?")
                    .collect(Collectors.joining(", "));
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
            sql += ") RETURNING (" + idField.getAnnotation(Column.class).name() + ");";
            ResultSet rs = null;
            try {
                rs = makePrepStmt(sql, record, fields).executeQuery();
                rs.next();
                return (Integer) rs.getObject(1);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("No DB connected");
            return -1;
        }
    }


    public static void createTable(Class<?> tableClass) {
        if (connected) {
            Table table = tableClass.getDeclaredAnnotation(Table.class);
            if (table == null) {
                throw new IllegalArgumentException("No @Table annotation found.");
            }
            try {
                stmt.executeUpdate("CREATE TABLE " + table.name() + "(\n" + createTableGetFields(tableClass) + "\n);");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else System.out.println("No DB connected");
    }

    public static void createTableIfNotExists(Class<?> tableClass) {
        if (connected) {
            Table table = tableClass.getDeclaredAnnotation(Table.class);
            if (table == null) {
                throw new IllegalArgumentException("No @Table annotation found.");
            }
            try {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + table.name() + "(\n" + createTableGetFields(tableClass) + "\n);");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else System.out.println("No DB connected");
    }

    private static String createTableGetFields(Class<?> tableClass) {
        return Arrays.stream(tableClass.getDeclaredFields())
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
    }

    private static <T> PreparedStatement makePrepStmt(String sql, T record, List<Field> fields) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            for (int i = 0; i < fields.size(); i++) {
                fields.get(i).setAccessible(true);
                Object obj = fields.get(i).get(record);
                if (fields.get(i).getType().isEnum() && obj != null) {
                    obj = obj.toString();
                }
                prepStmt.setObject(i + 1, obj);
            }
            return prepStmt;
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
