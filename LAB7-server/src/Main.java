import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static SeaManager sm = new SeaManager("collection.csv", new GUI());

    public static void main(String[] args) {
        sm.load();
        try {
            try (ServerSocket server = new ServerSocket(11037)) {
                while (true) {
                    Socket client = server.accept();
                    new Thread(new RequestsHandler(sm, client)).start();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
