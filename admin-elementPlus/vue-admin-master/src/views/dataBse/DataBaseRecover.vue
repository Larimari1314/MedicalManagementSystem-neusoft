<template>
  <section>
    <div style="text-align: center;color: red"><h1>医生表已删除数据</h1></div>
    <el-table
        :data="doctorList"
        border
        style="width: 100%"
        max-height="250" ref="doctorList">
      <el-table-column
          prop="id"
          label="医生编号"
          width="150">
      </el-table-column>
      <el-table-column
          label="姓名"
          width="280">
        <template scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>姓名: {{ scope.row.name }}</p>
            <p>医生编号: {{ scope.row.id }}</p>
            <p>医生所在科室: {{ scope.row.dname }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
          prop="dname"
          label="所在科室"
          width="200">
      </el-table-column>
      <el-table-column
          prop="sex"
          label="性别"
          width="130">
      </el-table-column>
      <el-table-column
          prop="sname"
          label="班次"
          width="165">
      </el-table-column>
      <el-table-column
          label="操作"
          width="300">
        <template scope="scope">
          <el-button
              @click.native="deleteRowDoctor(scope.$index, scope.row)"
              type="danger"
              size="small">
            永久删除
          </el-button>
          <el-button
              @click.native="dataRecoveryDoctor(scope.$index, scope.row)"
              type="primary"
              size="small">
            恢复数据
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="text-align: center;color: red"><h1>非药品表删除数据</h1></div>
    <el-table
        :data="nonDrugList"
        border
        style="width: 100%"
        max-height="250">
      <el-table-column
          prop="id"
          label="编号"
          width="150">
      </el-table-column>
      <el-table-column
          prop="cover"
          label="药品封面"
          width="250">
        <template scope="scope">
          <img :src="nonDrugList[scope.$index].cover" style="border-radius:50%; " width="100" height="100"
               alt="药品封面">
        </template>
      </el-table-column>
      <el-table-column
          label="姓名"
          width="200">
        <template scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>姓名: {{ scope.row.name }}</p>
            <p>数量: {{ scope.row.number }}</p>
            <p>价格: {{ scope.row.price }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
          prop="number"
          label="数量"
          width="150">
      </el-table-column>
      <el-table-column
          prop="price"
          label="价格"
          width="180">
      </el-table-column>
      <el-table-column
          label="操作"
          width="300">
        <template scope="scope">
          <el-button
              @click.native="deleteRowDrug(scope.$index, scope.row)"
              type="danger"
              size="small">
            永久删除
          </el-button>
          <el-button
              @click.native="dataRecoveryDrug(scope.$index, scope.row)"
              type="primary"
              size="small">
            恢复数据
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="text-align: center;color: red"><h1>用户表已删除数据</h1></div>
    <el-table
        :data="userList"
        border
        style="width: 100%"
        max-height="250">
      <el-table-column
          prop="id"
          label="编号"
          width="150">
      </el-table-column>
      <el-table-column
          prop="avatar"
          label="用户头像"
          width="180">
        <template scope="scope">
          <img :src="userList[scope.$index].avatar" style="border-radius:50%; " width="100" height="100"
               alt="药品封面">
        </template>
      </el-table-column>
      <el-table-column
          label="姓名"
          width="280">
        <template scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>姓名: {{ scope.row.name }}</p>
            <p>身份证号: {{ scope.row.identityNumber }}</p>
            <p>年龄: {{ scope.row.cover }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
          prop="cover"
          label="年龄"
          width="120">
      </el-table-column>
      <el-table-column
          prop="identityNumber"
          label="身份证号"
          width="180">
      </el-table-column>
      <el-table-column
          label="操作"
          width="300">
        <template scope="scope">
          <el-button
              @click.native="deleteRowUser(scope.$index, scope.row)"
              type="danger"
              size="small">
            永久删除
          </el-button>
          <el-button
              @click.native="dataRecoveryUser(scope.$index, scope.row)"
              type="primary"
              size="small">
            恢复数据
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>


<script>

import {
  checkDeleteDoctor,
  dataRecoveryDoctor,
  dataRecoveryDrug, dataRecoveryUser,
  deletePermanently,
  deletePermanentlyDrug, deletePermanentlyUser,
  getDeleteDrug,
  getDeleteUser
} from "../../api/api";

export default {
  data() {
    return {
      userList:[],
      nonDrugList: [],
      doctorList: [],
      tableData4: [{
        date: '2016-05-03',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }, {
        date: '2016-05-02',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }, {
        date: '2016-05-04',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }, {
        date: '2016-05-01',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }, {
        date: '2016-05-08',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }, {
        date: '2016-05-06',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }, {
        date: '2016-05-07',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }]
    }
  },
  methods: {
    deleteRow(index, rows) {
      rows.splice(index, 1);
    },
    deleteRowDrug(index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
            deletePermanentlyDrug(row.id).then((res) => {
              if (res.data.msgId == 'C200') {
                this.$notify.success({
                  title: '成功',
                  message: '删除成功',
                  offset: 100
                });
                this.getNonDrug()
              } else {
                this.$notify.error({
                  title: '失败',
                  message: '删除失败',
                  offset: 100
                });
                this.getNonDrug()
              }
            })
          }
      )
    },
    deleteRowUser(index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
            deletePermanentlyUser(row.id).then((res) => {
              if (res.data.msgId == 'C200') {
                this.$notify.success({
                  title: '成功',
                  message: '删除成功',
                  offset: 100
                });
                this.getUser()
              } else {
                this.$notify.error({
                  title: '失败',
                  message: '删除失败',
                  offset: 100
                });
                this.getUser()
              }
            })
          }
      )
    },
    deleteRowDoctor(index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
            deletePermanently(row.id).then((res) => {
              if (res.data.msgId == 'C200') {
                this.$notify.success({
                  title: '成功',
                  message: '删除成功',
                  offset: 100
                });
                this.getDoctorDelete()
              } else {
                this.$notify.error({
                  title: '失败',
                  message: '删除失败',
                  offset: 100
                });
                this.getDoctorDelete()
              }
            })
          }
      )
    },
    dataRecoveryDoctor(index, row) {
      dataRecoveryDoctor(row.id).then((res) => {
        if (res.data.msgId == 'C200') {
          this.$notify.success({
            title: '成功',
            message: '恢复成功',
            offset: 100
          });
          this.getDoctorDelete()
        } else {
          this.$notify.error({
            title: '失败',
            message: '恢复失败',
            offset: 100
          });
          this.getDoctorDelete()
        }
      })
    },
    dataRecoveryUser(index, row) {
      dataRecoveryUser(row.id).then((res) => {
        if (res.data.msgId == 'C200') {
          this.$notify.success({
            title: '成功',
            message: '恢复成功',
            offset: 100
          });
          this.getUser()
        } else {
          this.$notify.error({
            title: '失败',
            message: '恢复失败',
            offset: 100
          });
          this.getUser()
        }
      })
    },
    dataRecoveryDrug(index, row) {
      dataRecoveryDrug(row.id).then((res) => {
        if (res.data.msgId == 'C200') {
          this.$notify.success({
            title: '成功',
            message: '恢复成功',
            offset: 100
          });
          this.getNonDrug()
        } else {
          this.$notify.error({
            title: '失败',
            message: '恢复失败',
            offset: 100
          });
          this.getNonDrug()
        }
      })
    },
    getDoctorDelete() {
      checkDeleteDoctor().then((res) => {
        if (res.data.msgId == 'C200') {
          this.doctorList = res.data.result
        }
      })
    }
    , getNonDrug() {
      getDeleteDrug().then((res) => {
        if (res.data.msgId == 'C200') {
          this.nonDrugList = res.data.result
        }
      })
    }
    , getUser() {
      getDeleteUser().then((res) => {
        if (res.data.msgId == 'C200') {
          this.userList = res.data.result
        }
      })
    }
  }, created() {
    this.getDoctorDelete();
    this.getNonDrug();
    this.getUser()
  }
}
</script>

<style scoped>

</style>