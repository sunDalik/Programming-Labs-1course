import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class RequestsHandler implements Runnable {

    private SeaManager sm;
    private Socket client;

    RequestsHandler(SeaManager sm, Socket client) {
        this.sm = sm;
        this.client = client;
    }


    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> sm.save()));
    }
}
