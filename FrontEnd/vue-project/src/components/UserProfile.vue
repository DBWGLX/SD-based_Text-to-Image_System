<template>
    <div id="user-profile">
      <h2>User Profile</h2>
      <div v-if="userInfo">
        <p><strong>Username:</strong> {{ userInfo.username }}</p>
        <p><strong>Email:</strong> {{ userInfo.email }}</p>
        
      </div>
      <div v-else>
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
  
