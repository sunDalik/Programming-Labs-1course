

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main  {

    public static void main(String[] args) {
        SeaManager sm = new SeaManager();
        try {
            sm.MyCsv = new File(args[0]);
            sm.load();
        } catch (Exception e){
            System.out.println("Введите правильное имя файла в качестве аргумента командной строки, либо откройте доступ к чтению файла.");
            System.exit(1);
        }

        System.out.println("Введите команду (load, exit, sort, remove_first, remove_last, info, import, save, remove_greater, add_if_min, read, help)");
    loop: while(true){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> { sm.save(); }));

        Scanner in = new Scanner(System.in);
        String command = "";
        try {
            command = in.next();
        } catch (NoSuchElementException e){
            sm.save();
            System.exit(1);}
        switch(command) {
            case ("load"):
                try {
                    sm.load();
                } catch (Exception e){
                    System.out.println("Нет такого файла или доступ к чтению закрыт.");
                }
                break;
            case ("exit"):
                break loop;
            case ("sort"):
                sm.sort();
                break;
            case ("remove_first"):
                sm.remove_first();
                break;
            case ("remove_last"):
                sm.remove_last();
                break;
            case ("info"):
                System.out.println(sm.info());
                break;
            case("import"):
                System.out.println("Введите путь к файлу");
                try {
                    sm.importFrom(in.next());
                } catch (Exception e){
            System.out.println("Нет такого файла или доступ к чтению закрыт.");
        }
                break;
            case("save"):
                sm.save();
                break;
            case("remove_greater"):
                System.out.println("Введите объект в формате {\n" +
                        "\"powerLimit\": int,\n" +
                        "\"waveN\": int,\n" +
                        "\"wavePower\": int,\n" +
                        "\"waveLocation\": String\n" +
                        "}");
                try{
                    System.out.println("Удалено " + sm.remove_greater() + " элементов");
                } catch (Exception e){
                    System.out.println("Неверный формат");
                }
                break;
            case("add_if_min"):
                System.out.println("Введите объект в формате {\n" +
                        "\"powerLimit\": int,\n" +
                        "\"waveN\": int,\n" +
                        "\"wavePower\": int,\n" +
                        "\"waveLocation\": String\n" +
                        "}");
                try {
                    if(sm.add_if_min()){
                        System.out.println("Объект добавлен");
                    }
                    else{
                        System.out.println("Объект НЕ добавлен");
                    }
                } catch (Exception e){
                    System.out.println("Неверный формат");
                }
                break;
            case("read"):
                sm.read();
                break;
            case("help"):
                System.out.println("Список команд: load, exit, sort, remove_first, remove_last, info, import, save, remove_greater, add_if_min, read, help");
                break;
        }
        System.out.println("Введите следующую команду");
    }

    }
}
