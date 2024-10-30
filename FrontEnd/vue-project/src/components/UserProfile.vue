<template>
    <div id="user-profile" class="container">
      <div class="profile-header">
        <h2>User Profile</h2>
      </div>
      <div class="profile-body" v-if="userInfo">
        <div class="profile-info">
            <el-button>Default</el-button>
    <el-button type="primary">Primary</el-button>
    <el-button type="success">Success</el-button>
    <el-button type="info">Info</el-button>
    <el-button type="warning">Warning</el-button>
    <el-button type="danger">Danger</el-button>
          <p><strong>Username:</strong> {{ userInfo.username }}</p>
          <p><strong>Email:</strong> {{ userInfo.email }}</p>
          <!-- 根据实际情况添加更多个人信息 -->
        </div>
      </div>
      <div class="loading" v-else>
        <p>Loading...</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        userInfo: null,
      };
    },
    mounted() {
      this.fetchUserInfo();
    },
    methods: {
      fetchUserInfo() {
        // 获取用户信息，可以是从服务器获取，也可以是模拟数据
        // 示例：假设用户信息存储在localStorage中
        const isAuthenticated = localStorage.getItem('isAuthenticated');
        if (isAuthenticated) {
          this.userInfo = JSON.parse(localStorage.getItem('userInfo')) || {};
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 600px;
    margin: auto;
    padding: 20px;
    border-radius: 16px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1), 0 8px 16px rgba(0, 0, 0, 0.06);
    background-color: #ffffff;
    text-align: center;
  }
  
  .profile-header {
    background-color: #f8fafc;
    padding: 10px;
    border-radius: 16px 16px 0 0;
  }
  
  .profile-body {
    margin-top: 20px;
    text-align: left;
  }
  
  .profile-info {
    padding: 10px;
    border-radius: 8px;
    background-color: #ffffff;
    box-shadow: inset 0 0 0 1px rgba(0, 0, 0, 0.05);
  }
  
  .loading {
    margin-top: 20px;
    text-align: center;
    color: #777;
  }
  
  strong {
    font-weight: bold;
    color: #000;
  }
  
  p {
    margin-bottom: 10px;
    line-height: 1.5;
    font-size: 16px;
    color: #333;
  }
  
  /* 苹果风格的按钮 */
  button {
    background-color: #06b6d4;
    color: #ffffff;
    border: none;
    border-radius: 16px;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  button:hover {
    background-color: #0596b8;
  }
  

  input {
    padding: 10px;
    border: 1px solid #dadada;
    border-radius: 8px;
    font-size: 16px;
    transition: border-color 0.3s;
  }
  
  input:focus {
    border-color: #06b6d4;
    outline: none;
  }
  </style>