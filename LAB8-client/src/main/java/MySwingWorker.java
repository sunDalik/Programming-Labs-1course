import javax.swing.*;
import java.net.InetSocketAddress;
import java.util.Locale;

public class MySwingWorker extends SwingWorker<Void, Void> {

    private Locale locale;

    MySwingWorker(Locale locale) {
        this.locale = locale;
    }

    @Override
    protected Void doInBackground() {
        Connector connector = new Connector(new InetSocketAddress("localhost", 11037));
        connector.start();
        new GUI(connector, locale);
        return null;
    }
}
