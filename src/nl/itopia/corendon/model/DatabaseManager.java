package nl.itopia.corendon.model;
import nl.itopia.corendon.Config;
import nl.itopia.corendon.utils.Log;

import java.sql.*;
/**
 *
 * @author Jeroentje
 */
public class DatabaseManager {
    
    private static final DatabaseManager manager = new DatabaseManager();

    public static final String JDBC_EXCEPTION = "JDBC Exception: ";
    public static final String SQL_EXCEPTION = "SQL Exception: ";
    public Connection connection;

    private DatabaseManager() {}

    public static DatabaseManager getDefault() {
        return manager;
    }

    /**
     * Open database connection
     */
    public void openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Log.display("Establishing connection", Config.DB_HOST, Config.DB_NAME, Config.DB_USER, Config.DB_PASS);

            /** Open connection */
            String url = "jdbc:mysql://"+Config.DB_HOST+"/"+Config.DB_NAME;
            connection = DriverManager.getConnection(url, Config.DB_USER, Config.DB_PASS);
            Log.display(connection);
        } catch (ClassNotFoundException e) {
            Log.display(JDBC_EXCEPTION, e.getMessage());
        } catch (SQLException e) {
            Log.display(SQL_EXCEPTION, e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }

    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            Log.display(SQL_EXCEPTION, e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }

    /**
     * Executes a query without result.
     * @param query, the SQl query
     */
    public void executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery(query);
    }

    /**
     * Executes a query with result.
     * @param query, the SQL query
     */
    public ResultSet doQuery(String query) throws SQLException {
        ResultSet result = null;
        Statement statement = connection.createStatement();
        result = statement.executeQuery(query);

        return result;
    }
    
    /**
     * Executes a query with result. TODO: Correct javadocs
     * @param query, the SQL query
     */
    public boolean updateQuery(String query) throws SQLException {
        boolean result;
        Statement statement = connection.createStatement();
        result = statement.execute(query);

        return result;
    }

    /**
     * Executes a query with result.
     * @param query, the SQL query
     */
    public ResultSet insertQuery(String query) throws SQLException {
        
        ResultSet result = null;

        Statement statement = connection.createStatement();
        statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        result = statement.getGeneratedKeys();

        return result;
    }    
}
