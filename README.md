# 📚 BookStore App

## 📖 Description
**BookStore App** is a web platform developed with Java and Spring Boot.  
In this application, users can browse books, leave comments and ratings, while administrators can manage books and users.

### Main Features:
- User registration and login
- User roles: `USER` and `ADMIN`
- Admin panel for managing books and users (create, update, delete)
- Commenting on books
- Book rating (1 to 5 stars)
- Searching books by title and author
- Filtering books by genre, publication date, and average rating
- Access restrictions based on user roles
- Security implemented via Spring Security (authentication, authorization)

---

## ⚙️ Main Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Thymeleaf
- Hibernate ORM
- Lombok
- PostgreSQL Driver (or MySQL depending on your database)
- Spring Boot DevTools (for hot reloading)

> 📦 All dependencies are listed in the `pom.xml` file.

---

## 🚀 Installation and Running

### 1. Clone the repository
  ```bash
  git clone https://github.com/ayanasgba/Book-Hub-App.git
  ```
## 🚀 Installation and Running

### 2. Navigate to the project directory

    ```bash
    cd Book-Hub-App
    ```
    
### 3. Set up the database
- Create a new database in PostgreSQL (or MySQL)  
- Configure your database connection settings in `application.properties` or `application.yml`

### 4. Run the project
- **Through your IDE** (e.g., IntelliJ IDEA: Run → `BookstoreApplication`)  
- **Or via terminal:**

    ```bash
    mvn spring-boot:run
    ```

---

## 🧩 Usage

- Open your browser and go to: `http://localhost:8080`
- Register or log in

### User functionalities
- Browse available books  
- Leave comments on books  
- Rate books  

### Admin functionalities
- Create, update, and delete books  
- Manage users  

### Search and filter
- Use the search and filter forms on the homepage for easy book browsing  

---

🎉 **Thank you!**  
This project was created to practice Java and Spring Boot skills and to gain a better understanding of web development.  
