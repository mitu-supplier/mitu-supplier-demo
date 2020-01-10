<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 商品审核</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="商户名称">
                        <el-input v-model="formInline.supplierName" placeholder="商户名称"></el-input>
                    </el-form-item>
                    <el-form-item label="商品名称">
                        <el-input v-model="formInline.name" placeholder="商品名称"></el-input>
                    </el-form-item>
                    <el-form-item label="类目名称">
                        <el-input v-model="formInline.catalogName" placeholder="类目名称"></el-input>
                    </el-form-item>
                    <el-form-item label="审核状态">
                         <el-select v-model="formInline.auditStatus" placeholder="审核状态">
                            <el-option label="请选择" value=""></el-option>
                            <el-option label="待审核" value="0"></el-option>
                            <el-option label="审核通过" value="1"></el-option>
                            <el-option label="审核失败" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">查询</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <el-button type="primary" @click="batchAudit(1)" >审核通过</el-button>
                <el-button type="primary" @click="batchAudit(2)" >审核失败</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="catalogName" label="商品类目"  align="center" width=""></el-table-column>
                <el-table-column prop="supplierName" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column prop="name" label="商品名称"  align="center" width=""></el-table-column>
                <el-table-column prop="price" label="市场价（元）" align="center"  width=""></el-table-column>
                <el-table-column label="状态" align="center"  width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.auditStatus == '0'">待审核</span>
                        <span type="text" v-if="scope.row.auditStatus == '1'">审核通过</span>
                        <span type="text" class="red" v-if="scope.row.auditStatus == '2'">审核失败</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" class="red" @click="view(scope.$index, scope.row)">查看详情</el-button>
                    </template>
                </el-table-column>
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
            <!-- <loding v-if="aaa"></loding> -->
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
                aaa:false,
                page:1,
                total:0,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
                formInline:{
                    supplierName:'',
                    name:'',
                    catalogName:'',
                    auditStatus:''
                }   
            }
        },
        created() {
            this.getData();
        },
        methods: {
            onSubmit(){
                this.getData();
            },
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
            // 初始化数据
            async getData() {
                const products = await this.$http.get(baseURL_.mallUrl+'/products_audit/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'name':this.formInline.name,
                        'catalogName':this.formInline.catalogName,
                        'auditStatus':this.formInline.auditStatus,
                        'supplierName':this.formInline.supplierName
                    }
                });
                if(products.data.statusCode==200){
                  this.tableData=products.data.data.list;
                  this.total=products.data.data.total;
                  this.page=products.data.data.page;
                }
            },
            handleSelectionChange(val){
                this.multipleSelection = val;
            },
            batchAudit(audit){
                if(this.multipleSelection.length == 0){
                    this.$message("请先选择商品");
                    return ;
                }
                var msg = "";
                if(audit == 1){
                    msg = "审核通过";
                }
                if(audit == 2){
                    msg = "审核不通过";
                }
                var flag = 0;
                var ids = [];
                this.multipleSelection.forEach(e => {
                    ids.push(e.id);
                    if(e.auditStatus != 0){
                        flag ++;
                    }
                });
                if(flag == 0){
                    this.$confirm('确认'+msg+'？').then( e=> {
                        this.audit(ids.join(','), audit);
                    }).catch(_ => {});
                }else{
                    this.$message("不能重复审核！");
                }
            },
            async audit(ids, auditStatus){
                var params = {
                    'ids': ids,
                    'auditStatus': auditStatus
                }
                var auditResult = await this.$http.put(baseURL_.mallUrl+'/products_audit/batchAudit', this.$qs.stringify(params));
                this.$message(auditResult.data.data);
                if(auditResult.data.statusCode==200){
                    this.getData();
                }
            },
            view(){
                
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
    .input-width{
        width:60%;
    }
    .dialog-ztree{
        align:center;
    }
     ul.ztree{
        margin-top: 10px;
        border: 1px solid #617775;
        background: #f0f6e4;
        width: 250px;
        height: 280px;
        overflow-y: scroll;
        overflow-x: auto;
        margin:auto;
        
    }
</style>