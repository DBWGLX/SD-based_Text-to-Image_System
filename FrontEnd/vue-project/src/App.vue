<script setup></script>

<template>
  <div id="app">
    <router-view></router-view>

    <div class="router-links"> 
      <div class="left-links">
        <router-link to="/">
          <el-link type="primary">Home</el-link>   <!-- 使用element组件美化 -->
        </router-link>
        <router-link to="/login">
          <el-link type="primary">Login</el-link>  
        </router-link>        
      </div>
     
      <!-- 添加登录链接 -->
      <div class="right-links">
          <router-link to="/app">
            <el-link type="primary">App</el-link>  
          </router-link>
          <router-link to="/user" >
            <el-link type="primary">UserProfile</el-link>  
          </router-link>
      </div>

    </div>
  </div>
</template>

<style scoped>
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 确保应用至少占满整个视口高度 */
  margin: 0;
}

.router-links {
  display: flex;
  justify-content: space-between;
  position: fixed;
  bottom: 0px;/* 距离底部 */
  width: 100%;
  padding: auto;
}


.left-links,
.right-links {
  display: flex;
  gap: 10px;
}
</style>
