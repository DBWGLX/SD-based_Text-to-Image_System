import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/components/Home.vue'; // 确保路径正确
//import MyComponent from '@/components/MyComponent.vue'; // 确保路径正确
import Login from '@/components/Login.vue';// 导入登录组件
import AppInterface from '@/components/AppInterface.vue'; // 导入应用界面组件
import UserProfile  from '@/components/UserProfile.vue'; // 导入用户信息组件
import UserImages from '@/components/UserImages.vue'; // 导入用户历史图片组件
<<<<<<< HEAD
import Signup from '@/components/signup.vue';
=======
import BackendManagement from "@/components/BackComponents/BackendManagement.vue";
>>>>>>> backManagement

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },// 添加登录路由
  { path: '/app', component: AppInterface, meta: { requiresAuth: true } }, // 需要身份验证的路由
  { path: '/user', component: UserProfile, meta: { requiresAuth: true } }, // 个人信息页面的路由
  { path: '/user-images', component: UserImages, meta: { requiresAuth: true },name:'UserImages' }, // 用户历史图片页面的路由
<<<<<<< HEAD
  { path: '/signup', component: Signup, meta: { requiresAuth: true } }, // 注册页面的路由
=======
  {
    path: "/backend",
    component: BackendManagement,
    children: [
      // 首页
      {
            path: "/dashboard",
            name: "Dashboard",
            component: BackendManagement,
            // component: () => import("../views/index/Dashboard.vue"),
            meta: { title: "首页" },
      },
      // 基础数据--->专业信息、小组信息
      {
        path: "/basic",
        name: "Basic",
        // component: Layout,
        meta: { title: "基础数据" },
        children: [
          {
            path: "/basic/introduce",
            name: "Introduce",
            //component: () => import("../views/basic/Faculty.vue"),
            meta: { title: "专业信息" },
          },
          {
            path: "/basic/major",
            name: "Major",
            //component: () => import("../views/basic/Major.vue"),
            meta: { title: "小组信息" },
          },
        ],
      },
      // 用户管理
      {
        path: "/User",
        name: "User",
        // component: Layout,
        meta: { title: "用户管理" },
        children: [
          {
            path: "/User/info",
            name: "UserInfo",
            component: () => import("../components/BackComponents/info.vue"),
            meta: { title: "用户信息" },
          },
          {
            path: "/User/history",
            name: "UserHistory",
            // component: () => import("../views/student/Exam.vue"),
            meta: { title: "历史记录" },
          },
          {
            path: "/User/image",
            name: "UserImage",
            //component: () => import("../views/student/Image.vue"),
            meta: { title: "用户照片" },
          },
        ],
      },
      // 管理员管理
      {
        path: "/administrators",
        name: "Administrators",
        // component: Layout,
        meta: { title: "管理员" },
        children: [
          {
            path: "/administrators/account",
            name: "AdminAccount",
            // component: () => import("../views/user/Account.vue"),
            meta: { title: "登录账号" },
          },
          {
            path: "/administrators/roles",
            name: "AdminRoles",
            //component: () => import("../views/user/Roles.vue"),
            meta: { title: "角色信息" },
          },
          {
            path: "/administrators/menu",
            name: "AdminMenu",
            // component: () => import("../views/user/Menu.vue"),
            meta: { title: "菜单管理" },
          },
          {
            path: "/administrators/permission",
            name: "UserPermission",
            // component: () => import("../views/user/Permission.vue"),
            meta: { title: "权限管理" },
          },
        ],
      },
    ],
  },
>>>>>>> backManagement
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 添加导航守卫
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('isAuthenticated'); // 假设使用 localStorage 存储登录状态
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login'); // 如果未登录，重定向到登录页面
  } else {
    next(); // 允许访问
  }
});

export default router;
