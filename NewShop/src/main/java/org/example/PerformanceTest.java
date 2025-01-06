package org.example;



import jakarta.persistence.*;
import org.entity.Clothes;

import java.sql.*;
import java.util.List;

public class PerformanceTest {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/NewShop";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "ssafy";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("NewShop");

    public static void main(String[] args) {
        System.out.println("=== JDBC vs JPA Performance Test ===");

        // CREATE 테스트
        System.out.println("\n[CREATE]==================");
//        testJDBCCreate();
        testJPACreate();
        System.out.println("==========================");

        // READ 테스트
        System.out.println("\n[READ]==================");
//        testJDBCRead();
        testJPARead();
        System.out.println("==========================");

        // UPDATE 테스트
        System.out.println("\n[UPDATE]==================");
//        testJDBCUpdate();
        testJPAUpdate();
        System.out.println("==========================");

        // DELETE 테스트
        System.out.println("\n[DELETE]==================");
//        testJDBCDelete();
        testJPADelete();
        System.out.println("==========================");

        emf.close();
    }

    private static void testJDBCCreate() {
        long startTime = System.currentTimeMillis();
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO clothes (title, brand, prices) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            for (int i = 0; i < 10000; i++) {
                stmt.setString(1, "Title " + i);
                stmt.setString(2, "Brand " + i);
                stmt.setInt(3, i * 100);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("JDBC CREATE Time: " + (endTime - startTime) + " ms");
    }

    private static void testJPACreate() {
        long startTime = System.currentTimeMillis();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (int i = 0; i < 10000; i++) {
                Clothes clothes = new Clothes();
                clothes.setTitle("Title " + i);
                clothes.setBrand("Brand " + i);
                clothes.setPrices(i * 100);
                em.persist(clothes);
            }
            tx.commit();
        } finally {
            em.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("JPA CREATE Time: " + (endTime - startTime) + " ms");
    }

    private static void testJDBCRead() {
        long startTime = System.currentTimeMillis();
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM clothes";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String brand = rs.getString("brand");
                int prices = rs.getInt("prices");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("JDBC READ Time: " + (endTime - startTime) + " ms");
    }

    private static void testJPARead() {
        long startTime = System.currentTimeMillis();
        EntityManager em = emf.createEntityManager();
        try {
            List<Clothes> clothesList = em.createQuery("SELECT c FROM Clothes c", Clothes.class).getResultList();
        } finally {
            em.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("JPA READ Time: " + (endTime - startTime) + " ms");
    }

    private static void testJDBCUpdate() {
        long startTime = System.currentTimeMillis();
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE clothes SET prices = prices + 100 WHERE prices < 1000";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("JDBC UPDATE Time: " + (endTime - startTime) + " ms");
    }

    private static void testJPAUpdate() {
        long startTime = System.currentTimeMillis();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            List<Clothes> clothesList = em.createQuery("SELECT c FROM Clothes c WHERE c.prices < 1000", Clothes.class).getResultList();
            for (Clothes clothes : clothesList) {
                clothes.setPrices(clothes.getPrices() + 100);
            }
            tx.commit();
        } finally {
            em.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("JPA UPDATE Time: " + (endTime - startTime) + " ms");
    }

    private static void testJDBCDelete() {
        long startTime = System.currentTimeMillis();
        try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "DELETE FROM clothes WHERE prices < 1000";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("JDBC DELETE Time: " + (endTime - startTime) + " ms");
    }

    private static void testJPADelete() {
        long startTime = System.currentTimeMillis();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            List<Clothes> clothesList = em.createQuery("SELECT c FROM Clothes c WHERE c.prices < 1000", Clothes.class).getResultList();
            for (Clothes clothes : clothesList) {
                em.remove(clothes);
            }
            tx.commit();
        } finally {
            em.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("JPA DELETE Time: " + (endTime - startTime) + " ms");
    }
}

