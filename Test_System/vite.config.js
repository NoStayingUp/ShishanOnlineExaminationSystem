import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  server:{
    proxy: {
      "/api":  {
        target: "http://47.115.213.253:3090",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/,"")
      }
     }
  },
  plugins: [
    vue(),
  ],
  base:'/',//!!!!!!!!!
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})