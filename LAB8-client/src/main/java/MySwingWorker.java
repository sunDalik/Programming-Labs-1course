import javax.swing.*;
import java.net.InetSocketAddress;

public class MySwingWorker extends SwingWorker<Void, Void> {

    MySwingWorker() {

    }

    @Override
    protected Void doInBackground() {
        Connector connector = new Connector(new InetSocketAddress("localhost", 11037));
        connector.start();
        new GUI(connector);
        return null;
    }
}
