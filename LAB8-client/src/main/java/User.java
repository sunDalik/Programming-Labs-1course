import java.io.Serializable;

@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    public String command = ""; // is used to send different commands to server ("signIn" or "signUp")

    private static final long serialVersionUID = 90L;

    User(){}

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}