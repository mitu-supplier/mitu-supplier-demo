<template>
    <div class="table">
        <div class="">
            <el-row>
                <!-- <el-col :xl="6">&nbsp;</el-col> -->
                <el-col :span="12" :offset="6">
                    <div class="counnet">
                      <div class="supplierTop">供应商入驻</div>
                          <el-steps :active="active" finish-status="success" class="stepList" align-center>
                            <el-step title="商户入驻"></el-step>
                            <el-step title="完善信息"></el-step>
                            <el-step title="完善信息"></el-step>
                            <el-step title="完善信息"></el-step>
                            <el-step title="完善信息"></el-step>
                            <el-step title="等待审核"></el-step>
                          </el-steps>
                        
                          <div v-if="active == 5" class="complete">
                                <div>
                                  <img src="../../../assets/img/ok.png" alt="">
                                </div>
                                <div v-if="!vShow" class="waitFor">等待审核</div>
                                <div v-if="!vShow" class="waitForTime">24h内完成审核，您可以登录账号，查看审核进度</div>
                                <div v-if="vShow" class="waitForTime">商户信息有误，请确认后从新提交</div>
                                <div v-if="vShow" class="modifyFor" @click="toRegisted">修改申请</div>
                          </div>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>
  
<script>
    import { fetchData } from '../../../api/index';
    export default {
        name: 'supplierAudit',
        data() {
            return {
                active:5,
                vShow:false
            }
        },
        created() {
          this.getAuditType();
        },
        methods: {
          getAuditType(){
            var type = sessionStorage.getItem("auditType");
            if(type == 2){
              // 驳回
              this.vShow = true;
            }else{
              this.vShow = false;
            }
          },
          toRegisted(){
            this.$router.push({
                path: '/registed',
                query: {
                    activeId: 2
                }
            });
          },
          
        }
    }

</script>

<style scoped>
    .w50{
        width:350px;
    }
    .counnet{
      margin-top: 20px;
      padding: 10px 20px;
      box-shadow: -2px -2px 4px 0 #E2E2E2, 2px 2px 4px 0 #E2E2E2;
    }
    .supplierTop{
        height: 40px;
        line-height: 40px;
        margin-top:20px;
        font-size: 16px;
        border-bottom: 1px solid #ccc;
    }
    .stepList{
      margin: 30px 0;
    }
    
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 12px;
        text-align: center
    }
    .table{
        width: 100%;
        font-size: 12px;
    }
  
    .red{
        color: #ff0000;
    }
    .container{
        min-height:500px;
    }
    .input-width{
        width:60%;
    }
    .complete{
      width:100%;
      text-align: center;
    }
    .complete img{
      margin: 20px 0;
    }
    .complete .waitFor{
      font-size: 22px;
      color:#666666;
    }
    .complete .waitForTime{
      font-size: 14px;
      color:#666666;
      margin: 10px 0 30px;
    }
    .modifyFor{
      width:80px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      margin: 0 auto;
      cursor: pointer;
      border:1px solid #ccc;
      border-radius: 4px;
    }
</style>
<style >
  .stepList .el-step__title{
      font-size: 12px !important;
    }
  .stepList .el-step__icon{
    width:20px !important;
    height:20px !important;
  }
</style>