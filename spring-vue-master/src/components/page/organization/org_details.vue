<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 科室详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        
        <div class="container">
            <el-row style="margin-bottom:20px;">
                <table width="100%;" cellspacing="0" class="Companyform" border="1">
                    <tbody>
                    <tr>
                        <td class="companytitle">科室名称</td>
                        <td class="companytitle1">
                            {{permissData.name}}
                        </td>
                        <td class="companytitle">科室编号</td>
                        <td class="companytitle1" >
                        {{permissData.code}}
                        </td>
                    </tr>
                    <tr>
                        <td class="companytitle">创建时间</td>
                        <td class="companytitle1" >
                        {{permissData.createTime}}
                        </td>                                
                          <td class="companytitle">修改时间</td>
                        <td class="companytitle1" >
                        <span v-if="permissData.updateTime==null">{{permissData.createTime}}</span>
                        <span v-if="permissData.updateTime!=null">{{permissData.updateTime}}</span>
                        </td>             
                    </tr>
                        <tr>
                        <td class="companytitle">科室排序</td>
                        <td class="companytitle1" >
                            {{permissData.priority}}
                        </td>
                        <td class="companytitle">业务分管领导</td>
                        <td class="companytitle1" >
                          {{permissData.leader}}
                        </td>                                
                        
                    </tr>
                    </tr>
                        <tr>
                        <td class="companytitle">联系电话</td>
                        <td class="companytitle1" >{{permissData.leaderPhone}}</td>     
                        <td class="companytitle">性别</td>
                        <td class="companytitle1" >
                            <span v-if="permissData.leaderSex=='1'">男</span>
                            <span v-if="permissData.leaderSex=='0'">女</span>
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
                permissData:{},
                org_id:'',
                roel_name:''
            }
        },
        created() {
           this.org_id=this.$route.query.id;
           this.getpermissById();
        },
        methods: {
           async getpermissById(){
               const permiss = await this.$http.get(baseURL_.lyjUrl+'/organization/getById',{ 
                    params: {'id':this.org_id}
                });
                this.permissData=permiss.data.data;
               
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