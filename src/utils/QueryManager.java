/*
This Class is designed to handle the following:
    1. Creation of a Static Statement Object
    2. Detecting query types
    3. Returning a ResultSet Object
 */
package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * QueryManeger class is used to create and return statement object.
 * @author Omar Ahmad
 */
public class QueryManager {

    private static Statement statement; //statement reference

    /**
     * It Sets object of statement.
     * @param connection connection object.
     * @throws SQLException
     */
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    /**
     * It returns statement object.
     * @return statement
     */
    public static Statement getStatement() {
        return statement;
    }
}
