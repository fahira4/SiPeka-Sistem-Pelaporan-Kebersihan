package test.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnector {

    private static final String DB_URL = "jdbc:sqlite:src/main/java/test/database/SiPeka.db";
    private static boolean initialized = false;

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to SQLite has been established.");

            if (!initialized) {
                try (Statement stmt = conn.createStatement()) {
                    String sqlUser = """
                                CREATE TABLE IF NOT EXISTS user (
                                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                                    username TEXT NOT NULL,
                                    password TEXT NOT NULL
                                    RT TEXT NOT NULL,
                                );
                            """;
                    stmt.execute(sqlUser);
                    System.out.println("Tabel 'user' telah dipastikan ada.");
                    initialized = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error saat koneksi atau inisialisasi DB: " + e.getMessage());
        }
        return conn;
    }
}
