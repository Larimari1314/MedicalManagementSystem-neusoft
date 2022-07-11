import {
	createRouter,
	createWebHistory
} from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import List from '../views/Sys/List.vue'

const routes = [{
		path: '/',
		redirect: '/login'
	},
	{
		path: '/login',
		name: 'Login',
		component: Login
	},
	{
		path: '/home',
		name: 'Home',
		component: Home,
		redirect: '/home/list',
		children: [{
			path: '/home/list',
			component: List
		}, ]
	}
]

const router = createRouter({
	 history: createWebHistory(process.env.BASE_URL),
	 routes
})

export default router
