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

        try {
            Request request;
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

            while (true) {

                try {
                    request = (Request) ois.readObject();
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    request = new Request("");
                }
                String command = request.command;
                LinkedList<Sea> collection = request.collection;
                Sea object = request.object;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String temp;
                        try{
                        switch (command) {
                            case ("load"):
                                sm.load();
                                break;
                            case ("sort"):
                                sm.sort();
                                oos.writeObject("Коллекция отсортирована.");
                                break;
                            case ("remove_first"):
                                temp = sm.remove_first();
                                if (temp.equals("null")) {
                                    oos.writeObject("Коллекция пуста");
                                } else {
                                    oos.writeObject("Удален объект: " + temp);
                                }
                                break;
                            case ("remove_last"):
                                temp = sm.remove_last();
                                if (temp.equals("null")) {
                                    oos.writeObject("Коллекция пуста");
                                } else {
                                    oos.writeObject("Удален объект: " + temp);
                                }
                                break;
                            case ("info"):
                                oos.writeObject(sm.info());
                                break;
                            case ("import"):
                                sm.importCollection(collection);
                                oos.writeObject("Коллекция обновлена.");
                                break;
                            case ("remove_greater"):
                                oos.writeObject("Удалено " + sm.remove_greater(object) + " объектов.");
                                break;
                            case ("add_if_min"):
                                if (sm.add_if_min(object)) {
                                    oos.writeObject("Объект добавлен");
                                } else {
                                    oos.writeObject("Объект НЕ добавлен");
                                }
                                break;
                            case ("add"):
                                sm.add(object);
                                oos.writeObject("Объект добавлен");
                                break;
                            case ("read"):
                            case ("download"):
                                oos.writeObject(sm.read());
                                break;
                            case ("save"):
                                sm.save();
                                oos.writeObject("Коллекция сохранена на сервере.");
                                break;
                            case ("help"):
                                oos.writeObject(sm.help());
                                break;
                        }
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
