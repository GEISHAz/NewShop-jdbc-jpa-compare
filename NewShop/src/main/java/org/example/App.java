package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App 
{
    static void jdbcTest(){
        //
        String url = "jdbc:mysql://localhost:3306/NewShop";
        String user = "root";
        String password = "ssafy";
        try{
            // DB 연결
            Connection con = DriverManager.getConnection(url,user,password);
            System.out.println("JDBC DB 연결 성공.");

            // SQL 실행
            String sql = "INSERT INTO clothes (title,brand,prices) VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,"무스탕");
            statement.setString(2,"에르메스");
            statement.setInt(3,10000);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0){
                System.out.println("데이터삽입성공");
            }
            // 연결 종료
            con.close();
        }catch(SQLException e){
            System.out.println("JDBC 데이터 베이스 연결 오류!");
            e.printStackTrace();
        }

    }
    public static void main( String[] args )
    {
        //JDBC test

        jdbcTest();
        System.out.println();


    }
}
