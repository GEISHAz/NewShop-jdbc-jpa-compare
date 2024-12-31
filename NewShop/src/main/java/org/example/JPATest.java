package org.example;

import jakarta.persistence.*;
import org.entity.Clothes;

import java.util.List;

public class JPATest {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("NewShop");

    public static void main(String[] args) {
        createClothes("무스탕", "에르메스", 1000000);
        readClothes();
        updateClothes(1, 970000);
        readClothes();
        deleteClothes(1);
        readClothes();
        emf.close();
    }

    private static void createClothes(String title, String brand, int prices) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Clothes clothes = new Clothes();
            clothes.setTitle(title);
            clothes.setBrand(brand);
            clothes.setPrices(prices);
            em.persist(clothes);
            tx.commit();
            System.out.println("Clothes 저장 성공");
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void readClothes() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Clothes> clothesList = em.createQuery("SELECT c FROM Clothes c", Clothes.class).getResultList();
            for (Clothes clothes : clothesList) {
                System.out.printf("ID: %d, Title: %s, Brand: %s, Prices: %d\n",
                        clothes.getId(), clothes.getTitle(), clothes.getBrand(), clothes.getPrices());
            }
        } finally {
            em.close();
        }
    }

    private static void updateClothes(int id, int newPrice) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Clothes clothes = em.find(Clothes.class, id);
            if (clothes != null) {
                clothes.setPrices(newPrice);
            }
            tx.commit();
            System.out.println("Clothes 수정 성공");
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void deleteClothes(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Clothes clothes = em.find(Clothes.class, id);
            if (clothes != null) {
                em.remove(clothes);
            }
            tx.commit();
            System.out.println("Clothes 삭제 성공");
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
