import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
        Connector connector = new Connector(new InetSocketAddress("localhost", 11037));
        connector.start();
        new GUI(connector);
    }
}
