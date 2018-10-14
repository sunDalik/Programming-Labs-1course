import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class SeaManager {
    private List<Sea> seaList = Collections.synchronizedList(new LinkedList<>());
    private String file;
    private GUI gui;

    SeaManager(String file, GUI gui) {
        this.file = file;
        this.gui = gui;
    }

    /**
     * Loads all elements from file to collection
     */
    void load() {
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
     * Sorts elements by size value
     *
     * @return false if collection is empty
     */
    boolean sort() {
        if (seaList.size() > 0) {
            seaList = seaList.stream().sorted(Sea::compareTo).collect(Collectors.toList());
            //Collections.sort(seaList);
            gui.refreshTable(seaList);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes the first element of collection
     *
     * @return false if collection is empty
     */
    boolean remove_first() {
        if (seaList.size() > 0) {
            seaList.remove(0);
            gui.refreshTable(seaList);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes the last element of collection
     *
     * @return false if collection is empty
     */
    boolean remove_last() {
        if (seaList.size() > 0) {
            seaList.remove(seaList.size() - 1);
            gui.removeLastRow();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds all elements from file you chose
     */
    void importCollection() {
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new java.io.File("."));
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File collectionToImportFile = jfc.getSelectedFile();
            LinkedList<Sea> collectionToImport = new LinkedList<>();
            try {
                Scanner importLoader = new Scanner(collectionToImportFile);
                importLoader.useDelimiter("[,\n]");
                while (importLoader.hasNext()) {
                    collectionToImport.add(new Sea(importLoader.next(), Double.parseDouble(importLoader.next()), importLoader.nextInt(), Double.parseDouble(importLoader.next()), Double.parseDouble(importLoader.next()), Colors.valueOf(importLoader.next()), importLoader.next()));
                }
                importLoader.close();
            } catch (FileNotFoundException e) {
                gui.printToConsole("Файл был удален", true);
            } catch (IllegalArgumentException | InputMismatchException e) {
                gui.printToConsole("Неправильный формат коллекции!", true);
            }
            for (Sea sea : collectionToImport) {
                seaList.add(sea);
                gui.addToTable(sea);
            }
        }
    }

    /**
     * Deletes all elements greater than it
     *
     * @return number of deleted elements
     */
    int remove_greater(Sea object) {
        int n = 0;
        for (int i = seaList.size() - 1; i >= 0; i--) {
            if (seaList.get(i).compareTo(object) > 0) {
                seaList.remove(i);
                n++;
            }
        }
        gui.refreshTable(seaList);
        return n;
    }

    /**
     * Adds new element to collection if there are no other elements smaller or equal to it
     *
     * @return true - element added, false - not added
     */
    boolean add_if_min(Sea object) {
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
     * Adds new element to collection
     */
    void add(Sea object) {
        seaList.add(object);
        gui.addToTable(object);
    }

    /**
     * @return elements of collection in csv
     */
    private String read() {
        Stream<Sea> SeaStream = seaList.stream();
        return SeaStream.map(s -> s.toCsv() + "\n").collect(Collectors.joining());
    }

    /**
     * Saves collection to file
     */
    void save() {
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.print(read());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    List<Sea> getCollection() {
        return seaList;
    }
}