import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

const path = require("path");
//vue.config.js
export default defineConfig({
	plugins: [
		vue()
	],
	resolve: {
		alias: {
			"@": path.resolve(__dirname, "./src"),
			"comps": path.resolve(__dirname, "./src/components"),
			"views": path.resolve(__dirname, "./src/views"),
			"utils": path.resolve(__dirname, "./src/utils"),
			"routes": path.resolve(__dirname, "./src/routes"),
			"styles": path.resolve(__dirname, "./src/styles"),
		},
	},
	define: {
		//不加这个定义，router/index.js将提示process.env.BASE_URL未定义
		'process.env': {}
	},
	// 是否自动在浏览器打开
	open: true,
	// 是否开启 https
	https: false,
	// 服务端渲染
	ssr: false,
	base: "./",
	/**
	 * 与“根”相关的目录，构建输出将放在其中。如果目录存在，它将在构建之前被删除。
	 * @default 'dist'
	 */
	outDir: "dist",
	// proxy: {
	// 	"/api": {
	// 	 target: "http://localhost:8443/mystu/api",
	// 	 changeOrigin: true,
	// 	 rewrite: (path) => path.replace(/^\/api/, ""),
	// 	},
	//    },
})
