package org.exampe.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

public static Connection connect()  throws SQLException, ClassNotFoundException {
        int port = 3306;
        String hostname = "localhost";
        String database = "production";//используй свою базу
        String username = "root"; //используй свой юзернейм
        String password = "root"; //используй свой пароль

        String url = "jdbc:mysql://"+ hostname + port + "/" + database + "?serverTimezone=UTC"; // собери из переменніх віше
        String driver = "com.mysql.cj.jdbc.Driver";

        Class.forName(driver);

        return DriverManager.getConnection(url, username, password);
    }
}
