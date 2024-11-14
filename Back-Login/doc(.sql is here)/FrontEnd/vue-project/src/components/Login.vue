<template>
  <div class="login">
    <h2>用户登录</h2>
    <form @submit.prevent="login">
      <div>
        <label for="username">用户名:</label>
        <input type="text" v-model="username" required />
      </div>
      <div>
        <label for="password">密码:</label>
        <input type="password" v-model="password" required />
      </div>
      <div style="display: flex;justify-content: space-between;">
        <button style="width: 100%;margin-right: 4px;" @click="register">注册</button>
        <button style="width: 100%;margin-left: 4px;" type="submit">登录</button>
      </div>
    </form>
    <p v-if="errorMessage">{{ errorMessage }}</p>
  </div>
</template>

<script>
import { loginApi } from "@/api/auth";
export default {
  data() {
    return {
      username: '',
      password: '',
      errorMessage: '',
    };
  },
  methods: {
    login() {
      // 假设验证逻辑
      if (this.username !== '' && this.password !== '') {
        loginApi({
          username: this.username,
          password: this.password,
          role: 1
        })
          .then((response) => {
            const { token, user } = response.data;
            localStorage.setItem("accessToken", token); // Bearer eyJhbGciOiJIUzI1NiJ9.xxx.xxx
            localStorage.setItem('isAuthenticated', 'true'); // 设置登录状态
            this.$router.push('/app'); // 登录成功后重定向到应用界面
          })
          .catch((error) => {
            this.errorMessage = error;
          });

      } else {
        this.errorMessage = 'Invalid credentials';
      }
    },
    register() {
      this.$router.push('/register');
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
