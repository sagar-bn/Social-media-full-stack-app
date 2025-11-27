
function formatDate(iso) {
  try { return new Date(iso).toLocaleString(); } catch (e) { return iso; }
}

function getPostId() {
  const params = new URLSearchParams(window.location.search);
  return params.get("id");
}

function getToken(){
  return localStorage.getItem("token");
}
const API_BASE = "http://localhost:8081/api";
async function loadPost() {
  const id = getPostId();
  if (!id) {
    document.getElementById("post").innerHTML = "<p>No post ID provided.</p>";
    return;
  }
  


  try {
    const post = await apiRequest(`/posts/${id}`);
    document.getElementById("post").innerHTML = `
      <h2>${post.title}</h2>
      <div class="post-meta">by ${post.author || "Unknown"} • ${formatDate(post.createdAt || post.createdDate)}</div>
      <div class="post-content">${post.content}</div>
    `;
    loadComments(id);
  } catch (err) {
    document.getElementById("post").innerHTML = `<p class="error">Failed to load post: ${err.message || err}</p>`;
  }
}

async function apiRequest(endpoint, method = "GET", body = null){
  const headers = {"Content-Type":"application/json"};
  const token = getToken();
  if(token) headers["Authorization"] = `Bearer ${token}`;
  const res = await fetch(API_BASE + endpoint, {
    method,
    headers,
    body: body ? JSON.stringify(body) : undefined
  });
  const ct = res.headers.get("content-type") || "";
  if(ct.includes("application/json")){
    const json = await res.json();
    if(!res.ok) throw json;
    return json;
  } else {
    const text = await res.text();
    if(!res.ok) throw text || res.statusText;
    return text;
  }
}
async function loadComments(postId) {
  const commentList = document.getElementById("commentList");
  try {
    const comments = await apiRequest(`/posts/${postId}/comments`);
    console.log("Comments received:", comments); // Debug line
    
    if (!comments.length) commentList.innerHTML = "<p>No comments yet.</p>";
    else {
      commentList.innerHTML = comments.map(c => {
        console.log("Individual comment:", c); // Debug each comment
        return `
          <div class="comment">
            <strong>${c.commenter || c.username || c.author || "Unknown"}</strong> • ${formatDate(c.createdAt || c.createdDate || c.timestamp)}<br>
            ${c.content || c.text || "No content"}
          </div>
        `;
      }).join("");
    }
  } catch (err) {
    commentList.innerHTML = `<p class="error">Failed to load comments: ${err.message || err}</p>`;
  }
}

document.getElementById("commentForm")?.addEventListener("submit", async e => {
  e.preventDefault();
  const token = getToken();
  if (!token) {
    alert("You must be logged in to comment!");
    window.location.href = "login.html";
    return;
  }

  const postId = getPostId();
  const content = document.getElementById("commentContent").value;

  try {
    await apiRequest(`/posts/${postId}/comments`, "POST", { content });
    document.getElementById("commentContent").value = "";
    loadComments(postId);
  } catch (err) {
    document.getElementById("message").textContent = "Failed to post comment: " + (err.message || err);
  }
});


