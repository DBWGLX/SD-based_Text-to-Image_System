
import { createApp } from 'vue'
import App from './App.vue'
import router from './router';

//全局导入element-plus 
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'



createApp(App)
    .use(ElementPlus)
    .use(router)
    .mount('#app')

