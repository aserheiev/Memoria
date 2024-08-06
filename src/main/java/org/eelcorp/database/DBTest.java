package org.eelcorp.database;
import org.eelcorp.*;

public class DBTest {
    public static void main(String[] args) {

        UserDBDAO dao = new UserDAO();
        dao.selectAllUsers();

    }

    public static void createTestUsers() {
        int status = UserUtilities.createUser("Idort", "ungaBunga123!");
        int status2 = UserUtilities.createUser("Testuser", "AsdFasd!23");
        int status3 = UserUtilities.createUser("Eels", "EelsDeals4%1");
    }
}
