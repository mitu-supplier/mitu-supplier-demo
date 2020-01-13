<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 项目详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        
        <div class="container">
            <el-row style="margin-bottom:20px;">
                <table width="100%;" cellspacing="0" class="Companyform" border="1">
                    <tbody>
                    <tr>
                        <td class="companytitle">项目名称</td>
                        <td class="companytitle1">
                            {{projectData.projectName}}
                        </td>
                        <td class="companytitle">年度</td>
                        <td class="companytitle1" >
                        {{projectData.year}}
                        </td>
                    </tr>
                    <tr>
                        <td class="companytitle">创建时间</td>
                        <td class="companytitle1" >
                        {{projectData.createTime}}
                        </td>                                
                        <td class="companytitle">总预算（ 元）</td>
                        <td class="companytitle1" >
                            {{projectData.budget}}
                        </td>
                    </tr>
                        <tr>
                        <td class="companytitle">资金来源</td>
                        <td class="companytitle1" >
                          {{projectData.projectSource}}
                        </td>                                
                        <td class="companytitle">所属科室</td>
                        <td class="companytitle1" >{{projectData.orgName}}</td>     
                    </tr>
                    </tr>
                        <tr>
                        <td class="companytitle">项目负责人</td>
                        <td class="companytitle1" >
                        {{projectData.projectLeader}}
                        <td class="companytitle">联系方式</td>
                        <td class="companytitle1" >
                        {{projectData.leaderPhone}}
                           
                        </td>                                
                       
                    </tr>
                    <tr>
                        <td class="companytitle">执行进度</td>
                        <td class="companytitle1" >
                        {{projectData.per}}
                        <td class="companytitle">已执行金额（元）</td>
                        <td class="companytitle1" >
                        {{projectData.usedTotal}}
                           
                        </td>                                
                       
                    </tr>
                    <tr>
                        <td class="companytitle">剩余金额（元）</td>
                        <td class="companytitle1" >
                        {{projectData.laveTotal}}
                   
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
                projectData:{},
                project_id:'',
                roel_name:''
            }
        },
        created() {
           this.project_id=this.$route.query.id;
           this.getpermissById();
        },
        methods: {
           async getpermissById(){
               const project = await this.$http.get(baseURL_.lyjUrl+'/projects/getById',{ 
                    params: {'id':this.project_id}
                });
                this.projectData=project.data.data;
               
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