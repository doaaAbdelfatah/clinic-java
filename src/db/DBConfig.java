package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {

    public static Connection  createConnection() throws SQLException {
        String dbURL ="jdbc:mysql://127.0.0.1:3306/clinic_db";
        return DriverManager.getConnection(dbURL , "root" ,"");
    }

}
