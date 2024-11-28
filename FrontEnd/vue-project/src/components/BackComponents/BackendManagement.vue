// index.vue 注册
<script setup lang="ts">
// 引入组件
import MenuVue from "./Menu.vue";
import HeaderVue from "./Header.vue";
</script>
 
<template>
    <el-container  class="layout">
        <el-aside width="200px" class="aside"><MenuVue></MenuVue></el-aside>
        <el-container>
            <el-header class="header"> <HeaderVue></HeaderVue></el-header>
            <el-main class="main"><router-view></router-view></el-main>
        </el-container>
    </el-container>
  </template>


<style scoped>
.layout {
  height: 100%;
}

.aside {
  background-color: #304156;
}
.header {
  background-color: lightblue;
  height: 50px;
  border-bottom: 1px solid #e5e5e5;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
