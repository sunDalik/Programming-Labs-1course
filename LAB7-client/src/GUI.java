import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class GUI extends JFrame{

    GUI(){

        //Top panel elements
        JLabel hoText = new JLabel("Highlighted Object - ");
        JLabel nameText = new JLabel("Name:");
        JLabel sizeText = new JLabel("Size:");
        JLabel powerText = new JLabel("Power:");
        JLabel xText = new JLabel("X:");
        JLabel yText = new JLabel("Y:");
        JLabel colorText = new JLabel("Color:");
        JLabel dateText = new JLabel("Creation Date:");
        JLabel nameValue = new JLabel();
        JLabel sizeValue = new JLabel();
        JLabel powerValue = new JLabel();
        JLabel xValue = new JLabel();
        JLabel yValue = new JLabel();
        JLabel colorValue = new JLabel();
        JLabel dateValue = new JLabel();
        nameValue.setBorder(new LineBorder(Color.BLACK, 1));
        sizeValue.setBorder(new LineBorder(Color.BLACK, 1));
        powerValue.setBorder(new LineBorder(Color.BLACK, 1));
        xValue.setBorder(new LineBorder(Color.BLACK, 1));
        yValue.setBorder(new LineBorder(Color.BLACK, 1));
        colorValue.setBorder(new LineBorder(Color.BLACK, 1));
        dateValue.setBorder(new LineBorder(Color.BLACK, 1));

        //Right panel elements
        JLabel connectionText = new JLabel();
        connectionText.setBackground(Color.BLACK);
        connectionText.setOpaque(true);
        connectionText.setText(" ");
        connectionText.setBackground(Color.BLACK);
        JButton refreshButton = new JButton("Refresh");
        JLabel filtersText = new JLabel();
        filtersText.setBackground(Color.BLACK);
        filtersText.setOpaque(true);
        filtersText.setText(" ");
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
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");

        //Panels
        JPanel p17 = new JPanel();
        p17.setLayout(new BoxLayout(p17, BoxLayout.X_AXIS));
        p17.add(connectionText);
        p17.add(refreshButton);

        JPanel p16 = new JPanel();
        p16.setLayout(new BorderLayout());
        p16.add(filtersText, BorderLayout.NORTH);

        JPanel p15 = new JPanel();
        p15.setLayout(new BoxLayout(p15,BoxLayout.X_AXIS));
        p15.add(colorText2);

        JPanel p14 = new JPanel();
        p14.setLayout(new GridLayout(3,2,20,10));
        p14.add(blueCheckBox);
        p14.add(cyanCheckBox);
        p14.add(sapphireCheckBox);
        p14.add(mintCheckBox);
        p14.add(navyCheckBox);
        p14.add(emeraldCheckBox);

        JPanel p13 = new JPanel();
        p13.setLayout(new BoxLayout(p13,BoxLayout.X_AXIS));
        p13.add(nameStarts);
        p13.add(nameField);

        JPanel p12 = new JPanel();
        p12.setLayout(new BoxLayout(p12,BoxLayout.X_AXIS));
        p12.add(sizeFrom);
        p12.add(sizeFromTo);
        p12.add(sizeTo);

        JPanel p11 = new JPanel();
        p11.setLayout(new BoxLayout(p11,BoxLayout.X_AXIS));
        p11.add(powerFrom);
        p11.add(powerFromTo);
        p11.add(powerTo);

        JPanel p10 = new JPanel();
        p10.setLayout(new BoxLayout(p10,BoxLayout.X_AXIS));
        p10.add(dateFrom);
        p10.add(dateFromTo);
        p10.add(dateTo);

        JPanel p9 = new JPanel();
        p9.setLayout(new BoxLayout(p9,BoxLayout.X_AXIS));
        p9.add(xFromTo);

        JPanel p8 = new JPanel();
        p8.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        p8.add(new JLabel("from:"), c);
        c.gridx++;
        p8.add(xFromSlider,c);
        c.gridx++;
        p8.add(xFrom,c);
        c.gridx = 0;
        c.gridy++;
        p9.add(new JLabel("to:"),c);
        c.gridx++;
        p9.add(xToSlider,c);
        c.gridx++;
        p9.add(xTo,c);

        JPanel p7 = new JPanel();
        p7.setLayout(new BoxLayout(p7,BoxLayout.X_AXIS));
        p7.add(yFromTo);

        JPanel p6 = new JPanel();
        p6.setLayout(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 0;
        p6.add(new JLabel("from:"));
        c2.gridx++;
        p6.add(yFromSlider);
        c2.gridx++;
        p6.add(yFrom);
        c2.gridx = 0;
        c2.gridy++;
        p6.add(new JLabel("to:"));
        c2.gridx++;
        p6.add(yToSlider);
        c2.gridx++;
        p6.add(yTo);

        JPanel p5 = new JPanel();
        p5.setLayout(new BoxLayout(p5,BoxLayout.X_AXIS));
        p5.add(startButton);
        p5.add(Box.createRigidArea(new Dimension(20,0)));
        p5.add(stopButton);

        JPanel p4 = new JPanel();
        p4.setLayout(new BoxLayout(p4,BoxLayout.Y_AXIS));
        p4.add(p17);
        p4.add(p16);
        p4.add(p15);
        p4.add(p14);
        p4.add(p13);
        p4.add(p12);
        p4.add(p11);
        p4.add(p10);
        p4.add(p9);
        p4.add(Box.createRigidArea(new Dimension(0,10)));
        p4.add(p8);
        p4.add(p7);
        p4.add(p6);
        p4.add(p5);

        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout());

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p2.add(hoText);
        p2.add(nameText);
        p2.add(nameValue);
        p2.add(sizeText);
        p2.add(sizeValue);
        p2.add(powerText);
        p2.add(powerValue);
        p2.add(xText);
        p2.add(xValue);
        p2.add(yText);
        p2.add(yValue);
        p2.add(colorText);
        p2.add(colorValue);
        p2.add(dateText);
        p2.add(dateValue);
        p2.setBorder(new LineBorder(Color.BLACK, 2));

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.add(p2, BorderLayout.NORTH);
        p1.add(p3, BorderLayout.CENTER);
        p1.add(p4, BorderLayout.EAST);
        add(p1);


        setTitle("Sea Collection Viewer");
        setSize(1200,900);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
