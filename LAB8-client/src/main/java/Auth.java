import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Locale;
import java.util.ResourceBundle;

class Auth {
    private Locale rulocale = new Locale("ru");
    private Locale enlocale = new Locale("en");
    private Locale eslocale = new Locale("es", "MX");
    private Locale delocale = new Locale("de");
    private Locale ltlocale = new Locale("lt");
    private Locale chosenLocale = Locale.getDefault();
    private ResourceBundle bundle = ResourceBundle.getBundle("Bundle", chosenLocale, new UTF8Control());

    private JMenu language = new JMenu(bundle.getString("language"));
    private JLabel loginText = new JLabel(bundle.getString("login"));
    private JTextField loginField = new JTextField();
    private JLabel passwordText = new JLabel(bundle.getString("password"));
    private JPasswordField passwordField = new JPasswordField();
    private JFrame frame = new JFrame();
    private JLabel logTooShort = new JLabel(bundle.getString("logTooShort"));
    private JLabel passTooShort = new JLabel(bundle.getString("passTooShort"));
    private JLabel objection = new JLabel(bundle.getString("objection"));
    private JLabel serverUnavailable = new JLabel(bundle.getString("disconnected"));
    private JLabel userAlreadyExists = new JLabel(bundle.getString("userAlreadyExists"));
    private JButton signInButton = new JButton(bundle.getString("signIn"));
    private JButton signUpButton = new JButton(bundle.getString("signUp"));
    private SocketAddress server = new InetSocketAddress("localhost", 11037);

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
            User user = new User(loginField.getText(), MD5(new String(passwordField.getPassword())));
            signUserIn(user);
        });

        signUpButton.addActionListener(e -> {
            User user = new User(loginField.getText(), MD5(new String(passwordField.getPassword())));
            signUserUp(user);
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
        c.gridheight = 5;
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
        c.gridy++;
        serverUnavailable.setVisible(false);
        frame.add(serverUnavailable, c);

        frame.setTitle(bundle.getString("title0"));
        frame.setSize(350, 220);
        frame.setLocation(500, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void signUserIn(User user) {
        if (checkInput(user)) {
            try {
                SocketChannel sc = SocketChannel.open(server);
                ObjectOutputStream oos = new ObjectOutputStream(sc.socket().getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(sc.socket().getInputStream());
                user.command = "signIn";
                oos.writeObject(user);
                handleServerErrors((String)ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                setErrorMessage(serverUnavailable);
            }
        }
    }

    private void signUserUp(User user) {
        if (checkInput(user)) {
            try {
                SocketChannel sc = SocketChannel.open(server);
                ObjectOutputStream oos = new ObjectOutputStream(sc.socket().getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(sc.socket().getInputStream());
                user.command = "signUp";
                oos.writeObject(user);
                handleServerErrors((String)ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                setErrorMessage(serverUnavailable);
            }
        }
    }

    private boolean checkInput(User user) {
        if(new String(passwordField.getPassword()).length() <= 3){ //Can't check user's password because its encrypted
            setErrorMessage(passTooShort);
            return false;
        }
        else if (user.getLogin().length() <= 3) {
            setErrorMessage(logTooShort);
            return false;
        } else return true;
    }

    private void work() {
        frame.setVisible(false);
        frame.dispose();
        new MySwingWorker(chosenLocale).execute();
    }


    private String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ignored) {
        }
        return null;
    }

    private void changeLanguage(Locale locale) {
        chosenLocale = locale;
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
        serverUnavailable.setText(bundle.getString("disconnected"));
    }

    private void handleServerErrors(String error){
        switch (error) {
            case "":
                work();
                break;
            case "logTooShort":
                setErrorMessage(logTooShort);
                break;
            case "userAlreadyExists":
                setErrorMessage(userAlreadyExists);
                break;
            case "objection":
                setErrorMessage(objection);
                break;
        }
    }

    private void setErrorMessage(JLabel message) {
        logTooShort.setVisible(false);
        passTooShort.setVisible(false);
        userAlreadyExists.setVisible(false);
        objection.setVisible(false);
        serverUnavailable.setVisible(false);
        message.setVisible(true);
    }
}
