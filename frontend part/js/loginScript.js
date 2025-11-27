// document.addEventListener("DOMContentLoaded", () => {
//   document.querySelector(".js-login").addEventListener("submit", async (e) => {
//     e.preventDefault();
//   const password=document.getElementById("password").value.trim();
//   const email=document.getElementById("email").value.trim();


// const res =await fetch("http://localhost:8081/api/auth/login",{
//   method : "POST",
//   headers :{"Content-type":"application/json"},
//   body : JSON.stringify({email,password})

// })
// const data = await res.json()
//  if(res.ok){
//   alert("login successful");
//   console.log(data);
//  }
//  else{
//   alert(data.message || "login failed");
//  }
// }

// )
// }
// )

//   document.querySelector('.js-register').addEventListener("submit",async(e)=>{
//     e.preventDefault();
//     const username = document.getElementById("username").value.trim();
//     const password = document.getElementById("password").value.trim();
//     const email = document.getElementById("email").value.trime();

//     const res= await fetch("http://localhost:8081/api/auth/register",{
//        method :"POST",
//        headers : {"Content-type":"application/json"},
//        body: JSON.stringify({username,email,password})
//     }
//   )
//   const data = await res.json()
//   if(res.ok){
//     alert("created");
//     console.log(res.json);
//   }
//   else{
//     alert(data.message||"error");
//   }
//   })


document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.querySelector(".js-login");
  const registerForm = document.querySelector(".js-register");

  // LOGIN
  if (loginForm) {
    loginForm.addEventListener("submit", async (e) => {
      e.preventDefault();

      const email = document.getElementById("email").value.trim();
      const password = document.getElementById("password").value.trim();

      const res = await fetch("http://localhost:8081/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      const data = await res.json();

      if (res.ok) {
        const { token, username, email } = data;

        localStorage.setItem("token", token);
        localStorage.setItem("username", username);
        localStorage.setItem("email", email);

        const profileRes = await fetch("http://localhost:8081/api/users/profile", {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        });

        const profileData = await profileRes.json();
        window.profileData = profileData; 

        if (profileRes.ok) {
          localStorage.setItem("avatarUrl", profileData.avatarUrl);
          
           localStorage.setItem("username", profileData.username);
          localStorage.setItem("role", profileData.role);
          console.log(profileData.role);
          localStorage.setItem("firstName", profileData.firstName);
          localStorage.setItem("lastName", profileData.lastName);
          localStorage.setItem("bio", profileData.bio);


          alert("Login successful");
          window.location.href = "home.html";

        } else {
          alert(profileData.message || "Failed to fetch profile");
        }
      } else {
        alert(data.message || "Login failed");
      }
    });
  }

  // REGISTER
  if (registerForm) {
    registerForm.addEventListener("submit", async (e) => {
      e.preventDefault();

      const username = document.getElementById("username").value.trim();
      const email = document.getElementById("email").value.trim();
      const password = document.getElementById("password").value.trim();

      const res = await fetch("http://localhost:8081/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, email, password }),
      });

      const data = await res.json();

      if (res.ok) {
        alert("Account created successfully");
        console.log(data);
      } else {
        alert(data.message || "Registration failed");
      }
    });
  }
});

