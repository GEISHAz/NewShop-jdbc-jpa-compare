package org.example;

import java.sql.*;

public class App 
{
    private static void jdbcTest(){
        try{
            // DB 연결
            Connection con = connect();
            System.out.println("JDBC DB 연결 성공.");
            jdbcInsert(); // 데이터 입력
            jdbcSelect(); // 데이터 조회
            jdbcUpdate(); // 데이터 수정
            jdbcSelect(); // 데이터 조회
            jdbcInsert(); // 데이터 입력
            jdbcDelete(); // 데이터 삭제
            jdbcSelect(); // 데이터 조회
            // 연결 종료
            con.close();
            System.out.println("JDBC DB 연결 종료!");
        }catch(SQLException e){
            System.out.println("JDBC 데이터 베이스 연결 오류!");
            e.printStackTrace();
        }

    }

    private static Connection connect() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/NewShop";
        String user = "root";
        String password = "ssafy";
        return DriverManager.getConnection(url,user,password);
    }

    private static void jdbcUpdate() {
        try (Connection con = connect()) {
            System.out.println("JDBC DB 연결 성공.");

            // SQL 실행
            String sql = "UPDATE clothes SET prices = ? WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, 970000);
            statement.setInt(2, 1);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("데이터 수정 성공");
            }
        } catch (SQLException e) {
            System.out.println("데이터 수정 오류!");
            e.printStackTrace();
        }
    }

    private static void jdbcDelete() {
        try (Connection con = connect()) {
            System.out.println("JDBC DB 연결 성공.");

            // SQL 실행
            String sql = "DELETE FROM clothes WHERE prices = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, 1000000);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("데이터 삭제 성공");
            }
        } catch (SQLException e) {
            System.out.println("데이터 삭제 오류!");
            e.printStackTrace();
        }
    }

    private static void jdbcSelect() {
        try (Connection con = connect()) {
            System.out.println("JDBC DB 연결 성공.");

            // SQL 실행
            String sql = "SELECT * FROM clothes";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // 결과 출력
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String brand = resultSet.getString("brand");
                int prices = resultSet.getInt("prices");
                System.out.printf("ID: %d, Title: %s, Brand: %s, Prices: %d\n", id, title, brand, prices);
            }
        } catch (SQLException e) {
            System.out.println("데이터 조회 오류!");
            e.printStackTrace();
        }
    }

    private static void jdbcInsert() {
        try (Connection con = connect()){
            // SQL 실행
            String sql = "INSERT INTO clothes (title,brand,prices) VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, "무스탕");
            statement.setString(2, "에르메스");
            statement.setInt(3, 1000000);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("데이터삽입성공");
            }
        }catch (SQLException e){
            System.out.println("JDBC 데이터 베이스 Insert 과정 오류");
        }
    }

    public static void main( String[] args )
    {
        //JDBC test
        jdbcTest();
        System.out.println();

    }
}
