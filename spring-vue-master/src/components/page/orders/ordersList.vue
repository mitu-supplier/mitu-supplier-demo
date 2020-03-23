<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i>订单列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="商户名称">
                        <el-input v-model="formInline.supplierName" placeholder="商户名称"></el-input>
                    </el-form-item>
                    <el-form-item label="商品编码">
                        <el-input v-model="formInline.productCode" placeholder="商品编码"></el-input>
                    </el-form-item>
                    <el-form-item label="订单编号">
                        <el-input v-model="formInline.code" placeholder="订单编号"></el-input>
                    </el-form-item>
                    <el-form-item label="下单时间">
                                <el-date-picker
                                  size="mini"              
                                  v-model="formInline.searchTime"
                                  type="datetimerange"
                                  align="right"
                                  format="yyyy-MM-dd HH:mm:ss"
                                  value-format="yyyy-MM-dd HH:mm:ss"
                                  start-placeholder="开始日期"
                                  end-placeholder="结束日期"
                                  class="w50">
                                </el-date-picker>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">查询</el-button>
                        <el-button type="primary" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <el-button type="primary" @click="batchDelete" icon="el-icon-delete" >批量删除</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="code" label="订单编号"  align="center" width=""></el-table-column>
                <el-table-column prop="supplierName" label="店铺名称"  align="center" width=""></el-table-column>
                <el-table-column prop="createdAt" label="下单时间" align="center" width=""></el-table-column>
                <el-table-column label="订单总价（元）" align="center" width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.priceTotal == null">0.00</span>
                        <span type="text" v-else>{{scope.row.priceTotal.toFixed(3)}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="订单状态" align="center" width="">
                    <template slot-scope="status_scope">
                        <span type="text" v-if="status_scope.row.status == null" class="red"></span>
                        <span type="text" v-else-if="status_scope.row.status == 1" >待付款</span>
                        <span type="text" v-else-if="status_scope.row.status == 2" >待发货</span>
                        <span type="text" v-else-if="status_scope.row.status == 3" >发货中</span>
                        <span type="text" v-else-if="status_scope.row.status == 4" >已取消</span>
                        <span type="text" v-else-if="status_scope.row.status == 5" >售后</span>
                        <span type="text" v-else-if="status_scope.row.status == 6" >已完成</span>
                        <span type="text" v-else></span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" class="red" @click="view(scope.$index, scope.row)">查看详情</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
                page:1,
                total:0,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
                formInline:{
                    supplierName:'',
                    code:'',
                    searchTime:[],
                    productCode:''
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
            // 重置
            onReset(){
                this.formInline = {};
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
                var startTime = '';
                var endTime = '';
                if(this.formInline.searchTime && this.formInline.searchTime.length == 2){
                    startTime = this.formInline.searchTime[0];
                    endTime = this.formInline.searchTime[1];
                }
                const products = await this.$http.get(baseURL_.mallUrl+'/orders/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'supplierName':this.formInline.supplierName,
                        'code':this.formInline.code,
                        'startTime':startTime,
                        'endTime':endTime,
                        'productCode':this.formInline.productCode
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
            batchDelete(){
                if(this.multipleSelection.length == 0){
                    this.$message("请先选择订单");
                    return ;
                }
                this.$confirm('确认删除？').then( e=> {
                    var ids = [];
                    this.multipleSelection.forEach(e => {
                        ids.push(e.id);
                    });
                    this.delete(ids.join(','));
                }).catch(_ => {});
            },
            handleDelete(index, row){
                this.$confirm('确认删除？').then( e=> {
                   this.delete(row.id);
                }).catch(_ => {});
            },
            async delete(ids){
                var delResult = await this.$http.delete(baseURL_.mallUrl+'/orders/batchDelete',{ 
                    params: {
                        'ids': ids
                    }
                });
                var idLength = (ids+'').split(',').length;
                this.$message(delResult.data.data);
                if(delResult.data.statusCode==200){
                    if(this.tableData.length == idLength && this.page > 1){
                        this.page = this.page-1;
                    }
                    this.getData();
                }
            },
            view(index, row){
                var id = Base64.encode(row.id);
                this.$router.push({
                    path: '/ordersDetails',
                    name: 'ordersDetails',
                    query: {
                        id: id
                    }
                });
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
</style>