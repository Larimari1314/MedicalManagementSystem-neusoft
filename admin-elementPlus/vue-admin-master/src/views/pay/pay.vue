<template>
  <section class="paybg">
    <h1 style="text-align: center;font-size: 30px;font-family: 华文行楷;color: red;margin: 0px">支付界面</h1>
    <h2 style="color: black">此次支付编号为：{{ payId }}&emsp;&emsp;&emsp;支付倒计时：{{ totalTime }}</h2>
    <!--列表-->
    <el-table :data="payList" highlight-current-row
              style="width: 50%;">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="id" label="药品id" width="180" sortable>
      </el-table-column>
      <el-table-column prop="name" label="药品名称" width="150">
      </el-table-column>
      <el-table-column prop="number" label="药品数量" width="170">
      </el-table-column>
      <el-table-column prop="price" label="单价" width="180" sortable>
      </el-table-column>
    </el-table>
    <h1>总价为：{{pprice}}</h1>
    <div :hidden="hiddens">
    <h1 style="color: red">请扫描下方二维码链接支付</h1>
    <div>
    <img  :src="'data:image/png;base64,' + pauImg" alt="支付图片">
  </div>
    </div>
    <div :hidden="payingState" style="color: #13ce66;font-size: 30px">
      <h1>用户已扫描，请点击支付按钮支付</h1>
    </div>
    <div :hidden="successPaying" style="color: #13ce66;font-size: 30px">
      用户支付成功,正在跳转到发药界面
    </div>
  </section>
</template>

<script>
import {getQRCode, paymentStatement, paymentStatus} from "../../api/api";

export default {
  data() {
    return {
      successPaying:true,
      payingState:true,
      hiddens:false,
      payList: {},
      payId: '',
      pauImg:'',
      pprice:0,
      totalTime: 300,
      canClick: false
    }
  },
  methods: {
    GetQRCode(){
      getQRCode(this.payId).then((res)=>{
        this.pauImg= res.data
      })
    },
    //获取当前支付状态
    paymentStatus(){
      let payIds=sessionStorage.getItem("payId")+'status'
      paymentStatus(payIds).then((res)=>{
        if(res.data.msgId=='C201'){
          this.hiddens=true
          this.payingState=false
          this.successPaying=true
        }else if (res.data.msgId=='C408'){
          this.hiddens=false
          this.payingState=true
          this.successPaying=true
        }else if(res.data.msgId=='C200'){
          this.hiddens=true
          this.payingState=true
          this.successPaying=false
          this.$message({
            message: '支付成功，正在跳转发药界面',
            type: 'success'
          });
          this.$router.push('/workstation')
        }
      })
    },
    sendMsg() {
      if (this.canClick) return
      this.canClick = true
      this.content = this.totalTime
      let clock = window.setInterval(() => {
        this.totalTime--
        this.content = this.totalTime
        if (this.totalTime < 0) {
          window.clearInterval(clock)
          this.totalTime = 10
          this.canClick = false
        }
      }, 1000)
    },
      getPayList(){
      paymentStatement(this.payId).then((res)=>{
        if(res.data.msgId=='C404'){
          /*订单超时或不存在*/
          this.$notify.error({
            title: '错误',
            message: '订单超时！'
          });
          this.$router.go(-1);
        }else if (res.data.msgId=='C200'){
          this.payList=res.data.result
          for (let pay of this.payList){
            this.pprice=pay.pprice
          }
        }
      })
    }
  },
  mounted() {
    this.payId = sessionStorage.getItem("payId")
    this.GetQRCode();
    this.getPayList();
    this.sendMsg();
    setInterval(this.paymentStatus, 2000);
  }
}
</script>

<style lang="scss" scoped>
.paybg {
  height: 100%;
  background: linear-gradient(125deg, #ECFCFF 0%, #ECFCFF 40%, #B2FCFF calc(40% + 1px), #B2FCFF 60%, #5EDFFF calc(60% + 1px), #5EDFFF 72%, #3E64FF calc(72% + 1px), #3E64FF 100%);
  margin: 0 auto;
}
</style>