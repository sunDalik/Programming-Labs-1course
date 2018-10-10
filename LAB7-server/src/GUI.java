import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JFrame.EXIT_ON_CLOSE;


class GUI extends JFrame {

    private DefaultTableModel collectionModel;
    private JComboBox<Colors> colorsbox;
    private JTextField nameField;
    private JTextField sizeField;
    private JTextField powerField;
    private JSlider xSlider;
    private JSlider ySlider;
    private JLabel operationResult;

    GUI() {
        //Кнопка File в верхней строке
        JMenuBar menuBar = new JMenuBar();
        UIManager.put("Menu.font", new Font("Helvetica", Font.PLAIN, 16));
        UIManager.put("MenuItem.font", new Font("Helvetica", Font.PLAIN, 16));
        UIManager.put("TextField.font", new Font("Helvetica", Font.PLAIN, 15));
        JMenu file = new JMenu("File");
        JMenuItem load_item = new JMenuItem("Load");
        JMenuItem import_item = new JMenuItem("Import");
        JMenuItem save_item = new JMenuItem("Save");
        JMenuItem exit_item = new JMenuItem("Exit");
        file.add(load_item);
        file.add(import_item);
        file.add(save_item);
        file.add(exit_item);
        load_item.addActionListener(arg0 -> Main.sm.load());
        import_item.addActionListener(arg0 -> Main.sm.importCollection());
        save_item.addActionListener(arg0 -> Main.sm.save());
        exit_item.addActionListener(arg0 -> System.exit(0));
        menuBar.add(file);
        setJMenuBar(menuBar);

        // кнопки с командами
        JButton remove_greater_button = new JButton("Remove Greater");
        JButton remove_first_button = new JButton("Remove First");
        JButton remove_last_button = new JButton("Remove Last");
        JButton sort_button = new JButton("Sort");
        JButton add_if_min_button = new JButton("Add if Min");
        JButton add_button = new JButton("Add");
        remove_greater_button.setBounds(20, 20, 130, 40);
        remove_first_button.setBounds(165, 20, 130, 40);
        remove_last_button.setBounds(20, 70, 130, 40);
        sort_button.setBounds(165, 70, 130, 40);
        add_if_min_button.setBounds(20, 120, 130, 40);
        add_button.setBounds(165, 120, 130, 40);
        add(remove_greater_button);
        add(remove_first_button);
        add(remove_last_button);
        add(sort_button);
        add(add_if_min_button);
        add(add_button);

        //Таблица
        String[] columns = {"Name", "Size", "Power", "X", "Y", "Color", "Creation Date"};
        collectionModel = new DefaultTableModel();
        collectionModel.setColumnIdentifiers(columns);
        JTable table = new JTable(collectionModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(25);
        table.getColumnModel().getColumn(3).setPreferredWidth(25);
        table.getColumnModel().getColumn(4).setPreferredWidth(25);
        table.getColumnModel().getColumn(5).setPreferredWidth(45);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(320, 20, 750, 460);
        add(scrollPane);

        //Слайдеры
        xSlider = new JSlider();
        xSlider.setMinimum(-1000);
        xSlider.setMaximum(1000);
        xSlider.setBounds(35, 380, 250, 30);
        JLabel xValue = new JLabel("0.0");
        xValue.setBounds(90, 350, 80, 20);
        xSlider.addChangeListener(e -> {
            xValue.setText(Integer.toString(xSlider.getValue()));
        });
        add(xValue);
        add(xSlider);

        ySlider = new JSlider();
        ySlider.setMinimum(-1000);
        ySlider.setMaximum(1000);
        ySlider.setBounds(35, 450, 250, 30);
        JLabel yValue = new JLabel("0.0");
        yValue.setBounds(90, 420, 80, 20);
        ySlider.addChangeListener(e -> {
            yValue.setText(Integer.toString(ySlider.getValue()));
        });
        add(yValue);
        add(ySlider);

        //Комбобокс для цвета
        colorsbox = new JComboBox<Colors>();
        colorsbox.addItem(Colors.Blue);
        colorsbox.addItem(Colors.Sapphire);
        colorsbox.addItem(Colors.Navy);
        colorsbox.addItem(Colors.Cyan);
        colorsbox.addItem(Colors.Mint);
        colorsbox.addItem(Colors.Emerald);
        colorsbox.setBounds(165, 175, 130, 25);
        add(colorsbox);

        // Текстовые поля
        nameField = new JTextField();
        sizeField = new JTextField();
        powerField = new JTextField();
        nameField.setBounds(165, 220, 130, 25);
        sizeField.setBounds(165, 260, 130, 25);
        powerField.setBounds(165, 300, 130, 25);
        add(nameField);
        add(sizeField);
        add(powerField);

        // Окошко с результатами операций
        operationResult = new JLabel();
        operationResult.setBounds(20, 500, 1050, 30);
        operationResult.setBackground(Color.BLACK);
        operationResult.setOpaque(true);
        operationResult.setText(" Hello there");
        operationResult.setFont(new Font("Helvetica", Font.PLAIN, 18));
        operationResult.setForeground(Color.GREEN);
        add(operationResult);


        // Именна переменных
        JLabel colorText = new JLabel("Color:");
        JLabel nameText = new JLabel("Name:");
        JLabel sizeText = new JLabel("Size:");
        JLabel powerText = new JLabel("Power:");
        JLabel xText = new JLabel("X:");
        JLabel yText = new JLabel("Y:");
        colorText.setBounds(65, 175, 100, 20);
        nameText.setBounds(65, 220, 100, 20);
        sizeText.setBounds(65, 260, 100, 20);
        powerText.setBounds(65, 300, 100, 20);
        xText.setBounds(65, 350, 50, 20);
        yText.setBounds(65, 420, 50, 20);
        add(colorText);
        add(nameText);
        add(sizeText);
        add(powerText);
        add(xText);
        add(yText);

        //Листенеры
        add_button.addActionListener(args0 -> checkInput("add"));

        setTitle("Sea Collection Manager");
        setSize(1100, 620);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void checkInput(String command) {
        if (nameField.getText().isEmpty()) {
            printToConsole("Name field is empty!",true);
        }
        //check if isNumber other fields. Don't know how to do it yet.
        else {
            Main.sm.add(new Sea(nameField.getText(), Double.parseDouble(sizeField.getText()), (int)Double.parseDouble(powerField.getText()), (double) xSlider.getValue(), (double) ySlider.getValue(), (Colors) colorsbox.getSelectedItem()));
        }
    }

    void addToTable(Sea sea) {
        collectionModel.addRow(sea.toArray());
    }

    void refreshTable(java.util.List<Sea> seaList){
        collectionModel.setRowCount(0);
        for (Sea sea: seaList) addToTable(sea);
    }

    void printToConsole(String text, Boolean isError) {
        if (isError) {
            operationResult.setForeground(Color.RED);
        } else {
            operationResult.setForeground(Color.GREEN);
        }
        operationResult.setText(" " + text);
    }
}
