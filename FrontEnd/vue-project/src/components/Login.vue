<template>
  <div class="login">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div>
        <label for="username">Username:</label>
        <input type="text" v-model="username" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="password" required />
      </div>
      <button type="submit">Login</button>
    </form>
    <p v-if="errorMessage">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      username: '',
      password: '',
      errorMessage: '',
    };
  },
  methods: {
    async login() {
      try {
        // 发送 POST 请求到后端
        const response = await axios.post('http://localhost:8080/api/login/totp', {
          username: this.username,
          password: this.password,
        });

        // 假设后端返回的字段为 { success: true, token: '...' }
        if (response.data.success) {
          localStorage.setItem('isAuthenticated', 'true'); // 设置登录状态
          localStorage.setItem('authToken', response.data.token); // 保存后端返回的 token
          this.$router.push('/app'); // 登录成功后重定向到应用界面
        } else {
          this.errorMessage = response.data.message || 'Invalid credentials'; // 设置后端返回的错误信息
        }
      } catch (error) {
        // 捕获请求失败的错误
        this.errorMessage = 'Failed to login. Please try again later.';
        console.error(error); // 输出错误信息到控制台（可选）
      }
    },
  },

};
</script>

<style scoped>
.login {
  max-width: 400px;
  margin: auto;
  text-align: center;

  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
}

form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}


input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
  transition: border-color 0.3s;
}

input:focus {
  border-color: royalblue;
  outline: none;
}

button {
  padding: 10px;
  background-color: royalblue;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

button:hover {
  background-color: #0074b5;
}
</style>
