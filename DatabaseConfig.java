import javax.swing.*;
import java.sql.*;

public class DatabaseConfig {
    public static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    public static final String USER = "root";
    public static final String PASSWORD = "21510";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
