package org.eelcorp.database;

import org.eelcorp.User;

import java.rmi.server.UID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DBClient {
    private final String dbURL;
    private final String dbUser;
    private final String dbPass;

    public DBClient(String dbURL, String dbUser, String dbPass) {
        this.dbURL = dbURL;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    public void executeUpdate(String sql) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
             Statement stmt = conn.createStatement()) {

            try (ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                while (rs.next()) {
                    String uid = rs.getString("uid");
                    String name = rs.getString("name");
                    String pwdHash = rs.getString("password_hash");
                    LocalDateTime lastLogin = LocalDateTime.parse(rs.getString("lastlogin"), formatter);
                    int pb2x3 = rs.getInt("pb2x3");
                    int pb3x4 = rs.getInt("pb3x4");
                    int pb4x5 = rs.getInt("pb4x5");

                    User newUser = new User(UUID.fromString(uid), name, pwdHash, lastLogin, pb2x3, pb3x4, pb4x5);
                    users.add(newUser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("DEBUG LIST:");

        for (User user : users) {
            System.out.println(user);
        }

        return users;
    }

    public Map<Integer, Integer> selectScores(String sql) {
        Map<Integer, Integer> idScoreMap = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
             Statement stmt = conn.createStatement()) {

            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    idScoreMap.put(rs.getInt("score_id"), rs.getInt("score"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idScoreMap;
    }





}
