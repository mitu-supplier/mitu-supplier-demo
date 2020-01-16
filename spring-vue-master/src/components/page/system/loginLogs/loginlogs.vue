<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 登录日志</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="用户名">
                        <el-input v-model="formInline.userName" placeholder="用户名"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="searchBtn">搜索</el-button>
                        <el-button type="primary" icon="el-icon-refresh" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <!-- <el-input placeholder="筛选关键词" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" >搜索</el-button> -->
            </div>
            <el-table  :data="tableData"  border class="table" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="userName" label="用户名"  align="center" ></el-table-column>
                <el-table-column prop="loginName" label="登录名"  align="center" ></el-table-column>
                <el-table-column prop="roleName" label="角色名"  align="center" ></el-table-column>
                <el-table-column prop="createTime" label="登录时间" align="center"  ></el-table-column>
                <el-table-column prop="ip" label="IP" align="center" ></el-table-column>
               
            </el-table>
          
            <div class="pagination">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="page"
                    :page-sizes="[10, 15, 30, 50, 100]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total" >
                 </el-pagination>
            </div>
        </div>

        
    </div>
</template>
  
<script>
    import { fetchData } from '../../../../api/index';
    import baseURL_ from '@/utils/baseUrl.js';
    export default {
        name: 'basetable',
        data() {
            return {
                page:1,
                total:1000,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
                formInline:{
                    'name': ''
                }
            }
        },
        created() {
            this.getData();
        },
        methods: {
            //改变每页页数
            handleSizeChange(val){
                   this.pageSize=val;
                   this.getData();
            },
            //分页
            handleCurrentChange(val){
                   this.page=val;
                   this.getData();
            },
             handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // 初始化数据
            async getData() {
                const loginLogs = await this.$http.get(baseURL_.sysUrl+'/sysLoginLogs/list',{ 
                    params: {'page':this.page,'pageSize':this.pageSize,'userName':this.formInline.userName}
                    });
                if(loginLogs.data.statusCode==200){
                  this.tableData=loginLogs.data.data.list;
                  this.total=loginLogs.data.data.total;
                  this.page=loginLogs.data.data.page;
                }
            },
            // 重置
            onReset(){
                this.formInline = {};
                this.getData();
            },
            // 搜索 
            async searchBtn(){
                const permissions = await this.$http.get(baseURL_.sysUrl+'/sysLoginLogs/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'userName':this.formInline.userName
                    }
                });
                if(permissions.data.statusCode==200){
                  this.tableData=permissions.data.data.list;
                  this.total=permissions.data.data.total;
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
</style>