<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="药品规格">
          <el-select v-model="filters.spe" placeholder="请选择">
            <el-option
                v-for="item in Specification"
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
        <el-form-item style="float: right">
          <el-button type="success" size="small" @click="uploadServer">导入数据</el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" size="small" @click="dataTemplateDownload">数据模板下载</el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" size="small" @click="exportData">导出全部数据</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="nonDrug" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="cover" width="168" label="药品封面" scope="scope">
        <template scope="scope">
          <img :src="nonDrug[scope.$index].cover" style="border-radius:10%; " width="100" height="100"
               alt="药品封面">
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" width="120" sortable>
      </el-table-column>
      <el-table-column prop="sname" label="药品规格" width="120">
      </el-table-column>
      <el-table-column prop="number" label="药品数量" width="120" sortable>
      </el-table-column>
      <el-table-column prop="price" label="药品单价" min-width="100" sortable>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template scope="scope">
          <el-tooltip content="关闭后用户、医生无法查看药品" placement="top">
            <el-switch
                v-model="nonDrug[scope.$index].enable"
                on-text=""
                off-text=""
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="100"
                inactive-value="0"
                @change=changeSwing($event,scope.row)
            >
<!--              @change=changeSwing($event,scope.row)-->
            </el-switch>
          </el-tooltip>&nbsp;&nbsp;&nbsp;
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
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
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="药品编号" prop="id">
          <el-input v-model="editForm.id" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="药品封面" prop="avatar">
          <img :src="editForm.cover" style="border-radius:50%; " width="100" height="100"
               alt="药品封面">
          <el-upload
              action="http://localhost:8000/hospital/nondrug/avatar"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :limit="1"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
      
        </el-form-item>
        <el-form-item label="药品名称" prop="name">
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="药品规格" prop="sname">
          <el-input v-model="editForm.sname" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="现存数量">
          <el-input v-model="editForm.number" auto-complete="off" type="number"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="number">
          <el-input v-model="editForm.price" auto-complete="off" type="number"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="addForm.sex">
            <el-radio class="radio" :label="1">男</el-radio>
            <el-radio class="radio" :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker type="date" placeholder="选择日期" v-model="addForm.birth"></el-date-picker>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input type="textarea" v-model="addForm.addr"></el-input>
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
  getUserListPage,
  removeUser,
  batchRemoveUser,
  editUser,
  addUser,
  findAllSpecification,
  getAllDrugByRequire, DrugModifyEnable, deleteByIdsDrug, updateDrug
} from '../../api/api';

export default {
  data() {
    return {
      filters: {
        name: '',
        spe:''
      },
      Specification:[],
      nonDrug: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],//列表选中列

      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      editFormRules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ]
      },
      //编辑界面数据
      editForm: {
        id: 0,
        name: '',
        sex: -1,
        age: 0,
        birth: '',
        addr: ''
      },

      addFormVisible: false,//新增界面是否显示
      addLoading: false,
      addFormRules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ]
      },
      //新增界面数据
      addForm: {
        name: '',
        sex: -1,
        age: 0,
        birth: '',
        addr: ''
      }

    }
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    //获取用户列表
    getUsers() {
      let para = {
        page: this.page,
        name: this.filters.name,
        specification:this.filters.spe
      };
      this.listLoading = true;
      //NProgress.start();
      getAllDrugByRequire(para).then((res)=>{
        if(res.data.msgId=='C200'){
          this.total = res.data.result.total;
          this.nonDrug = res.data.result.list;
          this.nonDrug.forEach((item,index, arr) => {
            if (item.enable == 0) {
              item.enable = true
            } else {
              item.enable = false
            }})
          this.listLoading = false;
        }

      })
/*      getUserListPage(para).then((res) => {
        this.total = res.data.total;
        this.nonDrug = res.data.nonDrug;
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
        let para = {'ids': [row.id]};
        deleteByIdsDrug(para).then((res) => {
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
            let para = Object.assign({}, this.editForm);
            updateDrug(para).then((res) => {
              if(res.data.msgId='C200'){
                this.editLoading = false;
                this.$message({
                  message: '修改成功',
                  type: 'success'
                });
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;
                this.getUsers();
              }else {
                this.$message({
                  message: '修改失败',
                  type: 'error'
                });
              }
            });
          });
        }
      });
    },
    //新增
    addSubmit: function () {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.addForm);
            para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
            addUser(para).then((res) => {
              this.addLoading = false;
              //NProgress.done();
              this.$message({
                message: '提交成功',
                type: 'success'
              });
              this.$refs['addForm'].resetFields();
              this.addFormVisible = false;
              this.getUsers();
            });
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
        deleteByIdsDrug(param).then((res) => {
          if(res.data.msgId=='C200'){
            this.listLoading = false;
            this.$notify.success({
              title: '成功',
              message: '删除成功'
            });
          }else{
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
    getSpecification(){
      findAllSpecification().then((res)=>{
        this.Specification=res.data
      })
    },
    changeSwing:function (text,row) {
      let params;
      if(text){
        params = {id: row.id,enable:0};
      }else {
        params = {id: row.id,enable:1};
      }
      DrugModifyEnable(params).then((res)=>{
        if(res.data.msgId=='C200'){
          this.$notify.success({
            title: '成功',
            message: '修改成功'
          });
        }else {
          this.$notify.error({
            title: '失败',
            message: '修改失败'
          });
        }
      })
    },
  },
  mounted() {
    this.getUsers();
    this.getSpecification();
  }
}

</script>

<style scoped>

</style>