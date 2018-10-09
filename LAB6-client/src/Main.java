import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
        InetSocketAddress server = new InetSocketAddress("localhost", 11037);
        new Thread(new RequestsMaker(server)).run();
    }
}
