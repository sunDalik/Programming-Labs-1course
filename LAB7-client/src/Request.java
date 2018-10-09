import java.io.Serializable;
import java.util.LinkedList;

public class Request implements Serializable{
    private String command;
    private LinkedList<Sea> collection;
    private Sea object;
    private static final long serialVersionUID = 77L;

    public Request(String command) {
        this.command = command;
    }

    public Request(String command, LinkedList<Sea> collection) {
        this.command = command;
        this.collection = collection;
    }

    public Request(String command, Sea object) {
        this.command = command;
        this.object = object;
    }
}
