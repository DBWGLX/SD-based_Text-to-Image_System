import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/components/Home.vue'; // 确保路径正确
//import MyComponent from '@/components/MyComponent.vue'; // 确保路径正确
import Login from '@/components/Login.vue';// 导入登录组件
import AppInterface from '@/components/AppInterface.vue'; // 导入应用界面组件
import UserProfile  from '@/components/UserProfile.vue'; // 导入用户信息组件
import UserImages from '@/components/UserImages.vue'; // 导入用户历史图片组件
import Signup from '@/components/signup.vue';

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },// 添加登录路由
  { path: '/app', component: AppInterface, meta: { requiresAuth: true } }, // 需要身份验证的路由
  { path: '/user', component: UserProfile, meta: { requiresAuth: true } }, // 个人信息页面的路由
  { path: '/user-images', component: UserImages, meta: { requiresAuth: true },name:'UserImages' }, // 用户历史图片页面的路由
  { path: '/signup', component: Signup, meta: { requiresAuth: true } }, // 注册页面的路由
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
