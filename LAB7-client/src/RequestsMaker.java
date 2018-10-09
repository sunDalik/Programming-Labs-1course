import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.*;

public class RequestsMaker implements Runnable {
    static boolean isWorking = true;
    private SocketAddress server;

    public RequestsMaker(SocketAddress server) {
        this.server = server;
    }

    public void run() {
        while (true) {
            try {
                SocketChannel sc = connect();
                ObjectOutputStream oos = new ObjectOutputStream(sc.socket().getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(sc.socket().getInputStream());
                isWorking = true;
                System.out.println("Сервер доступен и готов принимать команды.\n(Введите help для получения списка доступных команд)");
                while (true) {
                    Scanner in = new Scanner(System.in);
                    String command = "";
                    try {
                        command = in.next();
                    } catch (NoSuchElementException e) {
                        System.exit(0);
                    }

                    switch (command) {
                        case ("import"):
                            System.out.println("Введите путь к файлу");
                            try {
                                String path = in.next();
                                Scanner importer = new Scanner(new File(path));
                                importer.useDelimiter("[,\n]");
                                LinkedList<Sea> collectionToImport = new LinkedList<>();
                                while (importer.hasNext()) {
                                    collectionToImport.add(new Sea(importer.next(), Double.parseDouble(importer.next()), importer.nextInt(), Double.parseDouble(importer.next()), Double.parseDouble(importer.next()), importer.next()));
                                }
                                importer.close();
                                oos.writeObject(new Request(command, collectionToImport));
                                System.out.println(ois.readObject());
                            } catch (FileNotFoundException e) {
                                System.out.println("Некорректный путь к файлу либо доступ к чтению закрыт.");
                            }
                            break;

                        case ("load"):
                            oos.writeObject(new Request(command));
                            break;

                        case ("remove_greater"):
                        case ("add_if_min"):
                        case ("add"):
                            System.out.println("Введите объект в формате {\n" +
                                    "\"name\": String,\n" +
                                    "\"size\": double,\n" +
                                    "\"powerLimit\": int,\n" +
                                    "\"latitude\": double,\n" +
                                    "\"longitude\": double\n" +
                                    "}");
                            try {
                                String objStr = "";
                                String str = in.nextLine();
                                while (true) {
                                    objStr += str;
                                    if (str.contains("}")) {
                                        break;
                                    }
                                    str = in.nextLine();
                                }
                                JSONParser jp = new JSONParser();
                                JSONObject jo = (JSONObject) jp.parse(objStr);
                                Sea seaObj = new Sea((String) jo.get("name"), ((Number) jo.get("size")).doubleValue(), ((Long) jo.get("powerLimit")).intValue(), ((Number) jo.get("latitude")).doubleValue(), ((Number) jo.get("longitude")).doubleValue());

                                oos.writeObject(new Request(command, seaObj));
                                System.out.println(ois.readObject());
                            } catch (ParseException e) {
                                System.out.println("Неверный формат");
                            }
                            break;

                        case ("read"):
                        case ("help"):
                        case ("info"):
                        case ("remove_last"):
                        case ("remove_first"):
                        case ("sort"):
                        case ("save"):
                            oos.writeObject(new Request(command));
                            System.out.println(ois.readObject());
                            break;

                        case ("download"):
                            System.out.println("Введите путь к файлу, в который нужно сохранить коллекцию.");
                            PrintWriter pw;
                            try {
                                pw = new PrintWriter(new Scanner(System.in).next());
                                oos.writeObject(new Request(command));
                                pw.print(ois.readObject());
                                pw.close();
                                System.out.println("Коллекция сохранена.");
                            } catch (FileNotFoundException e) {
                                System.out.println("Файл не найден.");
                            }
                            break;

                        case ("exit"):
                            System.exit(0);
                    }
                    System.out.println("Введите следующую команду");
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException ex) {
                if (isWorking) {
                    System.out.println("Не удается подключиться к серверу. Ожидайте...");
                    isWorking = false;
                }
            }
        }
    }

    private SocketChannel connect() {
        SocketChannel sc;
        try {
            sc = SocketChannel.open(server);
        } catch (IOException e) {
            if (isWorking) {
                System.out.println("Не удается подключиться к серверу. Ожидайте...");
                isWorking = false;
            }
            while (true) {
                try {
                    Thread.sleep(1000);
                    sc = SocketChannel.open(server);
                    break;
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (IOException exx) {
                }
            }
        }
        return sc;
    }
}




/*
 {
    "name": "asdfgh",
    "size": 44.002,
    "powerLimit": 434,
    "latitude": 1.11,
    "longitude": 9.9
     }
 */

/*
someSea,23.9,22,2.242,64.352,23-05-2018 17:54:40
Zelda,11.633,3,2.22,24.6457,21-05-2018 18:53:26
skyther,253.9264,53,822.64,855.21,20-05-2018 12:53:12
nuclear,10.8237,11,48.65,44.22,20-05-2018 11:53:53
moomin,37.267,63,23.86,23.86,15-05-2018 14:53:23
andrey,943.66,202,854.55,835.55,17-05-2018 13:53:53
lesha,36.456,631,223.44,123.56,11-05-2018 19:53:39
timofey,257.435,112,74,34,21-05-2018 20:53:22
vanya,827.29,980,372,73.5,06-05-2018 23:53:11
ivan,11.11,455,36.4,84.22,03-05-2018 11:53:09
vanek,123.321,88,243,66.7,14-05-2018 14:53:04
 */

/*
tok,36.456,631,223.44,123.56,11-05-2018 19:53:39
kot,257.435,112,74,34,21-05-2018 20:53:22
 */