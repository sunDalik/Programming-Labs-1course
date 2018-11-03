import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkHandler extends SwingWorker<Void, Void> {

    NetworkHandler() {

    }

    @Override
    protected Void doInBackground() {
        Connector connector = new Connector(new InetSocketAddress("localhost", 11037));
        connector.start();
        new GUI(connector);
        return null;
    }
}
