
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

class GUI extends JFrame {

    private ResourceBundle bundle = ResourceBundle.getBundle("Bundle", Locale.getDefault(), new UTF8Control());
    static private ResourceBundle staticBundle = ResourceBundle.getBundle("Bundle", Locale.getDefault(), new UTF8Control());
    private JMenu language = new JMenu(bundle.getString("language"));
    private Locale rulocale = new Locale("ru");
    private Locale enlocale = new Locale("en");
    private Locale eslocale = new Locale("es", "MX");
    private Locale delocale = new Locale("de");
    private Locale ltlocale = new Locale("lt");

    private static JLabel connectionText = new JLabel();
    private static boolean isConnected = false;
    JPanel p3;
    private ArrayList<Timer> timers;
    private Circle[] circleList = {};
    private ArrayList<Circle> filteredCircles;
    private List<Sea> seaList = Collections.synchronizedList(new LinkedList<>());
    private JLabel hoText = new JLabel(bundle.getString("info") + " - ");
    private JLabel nameText = new JLabel(bundle.getString("name") + ":");
    private JLabel sizeText = new JLabel(bundle.getString("size") + ":");
    private JLabel powerText = new JLabel(bundle.getString("power") + ":");
    private JLabel colorText = new JLabel(bundle.getString("color") + ":");
    private JLabel dateText = new JLabel(bundle.getString("creationDate") + ":");
    private JTextField nameValue = new JTextField();
    private JTextField sizeValue = new JTextField();
    private JTextField powerValue = new JTextField();
    private JTextField xValue = new JTextField();
    private JTextField yValue = new JTextField();
    private JTextField colorValue = new JTextField();
    private JTextField dateValue = new JTextField();
    private Connector connector;
    private JButton refreshButton = new JButton(bundle.getString("refresh"));
    private JLabel filtersText = new JLabel(" " + bundle.getString("greeting"));
    private int filtersTextNumber = 0; //Number of phrase in filtersText. Necessary for language changing.
    //colorText2 should look like "Color:" so this code capitalizes first letter
    private JLabel colorText2 = new JLabel(bundle.getString("color").substring(0, 1).toUpperCase() + bundle.getString("color").substring(1) + ":");
    private JCheckBox blueCheckBox = new JCheckBox(bundle.getString("blue"));
    private JCheckBox sapphireCheckBox = new JCheckBox(bundle.getString("sapphire"));
    private JCheckBox navyCheckBox = new JCheckBox(bundle.getString("navy"));
    private JCheckBox cyanCheckBox = new JCheckBox(bundle.getString("cyan"));
    private JCheckBox mintCheckBox = new JCheckBox(bundle.getString("mint"));
    private JCheckBox emeraldCheckBox = new JCheckBox(bundle.getString("emerald"));
    private JLabel nameStarts = new JLabel(bundle.getString("nameFilter"));
    private JTextField nameField = new JTextField();
    private JLabel sizeFromTo = new JLabel("< " + bundle.getString("size") + " <");
    private JTextField sizeFrom = new JTextField();
    private JTextField sizeTo = new JTextField();
    private JLabel powerFromTo = new JLabel("< " + bundle.getString("power") + " <");
    private JTextField powerFrom = new JTextField();
    private JTextField powerTo = new JTextField();
    private JLabel dateFromTo = new JLabel("< " + bundle.getString("date") + " <");
    private JTextField dateFrom = new JTextField();
    private JTextField dateTo = new JTextField();
    private JLabel from1 = new JLabel(bundle.getString("from") + ":");
    private JLabel from2 = new JLabel(bundle.getString("from") + ":");
    private JLabel to1 = new JLabel(bundle.getString("to") + ":");
    private JLabel to2 = new JLabel(bundle.getString("to") + ":");
    private JSlider xFromSlider = new JSlider();
    private JSlider xToSlider = new JSlider();
    private JSlider yFromSlider = new JSlider();
    private JSlider yToSlider = new JSlider();
    private JButton startButton = new JButton(bundle.getString("start"));
    private JButton stopButton = new JButton(bundle.getString("stop"));
    private DateTimeFormatter displayDateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.MEDIUM).withLocale(Locale.getDefault());
    private Sea chosenSea;
    private DateTimeFormatter filterDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss");


    GUI(Connector connector, Locale locale) {
        changeLanguage(locale);
        this.connector = connector;
        JLabel xText = new JLabel("x:");
        JLabel yText = new JLabel("y:");
        nameValue.setEditable(false);
        sizeValue.setEditable(false);
        powerValue.setEditable(false);
        xValue.setEditable(false);
        yValue.setEditable(false);
        colorValue.setEditable(false);
        dateValue.setEditable(false);

        JMenuBar menuBar = new JMenuBar();
        JMenuItem en_item = new JMenuItem("English");
        JMenuItem ru_item = new JMenuItem("Русский");
        JMenuItem es_item = new JMenuItem("Español");
        JMenuItem de_item = new JMenuItem("Deutsch");
        JMenuItem lt_item = new JMenuItem("Lietuvių");
        language.add(en_item);
        language.add(ru_item);
        language.add(es_item);
        language.add(de_item);
        language.add(lt_item);
        en_item.addActionListener(arg0 -> changeLanguage(enlocale));
        ru_item.addActionListener(arg0 -> changeLanguage(rulocale));
        es_item.addActionListener(arg0 -> changeLanguage(eslocale));
        de_item.addActionListener(arg0 -> changeLanguage(delocale));
        lt_item.addActionListener(arg0 -> changeLanguage(ltlocale));
        menuBar.add(language);
        setJMenuBar(menuBar);

        //Right panel elements
        connectionText.setBackground(Color.BLACK);
        connectionText.setOpaque(true);
        connectionText.setText(" ");
        connectionText.setBackground(Color.BLACK);
        connectionText.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        refreshButton.addActionListener(arg0 -> new Thread(this::refreshCollection).start());
        filtersText.setBackground(Color.BLACK);
        filtersText.setOpaque(true);
        filtersText.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        filtersText.setForeground(Color.GREEN);
        blueCheckBox.setSelected(true);
        sapphireCheckBox.setSelected(true);
        navyCheckBox.setSelected(true);
        cyanCheckBox.setSelected(true);
        mintCheckBox.setSelected(true);
        emeraldCheckBox.setSelected(true);

        dateFrom.setToolTipText(bundle.getString("format") + ": dd.MM.yyyy HH:mm:ss");
        dateTo.setToolTipText(bundle.getString("format") + ": dd.MM.yyyy HH:mm:ss");
        JLabel xFromTo = new JLabel("X:");
        xFromSlider.setMinimum(-1000);
        xFromSlider.setMaximum(1000);
        xFromSlider.setValue(-1000);
        JLabel xFrom = new JLabel("-1000");
        xFromSlider.addChangeListener(e -> xFrom.setText(Integer.toString(xFromSlider.getValue())));
        xToSlider.setMinimum(-1000);
        xToSlider.setMaximum(1000);
        xToSlider.setValue(1000);
        JLabel xTo = new JLabel("1000");
        xToSlider.addChangeListener(e -> xTo.setText(Integer.toString(xToSlider.getValue())));
        JLabel yFromTo = new JLabel("Y:");
        yFromSlider.setMinimum(-1000);
        yFromSlider.setMaximum(1000);
        yFromSlider.setValue(-1000);
        JLabel yFrom = new JLabel("-1000");
        yFromSlider.addChangeListener(e -> yFrom.setText(Integer.toString(yFromSlider.getValue())));
        yToSlider.setMinimum(-1000);
        yToSlider.setMaximum(1000);
        yToSlider.setValue(1000);
        JLabel yTo = new JLabel("1000");
        yToSlider.addChangeListener(e -> yTo.setText(Integer.toString(yToSlider.getValue())));
        xFrom.setPreferredSize(new Dimension(35, 20));
        xTo.setPreferredSize(new Dimension(35, 20));
        yFrom.setPreferredSize(new Dimension(35, 20));
        yTo.setPreferredSize(new Dimension(35, 20));
        startButton.addActionListener(args0 -> new Thread(this::checkFilters).start());
        stopButton.setEnabled(false);
        stopButton.addActionListener(args0 -> new Thread(this::stopAnimation).start());

        //Panels
        JPanel p17 = new JPanel();
        p17.setLayout(new GridLayout(1, 2));
        p17.add(connectionText);
        JPanel refreshButtonPanel = new JPanel();
        refreshButtonPanel.setLayout(new BoxLayout(refreshButtonPanel, BoxLayout.X_AXIS));
        refreshButtonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        refreshButtonPanel.add(refreshButton);
        p17.add(refreshButtonPanel);

        JPanel p16 = new JPanel();
        p16.setLayout(new BorderLayout());
        p16.add(filtersText);

        JPanel p15 = new JPanel();
        p15.setLayout(new BoxLayout(p15, BoxLayout.X_AXIS));
        p15.add(colorText2);

        JPanel p14 = new JPanel();
        p14.setLayout(new GridLayout(3, 2, 0, 5));
        p14.add(blueCheckBox);
        p14.add(cyanCheckBox);
        p14.add(sapphireCheckBox);
        p14.add(mintCheckBox);
        p14.add(navyCheckBox);
        p14.add(emeraldCheckBox);

        JPanel p14extended = new JPanel();
        p14extended.setLayout(new BoxLayout(p14extended, BoxLayout.X_AXIS));
        p14extended.add(Box.createRigidArea(new Dimension(60, 0)));
        p14extended.add(p14);

        JPanel p13 = new JPanel();
        p13.setLayout(new BoxLayout(p13, BoxLayout.X_AXIS));
        p13.add(nameStarts);
        p13.add(Box.createRigidArea(new Dimension(10, 0)));
        p13.add(nameField);

        JPanel p12 = new JPanel();
        p12.setLayout(new BoxLayout(p12, BoxLayout.X_AXIS));
        p12.add(sizeFrom);
        p12.add(Box.createRigidArea(new Dimension(7, 0)));
        p12.add(sizeFromTo);
        p12.add(Box.createRigidArea(new Dimension(7, 0)));
        p12.add(sizeTo);

        JPanel p11 = new JPanel();
        p11.setLayout(new BoxLayout(p11, BoxLayout.X_AXIS));
        p11.add(powerFrom);
        p11.add(Box.createRigidArea(new Dimension(7, 0)));
        p11.add(powerFromTo);
        p11.add(Box.createRigidArea(new Dimension(7, 0)));
        p11.add(powerTo);

        JPanel p10 = new JPanel();
        p10.setLayout(new BoxLayout(p10, BoxLayout.X_AXIS));
        p10.add(dateFrom);
        p10.add(Box.createRigidArea(new Dimension(7, 0)));
        p10.add(dateFromTo);
        p10.add(Box.createRigidArea(new Dimension(7, 0)));
        p10.add(dateTo);

        JPanel p9 = new JPanel();
        p9.setLayout(new BoxLayout(p9, BoxLayout.X_AXIS));
        p9.add(xFromTo);

        JPanel p8 = new JPanel();
        p8.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        p8.add(from1, c);
        c.gridx++;
        p8.add(xFromSlider, c);
        c.gridx++;
        p8.add(xFrom, c);
        c.gridx = 0;
        c.gridy++;
        p8.add(to1, c);
        c.gridx++;
        p8.add(xToSlider, c);
        c.gridx++;
        p8.add(xTo, c);

        JPanel p7 = new JPanel();
        p7.setLayout(new BoxLayout(p7, BoxLayout.X_AXIS));
        p7.add(yFromTo);

        JPanel p6 = new JPanel();
        p6.setLayout(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(0, 10, 10, 10);
        c2.gridx = 0;
        c2.gridy = 0;
        p6.add(from2, c2);
        c2.gridx++;
        p6.add(yFromSlider, c2);
        c2.gridx++;
        p6.add(yFrom, c2);
        c2.gridx = 0;
        c2.gridy++;
        p6.add(to2, c2);
        c2.gridx++;
        p6.add(yToSlider, c2);
        c2.gridx++;
        p6.add(yTo, c2);

        JPanel p5 = new JPanel();
        p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
        p5.add(startButton);
        p5.add(Box.createRigidArea(new Dimension(30, 0)));
        p5.add(stopButton);

        JPanel p4 = new JPanel();
        p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
        p4.add(Box.createRigidArea(new Dimension(0, 10)));
        p4.add(p17);
        p4.add(Box.createRigidArea(new Dimension(0, 10)));
        p4.add(p16);
        p4.add(Box.createRigidArea(new Dimension(0, 10)));
        p4.add(p15);
        p4.add(Box.createRigidArea(new Dimension(0, 10)));
        p4.add(p14extended);
        p4.add(Box.createRigidArea(new Dimension(0, 15)));
        p4.add(p13);
        p4.add(Box.createRigidArea(new Dimension(0, 15)));
        p4.add(p12);
        p4.add(Box.createRigidArea(new Dimension(0, 15)));
        p4.add(p11);
        p4.add(Box.createRigidArea(new Dimension(0, 15)));
        p4.add(p10);
        p4.add(Box.createRigidArea(new Dimension(0, 15)));
        p4.add(p9);
        p4.add(p8);
        p4.add(Box.createRigidArea(new Dimension(0, 10)));
        p4.add(p7);
        p4.add(p6);
        p4.add(Box.createRigidArea(new Dimension(0, 10)));
        p4.add(p5);
        p4.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel p4extended = new JPanel();
        p4extended.setLayout(new BoxLayout(p4extended, BoxLayout.X_AXIS));
        p4extended.add(Box.createRigidArea(new Dimension(10, 0)));
        p4extended.add(p4);
        p4extended.add(Box.createRigidArea(new Dimension(10, 0)));
        p4extended.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK));

        p3 = new JPanel();
        p3.setLayout(null);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        dateValue.setPreferredSize(new Dimension(150, 20));
        p2.add(hoText);
        p2.add(nameText);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(nameValue);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(sizeText);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(sizeValue);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(powerText);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(powerValue);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(xText);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(xValue);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(yText);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(yValue);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(colorText);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(colorValue);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(dateText);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));
        p2.add(dateValue);
        p2.add(Box.createRigidArea(new Dimension(8, 0)));

        JPanel p2extended = new JPanel();
        p2extended.setLayout(new BoxLayout(p2extended, BoxLayout.Y_AXIS));
        JPanel topSpace = new JPanel();
        topSpace.setLayout(new BorderLayout());
        topSpace.add(Box.createRigidArea(new Dimension(0, 8)), BorderLayout.NORTH);
        JPanel botSpace = new JPanel();
        botSpace.setLayout(new BorderLayout());
        botSpace.add(Box.createRigidArea(new Dimension(0, 8)), BorderLayout.NORTH);
        p2extended.add(topSpace);
        p2extended.add(p2);
        p2extended.add(botSpace);
        botSpace.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

        setLayout(new BorderLayout());
        add(p2extended, BorderLayout.NORTH);
        add(p3, BorderLayout.CENTER);
        add(p4extended, BorderLayout.EAST);

        setTitle(bundle.getString("title1"));
        setSize(1150, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        firstTimeRefresh();
    }

    static void setConnectionInfo(boolean isWorking) {
        isConnected = isWorking;
        if (isWorking) {
            connectionText.setForeground(Color.GREEN);
            connectionText.setText(" " + staticBundle.getString("connected"));
        } else {
            connectionText.setForeground(Color.RED);
            connectionText.setText(" " + staticBundle.getString("disconnected"));
        }
    }

    void setTopPanelInfo(Sea sea) {
        chosenSea = sea;
        nameValue.setText(sea.getName());
        sizeValue.setText(String.valueOf(sea.getSize()));
        powerValue.setText(String.valueOf(sea.getPower()));
        xValue.setText(String.valueOf(sea.getX()));
        yValue.setText(String.valueOf(sea.getY()));
        colorValue.setText(bundle.getString(sea.getColor().name().toLowerCase()));
        dateValue.setText(displayDateTimeFormatter.format(sea.getCreationDate()));
    }

    private void checkFilters() {
        startButton.setEnabled(false);
        if ((notANumeric(sizeFrom.getText()) && !sizeFrom.getText().isEmpty()) || (notANumeric(sizeTo.getText()) && !sizeTo.getText().isEmpty())) {
            setFiltersText(bundle.getString("incorrectSize"), true);
            filtersTextNumber = 1;
            startButton.setEnabled(true);
        } else if ((notANumeric(powerFrom.getText()) && !powerFrom.getText().isEmpty()) || (notANumeric(powerTo.getText()) && !powerTo.getText().isEmpty())) {
            setFiltersText(bundle.getString("incorrectPower"), true);
            filtersTextNumber = 2;
            startButton.setEnabled(true);
        } else {
            try {
                if (!dateFrom.getText().isEmpty()) {
                    filterDateTimeFormatter.parse(dateFrom.getText());
                }
                if (!dateTo.getText().isEmpty()) {
                    filterDateTimeFormatter.parse(dateTo.getText());
                }
                setFiltersText(bundle.getString("filtersCorrect"), false);
                filtersTextNumber = 4;
                applyFilters();
            } catch (DateTimeParseException e) {
                setFiltersText(bundle.getString("incorrectDate") + " dd.MM.yyyy HH:mm:ss!", true);
                filtersTextNumber = 3;
                startButton.setEnabled(true);
            }
        }
    }

    private boolean notANumeric(String str) {
        return !str.matches("-?\\d+(\\.\\d+)?");
    }

    private void setFiltersText(String message, boolean isError) {
        if (isError) filtersText.setForeground(Color.RED);
        else filtersText.setForeground(Color.GREEN);
        filtersText.setText(" " + message);
    }

    private void applyFilters() {
        filteredCircles = new ArrayList<>();
        Arrays.stream(circleList)
                .filter(o -> o.sea.getY() >= yFromSlider.getValue() && o.sea.getY() <= yToSlider.getValue())
                .filter(o -> o.sea.getX() >= xFromSlider.getValue() && o.sea.getX() <= xToSlider.getValue())
                .filter(o -> {
                    if (dateFrom.getText().isEmpty() || o.sea.getCreationDate().isAfter(LocalDateTime.parse(dateFrom.getText(), filterDateTimeFormatter))) {
                        return dateTo.getText().isEmpty() || o.sea.getCreationDate().isBefore(LocalDateTime.parse(dateTo.getText(), filterDateTimeFormatter));
                    }
                    return false;
                })
                .filter(o -> powerFrom.getText().isEmpty() || o.sea.getPower() >= Integer.parseInt(powerFrom.getText()))
                .filter(o -> powerTo.getText().isEmpty() || o.sea.getPower() <= Integer.parseInt(powerTo.getText()))
                .filter(o -> sizeFrom.getText().isEmpty() || o.sea.getSize() >= Double.parseDouble(sizeFrom.getText()))
                .filter(o -> sizeTo.getText().isEmpty() || o.sea.getSize() <= Double.parseDouble(sizeTo.getText()))
                .filter(o -> o.sea.getName().startsWith(nameField.getText()))
                .filter(o -> o.sea.getColor().equals(Colors.Blue) && blueCheckBox.isSelected() ||
                        o.sea.getColor().equals(Colors.Sapphire) && sapphireCheckBox.isSelected() ||
                        o.sea.getColor().equals(Colors.Navy) && navyCheckBox.isSelected() ||
                        o.sea.getColor().equals(Colors.Cyan) && cyanCheckBox.isSelected() ||
                        o.sea.getColor().equals(Colors.Mint) && mintCheckBox.isSelected() ||
                        o.sea.getColor().equals(Colors.Emerald) && emeraldCheckBox.isSelected())
                .forEach(filteredCircles::add);

        if (filteredCircles.size() == 0) {
            startButton.setEnabled(true);
            setFiltersText(bundle.getString("noObjectsFound"), false);
            filtersTextNumber = 5;
        } else {
            stopButton.setEnabled(true);
            setFiltersText(filteredCircles.size() + " " + bundle.getString("objectsFound"), false);
            filtersTextNumber = 6;
            timers = new ArrayList<>();
            for (Circle circle : filteredCircles) {
                timers.add(new Timer());
                timers.get(timers.size() - 1).schedule(new TimerTask() {
                    int counter = 0;

                    @Override
                    public void run() {
                        circle.transition();
                        p3.revalidate();
                        p3.repaint();
                        counter++;
                        if (counter == 70) {
                            stopAnimation();
                        }
                    }
                }, 100, 100);
            }
        }
    }

    private void stopAnimation() {
        for (Timer timer : timers) {
            timer.cancel();
        }
        for (Circle c : filteredCircles) {
            c.setNormalColor();
        }
        p3.revalidate();
        p3.repaint();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private void refreshCollection() {
        List<Sea> tempSeaList = connector.getCollection();
        if (tempSeaList != null) {
            seaList = tempSeaList;
            circleList = new Circle[seaList.size()];
            p3.removeAll();
            for (int i = 0; i < seaList.size(); i++) {
                Circle c = new Circle(seaList.get(i), this);
                circleList[i] = c;
                p3.add(c);
            }
            setFiltersText(bundle.getString("acquired") + " " + seaList.size() + " " + bundle.getString("elements"), false);
            filtersTextNumber = 7;
            p3.revalidate();
            p3.repaint();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        } else {
            setFiltersText(bundle.getString("tryAgain"), true);
            filtersTextNumber = 8;
        }
    }

    private void firstTimeRefresh() {
        while (true) {
            List<Sea> tempSeaList = connector.getCollection();
            if (tempSeaList != null) {
                seaList = tempSeaList;
                circleList = new Circle[seaList.size()];
                for (int i = 0; i < seaList.size(); i++) {
                    Circle c = new Circle(seaList.get(i), this);
                    circleList[i] = c;
                    p3.add(c);
                }
                p3.revalidate();
                p3.repaint();
                break;
            }
        }
    }

    private void changeLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("Bundle", locale, new UTF8Control());
        staticBundle = ResourceBundle.getBundle("Bundle", locale, new UTF8Control());
        displayDateTimeFormatter = displayDateTimeFormatter.withLocale(locale);
        connectionText.setText(" " + (isConnected ? staticBundle.getString("connected") : staticBundle.getString("disconnected")));
        setTitle(bundle.getString("title1"));
        language.setText(bundle.getString("language"));
        hoText.setText(bundle.getString("info") + " - ");
        nameText.setText(bundle.getString("name") + ":");
        sizeText.setText(bundle.getString("size") + ":");
        powerText.setText(bundle.getString("power") + ":");
        colorText.setText(bundle.getString("color") + ":");
        dateText.setText(bundle.getString("creationDate") + ":");
        refreshButton.setText(bundle.getString("refresh"));
        colorText2.setText(bundle.getString("color").substring(0, 1).toUpperCase() + bundle.getString("color").substring(1) + ":");
        blueCheckBox.setText(bundle.getString("blue"));
        sapphireCheckBox.setText(bundle.getString("sapphire"));
        navyCheckBox.setText(bundle.getString("navy"));
        cyanCheckBox.setText(bundle.getString("cyan"));
        mintCheckBox.setText(bundle.getString("mint"));
        emeraldCheckBox.setText(bundle.getString("emerald"));
        nameStarts.setText(bundle.getString("nameFilter"));
        sizeFromTo.setText("< " + bundle.getString("size") + " <");
        powerFromTo.setText("< " + bundle.getString("power") + " <");
        dateFromTo.setText("< " + bundle.getString("date") + " <");
        from1.setText(bundle.getString("from") + ":");
        from2.setText(bundle.getString("from") + ":");
        to1.setText(bundle.getString("to") + ":");
        to2.setText(bundle.getString("to") + ":");
        startButton.setText(bundle.getString("start"));
        stopButton.setText(bundle.getString("stop"));
        if (chosenSea != null) {
            setTopPanelInfo(chosenSea);
        }
        switch (filtersTextNumber) {
            case 0:
                setFiltersText(bundle.getString("greeting"), false);
                break;
            case 1:
                setFiltersText(bundle.getString("incorrectSize"), true);
                break;
            case 2:
                setFiltersText(bundle.getString("incorrectPower"), true);
                break;
            case 3:
                setFiltersText(bundle.getString("incorrectDate") + " dd-MM-yyyy!", true);
                break;
            case 4:
                setFiltersText(bundle.getString("filtersCorrect"), false);
                break;
            case 5:
                setFiltersText(bundle.getString("noObjectsFound"), false);
                break;
            case 6:
                setFiltersText(filteredCircles.size() + " " + bundle.getString("objectsFound"), false);
                break;
            case 7:
                setFiltersText(bundle.getString("acquired") + " " + seaList.size() + " " + bundle.getString("elements"), false);
                break;
            case 8:
                setFiltersText(bundle.getString("tryAgain"), true);
                break;
        }
    }
}