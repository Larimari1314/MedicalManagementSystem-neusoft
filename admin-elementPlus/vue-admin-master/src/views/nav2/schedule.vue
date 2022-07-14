<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="挂号名称"></el-input>
        </el-form-item>
        <el-form-item label="科室类别">
          <el-select v-model="filters.department" placeholder="请选择">
            <el-option
                v-for="item in departments"
                :key="item.d_id"
                :label="item.d_name"
                :value="item.d_id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="挂号级别">
          <el-select v-model="filters.registered" placeholder="请选择">
            <el-option
                v-for="item in registeres"
                :key="item.id"
                :label="item.valu"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-form-item>
          <el-button type="success" v-on:click="SetShiftTime">设置排班时间</el-button>
          <el-button type="success" v-on:click="getUsers">统一排班规则</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="100">
      </el-table-column>
      <el-table-column type="index" width="80">
      </el-table-column>
      <el-table-column prop="id" label="医生id" width="150" sortable>
      </el-table-column>
      <el-table-column prop="name" label="医生姓名" width="150" >
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="100" sortable>
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="100" >
      </el-table-column>
      <el-table-column prop="dname" label="科室" width="180" >
      </el-table-column>
      <el-table-column prop="sname" label="排班" width="180" >
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px"  ref="editForm">
        <el-form-item label="医生姓名" prop="name"  :rules="[
                      { required: true, message: '姓名不得为空'}]">
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="班次"  prop="schedule" :rules="[
                      { required: true, message: '请选择班次'}]">
          <el-select v-model="editForm.schedule" placeholder="请选择">
            <el-option
                v-for="item in schedules"
                :key="item.s_id"
                :label="item.s_name"
                :value="item.s_id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在科室" prop="department"  :rules="[
                      { required: true, message: '请选择科室'}]">
          <el-select v-model="editForm.department" placeholder="请选择">
            <el-option
                v-for="item in departments"
                :key="item.d_id"
                :label="item.d_name"
                :value="item.d_id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="医生年龄" prop="age"  :rules="[
                      { required: true, message: '医生年龄不得为空'}]">
          <el-input type="number" placeholder="年龄" v-model="editForm.age"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="设置排班时间" v-model="shiftTime" :close-on-click-modal="false">
      <el-form :model="scheduleList" label-width="80px"  ref="scheduleList">
        <el-form-item label="班次名称" prop="name"  :rules="[
                      { required: true, message: '姓名不得为空'}]">
          <el-input v-model="scheduleList.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="班次"  prop="schedule" :rules="[
                      { required: true, message: '请选择班次'}]">

        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="shiftTime = false">取消</el-button>
        <el-button type="primary" @click.native="shiftTimeSubmit" :loading="editLoading">提交</el-button>
      </div>

    </el-dialog>
  </section>
</template>

<script>
import util from '../../common/js/util'
//import NProgress from 'nprogress'
import {
  editUser,
  findDepartment,
  findByRegistered, findAllDoctor, deleteByIdsDoctor, findScheduleList, addDoctor, getAllScheduleList
} from '../../api/api';

export default {
  data() {
    return {
      scheduleList:[],
      schedules:'',
      departments:'',
      registeres:'',
      filters: {
        name: '',
        department:'',
        registered:''
      },
      users: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],//列表选中列

      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      //编辑界面数据
      editForm: {

      },

      shiftTime: false,//新增界面是否显示
      addLoading: false,
      //新增界面数据
    }
  },
  methods: {
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    //获取用户列表
    getUsers() {
      let para = {
        page: this.page,
        name: this.filters.name,
        department:this.filters.department,
        registered:this.filters.registered
      };
      this.listLoading = true;
      //NProgress.start();
      findAllDoctor(para).then((res) => {
        if(res.data.msgId=='C200'){
          this.listLoading = false;
          this.users=res.data.result.list
          this.total=res.data.result.total
        }

      });
    },
    //删除
    handleDel: function (index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        //NProgress.start();
        let para = {'ids': [row.id]};
        deleteByIdsDoctor(para).then((res) => {
          if (res.data.msgId == 'C200') {
            this.listLoading = false;
            this.$notify.success({
              title: '成功',
              message: '删除成功',
              offset: 100
            });
            this.getUsers();
          } else {
            this.$notify.error({
              title: '错误',
              message: '删除失败'
            });
          }
        });
      }).catch(() => {

      });
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
    },
    //显示新增界面
    handleAdd: function () {
      this.addFormVisible = true;
      this.addForm = {
        name: '',
        sex: -1,
        age: 0,
        birth: '',
        addr: ''
      };
    },
    //编辑
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.editForm);
            addDoctor(para).then((res)=>{
              if (res.data.msgId == 'C200') {
                this.$notify.success({
                  title: '成功',
                  message: '修改成功'
                });
                this.editLoading = false;
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;
                this.getUsers();
              } else {
                this.$notify.error({
                  title: '失败',
                  message: '修改失败'
                });
              }
            })
          });
        }
      });
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
    //批量删除
    batchRemove: function () {
      var ids = this.sels.map(item => item.id);
      let param = {"ids": ids}
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        deleteByIdsDoctor(param).then((res) => {
          if (res.data.msgId == 'C200') {
            this.listLoading = false;
            this.$notify.success({
              title: '成功',
              message: '删除成功'
            });
          } else {
            this.$notify.error({
              title: '失败',
              message: '删除失败'
            });
          }
          this.getUsers();
        });
      }).catch(() => {

      });
    },
    Obtain() {
      findDepartment().then((res)=>{
        this.departments=res.data
      });
      findByRegistered().then((res)=>{
        this.registeres=res.data
      });
      findScheduleList().then((res)=>{
        this.schedules=res.data
      })
    },
    SetShiftTime(){
      this.shiftTime=true;
      getAllScheduleList().then((res)=>{
        this.scheduleList=res.data
      })
    }
  },
  mounted() {
    this.getUsers();
    this.Obtain();
  }
}

</script>

<style scoped>

</style>