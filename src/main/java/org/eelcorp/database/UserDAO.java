package org.eelcorp.database;

import org.eelcorp.User;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserDAO implements UserDBDAO {
    private final DBClient dbClient;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/memoriaDB";

    // todo: unhardcode these idiot

    private final String USER = "MemeManager";
    private final String PASS = "rememberme";

    private final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS public.users\n" +
            "(\n" +
            "    \"uid\" character varying(48) NOT NULL,\n" +
            "    name character varying(48) NOT NULL,\n" +
            "    password_hash character varying(48) NOT NULL,\n" +
            "    lastlogin timestamp without time zone NOT NULL,\n" +
            "    pb2x3 integer DEFAULT 0,\n" +
            "    pb3x4 integer DEFAULT 0,\n" +
            "    pb4x5 integer DEFAULT 0,\n" +
            "    PRIMARY KEY (\"uid\")\n" +
            ");\n" +
            "\n" +
            "ALTER TABLE IF EXISTS public.users\n" +
            "    OWNER to \"MemeManager\"";

    private final String CREATE_2x3_TABLE = "CREATE TABLE IF NOT EXISTS public.scores2x3\n" +
            "(\n" +
            "    score_id serial NOT NULL,\n" +
            "    score integer NOT NULL,\n" +
            "    uid character varying(48) NOT NULL,\n" +
            "    PRIMARY KEY (score_id)\n" +
            ");\n" +
            "\n" +
            "ALTER TABLE IF EXISTS public.scores2x3\n" +
            "    OWNER to \"MemeManager\";\n" +
            "\n" +
            "GRANT ALL ON TABLE public.scores2x3 TO \"MemeManager\"";

    private final String CREATE_3x4_TABLE = "CREATE TABLE IF NOT EXISTS public.scores3x4\n" +
            "(\n" +
            "    score_id serial NOT NULL,\n" +
            "    score integer NOT NULL,\n" +
            "    uid character varying(48) NOT NULL,\n" +
            "    PRIMARY KEY (score_id)\n" +
            ");\n" +
            "\n" +
            "ALTER TABLE IF EXISTS public.scores3x4\n" +
            "    OWNER to \"MemeManager\";\n" +
            "\n" +
            "GRANT ALL ON TABLE public.scores3x4 TO \"MemeManager\"";

    private final String CREATE_4x5_TABLE = "CREATE TABLE IF NOT EXISTS public.scores4x5\n" +
            "(\n" +
            "    score_id serial NOT NULL,\n" +
            "    score integer NOT NULL,\n" +
            "    uid character varying(48) NOT NULL,\n" +
            "    PRIMARY KEY (score_id)\n" +
            ");\n" +
            "\n" +
            "ALTER TABLE IF EXISTS public.scores4x5\n" +
            "    OWNER to \"MemeManager\";\n" +
            "\n" +
            "GRANT ALL ON TABLE public.scores4x5 TO \"MemeManager\"";

    private final String DROP_USER_TABLE = "DROP TABLE IF EXISTS users";
    private final String DROP_2x3_TABLE = "DROP TABLE IF EXISTS scores2x3";
    private final String DROP_3x4_TABLE = "DROP TABLE IF EXISTS scores3x4";
    private final String DROP_4x5_TABLE = "DROP TABLE IF EXISTS scores4x5";


    private final String ADD_USER = "INSERT INTO users (uid, name, password_hash, lastlogin) " +
            "VALUES ('%s', '%s', '%s', '%s')";

    private final String SELECT_SCORES = "SELECT score FROM %s";

    private final String DELETE_SCORE = "DELETE FROM %s WHERE score_id = %d";


    public UserDAO() {
        dbClient = new DBClient(DB_URL, USER, PASS);
    }

    @Override
    public void addUser(User user) {
        String UID = user.getUserID().toString();
        String name = user.getName();
        String pwdHash = user.getPwd_hash();
        String lastLogin = user.getLast_login().toString().substring(0, 19).replace("T", " ");

        dbClient.executeUpdate(String.format(ADD_USER, UID, name, pwdHash, lastLogin));
    }

    @Override
    public User selectUser(int userID) {
        List<User> allUsers = selectAllUsers();

        if (allUsers.size() == 1) {
            return allUsers.get(0);
        } else if (!allUsers.isEmpty()) {
            for (User user : allUsers) {
                if (user.getUserID().equals(userID)) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public List<User> selectAllUsers() {
        return dbClient.getAllUsers();
    }

    @Override
    public boolean isNewHighScore(String tableName, int score) {
        String sql = String.format(SELECT_SCORES, tableName);

        // it has to be a map, because there can be two high scores that are the same
        // if that's the case, with a list we'd be deleting both records
        Map<Integer, Integer> scores = dbClient.selectScores(sql);

        // I want to keep only the top 10. If there's fewer than 10 scores, then it's automatically a high score
        if (scores.size() < 10) {
            return true;
        } else {
            // if the current score is greater than the smallest high score, we have a new high score
            int minHighScore = Collections.min(scores.values());

            if (score > minHighScore) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void replaceHighScore(String tableName, String uid) {
        // todo: this needs to do two things: delete the smallest old high score, then add the new score
        String sql = String.format(SELECT_SCORES, tableName);
        Map<Integer, Integer> scores = dbClient.selectScores(sql);

        // todo: find the key:value pair with the minimum value and the highest ID
        // wait why can't I just get this directly with an SQL request???

        /* test SQL:
        INSERT INTO scores2x3 (score, uid)
        VALUES (10, 'uid1'),
        (20, 'uid2'),
        (40, 'uid22'),
        (100, 'uid100'),
        (200, 'uid200'),
        (210, 'uid2'),
        (50, 'uid10'),
        (40, 'uid10'),
        (6, 'uid2'),
        (6, 'uid3');
         */

        int minHighScore = Collections.min(scores.values());
        int key = Integer.MIN_VALUE;

        // so now we have the key right?
        // this is probably stupid but what else is new
        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
            if (entry.getValue() == minHighScore) {
                if (entry.getKey() > key) {
                    key = entry.getKey();
                }
            }
        }

    }

    @Override
    public void replacePB(int userID) {

    }

    @Override
    public String getHighScores() {
        return "";
    }

    public void createTables() {
        dbClient.executeUpdate(CREATE_USER_TABLE);
        dbClient.executeUpdate(CREATE_2x3_TABLE);
        dbClient.executeUpdate(CREATE_3x4_TABLE);
        dbClient.executeUpdate(CREATE_4x5_TABLE);
    }

    public void dropTables() {
        dbClient.executeUpdate(DROP_USER_TABLE);
        dbClient.executeUpdate(DROP_2x3_TABLE);
        dbClient.executeUpdate(DROP_3x4_TABLE);
        dbClient.executeUpdate(DROP_4x5_TABLE);
    }

}
