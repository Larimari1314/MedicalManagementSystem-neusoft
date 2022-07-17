<template>
  <section >
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.id" placeholder="处方单号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-autocomplete
              class="inline-input"
              v-model="filters.doctorname"
              :fetch-suggestions="querySearch"
              placeholder="医生姓名"
              :trigger-on-focus="false"
              @select="handleSelect"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item>
          <el-autocomplete
              class="inline-input"
              v-model="filters.doctorname"
              :fetch-suggestions="querySearch"
              placeholder="患者姓名"
              :trigger-on-focus="false"
              @select="handleSelect"
          ></el-autocomplete>
        </el-form-item>
        <el-form-item label="当前状态">
          <el-select v-model="filters.state" placeholder="请选择">
            <el-option
                v-for="item in states"
                :key="item.coding"
                :label="item.value"
                :value="item.coding">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getPatientMedic">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="department" highlight-current-row v-loading="listLoading"
              style="width: 100%;">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="id" label="订单id" width="180" sortable>
      </el-table-column>
      <el-table-column prop="username" label="患者姓名" width="120">
      </el-table-column>
      <el-table-column prop="dname" label="医生名称" width="170">
      </el-table-column>
      <el-table-column prop="price" label="价格" width="120" sortable>
      </el-table-column>
      <el-table-column prop="state" label="当前状态" width="100">
        <template scope="scope">
          <el-tag v-if="department[scope.$index].state=='G008'" type="warning">未发放</el-tag>
          <el-tag v-if="department[scope.$index].state=='G009'" type="success">已发放</el-tag>
          <el-tag v-if="department[scope.$index].state=='G010'" type="danger">已退费</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="times" label="最后操作时间" width="180" sortable>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template scope="scope">
          <el-button :disabled="department[scope.$index].state!='G008'" size="small" @click="handleEdit(scope.$index, scope.row)" type="info">发药</el-button>
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)" type="info">查看订单详情</el-button>
          <el-button :disabled="department[scope.$index].state!='G009'"  size="small" @click="handleEdit(scope.$index, scope.row)" type="danger">退药</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--发药界面-->
    <el-dialog title="发药界面" v-model="DispensingMedicineVisible" :close-on-click-modal="false">
      <el-table
          :data="DispensingMedicine"
          style="width: 100%">
        <el-table-column
            prop="pid"
            label="订单id"
            width="180">
        </el-table-column>
        <el-table-column
            prop="name"
            label="药品名称"
            width="180">
        </el-table-column>
        <el-table-column
            prop="dprice"
            label="药品单价">
        </el-table-column>
        <el-table-column
            prop="number"
            label="药品数量">
        </el-table-column>
        <el-table-column
            prop="zprice"
            label="总价/￥">
        </el-table-column>
      </el-table>
      <h2>总价格:{{totalPrice}}</h2>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="DispensingMedicineVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">确定发药</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">确定发药</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import {
  addDepartment, dataValidation,
  findPatientMedic,
  getDepartClassList, getMedicationStatus,
  saveDepartment, ViewListMedicines
} from '../../api/api';
import {formatDate} from "element-ui";

export default {
  data() {
    return {
      pid:'',
      totalPrice:0,
      states:[],
      dialogImageUrl: '',
      dialogVisible: false,
      category: '',
      options: [],
      filters: {
        id: '',
        username:'',
        doctorname:'',
        state:''
      },
      department: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],//列表选中列

      DispensingMedicineVisible: false,//编辑界面是否显示
      editLoading: false,
      //编辑界面数据
      DispensingMedicine: {},

      addFormVisible: false,//新增界面是否显示
      addLoading: false,
      //新增界面数据
      addForm: {
        id: '',
        name: '',
        avatar: '',
        introduce: '',
        classification: ''
      }, restaurants: []
    }
  },
  methods: {
    handleSelect(item) {
      console.log(item);
    },
    querySearch(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.indexOf(queryString.toLowerCase()) === 0);
      };
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
      this.getPatientMedic();
    },
    getDepartClassList: function () {
      getDepartClassList().then((res) => {
        this.options = res.data.result
      })
    },
    //获取用户列表
    getPatientMedic() {
      let para = {
        state:this.filters.state,
        page: this.page,
        username: this.filters.username,
        id:this.filters.id,
        doctorName: this.filters.doctorname
      };
      this.listLoading = true;
      findPatientMedic(para).then((res) => {
        if (res.data.msgId == 'C200') {
          this.department = res.data.result.list;
          this.total = res.data.result.total;
          this.listLoading = false;
        }
      })
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.totalPrice=0
      this.pid=row.id
      ViewListMedicines(row.id).then(
          (res)=>{
            if(res.data.msgId=='C200'){
              this.DispensingMedicine=res.data.result
              for (let number of this.DispensingMedicine){
                this.totalPrice+=number.zprice
              }
            }

          }
      )
      this.DispensingMedicineVisible = true;
      this.DispensingMedicine = Object.assign({}, row);
    },
    //编辑
    editSubmit: function () {
      this.$confirm('确认发放药品吗?', '提示', {
        type: 'warning'
      }).then(() => {
        let param={
          id:this.pid,
          totalPrice:this.totalPrice
        }
        dataValidation(param).then((res)=>{
          if(res.data.msgId=='C503'){
            this.$notify.error({
              title: '错误',
              message: '数据校验未通过，请尝试刷新界面后重试'
            });
          }else if(res.data.msgId=='C200'){
            sessionStorage.setItem("payId",res.data.result)
            this.$notify.success({
              title: '成功',
              message: '数据校验通过，正在跳转链接'
            });
            this.$router.replace({path: '/pay'});
          } else if(res.data.msgId=='C504'){
            this.$notify.error({
              title: '失败',
              message: '药品已发放，请勿重复下单'
            });
            this.DispensingMedicineVisible = false;
          }
        })
      });
    },
    //新增
    addSubmit: function () {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addForm.id = 'DE' + this.addForm.id
            this.addLoading = true;
            //NProgress.start();
            // alert(JSON.stringify(this.addForm))
            saveDepartment(this.addForm).then((res) => {
              if (res.data.msgId == 'C200') {
                this.addFormVisible = false;
                this.addLoading = false;
                this.dialogImageUrl = '';
                this.$notify.success({
                  title: '成功',
                  message: '添加成功',
                  offset: 100
                });
                this.getUsers()
              } else {
                this.addFormVisible = false;
                this.addLoading = false;
                this.dialogImageUrl = '';
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
    this.getPatientMedic();
    this.getDepartClassList();
    getMedicationStatus().then((res)=>{
      this.states=res.data
    })
  }
}

</script>

<style scoped>

</style>