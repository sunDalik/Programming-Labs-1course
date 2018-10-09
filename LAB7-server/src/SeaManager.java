import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SeaManager {
    private List<Sea> seaList = Collections.synchronizedList(new LinkedList<Sea>());
    private Date initDate;
    private String file;

    public SeaManager(String file) {
        initDate = new Date();
        this.file = file;
    }

    /**
     * Загрузка коллекции из файла
     */
    public void load() {
        try {
            Scanner FileLoader = new Scanner(new File(file));
            FileLoader.useDelimiter("[,\n]");
            seaList.clear();
            while (FileLoader.hasNext()) {
                seaList.add(new Sea(FileLoader.next(), Double.parseDouble(FileLoader.next()), FileLoader.nextInt(), Double.parseDouble(FileLoader.next()), Double.parseDouble(FileLoader.next()), Colors.valueOf(FileLoader.next()), FileLoader.next()));
            }
            FileLoader.close();
        } catch (FileNotFoundException e) {
            System.out.println("В качестве аргумента передан некорректный путь к файлу либо доступ к его чтению закрыт.");
            System.exit(1);
        }
    }

    /**
     * Сортировка коллекции по возрастанию size моря
     */
    public void sort() {
        seaList = seaList.stream().sorted(Sea::compareTo).collect(Collectors.toList());
        //Collections.sort(seaList);
    }

    /**
     * Удаляет первый элемент коллекции
     */
    public String remove_first() {
        Stream<Sea> SeaStream = seaList.stream();
        if (SeaStream.limit(1).count() > 0) {
            Sea outputObj = seaList.get(0);
            seaList.remove(0);
            return outputObj.toCsv();
        } else {
            return "null";
        }
    }

    /**
     * Удаляет последний элемент коллекции
     */
    public String remove_last() {
        Supplier<Stream<Sea>> SeaStream = () -> seaList.stream();
        if (SeaStream.get().limit(1).count() > 0) {
            Sea outputObj = seaList.get((int) SeaStream.get().count() - 1);
            seaList.remove((int) SeaStream.get().count() - 1);
            return outputObj.toCsv();
        } else {
            return "null";
        }
    }

    /**
     * Выводит информацию о коллекции
     *
     * @return info - Тип коллекции, кол-во элементов в ней и дата ее инициализации
     */
    public String info() {
        Stream<Sea> SeaStream = seaList.stream();
        String info = "";
        info += "Тип: " + seaList.getClass() + "\n";
        info += "Количество элементов: " + SeaStream.count() + "\n";
        info += "Дата инициалазации: " + initDate + "\n";
        return info;
    }

    /**
     * Добавляет в коллекцию все элементы другой коллекции
     */
    public void importCollection(LinkedList<Sea> collectionToImport) {
        seaList.addAll(collectionToImport);
    }

    /**
     * Удаляет все элементы, превышающие по значению заданный
     *
     * @return n - число удаленных элементов
     */
    public int remove_greater(Sea object) {
        Supplier<Stream<Sea>> SeaStream = () -> seaList.stream();
        int n = 0;
        if (SeaStream.get().limit(1).count() == 0) return 0;
        for (int i = (int) SeaStream.get().count() - 1; i >= 0; i--) {
            if (seaList.get(i).compareTo(object) > 0) {
                seaList.remove(i);
                n++;
            }
        }
        return n;
    }

    /**
     * Добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента
     *
     * @return true - элемент добавлен, false - НЕ добавлен
     */
    public boolean add_if_min(Sea object) {
        Supplier<Stream<Sea>> SeaStreamSupplier = () -> seaList.stream();

        if (SeaStreamSupplier.get().limit(1).count() == 0) {
            seaList.add(object);
            return true;
        }
        if (object.compareTo(SeaStreamSupplier.get().min(Sea::compareTo).get()) < 0) {
            seaList.add(object);
            return true;
        } else return false;
    }

    /**
     * Добавляет новый объект в коллекцию
     */
    public void add(Sea object) {
        seaList.add(object);
    }

    /**
     * возвращает объекты коллекции в формате csv
     */
    public String read() {
        Stream<Sea> SeaStream = seaList.stream();
        return SeaStream.map(s -> s.toCsv() + "\n").collect(Collectors.joining());
    }

    /**
     * Выводит список всех доступных командыы
     */
    public String help() {
        return "Список команд: load, exit, sort, remove_first, remove_last, info, import, download, save, remove_greater, add_if_min, add, read, help";
    }

    public void save() {
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.print(read());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    /*
    {
    "name": "someSea",
    "size": 23,
    "powerLimit": 2,
    "latitude": 2.22,
    "longitude": 1.22
     }
*/

    //C:\Users\Далик\Desktop\aaa.csv
    // C:\Users\Далик\Desktop\bbb.csv


}
