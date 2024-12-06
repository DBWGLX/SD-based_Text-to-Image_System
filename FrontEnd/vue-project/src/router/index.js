import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/components/Home.vue'; // 确保路径正确
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
  { path: '/signup', component: Signup}, // 注册页面的路由
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 添加导航守卫
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('isAuthenticated'); // 假设使用 localStorage 存储登录状态
  console.log('导航守卫触发', { to: to.path, isAuthenticated, requiresAuth: to.meta.requiresAuth }); // 打印调试信息
  if (to.meta.requiresAuth && isAuthenticated !== 'true') {
    console.log('导航守卫触发', '未登录，重定向到登录页面');
    next('/login'); // 如果未登录，重定向到登录页面
  } else {
    next(); // 允许访问
  }
});


// 添加刷新功能（登录后，按钮变化），仅针对某些页面或路径
router.afterEach((to, from) => {
  // 如果是特定路由（如 '/app'）需要刷新页面，可以加条件判断
  if (to.path !== '/login' && from.path === '/login') {
    window.location.reload();
  }
});



export default router;
