package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * It is used to set up database connection.
 * @author Omar Ahmad
 */
public class Database {
    // It is url parts for idbc.
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql://";
    private static final String ipAddress =  "localhost/";
    private static final String dbName = "client_schedule";
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName + "?connectionTimeZone = SERVER";
    // the driver for interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection con = null;
    /**
     * It is for Database user name
     */
    private static final String userName = "sqlUser";
    /**
     * It is for Database password
     */
    private static final String password = "Passw0rd!";

    /**
     * It is used to Start the database connection.
     * @return the variable of connection.
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            con = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("connection successful");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return  con;
    }
    /**
     * This is the getter for Connection.
     * @return connection.
     */
    public static Connection getConnection() {
        return con;
    }
    /**
     * It is used to close the connection.
     */
    public static void closeConnection() {
        try {
            con.close();
            System.out.println("closed successful");
        } catch (SQLException throwables) {
            System.out.println( throwables.getMessage());
        }
    }
}
