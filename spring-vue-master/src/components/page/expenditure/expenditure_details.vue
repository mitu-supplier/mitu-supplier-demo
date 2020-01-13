<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 计划详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        
        <div class="container">
            <el-row style="margin-bottom:20px;">
                <table width="100%;" cellspacing="0" class="Companyform" border="1">
                    <tbody>
                    <tr>
                        <td class="companytitle">项目名称</td>
                        <td class="companytitle1">
                            {{expendData.projectName}}
                        </td>
                        <td class="companytitle">所属科室</td>
                        <td class="companytitle1" >{{expendData.orgName}}</td>     
                    </tr>
                    <tr>
                        <td class="companytitle">创建时间</td>
                        <td class="companytitle1" >
                        {{expendData.createTime}}
                        </td>                                
                        <td class="companytitle">支出时间</td>
                        <td class="companytitle1" >
                            {{expendData.expenditureTime}}月
                        </td>
                    </tr>
                        <tr>
                        <td class="companytitle">支出金额（元）</td>
                        <td class="companytitle1" >
                          {{expendData.expenditureTotal}}
                        </td> 
                                                       
                          <td class="companytitle">乙方单位</td>
                        <td class="companytitle1" >
                          {{expendData.partyBUnits}}
                        </td>  
                    </tr>
                    </tr>
                        <tr>
                        <td class="companytitle">支出凭证</td>
                        <td class="companytitle1" >
                         <a :href="expendData.expenditureAttachment" target="_blank"> {{expendData.expenditureAttachmentName}}</a>
                        </td> 
                                                       
                          <td class="companytitle">其他附件</td>
                        <td class="companytitle1" >
                          <a :href="expendData.otherAttachment" target="_blank">{{expendData.otherAttachmentName}}</a>
                        </td>  
                    </tr>
                    <tr>
                     <td class="companytitle">用途</td>
                        <td class="companytitle1" colspan='3' >
                          {{expendData.expenditureUsing}}
                        </td>   
                    </tr>
           
                        </tbody>
                </table>
            </el-row>
        </div>
    </div>
</template>

<script>
    import { fetchData } from '../../../api/index';
    import baseURL_ from '@/utils/baseUrl.js';
    export default {
        name: 'basetable',
        data() {
            return {
                expendData:{},
                expend_id:'',
            }
        },
        created() {
           this.expend_id=this.$route.query.id;
           this.getpermissById();
        },
        methods: {
           async getpermissById(){
                const expend = await this.$http.get(baseURL_.lyjUrl+'/expenditure/getById',{ 
                    params: {'id':this.expend_id}
                });
                this.expendData=expend.data.data;
               
            }
        }
        
    }

</script>

<style scoped>
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
    .mr10{
        margin-right: 10px;
    }
    .container{
        min-height:500px;
    }
 
 .bigcard{
  position: relative;
    height: 100%;
    background: #fff;
    padding: 20px;
}
.formmb .el-form-item {
    margin-bottom: 0px;
}
.Companyform tr {
    width: 100%;
    text-align: center;
}
.Companyform td {
    text-align: center;
  border: 1px solid #E2E2E2 ;
}
.Companyform{
 /* margin-top:40px; */
 color: #323232;
 border: 0px solid #E2E2E2 ;
}
.Companyform .companytitle{
   width: 150px;
   padding-right: 10px;
   text-align: right;
   background: #F9FAFC;
}
.Companyform .companytitle1{
   /* width: 176px; */
   text-align: left;
   padding-left: 10px;
}

.Companyform td{
   height: 32px;
   line-height: 32px;
}
.round{
    width: 16px;
    height: 16px;
    border-radius: 50%;
    background: #009B63;
    text-align: center;
    line-height: 16px;
    color: #fff;
    float: left;
    margin-bottom: 20px;
}


</style>