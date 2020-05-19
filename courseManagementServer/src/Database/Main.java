package Database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConnectWithDatabase c = new ConnectWithDatabase("47.102.200.197","root","XUAN","assignment");
        c.printResultSet(c.myExecuteQuery("SELECT * FROM studentLearning WHERE studentNumber='001';"));
    }
}
