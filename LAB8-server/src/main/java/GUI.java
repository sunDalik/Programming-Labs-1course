import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

class GUI extends JFrame {

    private DefaultTableModel collectionModel;
    private JComboBox<Colors> colorsbox;
    private JTextField nameField;
    private JTextField sizeField;
    private JTextField powerField;
    private JSlider xSlider;
    private JSlider ySlider;
    private JLabel operationResult;

    /**
     * Creating the G U I
     */
    GUI() {
        //File menu bar
        JMenuBar menuBar = new JMenuBar();
        UIManager.put("Menu.font", new Font("Helvetica", Font.PLAIN, 16));
        UIManager.put("MenuItem.font", new Font("Helvetica", Font.PLAIN, 16));
        UIManager.put("TextField.font", new Font("Helvetica", Font.PLAIN, 15));
        JMenu file = new JMenu("File");
        JMenuItem load_item = new JMenuItem("Load");
        JMenuItem save_item = new JMenuItem("Save");
        JMenuItem exit_item = new JMenuItem("Exit");
        file.add(load_item);
        file.add(save_item);
        file.add(exit_item);
        load_item.addActionListener(arg0 -> Auth.sm.load());
        save_item.addActionListener(arg0 -> Auth.sm.save());
        exit_item.addActionListener(arg0 -> System.exit(0));
        menuBar.add(file);
        setJMenuBar(menuBar);

        //Command Buttons
        JButton remove_greater_button = new JButton("Remove Greater");
        JButton remove_first_button = new JButton("Remove First");
        JButton remove_last_button = new JButton("Remove Last");
        JButton sort_button = new JButton("Sort");
        JButton add_if_min_button = new JButton("Add if Min");
        JButton add_button = new JButton("Add");

        //Table
        String[] columns = {"Name", "Size", "Power", "X", "Y", "Color", "Creation Date"};
        collectionModel = new DefaultTableModel();
        collectionModel.setColumnIdentifiers(columns);
        JTable table = new JTable(collectionModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(25);
        table.getColumnModel().getColumn(3).setPreferredWidth(25);
        table.getColumnModel().getColumn(4).setPreferredWidth(25);
        table.getColumnModel().getColumn(5).setPreferredWidth(35);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        JScrollPane scrollPane = new JScrollPane(table);

        //Sliders
        xSlider = new JSlider();
        xSlider.setMinimum(-1000);
        xSlider.setMaximum(1000);
        xSlider.setValue(0);
        JLabel xValue = new JLabel("0");
        xSlider.addChangeListener(e -> xValue.setText(Integer.toString(xSlider.getValue())));

        ySlider = new JSlider();
        ySlider.setMinimum(-1000);
        ySlider.setMaximum(1000);
        ySlider.setValue(0);
        JLabel yValue = new JLabel("0");
        ySlider.addChangeListener(e -> yValue.setText(Integer.toString(ySlider.getValue())));

        //Color ComboBox
        colorsbox = new JComboBox<>();
        colorsbox.addItem(Colors.Blue);
        colorsbox.addItem(Colors.Sapphire);
        colorsbox.addItem(Colors.Navy);
        colorsbox.addItem(Colors.Cyan);
        colorsbox.addItem(Colors.Mint);
        colorsbox.addItem(Colors.Emerald);

        //Text Fields for name, size and power
        nameField = new JTextField();
        sizeField = new JTextField();
        powerField = new JTextField();

        //Operations result window
        operationResult = new JLabel();
        operationResult.setBackground(Color.BLACK);
        operationResult.setOpaque(true);
        operationResult.setText(" Hello there");
        operationResult.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        operationResult.setForeground(Color.GREEN);

        //Text labels
        JLabel colorText = new JLabel("Color:");
        JLabel nameText = new JLabel("Name:");
        JLabel sizeText = new JLabel("Size:");
        JLabel powerText = new JLabel("Power:");
        JLabel xText = new JLabel("X:");
        JLabel yText = new JLabel("Y:");
        colorText.setHorizontalAlignment(SwingConstants.CENTER);
        nameText.setHorizontalAlignment(SwingConstants.CENTER);
        sizeText.setHorizontalAlignment(SwingConstants.CENTER);
        powerText.setHorizontalAlignment(SwingConstants.CENTER);
        xText.setHorizontalAlignment(SwingConstants.CENTER);
        yText.setHorizontalAlignment(SwingConstants.CENTER);

        //Listeners
        add_button.addActionListener(args0 -> checkInput("add"));
        add_if_min_button.addActionListener(args0 -> checkInput("add_if_min"));
        remove_last_button.addActionListener(args0 -> dontCheckInput("remove_last"));
        remove_first_button.addActionListener(args0 -> dontCheckInput("remove_first"));
        remove_greater_button.addActionListener(args0 -> checkInput("remove_greater"));
        sort_button.addActionListener(args0 -> dontCheckInput("sort"));


        //Layout
        JPanel p6 = new JPanel();
        p6.setLayout(new GridLayout(3, 2, 18, 10));
        p6.add(remove_greater_button);
        p6.add(remove_first_button);
        p6.add(remove_last_button);
        p6.add(sort_button);
        p6.add(add_if_min_button);
        p6.add(add_button);

        JPanel p7 = new JPanel();
        p7.setLayout(new GridLayout(4, 2, 10, 8));
        p7.add(p6);
        p7.add(colorText);
        p7.add(colorsbox);
        p7.add(nameText);
        p7.add(nameField);
        p7.add(sizeText);
        p7.add(sizeField);
        p7.add(powerText);
        p7.add(powerField);

        JPanel p8 = new JPanel();
        p8.setLayout(new BoxLayout(p8, BoxLayout.X_AXIS));
        p8.add(xText);
        p8.add(Box.createRigidArea(new Dimension(50, 0)));
        p8.add(xValue);

        JPanel p9 = new JPanel();
        p9.setLayout(new BoxLayout(p9, BoxLayout.Y_AXIS));
        p9.add(xSlider);

        JPanel p10 = new JPanel();
        p10.setLayout(new BoxLayout(p10, BoxLayout.X_AXIS));
        p10.add(yText);
        p10.add(Box.createRigidArea(new Dimension(50, 0)));
        p10.add(yValue);

        JPanel p11 = new JPanel();
        p11.setLayout(new BoxLayout(p11, BoxLayout.Y_AXIS));
        p11.add(ySlider);

        JPanel p5 = new JPanel();
        p5.setLayout(new BoxLayout(p5, BoxLayout.Y_AXIS));
        p5.add(Box.createRigidArea(new Dimension(0, 10)));
        p5.add(p6);
        p5.add(Box.createRigidArea(new Dimension(0, 15)));
        p5.add(p7);
        p5.add(Box.createRigidArea(new Dimension(0, 15)));
        p5.add(p8);
        p5.add(p9);
        p5.add(Box.createRigidArea(new Dimension(0, 20)));
        p5.add(p10);
        p5.add(p11);
        p5.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel p5extended = new JPanel();
        p5extended.setLayout(new BoxLayout(p5extended, BoxLayout.X_AXIS));
        p5extended.add(Box.createRigidArea(new Dimension(10, 0)));
        p5extended.add(p5);
        p5extended.add(Box.createRigidArea(new Dimension(10, 0)));

        JPanel p4 = new JPanel();
        p4.setLayout(new BorderLayout());
        p4.add(scrollPane, BorderLayout.CENTER);
        p4.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        p4.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(operationResult, BorderLayout.SOUTH);

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.add(p2, BorderLayout.SOUTH);
        p1.add(p5extended, BorderLayout.WEST);
        p1.add(p4, BorderLayout.CENTER);
        p1.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
        add(p1);

        setTitle("Sea Collection Manager");
        setSize(900, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Executes command that require checking input values
     *
     * @param command - add, add_if_min or remove_greater
     */
    private void checkInput(String command) {
        if (nameField.getText().isEmpty()) {
            printToConsole("Name field is empty!", true);
        } else if (notANumeric(sizeField.getText())) {
            printToConsole("Size value is not a number!", true);
        } else if (Double.parseDouble(sizeField.getText()) <= 0) {
            printToConsole("Size can't be negative!", true);
        } else if (notANumeric(powerField.getText())) {
            printToConsole("Power is not a number!", true);
        } else if ((int) Double.parseDouble(powerField.getText()) <= 0) {
            printToConsole("Power can't be negative", true);
        } else {
            Sea sea = new Sea(nameField.getText(), Double.parseDouble(sizeField.getText()), (int) Double.parseDouble(powerField.getText()), xSlider.getValue(), ySlider.getValue(), (Colors) colorsbox.getSelectedItem());
            switch (command) {
                case "add":
                    Auth.sm.add(sea);
                    int r = new Random().nextInt(7);
                    switch (r) {
                        case 0:
                            printToConsole("Good job, Mr. God", false);
                            break;
                        case 1:
                            printToConsole("Poseidon, grant us your blessing...", false);
                            break;
                        case 2:
                            printToConsole("Wow that's a lot of water", false);
                            break;
                        case 3:
                            printToConsole("Pwease hewp me I'm drowning OwO", false);
                            break;
                        case 4:
                            printToConsole("You are the ocean's gray waves...", false);
                            break;
                        case 5:
                            printToConsole("Woah this sea is pretty liquid", false);
                            break;
                        case 6:
                            printToConsole("splash splash", false);
                            break;
                    }
                    break;

                case "add_if_min":
                    if (Auth.sm.add_if_min(sea)) {
                        int r2 = new Random().nextInt(3);
                        switch (r2) {
                            case 0:
                                printToConsole("Cute little sea", false);
                                break;
                            case 1:
                                printToConsole("Such a tiny water", false);
                                break;
                            case 2:
                                printToConsole("s m a l l", false);
                                break;
                        }
                    } else {
                        int r3 = new Random().nextInt(3);
                        switch (r3) {
                            case 0:
                                printToConsole("Woah woah hold on that's too big", false);
                                break;
                            case 1:
                                printToConsole("B I G", false);
                                break;
                            case 2:
                                printToConsole("Sorry, big sea is not allowed", false);
                                break;
                        }
                    }
                    break;

                case "remove_greater":
                    int n = Auth.sm.remove_greater(sea);
                    if (n == 0) {
                        printToConsole("No sea can outdo the one you've entered...", false);
                    } else {
                        printToConsole(n + " great seas were completely disintegrated", false);
                    }
                    break;
            }
        }
    }

    /**
     * Executes command that don't require entering values
     *
     * @param command - sort, remove_first or remove_last
     */
    private void dontCheckInput(String command) {
        switch (command) {
            case ("sort"):
                if (Auth.sm.sort()) {
                    printToConsole("Chaos is defeated...", false);
                } else {
                    printToConsole("The world is empty...", false);
                }
                break;
            case ("remove_first"):
                if (Auth.sm.remove_first()) {
                    printToConsole("The original sea... It dried out!", false);
                } else {
                    printToConsole("No sea...", false);
                }
                break;
            case ("remove_last"):
                if (Auth.sm.remove_last()) {
                    int r = new Random().nextInt(3);
                    switch (r) {
                        case 0:
                            printToConsole("Evaporate. Let's start E V A P O R A T I N G", false);
                            break;
                        case 1:
                            printToConsole("Burn sea BURN!", false);
                            break;
                        case 2:
                            printToConsole("Some huge man has just drunk all the water in the last sea!", false);
                            break;
                    }
                } else {
                    printToConsole("I'm thirsty... God, bring us water!", false);
                }
                break;
        }
    }

    void addToTable(Sea sea) {
        collectionModel.addRow(sea.toArray());
    }

    void removeLastRow() {
        collectionModel.removeRow(collectionModel.getRowCount() - 1);
    }

    /**
     * Deletes all rows from table and loads all elements from collection
     *
     * @param seaList - collection to load
     */
    void refreshTable(java.util.List<Sea> seaList) {
        collectionModel.setRowCount(0);
        for (Sea sea : seaList) addToTable(sea);
    }

    /**
     * Prints message to the window with operations result
     *
     * @param message - literally message
     * @param isError - true - red color, false - green color
     */
    private void printToConsole(String message, Boolean isError) {
        if (isError) {
            operationResult.setForeground(Color.RED);
        } else {
            operationResult.setForeground(Color.GREEN);
        }
        operationResult.setText(" " + message);
    }

    private boolean notANumeric(String str) {
        return !str.matches("-?\\d+(\\.\\d+)?");
    }

}