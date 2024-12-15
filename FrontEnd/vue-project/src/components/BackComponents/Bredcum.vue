// 动态获取面包屑导航标准流程 /layout/header/Bredcum.vue

<script setup lang="ts">
import { Ref, ref, watch } from "vue";

// 定义 useRouter模块
import { useRoute, RouteLocationMatched } from "vue-router";
// 定义面包屑集合
const tabs: Ref<RouteLocationMatched[]> = ref([]);

// 获取路由信息
const route = useRoute();

const getBredCum = () => {
  //   console.log(route)
  //  console.log( route.matched)
  //  console.log( route.matched[0])
  let matched;
  // 在路由中筛选过滤路由匹配信息 --- route.metched --meta --meta.title
  if (route.path == "/backend") {
    matched = [{ path: "/dashboard", meta: { title: "首页" } } as any];
    // console.log(matched)
  } else {
    matched = route.matched.filter((item) => item.meta && item.meta.title);
  }

  tabs.value = matched;

  // console.log(matched)
};

getBredCum();

watch(
  () => route.path,
  () => getBredCum()
); // 路由变化时重新获取面包屑
// watch(()=>route.matched, getBredCum, { immediate: true }) // 路由变化时重新获取面包屑
</script>

<template>
  <el-breadcrumb separator="/">
    <el-breadcrumb-item v-for="item in tabs">{{
      item.meta.title
    }}</el-breadcrumb-item>
  </el-breadcrumb>
  <!-- <el-breadcrumb-item>首页</el-breadcrumb-item>
    <el-breadcrumb-item>基础数据</el-breadcrumb-item>
    <el-breadcrumb-item>院系管理</el-breadcrumb-item> -->
</template>

<style scoped></style>
