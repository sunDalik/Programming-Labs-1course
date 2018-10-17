import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Auth {

    static SeaManager sm;
    private JTextField loginField;
    private JPasswordField passwordField;
    private File usersData = new File("users.csv");
    JFrame frame;
    JLabel message;

    Auth() {
        frame = new JFrame();

        JLabel loginText = new JLabel("Login:");
        loginText.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel passwordText = new JLabel("Password:");
        passwordText.setHorizontalAlignment(SwingConstants.CENTER);
        message = new JLabel();
        message.setForeground(Color.RED);
        message.setPreferredSize(new Dimension(250, 20));

        loginField = new JTextField();
        loginField.setPreferredSize(new Dimension(100, 20));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(100, 20));

        JButton signInButton = new JButton("Sign In");
        JButton signUpButton = new JButton("Sign Up");
        signInButton.addActionListener(args0 -> {
            if (!checkUser()) {
                message.setText("Objection!");
            } else {
                work();
            }
        });

        signUpButton.addActionListener(e -> {
            if (checkIfExists()) {
                message.setText("User already exists");
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
        frame.add(message, c);

        frame.setTitle("Auth");
        frame.setSize(300, 200);
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
        frame.removeAll();
        sm = new SeaManager("collection.csv", new GUI());
        sm.load();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> sm.save()));
        try (ServerSocket server = new ServerSocket(11037)) {
            while (true) {
                Socket client = server.accept();
                new Thread(new RequestsHandler(sm, client)).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addUser() {
        if (loginField.getText().length() <= 2) {
            message.setText("Login must be at least 3 characters long");
        } else if (new String(passwordField.getPassword()).length() <= 3) {
            message.setText("Password must be at least 4 characters long");
        } else {
            try {
                Scanner sc = new Scanner(usersData);
                String old = "";
                while (sc.hasNextLine()){
                    old += sc.nextLine() + "\n";
                }
                PrintWriter pw = new PrintWriter(usersData);
                pw.write(old + loginField.getText() + "," + MD5(new String(passwordField.getPassword()) + "\n"));
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
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).equals(loginField.getText()) && passwords.get(i).equals(MD5(new String(passwordField.getPassword())))) {
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


    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
