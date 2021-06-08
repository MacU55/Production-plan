package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection connect() throws SQLException, ClassNotFoundException {
        int port = 3306;
        String hostname = "localhost";
        String database = "production";
        String username = "root";
        String password = "root";
        String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database +
                "?serverTimezone=UTC&autoReconnect=true&useSSL=false";

        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);

        return DriverManager.getConnection(url, username, password);
    }
}