package org.example;

import java.sql.*;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {

    // 하드코딩 접속
    //private static final String URL  = "jdbc:postgresql://localhost:5432/postgres";
    //private static final String USER = "postgres";
    //private static final String PASS = "1234";

    //환경변수 접속
    private static final String URL  = System.getenv().getOrDefault("DB_URL","jdbc:postgresql://localhost:5432/postgres");
    private static final String USER = System.getenv().getOrDefault("DB_USER","postgres");
    private static final String PASS = System.getenv().getOrDefault("DB_PASS","1234");

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT id, name, joined FROM table_test ORDER BY id");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long   id     = rs.getLong("id");
                String name   = rs.getString("name");
                Timestamp ts  = rs.getTimestamp("joined");

                System.out.printf("%d | %s | %s%n", id, name, ts);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}