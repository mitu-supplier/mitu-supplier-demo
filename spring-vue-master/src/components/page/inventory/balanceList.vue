<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i>直充库存</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="商户名称">
                        <el-input v-model="formInline.supplierName" placeholder="商户名称"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">查询</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable">
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="supplierName" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column label="账户余额（元）" align="center" width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.money == null" class="red">0.00</span>
                        <span type="text" v-else>{{scope.row.money.toFixed(2)}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" @click="addBalance(scope.$index, scope.row)">充值</el-button>
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
        <!-- 编辑弹出框 -->
        <el-dialog :title="titleName" :visible.sync="editVisible" width="25%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="商户名称">
                     <el-input v-model="form.supplierName" :disabled="edit" class="input"></el-input>
                </el-form-item>
                <el-form-item label="金额">
                    <el-input v-model="form.money" class="input"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
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
                total:0,
                pageSize:10,
                tableData: [],
                titleName:'',
                edit:true,
                formInline:{
                    supplierName:''
                },
                form:{
                    supplierName:'',
                    supplierId:'',
                    money:''
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
                const products = await this.$http.get(baseURL_.mallUrl+'/supplierBalanceRecord/balanceList',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'supplierName':this.formInline.supplierName
                    }
                });
                if(products.data.statusCode==200){
                  this.tableData=products.data.data.list;
                  this.total=products.data.data.total;
                  this.page=products.data.data.page;
                }
            },
            addBalance(index, row){
                this.form = {};
                this.form.supplierId = row.supplierId;
                this.form.supplierName = row.supplierName;
                this.editVisible=true;
                this.titleName="充值";
            },
            async saveEdit(){
                var money = this.form.money;
                if(money == null || money.trim() == ''){
                    this.$message("请输入金额");
                    return;
                }
                var add = await this.$http.post(baseURL_.mallUrl+'/supplierBalanceRecord/save',this.$qs.stringify(this.form));
                this.$message(add.data.data);
                if(add.data.statusCode==200){
                    this.editVisible=false;
                }
                this.getData();
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