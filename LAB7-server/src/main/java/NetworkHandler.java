import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkHandler extends SwingWorker<Void, Void> {
    private SeaManager sm;

    NetworkHandler(SeaManager sm) {
        this.sm = sm;
    }

    @Override
    protected Void doInBackground() {
        try (ServerSocket server = new ServerSocket(11037)) {
            while (true) {
                Socket client = server.accept();
                new Thread(new RequestsHandler(sm, client)).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
