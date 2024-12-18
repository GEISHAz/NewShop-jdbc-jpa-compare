# NewShop-jdbc-jpa-compare : 단계별 옷 쇼핑몰 시스템 개발 👀

**NewShop**은 단계적으로 발전하는 **옷 관리 쇼핑몰 시스템** 프로젝트입니다. JDBC와 JPA를 활용하여 CRUD 기능을 구현하고, 성능 비교를 통해 각 기술의 특징을 학습하는 것을 목표로 합니다.

---

## 📋 **프로젝트 개요**  
NewShop 프로젝트는 다음의 단계별 목표를 가지고 진행됩니다:  

1. **Ver 1**: JDBC를 사용해 데이터베이스 연결 및 데이터 삽입  
2. **Ver 2**: JDBC를 사용해 CRUD (Create, Read, Update, Delete) 기능 구현  
3. **Ver 3**: JPA를 사용해 CRUD 기능 재구현  
4. **Ver 4**: JDBC와 JPA의 성능 차이 비교  

---

## 🚀 **기술 스택**  
- **Java 17**  
- **JDBC** (Ver 1, 2)  
- **JPA (Hibernate)** (Ver 3, 4)  
- **MySQL**  
- **Maven**  
- **IDE**: IntelliJ / Eclipse  

---

## 🛠️ **버전별 목표 및 구현**  

### **🔹 Ver 1: 데이터베이스 연결 및 데이터 삽입 (JDBC)**  
**목표**  
- JDBC를 사용해 MySQL과 연결하고, `clothes` 테이블에 데이터를 삽입합니다.  

**구현 내용**  
- **DB 연결**: `DriverManager`를 통해 MySQL 연결  
- **데이터 삽입**: `PreparedStatement`를 사용해 SQL `INSERT` 실행  

**결과**  
- 데이터베이스 연결 성공 및 데이터 삽입 확인  
- **테이블 예시**  
   | id  | title   | brand     | prices | created_at          |  
   |-----|---------|-----------|--------|---------------------|  
   | 1   | 무스탕    | 에르메스    | 1000000 | 2024-06-10 10:00:00 |

---

### **🔹 Ver 2: CRUD 기능 완성 (JDBC)**  
**목표**  
- JDBC를 사용해 `CREATE`, `READ`, `UPDATE`, `DELETE` 기능을 구현합니다.  

**구현 내용**  
1. **CREATE**:  
   - SQL `INSERT` 문을 사용하여 데이터 삽입  
   - 예시: `"무스탕", "에르메스", 1000000` 삽입 성공  
2. **READ**:  
   - SQL `SELECT` 문을 사용하여 모든 데이터를 조회  
   - 출력 결과:  
      ```text
      ID: 1, Title: 무스탕, Brand: 에르메스, Prices: 1000000
      ```
3. **UPDATE**:  
   - 특정 데이터를 수정  
   - 예: `id=1`의 `prices` 값을 `970000`으로 수정 성공  
4. **DELETE**:  
   - 특정 데이터를 삭제  
   - 예: `prices=1000000` 데이터를 삭제 성공  

**결과**  
- 모든 CRUD 작업이 성공적으로 수행되었으며, 데이터베이스에 반영됨.  
- **데이터 예시 (최종 상태)**  
   | id  | title   | brand     | prices | created_at          |  
   |-----|---------|-----------|--------|---------------------|  
   | 1   | 무스탕    | 에르메스    | 970000  | 2024-06-10 10:00:00 |

**코드 주요 작업**  
- `Connection` 객체를 통한 데이터베이스 연결 및 해제  
- `PreparedStatement`로 SQL 작업 수행  
- 각 CRUD 작업에 대해 try-with-resources로 자원 관리  

---

### **🔹 Ver 3: JPA를 사용한 CRUD 기능 구현**  
**목표**  
- JPA (Hibernate)를 사용하여 동일한 CRUD 기능을 객체지향적으로 구현합니다.  

**구현 예정 내용**  
- **Entity 클래스 작성**: `clothes` 테이블과 매핑된 `Clothes` Entity 생성  
- **CRUD 구현**: `persist`, `find`, `merge`, `remove` 메서드를 사용해 CRUD 기능 구현  

---

### **🔹 Ver 4: JDBC와 JPA 성능 비교**  
**목표**  
- 동일한 CRUD 작업을 실행하여 **JDBC와 JPA의 속도 차이**를 비교합니다.  

**구현 예정 내용**  
- **대량 데이터 삽입**: 10,000건의 데이터를 삽입  
- **시간 측정**: `System.currentTimeMillis()`를 사용하여 작업 시간 측정  

---

## 📂 **프로젝트 구조**

```plaintext
NewShop-jdbc-jpa-compare/
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── App.java     # 메인 클래스, JDBC CRUD 실행
├── pom.xml                   # Maven 설정 파일
└── README.md                 # 프로젝트 설명
