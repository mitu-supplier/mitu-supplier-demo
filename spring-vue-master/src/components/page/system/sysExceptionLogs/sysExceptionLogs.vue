<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 操作日志</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input placeholder="筛选关键词" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" >搜索</el-button>
            </div>
            <el-table  :data="tableData"  border class="table" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="userName" label="用户名"  align="center" width="80"></el-table-column>
                <el-table-column prop="loginName" label="登录名"  align="center" width="80"></el-table-column>
                <el-table-column prop="roleName" label="角色名"  align="center" width="80"></el-table-column>
                <el-table-column prop="createTime" label="操作时间" align="center"  width="150"></el-table-column>
                <el-table-column prop="ip" label="IP" align="center" width="100"></el-table-column>
                <el-table-column prop="modelName" label="操作模块" align="center" ></el-table-column>
                <el-table-column prop="description" show-overflow-tooltip label="异常日志" align="center" ></el-table-column>
                <el-table-column prop="url" label="地址" align="center" width="150"></el-table-column>
                <el-table-column prop="methodType" label="请求类型" align="center" width="100"></el-table-column>
                <el-table-column prop="method" label="请求方法" align="center" width="100"></el-table-column>
                <el-table-column prop="args" label="参数" align="center" width="150"></el-table-column>
               
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
                const sysExceptionLogs = await this.$http.get(baseURL_.sysUrl+'/sysExceptionLogs/list',{ 
                    params: {'page':this.page,'pageSize':this.pageSize}
                    });
                if(sysExceptionLogs.data.statusCode==200){
                  this.tableData=sysExceptionLogs.data.data.list;    
                  this.total=sysExceptionLogs.data.data.total;
                  this.page=sysExceptionLogs.data.data.page;
                }
            },
            
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