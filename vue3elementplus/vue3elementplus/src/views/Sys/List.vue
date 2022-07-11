<template>
	<el-tabs type="border-card">
		<el-tab-pane label="学生管理">
			<el-form :inline="true" :model="searchParam" class="fl" >
				<el-form-item label="姓名">
					<el-input v-model="searchParam.stuName" placeholder="姓名"></el-input>
				</el-form-item>
				<el-form-item label="学校">
					<el-select v-model="searchParam.schoolCode" placeholder="活动区域">
						<el-option label="---" value=""></el-option>
						<el-option v-for="item in scllist" :label="item.schoolname" :value="item.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="性别">
					<el-select v-model="searchParam.sexCode" placeholder="活动区域">
						<el-option label="---" value=""></el-option>
						<el-option v-for="item in sexlist" :label="item.name" :value="item.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="onSubmit(1)">查询</el-button>
				</el-form-item>
			</el-form>
			<div class="fl" style="margin-top: -10px;margin-bottom: 10px;">
				<el-button size="medium" type="success" @click="addFormVisible = true">增加</el-button>
			</div>
			<el-table :data="result.list" border v-loading="loading"  @selection-change="selsChange">
        <el-table-column type="selection" width="55"></el-table-column>
				<el-table-column type="index" label="序号" width="150"></el-table-column>
				<el-table-column prop="stucode" label="学号" width="150"></el-table-column>
				<el-table-column prop="stuname" label="姓名" width="150"></el-table-column>
				<el-table-column prop="schoolname" label="学校" width="150"></el-table-column>
				<el-table-column prop="stuage" label="年龄" width="150"></el-table-column>
				<el-table-column prop="stubrithday" label="生日" width="150"></el-table-column>
				<el-table-column prop="name" label="性别" width="100"></el-table-column>
				<el-table-column label="操作" width="200" align="center">
					<template #default="scope">
						<el-row style="margin: auto">
              <el-button size="small" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
							<el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
							</el-button>
						</el-row>
					</template>
				</el-table-column>
			</el-table>
      <div class="toolbar">
        <el-button size="danger"  @click="batchRemove" type="success" :disabled="this.sels.length===0" style="float: left">批量删除</el-button>
        <el-button style="color:rgba(248,235,235,0.5);"> </el-button>
				<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange"
					:page-size="searchParam.onePageCount" :total="result.total" style="float:right;">
				</el-pagination>
      </div>
		</el-tab-pane>
	</el-tabs>
	<el-dialog v-model="addFormVisible" title="增加" >
    <div>
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="姓名" prop="stuname">
          <el-input v-model="formData.stuname" placeholder="请输入姓名" :maxlength="10" show-word-limit clearable
                    :style="{width: '100%'}"></el-input>
        </el-form-item>
        <el-form-item label="所在学校" prop="stuschid">
          <el-select v-model="formData.stuschid" placeholder="请选择所在学校" clearable :style="{width: '100%'}">
            <el-option v-for="(item, index) in stuschidOptions" :key="index" :label="item.label"
                       :value="item.value" :disabled="item.disabled"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" prop="stubrithdaystr">
          <el-date-picker
              v-model="formData.stubrithdaystr"
              type="date"
              placeholder="请选择出生日期"
              :shortcuts="shortcuts"
          />
        </el-form-item>
        <el-form-item label="请选择性别" prop="codenameid">
          <el-select v-model="formData.codenameid" placeholder="请选择性别" clearable :style="{width: '100%'}">
            <el-option v-for="(item, index) in codenameidOptions" :key="index" :label="item.label"
                       :value="item.value" :disabled="item.disabled"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item size="large">
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
	</el-dialog>
</template>
<script>
	//import axios from "axios"
  import {ElMessage, ElMessageBox, ElNotification} from 'element-plus'
  import { CircleCheckFilled } from '@element-plus/icons-vue'
  import {markRaw, ref} from "vue";

  export default {
    components: {},
    props: [],
		data() {
			return {
        shortcuts :[
          {
            text: 'Today',
            value: new Date(),
          },
          {
            text: 'Yesterday',
            value: () => {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24)
              return date
            },
          },
          {
            text: 'A week ago',
            value: () => {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
              return date
            },
          },
        ],
        formData: {
          id:'',
          stucode:'',
          stuage:'',
          stuname: undefined,
          stuschid: "",
          stubrithdaystr: null,
          codenameid: '',
        },
        rules: {
          stuname: [{
            required: true,
            message: '请输入姓名',
            trigger: 'blur'
          }],
          stuschid: [{
            required: true,
            message: '请选择所在学校',
            trigger: 'change'
          }],
          stubrithdaystr: [{
            required: true,
            message: '请选择出生日期',
            trigger: 'change'
          }],
          codenameid: [{
            required: true,
            message: '请至少选择一个请选择性别',
            trigger: 'change'
          }],
        },
        stuschidOptions: [{
          "label": "东北大学",
          "value": "C001"
        }, {
          "label": "大连理工",
          "value": "C002"
        }, {
          "label": "中国石油",
          "value": "C003"
        }, {
          "label": "东北林业",
          "value": "C004"
        }],
        codenameidOptions: [{
          "label": "男",
          "value": "B0002"
        }, {
          "label": "女",
          "value": "B0003"
        }],
        loading:false,
        sels:[],
				searchParam: {
					stuName: "",
					schoolCode: "",
					sexCode: "",
					currentpage: 1,
					onePageCount: 5
				},
				sexlist: [],
				scllist: [],
				result: [],
				addFormVisible: false,
				addform: {
					name: '',
					zone: ''
				}
			}
		},
		methods: {
      submitForm() {
        this.$refs['elForm'].validate(valid => {
          if (!valid) return
          ElMessageBox.confirm(
              '确定添加吗?',
              '警告',
              {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                icon: markRaw(CircleCheckFilled),
              }
          ).then(() => {
                this.$axios.post("/api/bstudent/add",
                    {
                      "id":this.formData.id,
                      "stuage":this.formData.stuage,
                      "stucode":this.formData.stucode,
                      "stuname":this.formData.stuname,
                      "stuschid":this.formData.stuschid,
                      "stubrithdaystr":this.formData.stubrithdaystr,
                      "codenameid":this.formData.codenameid
                    }, {
                      headers: {
                        'Authorization': sessionStorage.getItem('authtoken')
                      }
                    }).then((res)=>{
                  if(res.data.statusCode=='C200'){
                    ElNotification({
                      title: '成功！',
                      message: '添加成功',
                      type: 'success',
                    })
                    this.addFormVisible=false;
                    this.resetForm()
                    this.onSubmit(1)
                  }else {
                    ElNotification({
                      title: '失败！',
                      message: '添加失败',
                      type: 'error',
                    })
                  }
                })
              })
        })
      },
      resetForm() {
        this.$refs['elForm'].resetFields()
      },
      selsChange: function (sels) {
        this.sels = sels;
      },
			onSubmit(currentpage) {
/*				const loading = this.$loading({
					lock: true,
					text: 'Loading',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});*/
        this.loading=true;
				this.searchParam.currentpage = currentpage;
				//axios.post("http://localhost:8000/mystu/api/bstudent/getstudentpagerlist", {
				this.$axios.post("/api/bstudent/getstudentpagerlist", {
						"stuName": this.searchParam.stuName,
						"schId": this.searchParam.schoolCode,
						"sexCode": this.searchParam.sexCode,
						"pageNum": this.searchParam.currentpage,
						"pageSize": this.searchParam.onePageCount
					}, {
						headers: {
							'Authorization': sessionStorage.getItem('authtoken')
						}
					})
					.then(res => {
						this.result = res.data.result;
            this.loading=false;
						// loading.close();
					})
					.catch(error => {
						console.log(error)
            this.loading=false
						// loading.close();
					});
			},
      batchRemove(){
        ElMessageBox.confirm(
            '确定删除吗?',
            '警告',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
            }
        )
            .then(() => {
              var ids = this.sels.map(item => item.id);
              this.$axios.post("/api/bstudent/delete",
                  {
                    "ids": ids
                  }, {
                headers: {
                  'Authorization': sessionStorage.getItem('authtoken')
                }
              }).then((res)=>{
                if(res.data.statusCode=='C200'){
                  ElNotification({
                    title: '成功！',
                    message: '删除成功',
                    type: 'success',
                  })
                  this.onSubmit(1)
                }else {
                  ElNotification({
                    title: '失败！',
                    message: '删除失败',
                    type: 'error',
                  })
                }
              })
            })
      },
			handleDelete(index, row) {
        ElMessageBox.confirm(
            '确定删除吗?',
            '警告',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
            }
        )
            .then(() => {
              this.$axios.post("/api/bstudent/delete",
                  {
                    "ids": [row.id]
                  }, {
                    headers: {
                      'Authorization': sessionStorage.getItem('authtoken')
                    }
                  }).then((res)=>{
                if(res.data.statusCode=='C200'){
                  ElNotification({
                    title: '成功！',
                    message: '删除成功',
                    type: 'success',
                  })
                  this.onSubmit(1)
                }else {
                  ElNotification({
                    title: '失败！',
                    message: '删除失败',
                    type: 'error',
                  })
                }
              })
            })
      },
      handleCurrentChange(val) {
        this.onSubmit(val)
      },
      handleEdit(){

      }
		},
		mounted() {
			//axios.post("http://localhost:8000/mystu/api/bcodename/getcodelist", {
			this.$axios.post("/api/bcodename/getcodelist", {
					"codeId": "001"
				})
				.then(res => {
					this.sexlist = res.data.result;
				})
				.catch(error => {
					console.log(error);
				});

			//axios.post("http://localhost:8000/mystu/api/bschool/getallscllist")
			this.$axios.post("/api/bschool/getallscllist")
				.then(res => {
					this.scllist = res.data.result;
				})
				.catch(error => {
					console.log(error);
				});
		},created() {
      this.onSubmit(1);
    }
  }
</script>

<style lang="scss">
	.fr {
		float: right;
	}

	.fl {
		text-align: left;
	}
  .toolbar {
    background: #f2f2f2;
    padding: 10px;
    border:1px solid #dfe6ec;
    margin: 10px 0px;
  }
</style>
