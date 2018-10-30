import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

public class Connector extends Thread {

    private SocketAddress server;

    Connector(SocketAddress server) {
        this.server = server;
    }

    /**
     * Trying to connect infinitely to notify user when server is off without refreshing
     */
    @Override
    public void run() {
        connect();
        while (true) {
            connect();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    List<Sea> getCollection() {
        try {
            SocketChannel sc = SocketChannel.open(server);
            ObjectOutputStream oos = new ObjectOutputStream(sc.socket().getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(sc.socket().getInputStream());
            oos.writeObject("Get");
            try {
                GUI.setConnectionInfo(true);
                return (List<Sea>) ois.readObject();
            } catch (ClassNotFoundException e) {
                return null;
            }
        } catch (IOException e) {
            GUI.setConnectionInfo(false);
            return null;
        }
    }

    private void connect() {
        try {
            SocketChannel.open(server);
        } catch (IOException e) {
            GUI.setConnectionInfo(false);
            while (true) {
                try {
                    Thread.sleep(1000);
                    SocketChannel.open(server);
                    GUI.setConnectionInfo(true);
                    break;
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (IOException exx) {
                    //No connection. Trying again.
                }
            }
        }
    }
}
