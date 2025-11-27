# Social Media Full Stack App | VoicesOfUs

A full-stack social media platform where users can create accounts, post content, like, comment, and manage their profile.  
Backend is built with **Java Spring Boot**, and the frontend uses **HTML, CSS, JavaScript** with REST API integration.

---

## 🚀 Features
- User signup & login (JWT authentication)
- Create, edit, delete posts
- Like & comment feature
- fallow & unfallow
- Profile management
- Secure password encryption
- REST API-based architecture

---

## 🛠 Tech Stack

### Backend
- Java 
- Spring Boot
- Spring Security + JWT
- Hibernate / JPA
- MySQL

### Frontend
- HTML
- CSS
- JavaScript
- Fetch API

---

## 📁 Project Structure
/Frontend
- home.html
- profile.html
- login.html
- register.html
- post.js
- home.js
- auth.js

/Backend
src/main/java/...
src/main/resources/application.properties 

---

## ▶️ Running the Project

### Run Backend
```bash
cd Backend
mvn clean install
mvn spring-boot:run
Update DB config in:
spring.datasource.url=jdbc:mysql://localhost:3306/your database name
spring.datasource.username=root
spring.datasource.password=Your password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver





css
Copy code
src/main/resources/application.properties
Run Frontend
Just open: login.html

| Method | Endpoint              | Description                              |
| ------ | --------------------- | ---------------------------------------- |
| POST   | `/auth/register`      | Register a new user                      |
| POST   | `/auth/login`         | Login and get JWT token                  |
| GET    | `/users/{id}`         | Get user profile by ID                   |
| PUT    | `/users/{id}`         | Update user profile                      |
| GET    | `/users`              | Get all users                            |
| GET    | `/posts`              | Get all posts                            |
| POST   | `/posts`              | Create a new post                        |
| GET    | `/posts/{id}`         | Get a single post                        |
| PUT    | `/posts/{id}`         | Update a post                            |
| DELETE | `/posts/{id}`         | Delete a post                            |
| POST   | `/posts/{id}/like`    | Like a post                              |
| POST   | `/posts/{id}/comment` | Add comment to a post                    |
| GET    | `/posts/user/{id}`    | Get all posts by a specific user         |
| GET    | `/feed`               | Get posts from followed users            |
| POST   | `/follow/{id}`        | Follow a user                            |
| DELETE | `/unfollow/{id}`      | Unfollow a user                          |
| GET    | `/followers/{id}`     | Get list of followers for a user         |
| GET    | `/following/{id}`     | Get list of users a user is following    |
| GET    | `/notifications`      | Get all notifications for logged-in user |
| PUT    | `/profile/avatar`     | Upload or update profile picture         |

 

🙌 Contributions
Contributions are welcome.
Feel free to fork and submit pull requests.

📄 License
Distributed under the MIT License.



```
---
<h2>📸 Screenshots</h2>

<img src="screenshot\Screenshot 2025-11-27 075748.png" />
<img src="screenshot\Screenshot 2025-11-27 075732.png"/>
<img src="screenshot\Screenshot 2025-11-27 075702.png" />
<img src="screenshot\Screenshot 2025-11-27 075627.png" />
<img src="screenshot\Screenshot 2025-11-27 075607.png"/>
<img src="screenshot\Screenshot 2025-11-27 075540.png" />
<img src="screenshot\Screenshot 2025-11-27 075512.png" />


## 👨‍💻 Author

**Sagar B N**  
Full-Stack Developer | Java Spring Boot & JavaScript Frontend  

LinkedIn: [https://www.linkedin.com/in/sagar--bn](https://www.linkedin.com/in/sagar--bn)  
GitHub: [https://github.com/sagar-bn](https://github.com/sagar-bn)  
Email: bnsagar27@gmail.com