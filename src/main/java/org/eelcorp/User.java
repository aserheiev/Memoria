package org.eelcorp;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private UUID userID;
    private String name;
    private String pwd_hash;
    private LocalDateTime last_login;
    private int pb2x3;
    private int pb3x4;
    private int pb4x5;

    public User(UUID userID, String name, String pwd_hash, LocalDateTime last_login, int pb2x3, int pb3x4, int pb4x5) {
        this.userID = userID;
        this.name = name;
        this.pwd_hash = pwd_hash;
        this.last_login = last_login;
        this.pb2x3 = pb2x3;
        this.pb3x4 = pb3x4;
        this.pb4x5 = pb4x5;
    }

    public User(UUID userID, String name, String pwd_hash, LocalDateTime last_login) {
        this.userID = userID;
        this.name = name;
        this.pwd_hash = pwd_hash;
        this.last_login = last_login;
        pb2x3 = 0;
        pb3x4 = 0;
        pb4x5 = 0;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getPwd_hash() {
        return pwd_hash;
    }

    public LocalDateTime getLast_login() {
        return last_login;
    }

    public int getPB2x3() {
        return pb2x3;
    }

    public int getPB3x4() {
        return pb3x4;
    }

    public int getPB4x5() {
        return pb4x5;
    }

    @Override
    public String toString() {
        String info = "User ID: %s\n" +
                "User name: %s\n"
                + "PWD hash: %s\n"
                + "Last login: %s\n";

        return String.format(info, userID.toString(), name, pwd_hash, last_login.toString());
    }
}