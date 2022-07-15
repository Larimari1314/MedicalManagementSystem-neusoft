<template>
  <section>
    <div style="text-align: center;color: red"><h1>医生表已删除数据</h1></div>
    <el-table
        :data="doctorList"
        border
        style="width: 100%"
        max-height="250"  ref="doctorList">
      <el-table-column
          prop="id"
          label="医生编号"
          width="150">
      </el-table-column>
      <el-table-column
          label="姓名"
          width="300">
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
        :data="tableData4"
        border
        style="width: 100%"
        max-height="250">
      <el-table-column
          prop="date"
          label="日期"
          width="150">
      </el-table-column>
      <el-table-column
          label="姓名"
          width="180">
        <template scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>姓名: {{ scope.row.name }}</p>
            <p>住址: {{ scope.row.address }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
          prop="province"
          label="省份"
          width="120">
      </el-table-column>
      <el-table-column
          prop="city"
          label="市区"
          width="120">
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址"
          width="300">
      </el-table-column>
      <el-table-column
          prop="zip"
          label="邮编"
          width="120">
      </el-table-column>
      <el-table-column
          label="操作"
          width="120">
        <template scope="scope">
          <el-button
              @click.native.prevent="deleteRow(scope.$index, tableData4)"
              type="text"
              size="small">
            移除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="text-align: center;color: red"><h1>用户表已删除数据</h1></div>
    <el-table
        :data="tableData4"
        border
        style="width: 100%"
        max-height="250">
      <el-table-column
          prop="date"
          label="日期"
          width="150">
      </el-table-column>
      <el-table-column
          label="姓名"
          width="180">
        <template scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>姓名: {{ scope.row.name }}</p>
            <p>住址: {{ scope.row.address }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
          prop="province"
          label="省份"
          width="120">
      </el-table-column>
      <el-table-column
          prop="city"
          label="市区"
          width="120">
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址"
          width="300">
      </el-table-column>
      <el-table-column
          prop="zip"
          label="邮编"
          width="120">
      </el-table-column>
      <el-table-column
          label="操作"
          width="120">
        <template scope="scope">
          <el-button
              @click.native.prevent="deleteRow(scope.$index, tableData4)"
              type="text"
              size="small">
            移除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>


<script>

import {checkDeleteDoctor, dataRecoveryDoctor, deletePermanently} from "../../api/api";

export default {
  data() {
    return {
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
    deleteRowDoctor(index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).
          then(() => {
            deletePermanently(row.id).then((res) => {
              if (res.data.msgId == 'C200') {
                this.$notify.success({
                  title: '成功',
                  message: '删除成功',
                  offset: 100
                });
                this.getDoctorDelete()
              }else {
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
    dataRecoveryDoctor(index,row){
      dataRecoveryDoctor(row.id).then((res)=>{
        if (res.data.msgId == 'C200') {
          this.$notify.success({
            title: '成功',
            message: '恢复成功',
            offset: 100
          });
          this.getDoctorDelete()
        }else {
          this.$notify.error({
            title: '失败',
            message: '恢复失败',
            offset: 100
          });
          this.getDoctorDelete()
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
  }, created() {
    this.getDoctorDelete();
  }
}
</script>

<style scoped>

</style>