import javax.swing.*;
import java.awt.*;
import java.util.List;

class Auth {

    static SeaManager sm;
    private JTextField loginField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JFrame frame;
    private JLabel message;

    Auth() {
        ORM.createTableIfNotExists(User.class);
        frame = new JFrame();

        JLabel loginText = new JLabel("Login:");
        loginText.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel passwordText = new JLabel("Password:");
        passwordText.setHorizontalAlignment(SwingConstants.CENTER);
        message = new JLabel();
        message.setForeground(Color.RED);
        message.setPreferredSize(new Dimension(260, 20));

        loginField.setPreferredSize(new Dimension(130, 20));
        passwordField.setPreferredSize(new Dimension(130, 20));

        JButton signInButton = new JButton("Sign In");
        JButton signUpButton = new JButton("Sign Up");
        signInButton.addActionListener(args0 -> {
            User user = new User(loginField.getText(), MD5(new String(passwordField.getPassword())));
            if (!checkUser(user)) {
                message.setText("Objection!");
            } else {
                work();
            }
        });

        signUpButton.addActionListener(e -> {
            User user = new User(loginField.getText(), MD5(new String(passwordField.getPassword())));
            if (checkIfExists(user)) {
                message.setText("User already exists");
            } else {
                addUser(user);
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

        frame.setTitle("Authorisation");
        frame.setSize(350, 220);
        frame.setLocation(500, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private boolean checkIfExists(User user) {
        List<User> users = ORM.selectAll(User.class);
        if (users != null) {
            for (User dbUser : users) {
                if (dbUser.getLogin().equals(user.getLogin())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void work() {
        frame.setVisible(false);
        frame.dispose();
        sm = new SeaManager(new GUI());
        sm.load();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> sm.save()));
        new NetworkHandler(sm).execute();
    }

    private void addUser(User user) {
        if (new String(passwordField.getPassword()).length() <= 3) { //Can't check user's password because its encrypted
            message.setText("Password must be at least 3 characters long");
        } else if (user.getLogin().length() <= 3) {
            message.setText("Login must be at least 3 characters long");
        } else {
            ORM.insertRecord(user);
            work();
        }
    }

    private boolean checkUser(User user) {
        List<User> users = ORM.selectAll(User.class);
        if (users != null) {
            for (User dbUser : users) {
                if (dbUser.getLogin().equals(user.getLogin()) && dbUser.getPassword().equals(user.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }


    static String signClientIn(User user) {
        List<User> users = ORM.selectAll(User.class);
        if (users != null) {
            for (User dbUser : users) {
                if (dbUser.getLogin().equals(user.getLogin()) && dbUser.getPassword().equals(user.getPassword())) {
                    return ""; // no errors, user can enter
                }
            }
        }
        return "objection"; // login+password incorrect
    }


    static String signClientUp(User user) {
        List<User> users = ORM.selectAll(User.class);
        if (users != null) {
            for (User dbUser : users) {
                if (dbUser.getLogin().equals(user.getLogin())) {
                    return "userAlreadyExists";
                }
            }
        }
        if (user.getLogin().length() <= 3) {
            return "logTooShort";
        } else {  // There is no way to check password length since its already encrypted
            ORM.insertRecord(user);
            return "";
        }
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
}
