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
      <div>
        <label for="otp">OTP:</label>
        <input type="text" v-model="otp" required />
      </div>
      <div>
        <button type="button" @click="getOtp">获取验证码</button>
      </div>
      <button type="submit">登录</button>
    </form>
    <p v-if="errorMessage">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';
// 假设路由文件在src/router.js，这里根据实际情况调整导入路径
import router from '@/router';

export default {
  data() {
    return {
      username: '',
      password: '',
      otp: '',
      errorMessage: ''
    };
  },
  methods: {
    async getOtp() {
      try {
        const response = await axios.post('http://localhost:8080/api/auth/login/totp', {
          username: this.username,
          password: this.password
        });
        if (response.data.success) {
          this.errorMessage = '';
        } else {
          this.errorMessage = response.data.message || 'Invalid credentials';
        }
      } catch (error) {
        this.errorMessage = 'Failed to get OTP. Please try again later.';
        console.error(error);
      }
    },
    async login() {
      try {
        const response = await axios.post('http://localhost:8080/api/auth/login', {
          username: this.username,
          password: this.password,
          otp: this.otp
        });
        if (response.data.success) {
          localStorage.setItem('isAuthenticated', 'true');
          localStorage.setItem('authToken', response.data.token);
          // 使用正确引入的router实例进行跳转
          router.push('/app');
        } else {
          this.errorMessage = response.data.message || 'Invalid credentials';
        }
      } catch (error) {
          this.errorMessage = 'Failed to login. Please try again later.';
          console.error(error);
      }
    }
  }
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