<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 合同监管</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                项目名称：<el-input placeholder="项目名称" v-model="projectName"  class="handle-input mr10" style="width:150px;"></el-input>
                合同名称：<el-input placeholder="合同名称" v-model="contractName"  class="handle-input mr10" style="width:150px;"></el-input>
                科室名称：<el-input placeholder="科室名称" v-model="orgName"  class="handle-input mr10" style="width:150px;"></el-input>
                负责人：<el-input placeholder="负责人" v-model="leader"  class="handle-input mr10" style="width:150px;"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
                <el-button type="primary" icon="el-icon-search" @click="rest">重置</el-button>
            </div>
            <el-table  :data="tableData" border class="table" ref="multipleTable"  >
                <el-table-column type="selection" width="55" align="center" ></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="contractName" label="合同名称"  align="center" ></el-table-column>
                 <el-table-column prop="projectName" label="所属项目"  align="center" width="250"></el-table-column>
                <el-table-column prop="contractTotal" label="合同总金额（ 元）"  align="center" width="100"></el-table-column>
                <el-table-column prop="contractLeader" label="合同负责人"  align="center" width="100"></el-table-column>
                <el-table-column prop="contractLeaderPhone" label="联系方式"  align="center" width="100"></el-table-column>
                <el-table-column prop="contractTime" label="签订时间"  align="center" width="100"></el-table-column>
               
                <el-table-column prop="orgName" label="所属科室"  align="center" width="100"></el-table-column>
                

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
    import { fetchData } from '../../../api/index';
    import baseURL_ from '@/utils/baseUrl.js';
    export default {
        name: 'basetable',
        data() {
            return {
                editVisible: false,
                page:1,
                total:1000,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
               
                contractName:'',
                projectName:'',
                orgName:'',
                leader:''
            }
        },
        created() {
            this.getData();
        },
        computed: {
           
        },
        methods: {
            handleSizeChange(val){
                   this.pageSize=val;
                   this.getData();
            },
            handleCurrentChange(val){
                   this.page=val;
                   this.getData();
            },
             handleSelectionChange(val) {
                this.multipleSelection = val;
            },
           search(){
                this.getData();
            },
            rest(){
                this.projectName='';
                this.orgName='';
                this.leader='';
                this.contractName='';
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            async getData() {
                const user = await this.$http.get(baseURL_.lyjUrl+'/supervise/contractList',{ 
                    params: {'page':this.page,'pageSize':this.pageSize,'contractName':this.contractName,
                    'projectName':this.projectName,'orgName':this.orgName,'leader':this.leader}
                    });
                if(user.data.statusCode==200){
                  this.tableData=user.data.data.list;
                  this.total=user.data.data.total;
                  this.page=user.data.data.page;
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
    .input{
        width:300px;
    }
   ul.ztree{
        margin-top: 10px;
        border: 1px solid #617775;
        background: #f0f6e4;
        width: 250px;
        height: 180px;
        overflow-y: scroll;
        overflow-x: auto;
        margin:auto;
        
    }
</style>