<template>
  <div id="app">
    <!-- 导航栏 -->
    <header v-if="!$route.meta.hideHeader">
      <nav class="navbar">
        <div class="logo">
          <h2>SD Project</h2>
        </div>
        <ul class="menu">
          <li><router-link to="/">Home</router-link></li>
          <li><router-link to="/app">App</router-link></li>
          <li><a href="#About">About</a></li>
          <li><a href="#Contact">Contact</a></li>
        </ul>

        <!-- 右边的：登录/注册/退出按钮 -->
        <div class="auth-buttons">
          <!-- 未登录时显示 Sign in 和 Log in 按钮 -->
          <button v-if="!isAuthenticated" class="button-primary" @click="signIn">
            Sign in
          </button>
          <button v-if="!isAuthenticated" class="button-secondary" @click="login">
            Log in
          </button>

          <!-- 已登录时显示 UserProfile 和 Logout 按钮 -->
          <router-link v-if="isAuthenticated" to="/user">
            <button class="button-primary">UserProfile</button>
          </router-link>
          <button v-if="isAuthenticated" class="button-secondary" @click="logout">
            Logout
          </button>
        </div>
      </nav>
    </header>

    <!-- 页面内容 -->
    <router-view></router-view>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const isAuthenticated = ref(localStorage.getItem("isAuthenticated") === "true"); // 初始从 localStorage 获取登录状态

// 注册跳转
const signIn = () => {
  router.push("/signup");
};

// 登录跳转
const login = () => {
  router.push("/login");
};

// 监听 localStorage 中登录状态的变化
const handleStorageChange = (event) => {
  if (event.key === "isAuthenticated") {
    isAuthenticated.value = event.newValue === "true";
  }
};

// 生命周期钩子
onMounted(() => {
  window.addEventListener("storage", handleStorageChange);
});

onBeforeUnmount(() => {
  window.removeEventListener("storage", handleStorageChange);
});

// 退出登录逻辑
const logout = () => {
  localStorage.removeItem("token"); // 清除 token
  localStorage.setItem("isAuthenticated", "false"); // 更新登录状态
  isAuthenticated.value = false;
  router.push("/"); // 返回首页
};
</script>

<style scoped>
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  margin: 0;
}

header {
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  padding: 10px 0;
  background-color: #fff;
  z-index: 1000;
}

main {
  margin-top: 60px; /* 给页面内容留出顶部空间 */
}

/* 导航栏样式 */
.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 25px 50px;
  box-sizing: border-box;
}

.logo h2 {
  margin: 0;
  color: #333;
}

.menu {
  list-style-type: none;
  display: flex;
  gap: 20px;
  margin-left: 16px;
  padding: 0;
}

.menu li a {
  text-decoration: none;
  color: #007bff;
  font-weight: bold;
}

.auth-buttons {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-left: auto;
}

.auth-buttons button {
  margin-left: 10px;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 5px;
  font-size: 1rem;
}

.button-primary {
  background-color: #007bff;
  color: #fff;
  border: 1px solid #fff;
}

.button-secondary {
  background-color: #fff;
  color: #007bff;
  border: 1px solid #007bff;
}

.auth-buttons button:hover {
  background-color: 1px solid #0056b3;
}
</style>
