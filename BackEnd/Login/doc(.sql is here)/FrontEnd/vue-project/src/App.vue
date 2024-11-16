<script setup></script>

<template>
  <div id="app">
    <router-view></router-view>

    <div class="router-links"> 
      <router-link to="/">
        <el-link type="primary">Home</el-link>   <!-- 使用element组件美化 -->
      </router-link>
      <br />
      <router-link to="/login">
        <el-link type="primary">Login</el-link>  
      </router-link>
      <!-- 添加登录链接 -->
      <br />
      <router-link to="/app">
        <el-link type="primary">App</el-link>  
      </router-link>
      <br />
      <router-link to="/user" >
       <el-link type="primary">UserProfile</el-link>  
      </router-link>
    </div>
  </div>
</template>

<style scoped>
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 确保应用至少占满整个视口高度 */
}

.router-links {
  margin-top: auto; /* 将链接推到底部 */
}
</style>
