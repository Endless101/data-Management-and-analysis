import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DatabaseMDB {

    public void createTables(Connection connection) throws SQLException {
        Statement statement = null;
        //STEP 4: Execute a query
        System.out.println("Creating table in given database...");
        statement = connection.createStatement();

        String sql_article = "CREATE TABLE article "
                + "(article_key VARCHAR(255) not NULL, "
                + " authors VARCHAR(255), "
                + " title VARCHAR(1000), "
                + " journal VARCHAR(255), "
                + " year VARCHAR(255), "
                + " pages VARCHAR(255), "
                + " volume VARCHAR(255), "
                + " number VARCHAR(255)) ";

        String sql_proceeding = "CREATE TABLE proceedings "
                + "(proceeding_key VARCHAR(255) not NULL, "
                + " editor VARCHAR(255), "
                + " title VARCHAR(1000), "
                + " booktitle VARCHAR(255), "
                + " year VARCHAR(255), "
                + " volume VARCHAR(255))";

        String sql_inproceeding = "CREATE TABLE inproceedings "
                + "(inproceeding_key VARCHAR(255) not NULL, "
                + " title VARCHAR(1000), "
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
                + "(inproceeding_key VARCHAR(255) not NULL, "
                + " title VARCHAR(255))";

        statement.executeUpdate(sql_article);
        statement.executeUpdate(sql_proceeding);
        statement.executeUpdate(sql_inproceeding);
        statement.executeUpdate(sql_author);
        statement.executeUpdate(sql_editor);
        statement.executeUpdate(sql_journal);
        statement.executeUpdate(sql_conferences);

        System.out.println("Created all tables in given database successfully!");

        //finally block used to close resources
        if (statement != null) connection.close();
        if (connection != null) connection.close();
        System.out.println("All done!");
    }

    public static void loadCSVDataProceedings(Connection connection) throws SQLException, IOException {

        // https://www.codejava.net/coding/java-code-example-to-insert-data-from-csv-to-database
        String prooceedingsCSVFilePath = "proceedings.csv";

        // Creating a prepares insert statement
        String insertSql = "INSERT INTO proceedings (proceeding_key, editor, title, booktitle, year, volume) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertSql);

        BufferedReader lineReader = new BufferedReader(new FileReader(prooceedingsCSVFilePath));

        int batchSize = 20;
        String lineText = null;
        int count = 0;

        // skip header line
        lineReader.readLine();

        while ((lineText = lineReader.readLine()) != null) {
            String[] data = lineText.split(",");
            String proceeding_key = data[5];
            String title = data[4];
            String booktitle = data[3];
            String editor = data[2];
            String year = data[1];
            String volume = data[0];

            statement.setString(1, proceeding_key.replace("'", ""));
            statement.setString(2, editor.replace("'", ""));
            statement.setString(3, title.replace("'", ""));
            statement.setString(4, booktitle.replace("'", ""));
            statement.setString(5, year.replace("'", ""));
            statement.setString(6, volume.replace("'", ""));
            statement.addBatch();

            if (count % batchSize == 0) {
                statement.executeBatch();
            }
        }
        lineReader.close();

        // execute the remaining queries
        statement.executeBatch();
        connection.commit();
        connection.close();
        connection.rollback();
    }

    public static void loadCSVDataInproceedings(Connection connection) throws SQLException, IOException {

        // https://www.codejava.net/coding/java-code-example-to-insert-data-from-csv-to-database
        String inprooceedingsCSVFilePath = "inproceeding.csv";

        // Creating a prepares insert statement
        String insertSql = "INSERT INTO inproceedings (inproceeding_key, title, booktitle, year, pages) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertSql);

        BufferedReader lineReader = new BufferedReader(new FileReader(inprooceedingsCSVFilePath));

        int batchSize = 20;
        String lineText = null;
        int count = 0;

        // skip header line
        lineReader.readLine();

        while ((lineText = lineReader.readLine()) != null) {
            String[] data = lineText.split(",");
            String inproceeding_key = data[4];
            String title = data[3];
            String booktitle = data[2];
            String year = data[1];
            String pages = data[0];

            statement.setString(1, inproceeding_key.replace("'", ""));
            statement.setString(2, title.replace("'", ""));
            statement.setString(3, booktitle.replace("'", ""));
            statement.setString(4, year.replace("'", ""));
            statement.setString(5, pages.replace("'", ""));
            statement.addBatch();

            if (count % batchSize == 0) {
                statement.executeBatch();
            }
        }
        lineReader.close();

        // execute the remaining queries
        statement.executeBatch();
        connection.commit();
        connection.close();
        connection.rollback();
    }

    public static void loadCSVDataEditor(Connection connection) throws SQLException, IOException {

        // https://www.codejava.net/coding/java-code-example-to-insert-data-from-csv-to-database
        String editorCSVFilePath = "editor.csv";

        // Creating a prepares insert statement
        String insertSql = "INSERT INTO editor (proceeding_key, name) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertSql);

        BufferedReader lineReader = new BufferedReader(new FileReader(editorCSVFilePath));

        int batchSize = 20;
        String lineText = null;
        int count = 0;

        // skip header line
        lineReader.readLine();

        while ((lineText = lineReader.readLine()) != null) {
            String[] data = lineText.split(",");
            String proceeding_key = data[1];
            String name = data[0];

            statement.setString(1, proceeding_key.replace("'", ""));
            statement.setString(2, name.replace("'", ""));
            statement.addBatch();

            if (count % batchSize == 0) {
                statement.executeBatch();
            }
        }
        lineReader.close();

        // execute the remaining queries
        statement.executeBatch();
        connection.commit();
        connection.close();
        connection.rollback();
    }

    public static void loadCSVDataConference(Connection connection) throws SQLException, IOException {

        // https://www.codejava.net/coding/java-code-example-to-insert-data-from-csv-to-database
        String conferenceCSVFilePath = "conference.csv";

        // Creating a prepares insert statement
        String insertSql = "INSERT INTO conferences (inproceeding_key, title) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertSql);

        BufferedReader lineReader = new BufferedReader(new FileReader(conferenceCSVFilePath));

        int batchSize = 20;
        String lineText = null;
        int count = 0;

        // skip header line
        lineReader.readLine();

        while ((lineText = lineReader.readLine()) != null) {
            String[] data = lineText.split(",");
            String inproceeding_key = data[1];
            String title = data[0];

            statement.setString(1, inproceeding_key.replace("'", "").replace("key= ",""));
            statement.setString(2, title.replace("'", ""));
            statement.addBatch();

            if (count % batchSize == 0) {
                statement.executeBatch();
            }
        }
        lineReader.close();

        // execute the remaining queries
        statement.executeBatch();
        connection.commit();
        connection.close();
        connection.rollback();
    }

    public static void main (String[] args){
        // https://stackoverflow.com/questions/37909487/how-can-i-connect-to-mariadb-using-java
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
        final String DB_URL = "jdbc:mariadb://localhost:3306/dblp";

        //  Database credentials
        final String USER = "root";
        final String PASS = "0000";

        //STEP 3: Open a connection
        Connection connection;

        {
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Loading the data...");
        try {
            // loadCSVDataProceedings(connection);
            // loadCSVDataInproceedings(connection);
            // loadCSVDataEditor(connection);
            loadCSVDataConference(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Data has been loaded successfully!");
    }

}
