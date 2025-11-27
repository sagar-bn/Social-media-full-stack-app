  //  window.onload = () => {
  //   document.getElementById("username").value = localStorage.getItem("username") || "";
  //   document.getElementById("firstName").value = localStorage.getItem("firstName") || "";
  //   document.getElementById("lastName").value = localStorage.getItem("lastName") || "";
  //   document.getElementById("avatarUrl").value = localStorage.getItem("avatarUrl") || "";
  //   document.getElementById("bio").value = localStorage.getItem("bio") || "";
  // };

  // // update profile and push to backend
  // document.getElementById("profileForm").addEventListener("submit", async (e) => {
  //   e.preventDefault();

  //   const updatedUser = {
  //     username: document.getElementById("username").value,
  //     firstName: document.getElementById("firstName").value,
  //     lastName: document.getElementById("lastName").value,
  //     avatarUrl: document.getElementById("avatarUrl").value,
  //     bio: document.getElementById("bio").value
  //   };

  //   const token = localStorage.getItem("token");
  //   const res = await fetch("http://localhost:8081/api/users/update", {
  //     method: "PUT",
  //     headers: {
  //       "Content-Type": "application/json",
  //       "Authorization": `Bearer ${token}`
  //     },
  //     body: JSON.stringify(updatedUser)
  //   });

  //   if (res.ok) {
  //     alert("Profile updated successfully!");
  //     // update local storage
  //     Object.entries(updatedUser).forEach(([key, value]) => localStorage.setItem(key, value));
  //   } else {
  //     alert("Failed to update profile");
  //   }
  // });

  window.onload = () => {
  const avatarPreview = document.getElementById("avatarPreview");
  const avatarUrlInput = document.getElementById("avatarUrl");

  document.getElementById("username").value = localStorage.getItem("username") || "";
  document.getElementById("firstName").value = localStorage.getItem("firstName") || "";
  document.getElementById("lastName").value = localStorage.getItem("lastName") || "";
  document.getElementById("avatarUrl").value = localStorage.getItem("avatarUrl") || "";
  document.getElementById("bio").value = localStorage.getItem("bio") || "";

  avatarPreview.src = localStorage.getItem("avatarUrl") || "https://cdn-icons-png.flaticon.com/512/847/847969.png";

  // Avatar preview change on typing URL
  avatarUrlInput.addEventListener("input", () => {
    avatarPreview.src = avatarUrlInput.value || "https://cdn-icons-png.flaticon.com/512/847/847969.png";
  });

  // Predefined avatars
  const avatarOptions = document.querySelectorAll(".avatar-option");
  avatarOptions.forEach(option => {
    option.addEventListener("click", () => {
      avatarOptions.forEach(opt => opt.classList.remove("selected"));
      option.classList.add("selected");
      const selectedAvatar = option.getAttribute("data-avatar");
      avatarPreview.src = selectedAvatar;
      avatarUrlInput.value = selectedAvatar;
    });
  });
};

// Update profile and push to backend
document.getElementById("profileForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const updatedUser = {
    username: document.getElementById("username").value,
    firstName: document.getElementById("firstName").value,
    lastName: document.getElementById("lastName").value,
    avatarUrl: document.getElementById("avatarUrl").value,
    bio: document.getElementById("bio").value
  };

  const token = localStorage.getItem("token");
  const res = await fetch("http://localhost:8081/api/users/update", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify(updatedUser)
  });

  if (res.ok) {
    alert("Profile updated successfully!");
    Object.entries(updatedUser).forEach(([key, value]) => localStorage.setItem(key, value));
  } else {
    alert("Failed to update profile");
  }
});
