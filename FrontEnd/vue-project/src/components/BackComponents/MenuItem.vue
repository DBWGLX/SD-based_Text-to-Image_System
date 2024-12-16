// MenuItem.vue
<script setup lang="ts">
// 导入基本模块
import { reactive } from "vue";

// 初始化数据
let menuList = reactive([
  {
    path: "/",
    meta: {
      title: "首页",
      icon: "HomeFilled",
    },
  },
  {
    path: "/baisc",
    meta: {
      title: "基础数据",
      icon: "Setting",
    },
    children: [
      {
        path: "/basic/introduce",
        meta: {
          title: "专业信息",
          icon: "ShoppingBag",
        },
      },
      {
        path: "/basic/major",
        meta: {
          title: "小组信息",
          icon: "ShoppingCartFull",
        },
      },
    ],
  },
  // 用户管理
  {
    path: "/User",
    meta: {
      title: "用户管理",
      icon: "UserFilled",
    },
    children: [
      {
        path: "/User/info",
        meta: {
          title: "用户信息",
          icon: "VideoCameraFilled",
        },
      },
      {
        path: "/User/history",
        meta: {
          title: "历史记录",
          icon: "OfficeBuilding",
        },
      },
      {
        path: "/User/image",
        meta: {
          title: "用户照片",
          icon: "TakeawayBox",
        },
      },
    ],
  },
  {
    path: "/administrators",
    meta: {
      title: "管理员",
      icon: "Ticket",
    },
    children: [
      {
        path: "/administrators/account",
        meta: {
          title: "登录账号",
          icon: "Coordinate",
        },
      },
      {
        path: "/administrators/roles",
        meta: {
          title: "角色信息",
          icon: "CreditCard",
        },
      },
      {
        path: "/administrators/menu",
        meta: {
          title: "菜单管理",
          icon: "DeleteLocation",
        },
      },
      {
        path: "/administrators/permission",
        meta: {
          title: "权限管理",
          icon: "Goods",
        },
      },
    ],
  },
]);
</script>

<template>
  <template v-for="menu in menuList">
    <!-- 
            1.循环遍历menuList
            2.如果children存在并且不为空及长度大于0，则创建一级菜单,反之则创建多级
            3. 点击菜单，跳转到对应路径
        -->
    <el-sub-menu
      v-if="menu.children && menu.children.length > 0"
      :index="menu.path"
    >
      <!-- 展示二级菜单第一层 -->
      <template #title>
        <el-icon>
          <component class="icons" :is="menu.meta.icon"></component>
        </el-icon>
        <span>{{ menu.meta.title }}</span>
      </template>
      <!-- 展示二级菜单第二层 -->
      <!-- <template v-for="child in menu.children">
                <el-menu-item :index="child.path">
                    <span>{{ child.meta.title }}</span>
                </el-menu-item>
            </template> -->
      <template #default>
        <el-menu-item
          v-for="child in menu.children"
          :key="child.path"
          :index="child.path"
        >
          <el-icon>
            <component class="icons" :is="child.meta.icon"></component>
          </el-icon>
          <span style="color: aliceblue">{{ child.meta.title }}</span>
        </el-menu-item>
      </template>
    </el-sub-menu>
    <el-menu-item v-else :index="menu.path" style="text-align: left">
      <el-icon>
        <component class="icons" :is="menu.meta.icon"></component>
      </el-icon>
      <span>{{ menu.meta.title }}</span>
    </el-menu-item>
  </template>
</template>

<style scoped>
.el-menu-item {
  display: block;
  text-align: center;
  padding-right: 80px;
}
</style>
