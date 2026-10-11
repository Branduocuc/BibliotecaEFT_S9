package cl.duoc.biblioteca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL =
            "jdbc:mysql://localhost:3306/biblioteca";

    private static final String USER =
            "root";

    private static final String PASSWORD = System.getenv("DB_PASSWORD");


    private DatabaseConnection() {

        try {

            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error de conexión: "
                            + e.getMessage()
            );
        }
    }


    public static DatabaseConnection getInstance() {

        if (instance == null) {

            instance = new DatabaseConnection();
        }

        return instance;
    }


    public  Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {

            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );
        }

        return connection;
    }
}