<template>
	<div class="login-wrap">
		<div class="ms-login">
			<div class="ms-title">后台管理系统</div>
			<el-form :model="loginForm" :rules="loginRules" ref="loginForm" label-width="0px" class="ms-content">
				<el-form-item prop="username">
					<el-input v-model="loginForm.username" placeholder="username">
						<template #prepend>
							<el-button icon="el-icon-user"></el-button>
						</template>
					</el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input type="password" placeholder="password" v-model="loginForm.password"
						@keyup.enter="submitForm()">
						<template #prepend>
							<el-button icon="el-icon-lock"></el-button>
						</template>
					</el-input>
				</el-form-item>
				<div class="login-btn">
					<el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
				</div>
				<p class="login-tips"></p>
			</el-form>
		</div>
	</div>
</template>
<script>
	import { ElMessage } from "element-plus"
	export default {
		data() {
			const validateUsername = (rule, value, callback) => {
				if (value.length < 5) {
					callback(new Error('请输入正确的用户名'))
				} else {
					callback()
				}
			}
			const validatePass = (rule, value, callback) => {
				if (value.length < 5) {
					callback(new Error('密码不能小于5位'))
				} else {
					callback()
				}
			}
			return {
				loginForm: {
					username: '',
					password: ''
				},
				loginRules: {
					username: [{
						required: true,
						trigger: 'blur',
						validator: validateUsername
					}],
					password: [{
						required: true,
						trigger: 'blur',
						validator: validatePass
					}]
				},
			}
		},
		methods: {
			submitForm(loginForm) {
				this.$refs[loginForm].validate((valid) => {
					if (valid) {
						this.$axios.post("/api/authentication/login", {
								"userName": this.loginForm.username,
								"password": this.loginForm.password
							})
							.then(res => {
								console.log(res.data);
								if (res.data.msgId == "A001") {
									ElMessage.error({
										message: "您输入的用户名不存在",
										type: "success",
									})
								} else if (res.data.msgId == "A002") {
									ElMessage.error({
										message: "您输入的密码不正确",
										type: "success",
									})
								} else {
									// 登录成功
									sessionStorage.setItem("authtoken", res.data.result.authtoken);
									sessionStorage.setItem("username", res.data.result.username);
                  //存入用户头像地址
                  sessionStorage.setItem("avatar", res.data.result.avatar);
                  ElMessage.success({
										message: "登录成功",
										type: "success",
									})
									this.$router.push({
										path: '/home'
									})
								}
							})
							.catch(error => {
								console.log(error);
							});
					} else {
						return false;
					}
				});
			}
		}

	}
</script>
<style scoped>
	.login-wrap {
		position: absolute;
		top: 0;
		right: 0;
		left: 0;
		bottom: 0;
		background-image: url(../assets/background.jpg);
		background-size: 100%;
	}

	.ms-title {
		width: 100%;
		line-height: 50px;
		text-align: center;
		font-size: 20px;
		color: #fff;
		border-bottom: 1px solid #ddd;
	}

	.ms-login {
		position: absolute;
		left: 50%;
		top: 50%;
		width: 350px;
		margin: -190px 0 0 -175px;
		border-radius: 5px;
		background: rgba(255, 238, 255, 0.3);
		box-shadow: 0 0px 30px 0 #eee;
		overflow: hidden;
	}

	.ms-content {
		padding: 30px 30px;
	}

	.login-btn {
		text-align: center;
	}

	.login-btn button {
		width: 100%;
	}

	.login-tips {
		font-size: 12px;
		line-height: 30px;
		text-align: left;
		color: #fff;
	}
</style>
