// const userName = localStorage.getItem("username") || "User";
// const avatarUrl = localStorage.getItem("avatarUrl") || "https://cdn-icons-png.flaticon.com/512/3135/3135715.png";

// document.getElementById("userName").textContent = userName;
// document.querySelector(".avatar").src = avatarUrl;

// document.getElementById("logoutBtn").addEventListener("click", () => {
//   localStorage.clear();
//   window.location.href = "login.html";
// });


const API_BASE_URL = 'http://localhost:8081/api';

// Check authentication on page load
function checkAuth() {
  const token = localStorage.getItem('token');
  if (!token) {
    alert('Please login to continue');
    window.location.href = 'login.html';
    return false;
  }
  return true;
}

// Load user data from localStorage
function loadUserData() {
  const username = localStorage.getItem('username') || 'User';

  const firstName = localStorage.getItem('firstName') || '';
  const lastName = localStorage.getItem('lastName') || '';
  const avatarUrl = localStorage.getItem('avatarUrl');

  const displayName = firstName && lastName
    ? `${firstName} ${lastName}`
    : username;

  document.getElementById('userName').textContent = username;

  if (avatarUrl && avatarUrl !== "undefined" && avatarUrl.trim() !== "") {
    console.log("Avatar URL:", avatarUrl);
    document.getElementById('userAvatar').src = avatarUrl;
  } else {
    document.getElementById('userAvatar').src = 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png';
  }
}

async function fetchPosts() {

  const postsContainer = document.getElementById('postsContainer');

  try {
    const response = await fetch(`${API_BASE_URL}/posts/public/`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      throw new Error('Failed to fetch posts');
    }

    const posts = await response.json();
    displayPosts(posts);
  } catch (error) {
    console.error('Error fetching posts:', error);
    postsContainer.innerHTML = '<div class="error">Failed to load posts. Please try again later.</div>';
  }
}
function setupLikeButtons() {
  document.querySelectorAll('.like-btn').forEach(btn => {
    const postId = btn.getAttribute('data-id');
    updateLikeCount(postId, btn);

    btn.addEventListener('click', async () => {
      const token = localStorage.getItem('token');
      try {
        const res = await fetch(`${API_BASE_URL}/likes/posts/${postId}`, {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });

        if (!res.ok) throw new Error('Failed to toggle like');

        await updateLikeCount(postId, btn);
      } catch (err) {
        console.error(err);
        alert('Something went wrong while liking the post.');
      }
    });

  });

  document.querySelectorAll('.see-likes-btn').forEach(btn => {
    btn.addEventListener('click', async () => {
      const postId = btn.getAttribute('data-id');
      await showAllLikes(postId);
    });
  });
}


function setupFollowButtons() {
  document.querySelectorAll('.follow-btn').forEach(btn => {
    btn.addEventListener('click', async () => {
      const token = localStorage.getItem('token');
      const authorUsername = btn.getAttribute('data-author');

      if (!token || !authorUsername) {
        alert('Login required!');
        return;
      }

      try {
        const isFollowing = btn.textContent === 'Unfollow';
        const endpoint = isFollowing ? '/unfollow' : '/follow';
        const method = isFollowing ? 'DELETE' : 'POST';

        const res = await fetch(`${API_BASE_URL}/follows${endpoint}`, {
          method,
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ author: authorUsername })
        });

        if (!res.ok) throw new Error('Failed to update follow status');

        btn.textContent = isFollowing ? 'Follow' : 'Unfollow';
      } catch (err) {
        console.error('Follow error:', err);
        alert('Could not update follow status');
      }
    });
  });
}


async function updateLikeCount(postId, btn) {
  try {
    const token = localStorage.getItem('token');
    const res = await fetch(`${API_BASE_URL}/likes/posts/${postId}/count`, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });
    if (!res.ok) throw new Error('Failed to fetch likes count');
    const count = await res.json();
    btn.textContent = `❤️ ${count}`;
  } catch (err) {
    console.error(err);
    btn.textContent = '❤️ 0';
  }
}

async function showAllLikes(postId) {
  try {
    const token = localStorage.getItem('token');
    const res = await fetch(`${API_BASE_URL}/likes/posts/${postId}/users`, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });
    if (!res.ok) throw new Error('Failed to fetch likes list');
    const users = await res.json();

    if (users.length === 0) {
      alert('No likes yet');
    } else {
      const names = users.map(u => u.username || u.email || 'Anonymous').join('\n');
      alert(`Liked by:\n${names}`);
    }
  } catch (err) {
    console.error(err);
    alert('Failed to load who liked this post.');
  }
}


// Display posts in the grid
function displayPosts(posts) {
  const postsContainer = document.getElementById('postsContainer');

  if (!posts || posts.length === 0) {
    postsContainer.innerHTML = `
      <div class="empty-state">
        <h3>No posts yet</h3>
        <p>Be the first to create a post!</p>
      </div>
    `;
    return;
  }

  const postsGrid = document.createElement('div');
  postsGrid.className = 'posts-grid';

  const gradients = [
    'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
    'linear-gradient(135deg, #fa709a 0%, #fee140 100%)',
    'linear-gradient(135deg, #30cfd0 0%, #330867 100%)',
    'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)'
  ];

  posts.forEach((post, index) => {
    const postCard = document.createElement('div');
    postCard.className = 'post-card';

    const gradient = gradients[index % gradients.length];
    const authorAvatar = 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png';
    const authorName = post.author || 'Anonymous';
    const postDate = new Date(post.createdAt).toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric',
      year: 'numeric'
    });

    const excerpt = post.content
      ? (post.content.substring(0, 120) + (post.content.length > 120 ? '...' : ''))
      : 'No content available';

    const category = post.category || 'General';

    const postImageStyle = post.featuredImageUrl
      ? `background-image: url('${post.featuredImageUrl}'); background-size: cover; background-position: center;`
      : `background: ${gradient};`;

    postCard.innerHTML = `
      <div class="post-image" style="${postImageStyle}"></div>
      <div class="post-content">
        <span class="post-category">${category}</span>
        <h3 class="post-title">${post.title}</h3>
        <p class="post-excerpt">${excerpt}</p>
        <div class="post-meta">
         <div class="post-author">
         <img src="${authorAvatar}" alt="${authorName}" class="author-avatar">
           <span>${authorName}</span>
         <button class="follow-btn" data-author="${post.author}">Follow</button>

</div>

          <span>•</span>
          <div class="post-date">
            <span>${postDate}</span>
          </div>
        </div>
        <div class="post-actions">
          <button class="like-btn" data-id="${post.id}">❤️ 0</button>
          <button class="see-likes-btn" data-id="${post.id}">See all likes</button>
          <a class="btn btn-primary" href="post.html?id=${encodeURIComponent(post.id)}">Add Comments</a>
        </div>
      </div>
    `;

    postsGrid.appendChild(postCard);
  });

  postsContainer.innerHTML = '';
  postsContainer.appendChild(postsGrid);

  setupLikeButtons();
  setupFollowButtons();
}


// View individual post
function viewPost(postId) {
  // Navigate to post detail page
  window.location.href = `post.html?id=${postId}`;
}

// Logout functionality
document.getElementById('logoutBtn').addEventListener('click', () => {
  if (confirm('Are you sure you want to logout?')) {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('email');
    localStorage.removeItem('avatarUrl');
    localStorage.removeItem('role');
    localStorage.removeItem('firstName');
    localStorage.removeItem('lastName');
    localStorage.removeItem('bio');

    alert('Logged out successfully!');
    window.location.href = 'login.html';
  }
});

// Create post button

document.getElementById("userAvatar").addEventListener("click", () => {
  window.location.href = 'profile.html';

});
document.getElementById("createPostBtn").addEventListener("click", () => {
  window.location.href = "createPost.html";
});

// Initialize page

if (checkAuth()) {
  loadUserData();
  fetchPosts();
}



