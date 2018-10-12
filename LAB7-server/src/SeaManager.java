import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SeaManager {
    private List<Sea> seaList = Collections.synchronizedList(new LinkedList<Sea>());
    private String file;
    private GUI gui;

    public SeaManager(String file, GUI gui) {
        this.file = file;
        this.gui = gui;
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
            try {
                new File(file).createNewFile();
            } catch (IOException ee) {
                System.out.println(ee.getMessage());
                System.exit(1);
            }
        }
        gui.refreshTable(seaList);
    }

    /**
     * Сортировка коллекции по возрастанию size моря
     */
    public boolean sort() {
        if (seaList.size() > 0) {
            seaList = seaList.stream().sorted(Sea::compareTo).collect(Collectors.toList());
            //Collections.sort(seaList);
            gui.refreshTable(seaList);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Удаляет первый элемент коллекции
     */
    public boolean remove_first() {
        if (seaList.size() > 0) {
            seaList.remove(0);
            gui.refreshTable(seaList);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Удаляет последний элемент коллекции
     */
    public boolean remove_last() {
        if (seaList.size() > 0) {
            seaList.remove(seaList.size() - 1);
            gui.removeLastRow();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Добавляет в коллекцию все элементы другой коллекции
     */
    public void importCollection() {
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new java.io.File("."));
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File collectionToImportFile = jfc.getSelectedFile();
            LinkedList<Sea> collectionToImport = new LinkedList<Sea>();
            try {
                Scanner importLoader = new Scanner(collectionToImportFile);
                importLoader.useDelimiter("[,\n]");
                while (importLoader.hasNext()) {
                    collectionToImport.add(new Sea(importLoader.next(), Double.parseDouble(importLoader.next()), importLoader.nextInt(), Double.parseDouble(importLoader.next()), Double.parseDouble(importLoader.next()), Colors.valueOf(importLoader.next()), importLoader.next()));
                }
                importLoader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Файл был удален");
            }
            catch (IllegalArgumentException | InputMismatchException e){
                System.out.println("Неправильный формат коллекции!");
            }
            for (Sea sea : collectionToImport) {
                seaList.add(sea);
                gui.addToTable(sea);
            }
        }
    }

    /**
     * Удаляет все элементы, превышающие по значению заданный
     *
     */
    int remove_greater(Sea object) {
        int n = 0;
        for (int i = seaList.size() - 1; i >= 0; i--) {
            if (seaList.get(i).compareTo(object) > 0) {
                seaList.remove(i);
                n ++;
            }
        }
        gui.refreshTable(seaList);
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
            gui.addToTable(object);
            return true;
        }
        if (object.compareTo(SeaStreamSupplier.get().min(Sea::compareTo).get()) < 0) {
            seaList.add(object);
            gui.addToTable(object);
            return true;
        } else return false;
    }

    /**
     * Добавляет новый объект в коллекцию
     */
    void add(Sea object) {
        seaList.add(object);
        gui.addToTable(object);
    }

    /**
     * возвращает объекты коллекции в формате csv
     */
    String read() {
        Stream<Sea> SeaStream = seaList.stream();
        return SeaStream.map(s -> s.toCsv() + "\n").collect(Collectors.joining());
    }

    void save() {
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.print(read());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public List<Sea> getSeaList(){ return seaList; }


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
