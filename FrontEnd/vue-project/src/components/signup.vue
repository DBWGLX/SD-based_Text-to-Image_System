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
  
          <!-- 验证码输入框 -->
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
  import { ref } from 'vue';
  
  // 定义表单数据
  const username = ref('');
  const password = ref('');
  const email = ref('');
  const phone = ref('');
  const verificationCode = ref('');
  const errors = ref({
    username: '',
    password: '',
    email: '',
    phone: '',
    verificationCode: '',
  });
  
  // 验证表单数据
  const validateForm = () => {
    let isValid = true;
    errors.value = { username: '', password: '', email: '', phone: '', verificationCode: '' };
  
    if (!username.value) {
      errors.value.username = '用户名不能为空';
      isValid = false;
    }
  
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$/;
    if (!password.value || !passwordRegex.test(password.value)) {
      errors.value.password = '密码格式不正确（至少8位，包含大小写字母和数字）';
      isValid = false;
    }
  
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(email.value)) {
      errors.value.email = '邮箱格式不正确';
      isValid = false;
    }
  
    const phoneRegex = /^\d{11}$/;
    if (!phoneRegex.test(phone.value)) {
      errors.value.phone = '手机号格式不正确';
      isValid = false;
    }
  
    if (!verificationCode.value) {
      errors.value.verificationCode = '验证码不能为空';
      isValid = false;
    }
  
    // 验证验证码
    if (verificationCode.value !== sentCode.value) {
      errors.value.verificationCode = '验证码不正确';
      isValid = false;
    }
  
    return isValid;
  };
  
  // 模拟发送验证码
  const sentCode = ref(''); // 用于存储发送的验证码
  const isSendingCode = ref(false); // 控制按钮是否禁用
  
  const sendVerificationCode = () => {
    if (!email.value) {
      errors.value.email = '请先填写有效的邮箱';
      return;
    }
  
    isSendingCode.value = true;
  
    // 目前是模拟发送验证码，需要调用后端API来发送邮件验证码！
    setTimeout(() => {
      // 假设验证码是随机生成的
      sentCode.value = Math.floor(100000 + Math.random() * 900000).toString(); // 生成6位数字验证码
      console.log('验证码已发送至邮箱:', sentCode.value);
      alert('验证码已发送到您的邮箱，请查收！');
      isSendingCode.value = false;
    }, 2000);
  };
  
  // 提交表单,暂时只是打印表单数据，需要调用后端API来保存用户数据！
  const handleSubmit = () => {
    if (validateForm()) {
      console.log('Form Submitted', { username: username.value, email: email.value, phone: phone.value });
      alert('注册成功!');
    }
  };
  </script>
  
  <style scoped>
  .signup-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f0f0f0;
    margin: 0;
  }
  
  .signup-card {
    width: 400px;
    padding: 30px;
    border-radius: 12px;
    background-color: #ffffff;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    text-align: center;
  }
  
  h1 {
    font-size: 28px;
    color: #333;
    margin-bottom: 20px;
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
  
  .submit-button {
    width: 100%;
    padding: 12px;
    background-color: #007bff;
    color: white;
    font-size: 16px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    margin-top: 20px;
  }
  
  .submit-button:hover {
    background-color: #0056b3;
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
  </style>
  