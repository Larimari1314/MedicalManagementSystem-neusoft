<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="常规项名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getGeneralitem">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <template>
      <el-table :data="GeneralitemList" highlight-current-row v-loading="loading" style="width: 100%;">
        <el-table-column type="index" width="250">
        </el-table-column>
        <el-table-column prop="coding" label="常规项编号" width="300" sortable>
        </el-table-column>
        <el-table-column prop="value" label="常规项值" width="300" sortable>
        </el-table-column>
        <el-table-column label="操作" width="380">
          <template scope="scope">
            <el-button size="small" @click="editGeneralitem(scope.$index, scope.row)" type="info">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" ref="editForm">
        <el-form-item label="常数项" prop="name">
          <el-input v-model="editForm.coding" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="常数项值" prop="value" :rules="[
                      { required: true, message: '常数项值不得为空'}]">
          <el-input v-model="editForm.value" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

  </section>
</template>
<script>
import {editGeneralItem, getAllGeneralItem} from '../../api/api';

export default {
  data() {
    return {
      editLoading: false,
      editForm: [],
      editFormVisible: false,
      filters: {
        name: ''
      },
      loading: false,
      GeneralitemList: []
    }
  },
  methods: {
    editSubmit() {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.editLoading=true;
        let para={coding:this.editForm.coding,value:this.editForm.value}
        let _this=this;
        editGeneralItem(para).then((res)=>{
          if (res.data.msgId == 'C200') {
            this.editLoading=false
            this.editFormVisible=false
            this.$notify.success({
              title: '成功',
              message: '修改成功',
              offset: 100
            });
            this.getGeneralitem()
          } else {
            this.$notify.error({
              title: '失败',
              message: '修改失败',
              offset: 100
            });
            this.getGeneralitem()
          }
        })

      })
    },
    editGeneralitem(index, row) {
      this.editFormVisible = true
      this.editForm = Object.assign({}, row);
    },
    //获取用户列表
    getGeneralitem: function () {
      let para = {
        name: this.filters.name
      };
      this.loading = true;
      getAllGeneralItem(para).then((res) => {
        this.GeneralitemList = res.data;
        this.loading = false;
      });
    }
  },
  mounted() {
    this.getGeneralitem();
  }
};

</script>

<style scoped>

</style>