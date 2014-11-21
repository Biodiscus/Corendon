/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.itopia.corendon.model;
import nl.itopia.corendon.data.database.Parameter;
import nl.itopia.corendon.utils.Log;

//import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author Jeroentje
 */
public class DatabaseManager {
    private static final DatabaseManager manager = new DatabaseManager();

    public static final String JDBC_EXCEPTION = "JDBC Exception: ";
    public static final String SQL_EXCEPTION = "SQL Exception: ";
    public Connection connection;
    
    private static final String dbHost = "sql4.freesqldatabase.com";
    private static final String dbName = "sql458254";
    private static final String dbUser = "sql458254";
    private static final String dbPass = "kR5!eE6!";

    private DatabaseManager() {}

    public static DatabaseManager getDefault() {
        return manager;
    }

    public void openConnection(String host, String database, String user, String pass) throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Log.display("Establishing connection", host, database, user, pass);

            /** Open connection */
            String url = "jdbc:mysql://"+host+"/"+database;
            connection = DriverManager.getConnection(url, user, pass);
            Log.display(connection);
        } catch (ClassNotFoundException e) {
            System.exit(0);
        }
    }

    /**
     * Open database connection
     */
//    @Deprecated
    public void openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Log.display("Establishing connection", dbHost, dbName, dbUser, dbPass);

            /** Open connection */
            String url = "jdbc:mysql://"+dbHost+"/"+dbName;
            connection = DriverManager.getConnection(url, dbUser, dbPass);
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
//    @Deprecated
    public void executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            Log.display(SQL_EXCEPTION, e.getErrorCode(), e.getSQLState(), e.getMessage());
        }
    }

    /**
     * Executes a query with result.
     * @param query, the SQL query
     */
//    @Deprecated
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
//    @Deprecated
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
//    @Deprecated
    public ResultSet insertQuery(String query) throws SQLException {
        ResultSet result = null;

        Statement statement = connection.createStatement();
        statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        result = statement.getGeneratedKeys();

        return result;
    }


    public ResultSet select(String tableName, Parameter ... parameters) throws SQLException {
        ResultSet result;

        String query = "SELECT * FROM "+tableName+" WHERE ";

        int length = parameters.length;
        for(int i = 0; i < length; i ++) {
            Parameter parameter = parameters[i];

            query += parameter.name + "=?";
            if(length > 1) {
                query += " AND ";
            }
        }

        query += ";";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        // After creating the query and after create the prepared statement, fill the object with the given parameters
        for(int i = 0; i < length; i ++) {
            Parameter parameter = parameters[i];
            statement.setObject((i+1), parameter.value);
        }

        result = statement.executeQuery();

        return result;
    }


}
