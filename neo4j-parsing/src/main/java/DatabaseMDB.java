import java.sql.*;
import java.io.*;

public class DatabaseMDB {

    static final String USER = "peanut222link";
    static final String PASS = "password";
    static final String CSV_FILE = "journal.csv";

    int batchSize = 20;

    public static void createTables(Connection connection) {
        Statement statement = null;
        try {
            System.out.println("Creating table in given database...");
            statement = connection.createStatement();

            // PUT ALL THE DIFFERENT TABLES HERE

            String journalTable = "CREATE TABLE JOURNAL "
                    + "(article_key VARCHAR(255) not NULL,"
                    + "title VARCHAR(255));";

            statement.executeUpdate(journalTable);
            System.out.println("Created tables in given database...");
        } catch (SQLException e) {
            //Handle errors for JDBC
            e.printStackTrace();
        }
    }

    public static void readFromCSV(Connection connection) {

        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO JOURNAL (article_key, title) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(CSV_FILE));
            int batchSize = 20;
            String lineText;

            int counter = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");

                String title = data[0];
                String article_key = data[1];

                statement.setString(1, article_key);
                statement.setString(2, title);

                statement.addBatch();
                counter += 1;

                if (counter % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            lineReader.close();
            statement.executeBatch();
            connection.commit();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // https://stackoverflow.com/questions/37909487/how-can-i-connect-to-mariadb-using-java

    public static void main() throws FileNotFoundException {
        Connection connection = null;
        try {
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/databaseDIM", USER, PASS);
            System.out.println("Connected database successfully...");

            createTables(connection);
            readFromCSV(connection);

        } catch (SQLException e) {
            //Handle errors for JDBC
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Goodbye!");
    }

}
