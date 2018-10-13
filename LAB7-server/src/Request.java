import java.io.Serializable;
import java.util.LinkedList;

public class Request implements Serializable{
    String command = "";
    LinkedList<Sea> collection = new LinkedList<>();
    private static final long serialVersionUID = 77L;

    public Request(String command) {
        this.command = command;
    }

    public Request(String command, LinkedList<Sea> collection) {
        this.command = command;
        this.collection = collection;
    }
}