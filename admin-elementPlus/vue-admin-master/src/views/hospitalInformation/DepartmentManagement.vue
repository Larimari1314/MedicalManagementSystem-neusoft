<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="科室名称"></el-input>
        </el-form-item>
        <el-form-item label="科室类别">
          <el-select v-model="category" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="department" highlight-current-row v-loading="listLoading"
              style="width: 100%;">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="id" label="科室编码" width="120" sortable>
      </el-table-column>
      <el-table-column prop="avatar" label="科室图片" width="200" sortable>
        <template scope="scope">
          <img :src="department[scope.$index].avatar" style="border-radius:10%; " width="150" height="100"
               alt="科室封面">
        </template>
      </el-table-column>
      <el-table-column prop="introduce" label="科室介绍" width="350" sortable>
      </el-table-column>
      <el-table-column prop="classification" label="科室分类" width="150" sortable>
      </el-table-column>
      <el-table-column prop="name" label="科室名称" width="200" sortable>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)" type="info">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="5" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="科室编码" prop="name">
          <el-input v-model="editForm.id" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="科室封面" prop="avatar">
          <template scope="scope">
            <img :src="editForm.avatar" style="border-radius:10%; " width="100" height="100"
                 alt="科室封面">
            <el-upload
                action="http://localhost:8000/hospital/department/avatar"
                list-type="picture-card"
                ref="edit"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove">
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog v-model="dialogVisible" size="tiny">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
          </template>
        </el-form-item>
        <el-form-item label="科室类别">
          <el-select v-model="editForm.classification" placeholder="请选择" disabled>
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="科室名称" prop="name" :rules="[
                      { required: true, message: '科室名称不得为空'}]">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="科室介绍" prop="introduce" :rules="[
                      { required: true, message: '科室介绍不得为空'}]">
          <el-input type="textarea" v-model="editForm.introduce"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false;">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="80px"  ref="addForm">
        <el-form-item label="科室编码" prop="id" :rules="[
                      { required: true, message: '科室编码不能为空'},
                      {pattern: /^[0-9]{3}$/,message: '长度必须为三位，且为数字'}
                      ]">
          <el-input v-model="addForm.id" auto-complete="off" >
            <template slot="prepend">DE</template>
          </el-input>
        </el-form-item>
        <el-form-item label="科室分类" prop="classification" :rules="[
                      { required: true, message: '科室分类不能为空'}]">
          <el-select v-model="addForm.classification" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="科室图片">
          <template scope="scope">
            <el-upload
                action="http://localhost:8000/hospital/department/avatar"
                ref="upload"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :limit="1"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog v-model="dialogVisible" size="tiny">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
          </template>
        </el-form-item>
        <el-form-item label="科室名称" prop="name" :rules="[
                      { required: true, message: '科室名称不得为空'}]">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="科室介绍" prop="introduce" :rules="[
                      { required: true, message: '科室介绍不得为空'}]">
          <el-input type="textarea" v-model="addForm.introduce"  ></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import util from '../../common/js/util'
//import NProgress from 'nprogress'
import {
  addDepartment,
  addUser,
  batchRemoveUser,
  editUser,
  getDepartClassList,
  getDepartment,
  removeUser, saveDepartment
} from '../../api/api';

export default {
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      category: '',
      options: [],
      filters: {
        name: ''
      },
      department: [],
      total: '',
      page: 1,
      listLoading: false,
      sels: [],//列表选中列

      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      //编辑界面数据
      editForm: {
      },

      addFormVisible: false,//新增界面是否显示
      addLoading: false,
      //新增界面数据
      addForm: {
        id:'',
        name: '',
        avatar:'',
        introduce:'',
        classification:''
      }

    }
  },
  methods: {
    handleRemove(file, fileList) {
      let index = fileList.findIndex( fileItem => {
        return fileItem.uid === file.uid
      })
      fileList.splice(index, 1)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    getDepartClassList: function () {
      getDepartClassList().then((res) => {
        this.options = res.data.result
      })
    },
    //获取用户列表
    getUsers() {
      let para = {
        page: this.page,
        departmentClassification: this.category,
        name: this.filters.name
      };
      this.listLoading = true;
      getDepartment(para).then((res) => {
        if (res.data.msgId == 'C200') {
          this.department = res.data.result.list;
          this.total = res.data.result.total
          this.listLoading = false;
        }
      })
      //NProgress.start();
      /*	getUserListPage(para).then((res) => {
          this.total = res.data.total;
          this.users = res.data.users;
          this.listLoading = false;
          //NProgress.done();
        });*/
    },
    //删除
    handleDel: function (index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        //NProgress.start();
        let para = {id: row.id};
        removeUser(para).then((res) => {
          this.listLoading = false;
          //NProgress.done();
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.getUsers();
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
        id:'',
        name: '',
        avatar:'',
        introduce:'',
        classification:''
      };
    },
    //编辑
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            addDepartment(this.editForm).then((res)=>{
              this.$refs.edit.clearFiles()
              if(res.data.msgId=='C200'){
                this.editFormVisible = false;
                this.editLoading = false;
                this.dialogImageUrl='';
                this.$notify.success({
                  title: '成功',
                  message: '修改成功',
                  offset: 100
                });
                this.getUsers()
              }else {
                this.$refs.edit.clearFiles()
                this.editFormVisible = false;
                this.editLoading = false;
                this.dialogImageUrl='';
                this.$notify.error({
                  title: '错误',
                  message: '修改失败'
                });
              }
            })
          });
        }
      });
    },
    //新增
    addSubmit: function () {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addForm.id='DE'+this.addForm.id
            this.addLoading = true;
            //NProgress.start();
            // alert(JSON.stringify(this.addForm))
            saveDepartment(this.addForm).then((res)=>{
              if(res.data.msgId=='C200'){
                this.$refs.upload.clearFiles()
                this.addFormVisible = false;
                this.addLoading = false;
                this.dialogImageUrl='';
                this.$notify.success({
                  title: '成功',
                  message: '添加成功',
                  offset: 100
                });
                this.getUsers()
              }else if(res.data.msgId=='C405'){
                this.addForm.id=''
                this.$notify.error({
                  title: '错误',
                  message: '科室编码重复'
                });
              }
              else {
                this.$refs.upload.clearFiles()
                this.addFormVisible = false;
                this.addLoading = false;
                this.dialogImageUrl='';
                this.$notify.error({
                  title: '错误',
                  message: '添加失败'
                });
              }
            })
          });
        }
      });
    }
  },
  mounted() {
    this.getUsers();
    this.getDepartClassList();
  }
}

</script>

<style scoped>

</style>