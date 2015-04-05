package myusick.model.DTO;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class LoginDTO implements Serializable {
    private String user;
    private String password;
    private int userId;
    private Publisher[] groups;

    public LoginDTO(){}
    public LoginDTO(String user, String password, int userId, Publisher[] groups) {
        this.user = user;
        this.password = password;
        this.userId = userId;
        this.groups = groups;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Publisher[] getGroups() {
        return groups;
    }

    public void setGroups(Publisher[] groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", userId=" + userId +
                ", groups=" + Arrays.toString(groups) +
                '}';
    }
}
