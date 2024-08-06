package org.eelcorp.database;
import org.eelcorp.User;

import java.util.List;

public interface UserDBDAO {
    public void addUser(User user);

    public User selectUser(int userID);

    public List<User> selectAllUsers();

    public boolean isNewHighScore(String table_name, int score);

    public void replaceHighScore(String table_name, String uid);

    public void replacePB(int userID);

    public String getHighScores();

    public void createTables();

    public void dropTables();
}
