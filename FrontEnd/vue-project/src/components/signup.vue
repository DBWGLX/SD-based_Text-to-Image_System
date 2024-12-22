<template>
  <div class="signup-container">
    <div class="signup-card">
      <h1>注册</h1>
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="username">用户名</label>
          <input type="text" id="username" v-model="username" placeholder="请输入你的用户名" />
          <span class="error-message" v-if="errors.username">{{ errors.username }}</span>
        </div>

        <div class="form-group">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="password" placeholder="密码不少于8位，包含大小写字母和数字" />
          <span class="error-message" v-if="errors.password">{{ errors.password }}</span>
        </div>

        <div class="form-group">
          <label for="email">邮箱</label>
          <input type="email" id="email" v-model="email" placeholder="请输入你的邮箱" />
          <span class="error-message" v-if="errors.email">{{ errors.email }}</span>
        </div>

        <div class="form-group">
          <label for="phone">手机号</label>
          <input type="text" id="phone" v-model="phone" placeholder="请输入你的手机号" />
          <span class="error-message" v-if="errors.phone">{{ errors.phone }}</span>
        </div>

        <div class="form-group">
          <label for="verificationCode">验证码</label>
          <div class="verification-container">
            <input type="text" id="verificationCode" v-model="verificationCode" placeholder="请输入验证码" />
            <button type="button" @click="sendVerificationCode" :disabled="isSendingCode" class="send-code-button">
              {{ isSendingCode ? '发送中...' : '发送验证码' }}
            </button>
          </div>
          <span class="error-message" v-if="errors.verificationCode">{{ errors.verificationCode }}</span>
        </div>

        <button type="submit" class="submit-button">Sign Up</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router"; // 引入路由对象
// 初始化路由对象
const router = useRouter();

// 表单数据
const username = ref("");
const password = ref("");
const email = ref("");
const phone = ref("");
const verificationCode = ref("");
const errors = ref({
  username: "",
  password: "",
  email: "",
  phone: "",
  verificationCode: "",
});

// 验证表单
const validateForm = () => {
  let isValid = true;
  errors.value = { username: "", password: "", email: "", phone:"",verificationCode: "" };

  if (!username.value) {
    errors.value.username = "用户名不能为空";
    isValid = false;
  }

  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/;
  if (!password.value || !passwordRegex.test(password.value)) {
    errors.value.password = "密码格式不正确（至少8位，包含大小写字母和数字）";
    isValid = false;
  }

  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  if (!emailRegex.test(email.value)) {
    errors.value.email = "邮箱格式不正确";
    isValid = false;
  }

  const phoneRegex = /^\d{11}$/;
  if (!phoneRegex.test(phone.value)) {
    errors.value.phone = "手机号格式不正确";
    isValid = false;
  }

  if (!verificationCode.value) {
    errors.value.verificationCode = "验证码不能为空";
    isValid = false;
  }

  return isValid;
};

// 验证码功能
const isSendingCode = ref(false);
const sendVerificationCode = async () => {
  if (!email.value) {
    errors.value.email = "请先填写有效的邮箱";
    return;
  }

  isSendingCode.value = true;

  try {
    const response = await fetch("http://localhost:8081/verify", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email: email.value }),
    });

    // 检查响应是否成功
    const data = await response.json();
    if (data.code === true) {
      // 验证码发送成功
      alert(data.data); // 显示 "邮件已发送"
    } else {
      throw new Error(data.msg || "验证码发送失败");
    }
  } catch (error) {
    console.error("验证码发送失败：", error.message);
    alert(error.message || "网络错误，请稍后重试");
  } finally {
    isSendingCode.value = false;
  }
};


// 提交表单
const handleSubmit = async () => {
  if (!validateForm()) return;

  try {
    const response = await axios.post("http://localhost:8081/register", {
      username: username.value,
      password: password.value,
      email: email.value,
      phone: phone.value,
      code: verificationCode.value,
    });

    const result = response.data;
    console.log("后端返回的数据：", result);

    if (result.code === true) {
      alert(result.data || "注册成功！");
      
      // 清空表单
      username.value = "";
      password.value = "";
      email.value = "";
      phone.value = "";
      verificationCode.value = "";

      console.log("正在跳转到登录页面...");
      await router.push('/login'); // 跳转到登录页面
    } else {
      alert(result.msg || "注册失败！");
    }
  } catch (error) {
    console.error("注册请求失败：", error.message);
    alert("注册失败，请检查网络连接！");
  }
};

</script>

<style scoped>
/* 样式保持不变，使用之前的 CSS */
.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f0f0;
}

.signup-card {
  width: 400px;
  padding: 30px;
  border-radius: 12px;
  background-color: #ffffff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 28px;
  color: #333;
  margin-bottom: 20px;
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  font-size: 16px;
  color: #555;
  margin-bottom: 8px;
  display: block;
}

input {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 6px;
  margin-top: 5px;
}

input:focus {
  border-color: #007bff;
  outline: none;
}

.error-message {
  color: red;
  font-size: 14px;
  margin-top: 5px;
}

.verification-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.send-code-button {
  padding: 10px;
  font-size: 14px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.send-code-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.submit-button {
  width: 100%;
  padding: 12px;
  background-color: #007bff;
  color: white;
  font-size: 16px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #0056b3;
}
</style>
