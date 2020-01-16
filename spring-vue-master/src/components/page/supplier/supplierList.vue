<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 商户管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" ref="formInline" :model="formInline" class="demo-form-inline">
                    
                    <el-form-item label="商户名称">
                        <el-input v-model="formInline.name" placeholder="商户名称"></el-input>
                    </el-form-item>
                    
                    <el-form-item label="审核状态">
                         <el-select v-model="formInline.status" placeholder="审核状态">
                            <el-option label="待审核" value="0"></el-option>
                            <el-option label="审核通过" value="1"></el-option>
                            <el-option label="审核失败" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="onSubmit">查询</el-button>
                        <el-button type="primary" icon="el-icon-refresh" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <el-button type="primary" v-if="isSupplier != '1'" icon="el-icon-plus" @click="add_supplier()" >添加商户</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="code" label="商户号"  align="center" width=""></el-table-column>
                <el-table-column prop="name" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column prop="registerAddress" label="商户地址"  align="center" width=""></el-table-column>
                
                <el-table-column prop="enterTypeName" label="入驻类型" align="center"  width=""></el-table-column>
                <el-table-column prop="contactName" label="联系人" align="center"  width="80"></el-table-column>
                <el-table-column prop="contactMobile" label="联系电话" align="center"  width=""></el-table-column>
                <el-table-column label="状态" align="center"  width="80">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.status == '0'">待审核</span>
                        <span type="text" v-if="scope.row.status == '1'">审核通过</span>
                        <span type="text" class="red" v-if="scope.row.status == '2'">审核失败</span>
                    </template>
                </el-table-column> 
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-search" @click="view(scope.$index, scope.row)">查看</el-button>
                        <el-button type="text" v-if="isSupplier == '1'" icon="el-icon-edit" class="red" @click="handleUpdate(scope.$index, scope.row)">修改</el-button>
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
        <el-dialog title="审核记录" class="dialogBox" :visible.sync="editVisible" width="40%">
            
           <el-table :data="auditData" border class="table" >
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="auditUserName" label="审核人"  align="center" width="80"></el-table-column>
                <el-table-column prop="createTime" label="审核时间"  align="center" width="150"></el-table-column>
                <el-table-column prop="auditReason" label="审核意见"  align="center" width="">
                   <template slot-scope="scope">
                         <span v-if="scope.row.auditResult=='1'">通过</span>
                         <span v-if="scope.row.auditResult=='2'">{{scope.row.auditReason}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="auditResult" label="审核状态"  align="center" width="">
                   <template slot-scope="scope">
                         <span v-if="scope.row.auditResult=='1'">通过</span>
                         <span v-if="scope.row.auditResult=='2'">不通过</span>
                    </template>
                </el-table-column>
           </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
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
                aaa:false,
                page:1,
                total:0,
                pageSize:10,
                tableData: [],
                editVisible:false,
                multipleSelection: [],
                auditData:[],
                formInline:{
                    name:'',
                    status:''
                },
                isSupplier:1
            }
        },
        created() {
            this.getData();
            this.vaIsSupplier();
        },
        methods: {
            onReset(){
              this.formInline.name="";
              this.formInline.status="";
              this.getData();
            },
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
                const products = await this.$http.get(baseURL_.mallUrl+'/supplier/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'name':this.formInline.name,
                        'status':this.formInline.status
                        
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
            view(index,item){
                this.$router.push({
                    path: '/supplierSee',
                    name: 'supplierSee',
                    params: {
                        id: item.id
                    }
                });
            },
            async vaIsSupplier(){
              var res = await this.$http.get(baseURL_.mallUrl+'/supplier/isSupplier');
              this.isSupplier = res.data.data;
            },
            handleUpdate(index, row){

            },
            add_supplier(){
                this.$router.push('/addSupplier');
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