import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

import * as ElementPlusIconsVue from "@element-plus/icons-vue"; // 导入所以 icon图标

//全局导入element-plus
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";

// 创建app
const app = createApp(App);

// 注册到全局变量
import store from "../store"; // 导入创建的store对象

app.use(store).use(router).use(ElementPlus).mount("#app");

// // 添加 locale:zhCn
// app.use(store).use(router).use(ElementPlus, { locale: zhCn }).mount("#app");

// 遍历所有 icon将每个图标以组件方式加载到app中
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
