import java.sql.*;

public class DatabaseMDB {

    // JDBC driver name and database URL

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://localhost:3306/dblp";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "0000";

    // https://stackoverflow.com/questions/37909487/how-can-i-connect-to-mariadb-using-java

    public void createTables () {
        Connection connection = null;
        Statement statement = null;
        try {
            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(
                    DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating table in given database...");
            statement = connection.createStatement();

            String sql_article = "CREATE TABLE article "
                    + "(article_key VARCHAR(255) not NULL, "
                    + " authors VARCHAR(255), "
                    + " title VARCHAR(255), "
                    + " journal VARCHAR(255), "
                    + " year VARCHAR(255), "
                    + " pages VARCHAR(255), "
                    + " volume VARCHAR(255), "
                    + " number VARCHAR(255)) ";

            String sql_proceeding = "CREATE TABLE proceedings "
                    + "(proceeding_key VARCHAR(255) not NULL, "
                    + " editor VARCHAR(255), "
                    + " title VARCHAR(255), "
                    + " booktitle VARCHAR(255), "
                    + " year VARCHAR(255), "
                    + " volume VARCHAR(255))";

            String sql_inproceeding = "CREATE TABLE inproceedings "
                    + "(inproceeding_key VARCHAR(255) not NULL, "
                    + " title VARCHAR(255), "
                    + " booktitle VARCHAR(255), "
                    + " year VARCHAR(255), "
                    + " pages VARCHAR(255)) ";

            String sql_author = "CREATE TABLE author "
                    + "(article_key VARCHAR(255) not NULL, "
                    + " inproceeding_key VARCHAR(255) not NULL,"
                    + " name VARCHAR(255) )";

            String sql_editor = "CREATE TABLE editor "
                    + "(proceeding_key VARCHAR(255) not NULL,"
                    + " name VARCHAR(255) )";

            String sql_journal = "CREATE TABLE journal "
                    + "(article_key VARCHAR(255) not NULL, "
                    + " title VARCHAR(255))";

            String sql_conferences = "CREATE TABLE conferences "
                    + "(proceeding_key VARCHAR(255) not NULL, "
                    + " title VARCHAR(255))";




            statement.executeUpdate(sql_article);
            statement.executeUpdate(sql_proceeding);
            statement.executeUpdate(sql_inproceeding);
            statement.executeUpdate(sql_author);
            statement.executeUpdate(sql_editor);
            statement.executeUpdate(sql_journal);
            statement.executeUpdate(sql_conferences);

            System.out.println("Created all table in given database successfully!");
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
        System.out.println("All done!");
    }
}
