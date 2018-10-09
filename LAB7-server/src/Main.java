import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        new GUI();

        SeaManager sm = null;
        try {
            sm = new SeaManager(args[0]);
        } catch (Exception e){
            System.out.println("Введите правильное имя файла в качестве аргумента командной строки.");
            System.exit(1);
        }
        //sm.load();
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
