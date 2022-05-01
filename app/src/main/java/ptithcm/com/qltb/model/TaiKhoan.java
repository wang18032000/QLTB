package ptithcm.com.qltb.model;

import java.io.Serializable;

/**
 * Created by apoll on 4/30/2022.
 */

public class TaiKhoan implements Serializable {
    private String username;
    private String password;
    private String email;
    private String maPQ;

    public TaiKhoan() {
    }

    public TaiKhoan(String username, String password, String email, String maPQ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.maPQ = maPQ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaPQ() {
        return maPQ;
    }

    public void setMaPQ(String maPQ) {
        this.maPQ = maPQ;
    }
}
