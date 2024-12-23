<template>
  <div class="login">
    <h2 class="login_text">Login</h2>
    <form @submit.prevent="login" class="form-grid">
      <div class="form-item username">
        <label for="username">Username:</label>
        <input type="text" v-model="username" required />
        <div class="col"></div>
      </div>
      <div class="form-item password">
        <label for="password">Password:</label>
        <input type="password" v-model="password" required />
      </div>
      <div class="form-item otp">
        <label for="otp">OTP:</label>
        <input type="text" v-model="otp" required />
        <div>
          <button type="button" @click="getOtp">获取验证码</button>
        </div>
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
        const response = await axios.post('http://localhost:8082/api/auth/login/totp', {
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
      // 向后端发送登录请求
      const response = await axios.post('http://localhost:8082/api/auth/login', {
        username: this.username,
        password: this.password,
        otp: this.otp
      });

      // 检查后端返回的数据
      if (response.data.code === true) { // 假设后端成功时的状态码为200
        console.log(response.data);

        // 将 JWT 存入 localStorage
        const resp = response.data.data; // 根据后端返回的 JSON 结构获取 token
        const jwt = resp.jwt;
        const userId = resp.userId;
        localStorage.setItem('authToken', jwt);
        localStorage.setItem('userId', userId);
        // 将用户的登录状态记录
        localStorage.setItem('isAuthenticated', 'true');
        
        // 跳转到目标页面
        this.$router.push('/app');
        
      } else {
        // 如果登录失败，显示错误信息
        this.errorMessage = response.data.message || 'Invalid credentials';
      }
    } catch (error) {
      // 捕获请求错误
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

.login_text {
  margin-bottom: 20px;
}

form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

label {
  width: 60px; /* 固定宽度，确保对齐 */
  text-align: left; /* 右对齐 */
  margin-right: 10px;
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

.form-item {
  display: flex;
  align-items: center;
  gap: 5px;
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

