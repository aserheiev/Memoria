package org.eelcorp;

import org.eelcorp.database.UserDAO;
import org.eelcorp.database.UserDBDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserUtilities {

    public static int createUser(String name, String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";

        // 48 is here because my database has a field of length 48, it doesn't have to be this way
        if (name == null || name.isEmpty() || name.length() > 48) {
            System.out.println("INVALID NAME");
            return 1;  // code 1: invalid name
        }

        if (password == null || password.isEmpty() || !password.matches(passwordRegex)) {
            System.out.println("INVALID PASSWORD");
            return 2;  // code 2: invalid password
        }

        // todo: check if by some cosmic coincidence it hasn't generated an existing one
        UUID newUserID = UUID.randomUUID();

        String passwordHash = generateHash(password);

        User newUser = new User(newUserID, name, passwordHash, LocalDateTime.now());

        System.out.println("New user added: ");
        System.out.println(newUser);

        // adds user to the database
        UserDBDAO dao = new UserDAO();

        dao.addUser(newUser);

        return 0;  // code 0: OK
    }

    private static String generateHash(String password) {
        String passwordHash;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            passwordHash = new String(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return passwordHash;
    }
}
