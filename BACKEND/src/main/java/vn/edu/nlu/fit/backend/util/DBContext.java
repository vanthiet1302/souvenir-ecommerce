package vn.edu.nlu.fit.backend.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    private static final String SERVER_NAME = "localhost";
    private static final String DB_NAME = "inola_db";
    private static final String PORT_NUMBER = "3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static final String URL =
            "jdbc:mysql://" + SERVER_NAME + ":" + PORT_NUMBER + "/" + DB_NAME +
                    "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy MySQL Driver", e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            System.out.println(new DBContext().getConnection());
            System.out.println("Kết nối CSDL thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
    }
}