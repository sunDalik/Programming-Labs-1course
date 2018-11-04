public class Main {
    public static void main(String[] args) {
        if (ORM.connect("jdbc:postgresql://localhost:5432/postgres", "sundalik", "postgres")) {
            new Auth();
        } else {
            System.out.println("Unable to connect to DB");
            System.exit(1);
        }
    }
}
