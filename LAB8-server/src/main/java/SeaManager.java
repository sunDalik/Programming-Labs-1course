import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class SeaManager {
    private List<Sea> seaList = Collections.synchronizedList(new LinkedList<>());
    private List<Sea> removedSeas = Collections.synchronizedList(new LinkedList<>());
    private GUI gui;

    SeaManager(GUI gui) {
        this.gui = gui;
        ORM.createTableIfNotExists(Sea.class);
    }

    /**
     * Loads all elements from DB to collection
     */
    void load() {
        seaList = ORM.selectAll(Sea.class);
        if (seaList == null) seaList = Collections.synchronizedList(new LinkedList<>());
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
            removedSeas.add(seaList.get(0));
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
            removedSeas.add(seaList.get(seaList.size() - 1));
            seaList.remove(seaList.size() - 1);
            gui.removeLastRow();
            return true;
        } else {
            return false;
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
                removedSeas.add(seaList.get(i));
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
     * Saves collection to DB
     */
    void save() {
        for (Sea sea : seaList) {
            if (sea.getId() == null) sea.setId(ORM.insertRecord(sea));
            else ORM.updateRecord(sea);
        }
        for (Sea sea : removedSeas) {
            if (sea.getId() != null) ORM.deleteRecord(sea);
        }
    }

    List<Sea> getCollection() {
        return seaList;
    }
}