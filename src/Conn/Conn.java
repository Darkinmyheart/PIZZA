package Conn;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lethi
 */

public class Conn {

    String databaseName = "PizzaDAM2";
    String url = "jdbc:sqlserver://localhost:1433;databaseName=" + databaseName; 
    String pass = "1234";
    String user = "sa";

    public Connection getConnection() throws SQLException {        
        return DriverManager.getConnection(url, user, pass);
    }
}
