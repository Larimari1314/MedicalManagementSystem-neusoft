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
              v-model="filters.username"
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
      <el-table-column prop="id" label="订单id" width="180">
      </el-table-column>
      <el-table-column prop="username" label="患者姓名" width="120">
      </el-table-column>
      <el-table-column prop="dname" label="医生名称" width="170">
      </el-table-column>
      <el-table-column prop="price" label="价格" width="120">
      </el-table-column>
      <el-table-column prop="state" label="当前状态" width="100">
        <template scope="scope">
          <el-tag v-if="department[scope.$index].state=='G008'" type="warning">未发放</el-tag>
          <el-tag v-if="department[scope.$index].state=='G009'" type="success">已发放</el-tag>
          <el-tag v-if="department[scope.$index].state=='G010'" type="danger">已退费</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="times" label="最后操作时间" width="180">
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template scope="scope">
          <el-button :disabled="department[scope.$index].state!='G008'" size="small" @click="handleEdit(scope.$index, scope.row)" type="info">发药</el-button>
          <el-button size="small" @click="checkOrder(scope.$index, scope.row)" type="info">查看订单详情</el-button>
          <el-button :disabled="department[scope.$index].state!='G009'"  size="small" @click="withdrawal(scope.$index, scope.row)" type="danger">退药</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="50" :total="total"
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


    <el-dialog title="新增" v-model="orderDetails" :close-on-click-modal="false">
      <div class="modal" id="modal" v-watermark="watermark">
        <div class="content">
          <table id="msg" class="msg">
            <tr>
              <td style="text-align: left">订单ID:&nbsp;&nbsp;{{orderList.pid}}</td>
              <td style="text-align: left">开药科室:&nbsp;&nbsp;{{orderList.dename}}</td>
            </tr>
            <tr>
              <td style="text-align: left">开单时间:&nbsp;&nbsp;{{orderList.pdate}}</td>
              <td style="text-align: left">开单医生:&nbsp;&nbsp; {{orderList.dname}}</td>
            </tr>
            <tr>
              <td style="text-align: left">患者姓名:&nbsp;&nbsp;{{orderList.username}}</td>
              <td style="text-align: left">身份证号:&nbsp;&nbsp;{{orderList.identityNumber}}</td>
            </tr>
            <tr>
              <td style="text-align: left">总价:&nbsp;&nbsp;{{orderList.price}}</td>
              <td style="text-align: left">就诊类型:&nbsp;&nbsp;{{orderList.registered}}</td>
            </tr>
          </table>
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
                width="150">
            </el-table-column>
            <el-table-column
                prop="dprice"
                label="单价">
            </el-table-column>
            <el-table-column
                prop="number"
                label="数量">
            </el-table-column>
            <el-table-column
                prop="zprice"
                label="总价/￥">
            </el-table-column>
          </el-table>
          <br>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="orderDetails = false;sameOrder=true">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading" :disabled="sameOrder">创建相同订单</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import {
  addDepartment, createSameOrder, dataValidation,
  findPatientMedic,
  getDepartClassList, getMedicationStatus, orderDetails,
  saveDepartment, ViewListMedicines, withdrawal
} from '../../api/api';
import {formatDate} from "element-ui";

export default {
  data() {
    return {
      sameOrder:true,
      tableData:'',
      orderList:'',
      watermark:'',
      pid:'',
      totalPrice:0,
      states:[],
      dialogImageUrl: '',
      dialogVisible: false,
      options: [],
      filters: {
        id: '',
        username:'',
        doctorname:'',
        state:''
      },
      department: [],
      total: '',
      page: 1,
      listLoading: false,

      DispensingMedicineVisible: false,//编辑界面是否显示
      editLoading: false,
      //编辑界面数据
      DispensingMedicine: {},

      orderDetails: false,//新增界面是否显示
      addLoading: false,
      restaurants: []
    }
  },
  methods: {
    checkOrder(index,row){
      this.orderDetails=true
      this.sameOrder=true
      if(row.state==='G010'){
        this.sameOrder=false
      }
      this.watermark = {
        text1: row.id,
        text2: row.username,
        textColor: '#f1bbbd'
      }
      orderDetails(row.id).then((res)=>{
        if(res.data.msgId=='C200'){
          this.orderList=res.data.result[0]
        }
      })
      this.pid=row.id
      ViewListMedicines(row.id).then(
          (res)=>{
            if(res.data.msgId=='C200'){
              this.DispensingMedicine=res.data.result
            }
          }
      )
    },
    withdrawal(index,row){
      this.$confirm('确认退回药品吗?', '提示', {
        type: 'warning'
      }).then(() => {
        withdrawal(row.id).then((res)=>{
          if(res.data.msgId=='C405'){
            this.$notify.error({
              title: '错误',
              message: '当前药品未发放，请刷新重试'
            });
          }else if(res.data.msgId=='C500'){
            this.$notify.error({
              title: '错误',
              message: '内部错误，请联系管理员'
            });
          }else if(res.data.msgId=='C200'){
            this.$notify.success({
              title: '成功',
              message: '退药成功'
            });
            this.getPatientMedic()
          }
        })
      })
    },
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
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            let id=this.orderList.pid;
            this.addLoading = true;
            createSameOrder(id).then((res)=>{
              if(res.data.msgId=='C404'){
                this.addLoading = false;
                this.orderDetails=false
                this.$notify.error({
                  title: '失败',
                  message: '订单不存在或此订单未退款'
                });
              }else if(res.data.msgId=='C405'){
                this.addLoading = false;
                this.orderDetails=false
                this.$notify.error({
                  title: '失败',
                  message: '当天无法创建新的订单，请患者改日再来'
                });
              }else if(res.data.msgId=='C200'){
                this.addLoading = false;
                this.orderDetails=false;
                this.filters.id=res.data.result
                this.getPatientMedic()
                this.$notify.success({
                  title: '成功',
                  message: '创建订单成功！订单id为'+res.data.result
                });
              }
            })
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
.content {
  max-height: 500px;
  overflow-y: auto;
  width: 600px;
}
.modal {
  width: 600px;
  margin: 0 auto;
  position: relative;
  top: 50%; /*偏移*/
  background: #ffffff;
  border-radius: 5px;
}


.confirm > span {
  flex: 1;
  height: 50px;
  line-height: 50px;
  font-size: 16px;
  text-align: center;
}

.confirm > span:nth-child(1) {
  color: red;
}

.confirm > span:nth-child(2) {
  border-left: 1px solid #eee;
}


.title > p {
  height: 30px;
  line-height: 40px;
  text-align: center;
  font-size: 18px;
  font-weight: bold;

}


.content > table {
  padding: 0 10px 10px 10px;
  margin: 0 auto;
  width: 100%;

}

.msg {
  /* border-spacing: 80px 10px; */
  width: 100%;
  height: 150px;
  text-align: center;
  /* border-style:solid;
  border-color:rgb(120, 121, 164); */
  table-layout: fixed
}

</style>