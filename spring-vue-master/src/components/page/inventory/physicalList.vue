<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 实物库存</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="类目名称">
                        <el-input v-model="formInline.catalogName" placeholder="类目名称"></el-input>
                    </el-form-item>
                    <el-form-item label="商品名称">
                        <el-input v-model="formInline.name" placeholder="商品名称"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">查询</el-button>
                        <el-button type="primary" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box canahead">
            </div>

            <el-table :data="tableData" border class="table" ref="multipleTable">
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="catalogName" label="商品类目"  align="center" width=""></el-table-column>
                <el-table-column prop="name" label="商品名称"  align="center" width=""></el-table-column>
                <el-table-column prop="supplierName" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column prop="inventoryNum" label="剩余库存" align="center"  width="">
                    <template slot-scope="scope">
                        <!-- v-on:focus="stockfocus(scope.row)"  -->
                        <el-input :value="scope.row.inventoryNum" v-model="scope.row.inventoryNum" v-on:blur="stockBlur(scope.row)" ></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="" label="库存告急" align="center"  width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.inventoryNum <= scope.row.inventoryAlertNum" class="red">库存不足</span>
                        <span type="text" v-else></span>
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
                formInline:{
                    name:'',
                    catalogName:''
                },
                myHeaders: {
                    'token': ''
                }
            }
        },
        created() {
            this.getData();
        },
        methods: {
            // stockfocus(row){
            //     console.log(row)
            //     this.stockRows = row;
            // },
            stockBlur(row){
                // alert(this.stockRows.inventoryNum)
                // alert(row.inventoryNum)
                // console.log(row.inventoryNum)
                // if(this.stockRows.inventoryNum == row.inventoryNum){
                //     return;
                // }
                // console.log(row);
                var num = row.inventoryNum;
                var numfist = 2;
                if(num.length > 1){
                    numfist = num.substring(0,1)
                }
                if(numfist <= 0){
                    this.$message("第一位不能是0");
                    return;
                }
                this.$confirm('确认修改剩余库存？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.editInventory(row);
                }).catch(() => {
                    this.getData();
                });

                
            },
            async editInventory(row){
                const data = await this.$http.get(baseURL_.mallUrl+'/physical/updateInventory',{ 
                    params: {
                        'productId':row.id,
                        'inventoryNum':row.inventoryNum
                    }
                });
                if(data.data.statusCode==200){
                    this.$message({
                        type: "success",
                        message: data.data.data
                    });
                    this.getData();
                }
            },
            onSubmit(){
                this.getData();
            },
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
                const products = await this.$http.get(baseURL_.mallUrl+'/physical/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'name':this.formInline.name,
                        'catalogName':this.formInline.catalogName
                    }
                });
                if(products.data.statusCode==200){
                  this.tableData=products.data.data.list;
                  this.total=products.data.data.total;
                  this.page=products.data.data.page;
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
    .input-width{
        width:60%;
    }
    .inline-block {
        display: inline-block;
    } 
</style>
<style>
.canahead .upload-excel-file .el-upload--text {    
    border: none !important;
    display: inline-block;
    width: 110px;
    height: 40px;
    float: left;
  }
</style>