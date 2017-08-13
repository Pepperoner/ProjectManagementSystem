package dao.jdbcDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDbDAO {

    public static Connection getConnection() throws SQLException {
        final String URL = "jdbc:mysql://localhost:3306/developers_db?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        final String USER = "root";
        final String PASSWORD = "1111";

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
