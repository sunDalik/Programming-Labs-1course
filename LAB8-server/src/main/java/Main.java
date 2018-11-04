public class Main {
    public static void main(String[] args) {
        try {
            //on helios: jdbc:postgresql://pg:5432/studs?currentSchema=s243864
            //local: jdbc:postgresql://localhost:5432/postgres
            //db on helios, server+client locally: jdbc:postgresql://localhost:5432/studs?currentSchema=s243864
            //in this case you also have to launch forward port like this: ssh -L 5432:pg:5432 helios
            if (ORM.connect(args[0], args[1], args[2])) {
                new Auth();
            } else {
                System.out.println("Unable to connect to DB");
                System.exit(1);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Pass 3 arguments: DB Url, Login and Password");
            System.exit(1);
        }
    }
}
