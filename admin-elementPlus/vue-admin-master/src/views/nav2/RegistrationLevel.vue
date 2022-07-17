<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="挂号名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getRegistered">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="registereds" highlight-current-row v-loading="listLoading"  style="width: 100%;">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="id" label="挂号级别id" width="280" >
      </el-table-column>
      <el-table-column prop="name" label="挂号级别" width="300"  >
      </el-table-column>
      <el-table-column prop="price" label="挂号费用" width="300" sortable>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" @click="handleLook(scope.$index, scope.row)" type="info">查看所属医师</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px"  ref="editForm">
        <el-form-item label="挂号名称" prop="name">
          <el-input v-model="editForm.name" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="挂号级别id">
          <el-input v-model="editForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="挂号费用">
          <el-input placeholder="选择日期" v-model="editForm.price" type="number"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>
    <el-dialog title="查看" v-model="lookAllVisible" :close-on-click-modal="false">
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="doctor">
          <el-form-item>
            <el-input v-model="doctor.name" placeholder="医生名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="getDoctorList">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="doctorList" highlight-current-row v-loading="listLoading"  style="width: 100%;">
        <el-table-column prop="name" label="医生姓名" width="150" >
        </el-table-column>
        <el-table-column prop="sex" label="医生性别" width="120"  >
        </el-table-column>
        <el-table-column prop="age" label="医生年龄" width="120" sortable>
        </el-table-column>
        <el-table-column prop="sname" label="医生当前班次" width="150" >
        </el-table-column>
        <el-table-column prop="dname" label="医生所在科室" width="170" >
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="prev, pager, next" @current-change="handleCurrentChangeDoctor" :page-size="10" :total="doctorTotal" style="float:right;">
        </el-pagination>
      </el-col>
    </el-dialog>
  </section>
</template>

<script>
import {
  findDoctorByRegistration,
  findRegistered,
  updateRegisteredPrice
} from '../../api/api';

export default {
  data() {
    return {
      rowId:'',
      doctorTotal:0,
      lookAllVisible:false,
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }],
      doctor:{
        name:''
      },
      filters: {
        name: ''
      },
      doctorList:[],
      registereds: [],
      total: 0,
      page: 1,
      listLoading: false,
      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      //编辑界面数据
      editForm: {
        id: 0,
        name: '',
        price: '',
        version:-1
      },
    }
  },
  methods: {
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    handleCurrentChangeDoctor(val) {
      this.page = val;
      this.getDoctorList();
    },
    //获取用户列表
    getRegistered() {
      let para = {
        page: this.page,
        name: this.filters.name
      };
      findRegistered(para).then((res) => {
        if(res.data.msgId=='C200'){
          this.registereds=res.data.result.list
          this.listLoading = false;
        }
      });
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
    },
    //编辑
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.editForm);
            updateRegisteredPrice(para).then((res)=>{
              if(res.data.msgId=='C200'){
                this.editLoading = false;
                this.$notify.success({
                  title: '成功',
                  message: '修改成功'
                });
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;
                this.getRegistered();
              }else {
                this.editLoading = false;
                this.$notify.error({
                  title: '错误',
                  message: '请尝试刷新界面再进行修改'
                });
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;
              }
            })
          });
        }
      });
    },
    handleLook: function (index, row) {
      this.rowId=row.id
      this.lookAllVisible=true;
      this.getDoctorList()
    },
    getDoctorList(){
      let param={
        "regisId":this.rowId,
        "name":this.doctor.name,
        "page":this.page
      }
      findDoctorByRegistration(param).then((res)=>{
        if(res.data.msgId='C200'){
          this.doctorList=res.data.result.list
          this.doctorTotal=res.data.result.total
        }
      })
    }
  },
  mounted() {
    this.getRegistered();
  }
}

</script>

<style scoped>

</style>