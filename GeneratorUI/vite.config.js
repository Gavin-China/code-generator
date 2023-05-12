import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

import { resolve } from "path";
// https://vitejs.dev/config/
export default defineConfig(() => {
  return {
    plugins: [vue()],
    build: {
      target: "es2015",
    },
    // 项目根路径
    base: "./",
    // 端口和是否启动时打开页面
    server: {
      open: true,
      cors: true, // 允许跨域
      //  反向代理配置，注意rewrite写法，开始没看文档在这里踩了坑
      proxy: {
        "/dev-api": {
          target: "http://127.0.0.1:8999", // 请求地址
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/dev-api/, ""),
        },
      },
    }, // @/指向src
    resolve: {
      alias: {
        "@": resolve(__dirname, "src"),
      },
    },
  };
});
