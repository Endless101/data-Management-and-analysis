import java.sql.*;

public class Database2 {

    // JDBC driver name and database URL

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://192.168.100.174/db";

    //  Database credentials
    static final String USER = "peanut222link";
    static final String PASS = "password";

    // https://stackoverflow.com/questions/37909487/how-can-i-connect-to-mariadb-using-java

    public void main() {
        Connection connection = null;
        Statement statement = null;
        try {
            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/databaseDIM", USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating table in given database...");
            statement = connection.createStatement();

            String sql = "CREATE TABLE REGISTRATION "
                    + "(id INTEGER not NULL, "
                    + " first VARCHAR(255), "
                    + " last VARCHAR(255), "
                    + " age INTEGER, "
                    + " PRIMARY KEY ( id ))";

            statement.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null) {
                    connection.close();
                }
            } catch (SQLException se) {
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
