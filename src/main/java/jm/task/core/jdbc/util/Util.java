package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String PASSWORD = "Qazpwsl123";
    private static final String USERNAME = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/new";

    private Util() {}

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                URL, USERNAME, PASSWORD
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
