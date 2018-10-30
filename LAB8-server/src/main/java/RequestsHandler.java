import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestsHandler implements Runnable {

    private SeaManager sm;
    private Socket client;

    RequestsHandler(SeaManager sm, Socket client) {
        this.sm = sm;
        this.client = client;
    }

    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            String request;
            while (true) {
                try {
                    request = (String) ois.readObject();
                } catch (ClassNotFoundException e) {
                    request = "";
                }
                if (request.equals("Get")) {
                    oos.writeObject(sm.getCollection());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
