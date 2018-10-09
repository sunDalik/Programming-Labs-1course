import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.channels.CompletionHandler;
import java.util.*;


public class SeaManager {
    File MyCsv;
    private List<Sea> seaList = new LinkedList<Sea>();
    private Date initDate;

    public SeaManager(){
        initDate = new Date();
    }

    /**
     * Загрузка коллекции из файла MyScv (Первый аргумент командной строки)
     * @throws FileNotFoundException
     */
    public void load() throws FileNotFoundException {
        seaList.clear();
        Scanner scanner = new Scanner(MyCsv);
        scanner.useDelimiter("[,\n]");
        while(scanner.hasNext()){
            seaList.add(new Sea(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.next()));
        }
        scanner.close();
    }

    /**
     * Сортировка коллекции по возрастанию powerLimit моря
     */
    public void sort(){
        Collections.sort(seaList);
    }

    /**
     * Удаляет первый элемент коллекции
     */
    public void remove_first(){
        if(seaList.size() > 0) {
            seaList.remove(0);
        }
    }

    /**
     * Удаляет последний элемент коллекции
     */
    public void remove_last(){
        if (seaList.size() > 0) {
            seaList.remove(seaList.size() - 1);
        }
    }

    /**
     * Выводит информацию о коллекции
     * @return info - Тип коллекции, кол-во элементов в ней и дата ее инициализации
     */
    public String info(){
        String info = "";
        info += "Тип: " + seaList.getClass() + "\n";
        info += "Количество элементов: " + seaList.size() + "\n";
        info += "Дата инициалазации: " + initDate + "\n";
        return info;
    }

    /**
     * Сохраняет коллекцию в файл MyScv (Первый аргумент командной строки)
     */
    public void save(){
        try{
            PrintWriter pw = new PrintWriter(MyCsv);
            for (Sea sea : seaList) {
                pw.write(sea.toCsv() + "\n");
            }
            pw.close();
        } catch (Exception e){
            System.out.println("Нет такого файла или доступ к записи закрыт.");
            return;
        }

    }

    /**
     * Добавляет элементы в коллекцию из файла
     * @param path - путь к файлу из которого происходит импорт
     * @throws FileNotFoundException
     */
    public void importFrom(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        scanner.useDelimiter("[,\n]");
        while(scanner.hasNext()){
            seaList.add(new Sea(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.next()));
        }
        scanner.close();
    }

    /**
     * Удаляет все элементы, превышающие по значению заданный
     * @return n - число удаленных элементов
     */
    public int remove_greater() throws ParseException{
        Scanner in = new Scanner(System.in);
        String ObjStr = "";
        String str = in.nextLine();
        while (!(str).equals("")) {
            ObjStr += str;
            str = in.nextLine();
        }
        JSONParser jp = new JSONParser();
        JSONObject jo = (JSONObject) jp.parse(ObjStr);
        Sea Obj = new Sea( ((Long)jo.get("powerLimit")).intValue(), ((Long)jo.get("waveN")).intValue(), ((Long)jo.get("wavePower")).intValue(), (String)jo.get("waveLocation"));
        int n = 0;
        System.out.println(Obj);
        for (int i = seaList.size() -1; i >= 0 ;i--){
            if(seaList.get(i).compareTo(Obj) > 0){
                seaList.remove(i);
                n++;
            }
        }
        return n;
    }

    /**
     * Добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента
     * @return true - элемент добавлен, false - НЕ добавлен
     */
    public boolean add_if_min() throws ParseException{
        Scanner in = new Scanner(System.in);
        String ObjStr = "";
        String str = in.nextLine();
        while (!(str).equals("")) {
            ObjStr += str;
            str = in.nextLine();
        }
        JSONParser jp = new JSONParser();
        JSONObject jo = (JSONObject) jp.parse(ObjStr);
        Sea Obj = new Sea( ((Long)jo.get("powerLimit")).intValue(), ((Long)jo.get("waveN")).intValue(), ((Long)jo.get("wavePower")).intValue(), (String)jo.get("waveLocation"));

        List<Sea> seaListTemp = new LinkedList<>(seaList);
        Collections.sort(seaListTemp);
        if(seaListTemp.size() > 0) {
            if (Obj.compareTo(seaListTemp.get(0)) < 0) {
                seaList.add(Obj);
                return true;
            }
            return false;
        }
        else {
            seaList.add(Obj);
            return  true;
        }
    }

    /**
     * Выводит объекты коллекции в формате csv
     */
    public void read(){
        for (Sea sea:seaList){
            System.out.println(sea.toCsv());
        }
    }



    /*
    {
"powerLimit": 0,
"waveN": 1,
"wavePower": 2,
"waveLocation": "errror"
}

{
"powerLimit": 99999,
"waveN": 1,
"wavePower": 2,
"waveLocation": "big"
}
     */

    // C:\Users\Далик\Desktop\bbb.csv

    /*
    1,3,12,sidajdi
4,1,9,uewiwjew
9,6,11,oeuwjioads
98,12,1,ksdjsa
2,6,99,saidsdsaw
98,1,87,disjandi
35,6,1,sidjs
8,8,8,kdsj
     */

    /*
    9,1,999,hidsakda
1000,1212,737283,oowda
0,3278,78237,saidadjs
9128,1,1,sojds
     */
}
