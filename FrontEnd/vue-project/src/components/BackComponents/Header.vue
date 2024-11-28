// 父组件 Header.vue引入
<script setup lang="ts">
// 导入组件
import UserInfo from "./UserInfo.vue";
import Collaspe from "./Collaspe.vue";
import Bredcum from "./Bredcum.vue";

</script>

<template>
     <!-- 靠着横排显示将两组件放在同一个div中 -->
  <div style="display: flex; align-items: center">
    <Collaspe></Collaspe>
    <Bredcum></Bredcum>
  </div>
  <UserInfo></UserInfo>
</template>

<style scoped></style>
