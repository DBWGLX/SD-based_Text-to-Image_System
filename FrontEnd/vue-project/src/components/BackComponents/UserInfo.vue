// 用户下拉框定义 UserInfo.vue
<script setup lang="ts"></script>

<template>
  <el-dropdown>
    <span class="el-dropdown-link">
      <el-icon style="margin-right: 5px"><user-filled /></el-icon>
      <span>admin</span>
      <el-icon class="el-icon--right" style="margin-left: 5px">
        <arrow-down />
      </el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item>用户信息</el-dropdown-item>
        <el-dropdown-item>修改密码</el-dropdown-item>
        <el-dropdown-item>安全退出</el-dropdown-item>
        <!-- <el-dropdown-item disabled>Action 4</el-dropdown-item>
        <el-dropdown-item divided>Action 5</el-dropdown-item> -->
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<style scoped></style>
