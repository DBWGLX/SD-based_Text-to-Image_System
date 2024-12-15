// 新建MenuLogo.vue

<script setup lang="ts"></script>

<template>
  <div class="logo">
    <!-- 图片 -->
    <!-- <img src="../../assets/logo_main.png" alt="Logo" /> -->
    <span class="title">信息管理系统</span>
  </div>
</template>

<style scoped>
.logo {
  background-color: #2b2f3a;
  height: 50px;
  border: none;
  line-height: 50px;
  display: flex;
  align-items: center;
  padding-left: 15px;
  color: #fff;
}
.logo img {
  width: 32px;
  height: 32px;
  margin-right: 12px;
}
.logo span {
  font-weight: 600;
  line-height: 50px;
  font-size: 16px;
  font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
  vertical-align: middle;
}
</style>
