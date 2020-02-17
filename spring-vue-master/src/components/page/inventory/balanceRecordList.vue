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
                    <el-form-item label="入库人员">
                        <el-input v-model="formInline.userName" placeholder="入库人员"></el-input>
                    </el-form-item>
                    <el-form-item label="入库时间">
                        <!-- <el-date-picker
                            v-model="formInline.startTime"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择开始时间">
                        </el-date-picker>
                        -
                        <el-date-picker
                            v-model="formInline.endTime"
                            type="datetime"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择结束时间">
                        </el-date-picker> -->
                        <el-date-picker
                            v-model="value1"
                            type="datetimerange"
                            range-separator="至"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            start-placeholder="选择开始时间"
                            end-placeholder="选择结束时间">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="onSubmit">查询</el-button>
                        <el-button type="primary" icon="el-icon-refresh" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable">
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="supplierName" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column label="金额（元）" align="center" width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.money == null" class="red">0.00</span>
                        <span type="text" v-else>{{scope.row.money.toFixed(2)}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="createdAt" label="入库时间" align="center"  width=""></el-table-column>
                <el-table-column prop="userName" label="入库人员" align="center"  width=""></el-table-column>
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
                supplierId:'',
                operationType:'',
                formInline:{
                    supplierName:'',
                    userName:'',
                    startTime:'',
                    endTime:''
                },
                value1:''
            }
        },
        created() {
            this.supplierId=this.$route.query.id;
            this.operationType=this.$route.query.operationType;
            this.getData();
        },
        methods: {
            onSubmit(){
                if(this.value1.length >0){
                    this.formInline.startTime = this.value1[0];
                    this.formInline.endTime = this.value1[1];
                }
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

                
                // var st = this.formInline.startTime;
                // var et = this.formInline.endTime;
                // if(st != null && st != '' && et != null && et != ''){
                //     if(st > et){
                //         this.$message("开始时间不能大于结束时间");
                //         return;
                //     }
                // }
                const products = await this.$http.get(baseURL_.mallUrl+'/supplierBalanceRecord/recordList',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'supplierName':this.formInline.supplierName,
                        'supplierId':this.supplierId,
                        'userName':this.formInline.userName,
                        'startTime':this.formInline.startTime,
                        'endTime':this.formInline.endTime,
                        'operationType':this.operationType
                    }
                });
                if(products.data.statusCode==200){
                  this.tableData=products.data.data.list;
                  this.total=products.data.data.total;
                  this.page=products.data.data.page;
                }
            },
            onReset(){
                this.value1 = '';
                this.formInline = {};
                this.getData();
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
    .input-width{
        width:60%;
    }
</style>