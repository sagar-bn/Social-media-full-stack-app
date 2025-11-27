# Social Media Full Stack App

A full-stack social media platform where users can create accounts, post content, like, comment, and manage their profile.  
Backend is built with **Java Spring Boot**, and the frontend uses **HTML, CSS, JavaScript** with REST API integration.

---

## 🚀 Features
- User signup & login (JWT authentication)
- Create, edit, delete posts
- Like & comment feature
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

css
Copy code
src/main/resources/application.properties
Run Frontend
Just open:

bash
Copy code
Frontend/ index.html
Method	Endpoint	    Description
POST	/auth/register	Register user
POST	/auth/login	    Login & get JWT
GET	/posts	          Get all posts
POST	/posts	        Create post
PUT	/posts/{id}	      Edit post
DELETE	/posts/{id}	  Delete post
POST	/posts/{id}/like Like post
POST	/posts/{id}/comment	Add comment

 

🙌 Contributions
Contributions are welcome.
Feel free to fork and submit pull requests.

📄 License
Distributed under the MIT License.



```
<h2>📸 Screenshots</h2>

<img src="screenshot\Screenshot 2025-11-27 075748.png" width="300"/>
<img src="screenshot\Screenshot 2025-11-27 075732.png" width="300"/>
<img src="screenshot\Screenshot 2025-11-27 075702.png" width="300"/>
<img src="screenshot\Screenshot 2025-11-27 075627.png" width="300"/>
<img src="screenshot\Screenshot 2025-11-27 075607.png" width="300"/>
<img src="screenshot\Screenshot 2025-11-27 075540.png" width="300"/>
<img src="screenshot\Screenshot 2025-11-27 075512.png" width="300"/>


## 👨‍💻 Author

**Sagar B N**  
Full-Stack Developer | Java Spring Boot & JavaScript Frontend  

LinkedIn: [https://www.linkedin.com/in/sagar--bn](https://www.linkedin.com/in/sagar--bn)  
GitHub: [https://github.com/sagar-bn](https://github.com/sagar-bn)  
Email: bnsagar27@gmail.com