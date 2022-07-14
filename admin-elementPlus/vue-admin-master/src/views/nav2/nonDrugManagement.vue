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
    <el-dialog title="上传表单" v-model="uploadServerVisibleDrug" :close-on-click-modal="false">
      <el-alert
          title="上传须知"
          type="warning"
          description="上传表单格式务必按照模板，一次仅限上传一个文件"
          show-icon>
      </el-alert>
      <!--最多上传1个文件，点击之后不立即上传-->
      <el-upload
          class="upload-demo"
          ref="upload"
          drag
          :limit="1"
          action="http://localhost:8000/hospital/nondrug/uploadToServer"
          :file-list="fileList"
          :on-exceed="handleExceed"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传xlsx文件，且文件大小不宜过大</div>
      </el-upload>
      <el-button type="success" plain @click="uploadToServer">确认提交</el-button>
    </el-dialog>
    <!--列表-->
    <el-table :data="nonDrug" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
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
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total"
                     style="float:right;">
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
      <el-form :model="addForm" label-width="80px" ref="addForm">
        <el-form-item label="药品名称" prop="name" :rules="[
                      { required: true, message: '姓名不能为空'}]">
          <el-input v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="药品封面" prop="cover">
          <el-upload
              action="http://localhost:8000/hospital/nondrug/avatar"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :limit="1"
          ><i class="el-icon-plus"></i></el-upload>
        </el-form-item>
        <el-form-item label="药品规格" prop="sname" :rules="[
                      { required: true, message: '药品规格不能为空'}]">
          <el-select v-model="addForm.sname" placeholder="请选择">
            <el-option
                v-for="item in Specification"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="药品数量" :rules="[
                      { required: true, message: '药品数量不能为空'}]" prop="number">
          <el-input type="number" v-model="addForm.number"></el-input>
        </el-form-item>
        <el-form-item label="药品单价" :rules="[
                      { required: true, message: '药品单价不能为空'}]" prop="price">
          <el-input type="number" v-model="addForm.price"></el-input>
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
//import NProgress from 'nprogress'
import {
  addDrug, analyseFileDrug,
  deleteByIdsDrug,
  DrugModifyEnable,
  exportDataDrug,
  findAllSpecification,
  getAllDrugByRequire,
  templateDownloadDrug,
  updateDrug
} from '../../api/api';

export default {
  data() {
    return {
      fileList: [],
      uploadServerVisibleDrug:false,
      filters: {
        name: '',
        spe: ''
      },
      Specification: [],
      nonDrug: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],//列表选中列

      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      editFormRules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ]
      },
      //编辑界面数据
      editForm: {},
      addFormVisible: false,//新增界面是否显示
      addLoading: false,
      //新增界面数据
      addForm: {
        id: '',
        name: '',
        cover: '',
        sname: '',
        number: 0,
        price: 0,
        version: 0
      }

    }
  },
  methods: {
    handleExceed(files, fileListDoctor) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileListDoctor.length} 个文件`);
    }, handlePreview(file) {
      console.log(file);
    }, beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
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
        specification: this.filters.spe
      };
      this.listLoading = true;
      //NProgress.start();
      getAllDrugByRequire(para).then((res) => {
        if (res.data.msgId == 'C200') {
          this.total = res.data.result.total;
          this.nonDrug = res.data.result.list;
          this.nonDrug.forEach((item, index, arr) => {
            if (item.enable == 0) {
              item.enable = true
            } else {
              item.enable = false
            }
          })
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
        id: '',
        name: '',
        cover: '',
        sname: '',
        number: 0,
        price: 0,
        version: 0
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
              if (res.data.msgId = 'C200') {
                this.editLoading = false;
                this.$message({
                  message: '修改成功',
                  type: 'success'
                });
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;
                this.getUsers();
              } else {
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
            alert(JSON.stringify(this.addForm))
            this.addLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.addForm);
            addDrug(para).then((res) => {
              if (res.data.msgId == 'C404') {
                this.addLoading = false;
                this.$notify.error({
                  title: '错误',
                  message: '请添加封面图片'
                });
              } else if (res.data.msgId == 'C200') {
                this.addLoading = false;
                this.$notify.success({
                  title: '成功',
                  message: '添加成功'
                });
                this.$refs['addForm'].resetFields();
                this.addFormVisible = false;
                this.getUsers();
              }
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
    getSpecification() {
      findAllSpecification().then((res) => {
        this.Specification = res.data
      })
    },
    changeSwing: function (text, row) {
      let params;
      if (text) {
        params = {id: row.id, enable: 0};
      } else {
        params = {id: row.id, enable: 1};
      }
      DrugModifyEnable(params).then((res) => {
        if (res.data.msgId == 'C200') {
          this.$notify.success({
            title: '成功',
            message: '修改成功'
          });
        } else {
          this.$notify.error({
            title: '失败',
            message: '修改失败'
          });
        }
      })
    },
    dataTemplateDownload() {
      templateDownloadDrug().then((res) => {
        const disposition = res.headers['content-disposition'];
        let fileName = disposition.match(/=(.*)$/)[1];
        let blob = new Blob([res.data])
        let downloadElement = document.createElement('a')
        let href = window.URL.createObjectURL(blob)
        downloadElement.href = href
        downloadElement.download = fileName
        document.body.appendChild(downloadElement)
        downloadElement.click()
        document.body.removeChild(downloadElement)
        window.URL.revokeObjectURL(href)
      })
    }, exportData() {
      exportDataDrug().then((res) => {
        const disposition = res.headers['content-disposition'];
        let fileName = disposition.match(/=(.*)$/)[1];
        let blob = new Blob([res.data])
        let downloadElement = document.createElement('a')
        let href = window.URL.createObjectURL(blob)
        downloadElement.href = href
        downloadElement.download = fileName
        document.body.appendChild(downloadElement)
        downloadElement.click()
        document.body.removeChild(downloadElement)
        window.URL.revokeObjectURL(href)
      })
    },
    uploadServer: function () {
      this.uploadServerVisibleDrug = true;
    },
    uploadToServer() {
      analyseFileDrug().then((res) => {
        this.uploadServerVisibleUser = false;
        if(res.data.msgId=='C500'){
          this.$notify.error({
            title: '错误',
            message: '数据异常，拒绝添加'
          });
        }else if(res.data.msgId=='C501'){
          this.$notify.error({
            title: '错误',
            message: '可能的原因是：数据库存在相同数据，拒绝添加'
          });
        }else if(res.data.msgId=='C200'){
          this.$notify.success({
            title: '成功',
            message: '添加成功'
          });
          this.getUsers()
        }else if(res.data.msgId=='C502'){
          this.$notify.error({
            title: '错误',
            message: '数据上传失败'
          });
        }
      })
    }
  },
  mounted() {
    this.getUsers();
    this.getSpecification();
  }
}

</script>

<style scoped>

</style>