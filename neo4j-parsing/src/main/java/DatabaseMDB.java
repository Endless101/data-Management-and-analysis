import java.sql.*;
import java.io.*;

public class DatabaseMDB {

    // JDBC driver name and database URL

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://192.168.100.174/db";

    static String csvFile = "journal.csv";
    int batchSize = 20;

    //  Database credentials
    static final String USER = "peanut222link";
    static final String PASS = "password";

    public static void readFromCSV() throws FileNotFoundException {

        try {
            String sql = "INSERT INTO review (course_name, student_name, timestamp, rating, comment) VALUES (?, ?, ?, ?, ?)";

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFile));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String title = data[0];
                String article_key = data[1];
                System.out.println(title + " " + article_key);
            }

            lineReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // https://stackoverflow.com/questions/37909487/how-can-i-connect-to-mariadb-using-java

    public static void main() throws FileNotFoundException {
        Connection connection = null;
        Statement statement = null;

        readFromCSV();

        /* try {
            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/databaseDIM", USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating table in given database...");
            statement = connection.createStatement();

            String sql = "CREATE TABLE JOURNAL "
                    + "(article_key VARCHAR(255) not NULL,"
                    + "title VARCHAR(255),"
                    + "PRIMARY KEY (article_key));";

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
        System.out.println("Goodbye!"); */


    }

    // Procedure for creating the tables


    // Procedure for reading the csv files into the right tables

}
