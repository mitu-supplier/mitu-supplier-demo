<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 项目资料详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        
        <div class="container">
            <el-row style="margin-bottom:20px;" v-show="type0">
                <table width="100%;" cellspacing="0" class="Companyform" border="1">
                    <tbody>
                    <tr>
                        <td class="companytitle">合同名称</td>
                        <td class="companytitle1">
                            {{contractData.contractName}}
                        </td>
                        <td class="companytitle">资料类型</td>
                        <td class="companytitle1" >
                            <span v-if="contractData.contractType=='1'">其他资料</span>
                            <span v-if="contractData.contractType=='0'">项目合同</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="companytitle">创建时间</td>
                        <td class="companytitle1" >
                        {{contractData.createTime}}
                        </td>  
                        <td class="companytitle">修改时间</td>
                        <td class="companytitle1" >
                        <span v-if="contractData.updateTime == null">{{contractData.createTime}}</span>
                        <span v-if="contractData.updateTime != null">{{contractData.updateTime}}</span>
                        </td>                                 
                        
                    </tr>
                        <tr>
                        <td class="companytitle">合同总金额（ 元）</td>
                        <td class="companytitle1" >
                            {{contractData.contractTotal}}
                        </td>
                        <td class="companytitle">合同负责人</td>
                        <td class="companytitle1" >
                          {{contractData.contractLeader}}
                        </td>                                
                            
                    </tr>
                    </tr>
                        <tr>
                         <td class="companytitle">联系方式</td>
                        <td class="companytitle1" >{{contractData.contractLeaderPhone}}</td>
                        <td class="companytitle">签订时间</td>
                        <td class="companytitle1" >
                        {{contractData.contractTime}} </td>
                                                    
                       
                    </tr>
                    <tr>
                       <td class="companytitle">所属项目</td>
                        <td class="companytitle1" >
                        {{contractData.projectName}}
                           
                        </td>    
                        <td class="companytitle">所属科室</td>
                        <td class="companytitle1" >{{contractData.orgName}}</td>
                        
                    </tr>
                       <tr>
                         <td class="companytitle">合同附件</td>
                        <td class="companytitle1" colspan='3'>
                         <span v-for="item in contractData.contractAttachment">
                           <a :href="item.url" target="_blank" style="margin-left:10px;">{{item.name}}</a>
                         </span>
                        </td>
                       </tr>
                       <tr>
                         <td class="companytitle">其他附件</td>
                        <td class="companytitle1" colspan='3'>
                         <span v-for="item in contractData.otherAttachment">
                           <a :href="item.url" target="_blank" style="margin-left:10px;">{{item.name}}</a>
                         </span>
                        </td>
                       </tr>
                        </tbody>
                </table>
            </el-row>
            <el-row style="margin-bottom:20px;" v-show="type1">
                <table width="100%;" cellspacing="0" class="Companyform" border="1">
                    <tbody>
                    <tr>
                        <td class="companytitle">资料名称</td>
                        <td class="companytitle1">
                            {{contractData.contractName}}
                        </td>
                        <td class="companytitle">资料类型</td>
                        <td class="companytitle1" >
                            <span v-if="contractData.contractType=='1'">其他资料</span>
                            <span v-if="contractData.contractType=='0'">项目合同</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="companytitle">创建时间</td>
                        <td class="companytitle1" >
                        {{contractData.createTime}}
                        </td>   
                        <td class="companytitle">修改时间</td>
                        <td class="companytitle1" >
                        <span v-if="contractData.updateTime==null">{{contractData.createTime}}</span>
                        <span v-if="contractData.updateTime!=null">{{contractData.updateTime}}</span>
                        </td>                                       
                        
                    </tr>
                        
                    <tr>
                        <td class="companytitle">所属科室</td>
                        <td class="companytitle1" >{{contractData.orgName}}</td>
                        <td class="companytitle">所属项目</td>
                        <td class="companytitle1" >
                        {{contractData.projectName}} </td>
                        
                    </tr>
                    <tr>
                      <td class="companytitle">其他附件</td>
                      <td class="companytitle1" colspan='3'>
                         <span v-for="item in contractData.otherAttachment">
                           <a :href="item.url" target="_blank" style="margin-left:10px;">{{item.name}}</a>
                         </span>
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
                contractData:{},
                contract_id:'',
                roel_name:'',
                type0:false,
                type1:false
            }
        },
        created() {
           this.contract_id=this.$route.query.id;
           this.getpermissById();
        },
        methods: {
            down(url){
               location.href=url;
            },
            fileOpen(fileUrl){
                alert(fileUrl)
               location.href=fileUrl;
            },
           async getpermissById(){
               const contract = await this.$http.get(baseURL_.lyjUrl+'/contract/getById',{ 
                    params: {'id':this.contract_id}
                });
                this.contractData=contract.data.data;
                if(contract.data.data.contractType=='0'){
                    this.contractData.contractAttachment=eval(contract.data.data.contractAttachment)
                    this.contractData.otherAttachment=eval(contract.data.data.otherAttachment)
                    this.type0=true;
                }else{
                   this.type1=true;
                }
               
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