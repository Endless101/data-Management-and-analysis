import java.sql.*;
import java.io.*;

public class DatabaseMDB {

    static final String USER = "peanut222link";
    static final String PASS = "password";

    public static void createTables(Connection connection) {
        Statement statement = null;
        try {
            System.out.println("Creating table in given database...");
            statement = connection.createStatement();

            String sql_article = "CREATE TABLE articles "
                    + "(article_key VARCHAR(255) not NULL, "
                    + " authors VARCHAR(255), "
                    + " title VARCHAR(255), "
                    + " journal VARCHAR(255), "
                    + " year VARCHAR(255), "
                    + " pages VARCHAR(255), "
                    + " volume VARCHAR(2040), "
                    + " number VARCHAR(255)) ";
            String sql_proceeding = "CREATE TABLE proceedings "
                    + "(proceeding_key VARCHAR(255) not NULL, "
                    + " editor VARCHAR(255), "
                    + " title VARCHAR(255), "
                    + " booktitle VARCHAR(255), "
                    + " year VARCHAR(255), "
                    + " volume VARCHAR(2040))";
            String sql_inproceeding = "CREATE TABLE inproceedings "
                    + "(inproceeding_key VARCHAR(255) not NULL, "
                    + " title VARCHAR(255), "
                    + " booktitle VARCHAR(255), "
                    + " year VARCHAR(255), "
                    + " pages VARCHAR(255)) ";
            String sql_author = "CREATE TABLE authors "
                    + "(article_key VARCHAR(255) not NULL, "
                    + " inproceeding_key VARCHAR(255) not NULL,"
                    + " name VARCHAR(255) )";
            String sql_editor = "CREATE TABLE editors "
                    + "(proceeding_key VARCHAR(255) not NULL,"
                    + " name VARCHAR(255) )";
            String sql_journal = "CREATE TABLE journals "
                    + "(article_key VARCHAR(255) not NULL, "
                    + " title VARCHAR(255))";
            String sql_conference = "CREATE TABLE conferences "
                    + "(proceeding_key VARCHAR(255) not NULL, "
                    + " title VARCHAR(255))";

            statement.executeUpdate(sql_article);
            statement.executeUpdate(sql_proceeding);
            statement.executeUpdate(sql_inproceeding);
            statement.executeUpdate(sql_author);
            statement.executeUpdate(sql_editor);
            statement.executeUpdate(sql_journal);
            statement.executeUpdate(sql_conference);

            System.out.println("Created tables in given database...");
        } catch (SQLException e) {
            //Handle errors for JDBC
            e.printStackTrace();
        }
    }

    public static void readFromCSV(Connection connection, String csv,  String sql, int amount) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            BufferedReader lineReader = new BufferedReader(new FileReader(csv));
            int batchSize = 20;
            String lineText;
            int counter = 0;
            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                for( int i = 1; i <= amount; i++) {
                    statement.setString(i, data[i-1]);
                }
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

    public static void loadArticle(Connection connection) {
        readFromCSV(connection, "article.csv","INSERT INTO articles (article_key, authors, title, journal, year, pages, volume, number ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", 8);
    }
    public static void loadProceeding(Connection connection) {
        readFromCSV(connection, "proceedings.csv","INSERT INTO proceedings (proceeding_key, editor, title, booktitle, year, volume) VALUES (?, ?, ?, ?, ?, ?)", 6);
    }
    public static void loadInproceeding(Connection connection) {
        readFromCSV(connection, "inproceeding.csv","INSERT INTO inproceedings (inproceeding_key, title, booktitle, year, pages) VALUES (?, ?, ?, ?, ?)", 5);
    }
    public static void loadAuthor(Connection connection) {
        readFromCSV(connection, "author.csv","INSERT INTO authors (article_key, inproceeding_key, name) VALUES (?, ?, ?)", 3);
    }
    public static void loadEditor(Connection connection) {
        readFromCSV(connection, "editor.csv", "INSERT INTO editors (proceeding_key, name) VALUES (?, ?)", 2);
    }
    public static void loadJournal(Connection connection) {
        readFromCSV(connection, "journal.csv", "INSERT INTO journals (article_key, title) VALUES (?, ?)", 2);
    }
    public static void loadConference(Connection connection) {
        readFromCSV(connection, "conference.csv","INSERT INTO conferences (proceeding_key, title) VALUES (?, ?)", 2);
    }

    public static void loadData(Connection connection) {
        System.out.println("Started loading Data");
        loadArticle(connection);
        System.out.println("Loaded articles");
        loadProceeding(connection);
        System.out.println("Loaded proceedings");
        loadInproceeding(connection);
        System.out.println("Loaded inproceedings");
        loadAuthor(connection);
        System.out.println("Loaded authors");
        loadEditor(connection);
        System.out.println("Loaded editors");
        loadJournal(connection);
        System.out.println("Loaded journals");
        loadConference(connection);
        System.out.println("Loaded conferences");
    }

    // https://stackoverflow.com/questions/37909487/how-can-i-connect-to-mariadb-using-java

    public static void main() throws FileNotFoundException {
        Connection connection = null;
        try {
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/databaseDIM", USER, PASS);
            System.out.println("Connected database successfully...");
            createTables(connection);
            loadData(connection);
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
