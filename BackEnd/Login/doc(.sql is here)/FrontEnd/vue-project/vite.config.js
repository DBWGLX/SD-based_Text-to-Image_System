import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// Element Plus组件库
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'


// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [
        // 自动导入图标组件
        IconsResolver({
          prefix: 'Icon'
        })
      ]
    }),
 
    Components({
      resolvers: [
        // 自动注册图标组件
        IconsResolver({
          enabledCollections: ['ep']
        })
      ]
    }),
    
    Icons({
      autoInstall: true
    })

  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    // 运行是否自动打开浏览器
    open: true,
    proxy: {
      /** 代理前缀为 /dev-api 的请求  */
      ["/api"]: {
        changeOrigin: true,
        // 接口地址
        target: "http://127.0.0.1:8081",
        rewrite: (path) =>
          path.replace(new RegExp("^" + "/api"), ""),
      },
    },
  },
})
