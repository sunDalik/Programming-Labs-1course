import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class GUI extends JFrame {

    static private JLabel connectionText;
    private JPanel p3;
    private JPanel p3null;
    private List<Sea> seaList = Collections.synchronizedList(new LinkedList<>());
    private Connector connector;

    GUI(Connector connector) {
        this.connector = connector;
        //Top panel elements
        JLabel hoText = new JLabel(" Highlighted Object - ");
        JLabel nameText = new JLabel("Name:");
        JLabel sizeText = new JLabel("Size:");
        JLabel powerText = new JLabel("Power:");
        JLabel xText = new JLabel("X:");
        JLabel yText = new JLabel("Y:");
        JLabel colorText = new JLabel("Color:");
        JLabel dateText = new JLabel("Creation Date:");
        JTextField nameValue = new JTextField();
        JTextField sizeValue = new JTextField();
        JTextField powerValue = new JTextField();
        JTextField xValue = new JTextField();
        JTextField yValue = new JTextField();
        JTextField colorValue = new JTextField();
        JTextField dateValue = new JTextField();
        nameValue.setEditable(false);
        sizeValue.setEditable(false);
        powerValue.setEditable(false);
        xValue.setEditable(false);
        yValue.setEditable(false);
        colorValue.setEditable(false);
        dateValue.setEditable(false);

        //Right panel elements
        connectionText = new JLabel();
        connectionText.setBackground(Color.BLACK);
        connectionText.setOpaque(true);
        connectionText.setText(" ");
        connectionText.setBackground(Color.BLACK);
        connectionText.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(arg0 -> new Thread(this::refreshCollection));
        JLabel filtersText = new JLabel();
        filtersText.setBackground(Color.BLACK);
        filtersText.setOpaque(true);
        filtersText.setText(" I wonder what will happen...");
        filtersText.setFont(new Font("Sans-Serif", Font.PLAIN, 16));
        filtersText.setForeground(Color.GREEN);
        JLabel colorText2 = new JLabel("Color:");
        JCheckBox blueCheckBox = new JCheckBox("Blue");
        JCheckBox sapphireCheckBox = new JCheckBox("Sapphire");
        JCheckBox navyCheckBox = new JCheckBox("Navy");
        JCheckBox cyanCheckBox = new JCheckBox("Cyan");
        JCheckBox mintCheckBox = new JCheckBox("Mint");
        JCheckBox emeraldCheckBox = new JCheckBox("Emerald");
        JLabel nameStarts = new JLabel("Name starts with:");
        JTextField nameField = new JTextField();
        JTextField sizeFrom = new JTextField();
        JLabel sizeFromTo = new JLabel("< size <");
        JTextField sizeTo = new JTextField();
        JTextField powerFrom = new JTextField();
        JLabel powerFromTo = new JLabel("< power <");
        JTextField powerTo = new JTextField();
        JTextField dateFrom = new JTextField();
        JLabel dateFromTo = new JLabel("< date <");
        JTextField dateTo = new JTextField();
        JLabel xFromTo = new JLabel("X:");
        JSlider xFromSlider = new JSlider();
        xFromSlider.setMinimum(-1000);
        xFromSlider.setMaximum(1000);
        JLabel xFrom = new JLabel("0");
        xFromSlider.addChangeListener(e -> xFrom.setText(Integer.toString(xFromSlider.getValue())));
        JSlider xToSlider = new JSlider();
        xToSlider.setMinimum(-1000);
        xToSlider.setMaximum(1000);
        JLabel xTo = new JLabel("0");
        xToSlider.addChangeListener(e -> xTo.setText(Integer.toString(xToSlider.getValue())));
        JLabel yFromTo = new JLabel("Y:");
        JSlider yFromSlider = new JSlider();
        yFromSlider.setMinimum(-1000);
        yFromSlider.setMaximum(1000);
        JLabel yFrom = new JLabel("0");
        yFromSlider.addChangeListener(e -> yFrom.setText(Integer.toString(yFromSlider.getValue())));
        JSlider yToSlider = new JSlider();
        yToSlider.setMinimum(-1000);
        yToSlider.setMaximum(1000);
        JLabel yTo = new JLabel("0");
        yToSlider.addChangeListener(e -> yTo.setText(Integer.toString(yToSlider.getValue())));
        xFrom.setPreferredSize(new Dimension(30, 20));
        xTo.setPreferredSize(new Dimension(30, 20));
        yFrom.setPreferredSize(new Dimension(30, 20));
        yTo.setPreferredSize(new Dimension(30, 20));
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");

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
        p8.add(new JLabel("from:"), c);
        c.gridx++;
        p8.add(xFromSlider, c);
        c.gridx++;
        p8.add(xFrom, c);
        c.gridx = 0;
        c.gridy++;
        p8.add(new JLabel("to:"), c);
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
        p6.add(new JLabel("from:"), c2);
        c2.gridx++;
        p6.add(yFromSlider, c2);
        c2.gridx++;
        p6.add(yFrom, c2);
        c2.gridx = 0;
        c2.gridy++;
        p6.add(new JLabel("to:"), c2);
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

        p3 = new JPanel();
        p3.setLayout(new BorderLayout());

        p3null = new JPanel();
        p3null.setLayout(null);
        p3.add(p3null);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        hoText.setFont(new Font("Helvetica", Font.PLAIN, 16));
        nameText.setFont(new Font("Helvetica", Font.PLAIN, 16));
        sizeText.setFont(new Font("Helvetica", Font.PLAIN, 16));
        powerText.setFont(new Font("Helvetica", Font.PLAIN, 16));
        xText.setFont(new Font("Helvetica", Font.PLAIN, 16));
        yText.setFont(new Font("Helvetica", Font.PLAIN, 16));
        colorText.setFont(new Font("Helvetica", Font.PLAIN, 16));
        dateText.setFont(new Font("Helvetica", Font.PLAIN, 16));
        p2.add(hoText);
        p2.add(nameText);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(nameValue);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(sizeText);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(sizeValue);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(powerText);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(powerValue);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(xText);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(xValue);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(yText);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(yValue);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(colorText);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(colorValue);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(dateText);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));
        p2.add(dateValue);
        p2.add(Box.createRigidArea(new Dimension(10, 0)));

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

        setTitle("Sea Collection Viewer");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        firstTimeRefresh();
    }

    static void setConnectionInfo(boolean isWorking) {
        if (isWorking) {
            connectionText.setForeground(Color.GREEN);
            connectionText.setText(" Connection: true");
        } else {
            connectionText.setForeground(Color.RED);
            connectionText.setText(" Server is unavailable");
        }
    }

    private void refreshCollection() {
        List<Sea> tempSeaList = connector.getCollection();
        if (tempSeaList != null) {
            seaList = tempSeaList;
            for (int i = 0; i < seaList.size(); i++) {
            }
        }
    }

    private void firstTimeRefresh() {
        while (true) {
            List<Sea> tempSeaList = connector.getCollection();
            if (tempSeaList != null) {
                seaList = tempSeaList;
                Circle[] circleList = new Circle[seaList.size()];
                for (int i = 0; i < seaList.size(); i++) {
                    Object[] seaData = seaList.get(i).toArray();
                    Color seaColor = ((Colors) seaData[5]).getRgbColor();
                    String seaColorName = ((Colors) seaData[5]).name();
                    int seaX = (int) seaData[3];
                    int seaY = (int) seaData[4];
                    String seaName = (String) seaData[0];
                    double seaSize = (double) seaData[1];
                    int seaPower = (int) seaData[2];
                    String seaDate = (String) seaData[6];

                    circleList[i] = new Circle(seaX, seaY, ((Double) seaSize).intValue(), seaColor);
                    //circleList[i].setBackground(((Colors) seaData[5]).getRgbColor());
                    //circleList[i].setBounds(seaX, seaY, ((Double) seaSize).intValue(), ((Double) seaSize).intValue());

                    p3.add(circleList[i]);

                    this.revalidate();
                }

                break;
            }
        }
    }
}
