<template>
	<div class="header">
		<div class="logo">后台管理系统</div>
		<div class="header-right">
			<div class="header-user-con">
				<!-- 用户头像 -->
				<div class="user-avator">
					<img :src="avatar" />
				</div>
				<!-- 用户名下拉菜单 -->
				<el-dropdown class="user-name" trigger="click" @command="handleCommand">
					<span class="el-dropdown-link">
						{{ userName }}
						<i class="el-icon-caret-bottom"></i>
					</span>
					<template #dropdown>
						<el-dropdown-menu>
							<a href="https://github.com/lin-xin/vue-manage-system" target="_blank">
								<el-dropdown-item @click.native="adminInformation">项目仓库</el-dropdown-item>
							</a>
							<el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
						</el-dropdown-menu>
					</template>
				</el-dropdown>
			</div>
		</div>
	</div>
</template>
<script setup>
	import {
		reactive,
		toRefs,
		computed,
		onMounted
	} from "vue";
	import {
		useStore
	} from "vuex";
	import {
		useRouter
	} from "vue-router";
	const Store = useStore();
	const Router = useRouter();
  const avatar=sessionStorage.getItem("avatar");
	const userName = sessionStorage.getItem("username");
	const handleCommand = (e) => {
		if (e == "loginout") {
			sessionStorage.removeItem("authtoken");
			Router.push("/login");
		}
	};
</script>
<style scoped>
	.header {
		position: relative;
		box-sizing: border-box;
		width: 100%;
		height: 70px;
		font-size: 22px;
		background: #242f42;
		color: #fff;
	}

	.collapse-btn {
		float: left;
		padding: 0 21px;
		cursor: pointer;
		line-height: 70px;
	}

	.header .logo {
		float: left;
		width: 250px;
		line-height: 70px;
	}

	.header-right {
		float: right;
		padding-right: 50px;
	}

	.header-user-con {
		display: flex;
		height: 70px;
		align-items: center;
	}

	.btn-fullscreen {
		transform: rotate(45deg);
		margin-right: 5px;
		font-size: 24px;
	}

	.btn-bell,
	.btn-fullscreen {
		position: relative;
		width: 30px;
		height: 30px;
		text-align: center;
		border-radius: 15px;
		cursor: pointer;
	}

	.btn-bell-badge {
		position: absolute;
		right: 0;
		top: -2px;
		width: 8px;
		height: 8px;
		border-radius: 4px;
		background: #f56c6c;
		color: #fff;
	}

	.btn-bell .el-icon-bell {
		color: #fff;
	}

	.user-name {
		margin-left: 10px;
	}

	.user-avator {
		margin-left: 20px;
		transition: 0.5s;
	}

	.user-avator:hover {
		transform: rotate(360deg);
	}

	.user-avator img {
		display: block;
		width: 40px;
		height: 40px;
		border-radius: 50%;
	}

	.el-dropdown-link {
		color: #fff;
		cursor: pointer;
		margin-left: 5px;
	}

	.el-dropdown-menu__item {
		text-align: center;
	}
</style>
