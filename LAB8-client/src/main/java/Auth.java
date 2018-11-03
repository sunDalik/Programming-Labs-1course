import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

class Auth {
    private Locale rulocale = new Locale("ru");
    private Locale enlocale = new Locale("en");
    private Locale eslocale = new Locale("es", "MX");
    private Locale delocale = new Locale("de");
    private Locale ltlocale = new Locale("lt");
    private ResourceBundle bundle = ResourceBundle.getBundle("Bundle", Locale.getDefault(), new UTF8Control());

    private JMenu language = new JMenu(bundle.getString("language"));
    private JLabel loginText = new JLabel(bundle.getString("login"));
    private JTextField loginField = new JTextField();
    private JLabel passwordText = new JLabel(bundle.getString("password"));
    private JPasswordField passwordField = new JPasswordField();
    private File usersData = new File("users.csv");
    private JFrame frame = new JFrame();
    private JLabel logTooShort = new JLabel(bundle.getString("logTooShort"));
    private JLabel passTooShort = new JLabel(bundle.getString("passTooShort"));
    private JLabel objection = new JLabel(bundle.getString("objection"));
    private JLabel userAlreadyExists = new JLabel(bundle.getString("userAlreadyExists"));
    private JButton signInButton = new JButton(bundle.getString("signIn"));
    private JButton signUpButton = new JButton(bundle.getString("signUp"));

    Auth() {
        loginText.setHorizontalAlignment(SwingConstants.CENTER);
        passwordText.setHorizontalAlignment(SwingConstants.CENTER);
        logTooShort.setForeground(Color.RED);
        passTooShort.setForeground(Color.RED);
        objection.setForeground(Color.RED);
        userAlreadyExists.setForeground(Color.RED);
        loginField.setPreferredSize(new Dimension(130, 20));
        passwordField.setPreferredSize(new Dimension(130, 20));

        JMenuBar menuBar = new JMenuBar();
        UIManager.put("Menu.font", new Font("Helvetica", Font.PLAIN, 16));
        UIManager.put("MenuItem.font", new Font("Helvetica", Font.PLAIN, 16));
        UIManager.put("TextField.font", new Font("Helvetica", Font.PLAIN, 15));
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
        frame.setJMenuBar(menuBar);

        signInButton.addActionListener(args0 -> {
            if (!checkUser()) {
                logTooShort.setVisible(false);
                passTooShort.setVisible(false);
                userAlreadyExists.setVisible(false);
                objection.setVisible(true);
            } else {
                work();
            }
        });

        signUpButton.addActionListener(e -> {
            if (checkIfExists()) {
                logTooShort.setVisible(false);
                passTooShort.setVisible(false);
                userAlreadyExists.setVisible(true);
                objection.setVisible(false);
            } else {
                addUser();
            }
        });

        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        frame.add(loginText, c);
        c.gridx++;
        frame.add(loginField, c);
        c.gridx = 0;
        c.gridy++;
        frame.add(passwordText, c);
        c.gridx++;
        frame.add(passwordField, c);
        c.gridx = 0;
        c.gridy++;
        frame.add(signUpButton, c);
        c.gridx++;
        frame.add(signInButton, c);
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridheight = 4;
        logTooShort.setVisible(false);
        frame.add(logTooShort, c);
        c.gridy++;
        passTooShort.setVisible(false);
        frame.add(passTooShort, c);
        c.gridy++;
        userAlreadyExists.setVisible(false);
        frame.add(userAlreadyExists, c);
        c.gridy++;
        objection.setVisible(false);
        frame.add(objection, c);

        frame.setTitle(bundle.getString("title0"));
        frame.setSize(350, 220);
        frame.setLocation(500, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private boolean checkIfExists() {
        try {
            Scanner sc = new Scanner(usersData);
            sc.useDelimiter("[,\n]");
            ArrayList<String> users = new ArrayList<>();
            while (sc.hasNext()) {
                users.add(sc.next());
                sc.next();
            }
            for (String user : users) {
                if (user.equals(loginField.getText())) {
                    return true;
                }
            }
            return false;
        } catch (FileNotFoundException e) {
            return createNewUsersDataFile();
        }
    }

    private void work() {
        frame.setVisible(false);
        frame.dispose();
        new MySwingWorker().execute(); //other way it doesn't work :\
    }

    private void addUser() {
        if (loginField.getText().length() <= 3) {
            logTooShort.setVisible(true);
            passTooShort.setVisible(false);
            userAlreadyExists.setVisible(false);
            objection.setVisible(false);
        } else if (new String(passwordField.getPassword()).length() <= 3) {
            logTooShort.setVisible(false);
            passTooShort.setVisible(true);
            userAlreadyExists.setVisible(false);
            objection.setVisible(false);
        } else {
            try {
                Scanner sc = new Scanner(usersData);
                String old = "";
                while (sc.hasNextLine()) {
                    old += sc.nextLine() + "\n";
                }
                PrintWriter pw = new PrintWriter(usersData);
                pw.write(old + loginField.getText() + "," + MD5(new String(passwordField.getPassword())) + "\n");
                pw.close();
                work();
            } catch (FileNotFoundException e) {
                createNewUsersDataFile();
                addUser();
            }
        }
    }

    private boolean checkUser() {
        try {
            Scanner sc = new Scanner(usersData);
            sc.useDelimiter("[,\n]");
            ArrayList<String> users = new ArrayList<>();
            ArrayList<String> passwords = new ArrayList<>();
            while (sc.hasNext()) {
                users.add(sc.next());
                passwords.add(sc.next());
            }
            String login = loginField.getText();
            String password = MD5(new String(passwordField.getPassword()));
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).equals(login) && passwords.get(i).equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (FileNotFoundException e) {
            return createNewUsersDataFile();
        }
    }

    private boolean createNewUsersDataFile() {
        try {
            new File("users.csv").createNewFile();
            return false;
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(1);
            return false;
        }
    }


    private String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ignored) {
        }
        return null;
    }

    private void changeLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("Bundle", locale, new UTF8Control());
        frame.setTitle(bundle.getString("title0"));
        language.setText(bundle.getString("language"));
        loginText.setText(bundle.getString("login"));
        passwordText.setText(bundle.getString("password"));
        signInButton.setText(bundle.getString("signIn"));
        signUpButton.setText(bundle.getString("signUp"));
        objection.setText(bundle.getString("objection"));
        logTooShort.setText(bundle.getString("logTooShort"));
        passTooShort.setText(bundle.getString("passTooShort"));
        userAlreadyExists.setText(bundle.getString("userAlreadyExists"));

    }
}
