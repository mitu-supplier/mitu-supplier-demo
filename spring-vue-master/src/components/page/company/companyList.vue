<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i>公司列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="ID">
                        <el-input v-model="formInline.code" placeholder="ID"></el-input>
                    </el-form-item>
                    <el-form-item label="公司名称">
                        <el-input v-model="formInline.name" placeholder="公司名称"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="onSubmit">查询</el-button>
                        <el-button type="primary" icon="el-icon-refresh" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <el-button type="primary"  @click="add" icon="el-icon-plus" >添加</el-button>
                <el-button type="primary" @click="batchDelete" icon="el-icon-delete" >批量删除</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="code" label="ID"  align="center" width=""></el-table-column>
                <el-table-column prop="name" label="公司名称" align="center" width=""></el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleUpdate(scope.$index, scope.row)">修改</el-button>
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
        <!-- 编辑弹出框 -->
        <el-dialog :title="titleName" :visible.sync="editVisible" width="25%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="ID">
                <el-input v-model="form.code" class="input"></el-input>
                </el-form-item>
                <el-form-item label="公司名称">
                    <el-input v-model="form.name" class="input"></el-input>
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
                multipleSelection: [],
                titleName:'',
                formInline:{
                    code:'',
                    name:''
                },
                form:{
                    id:'',
                    code:'',
                    name:''
                } 
            }
        },
        created() {
            this.getData();
        },
        methods: {
            onReset(){
              this.formInline.code="";
              this.formInline.name="";
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
                const products = await this.$http.get(baseURL_.mallUrl+'/company/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'code':this.formInline.code,
                        'name':this.formInline.name
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
                    this.$message("请先选择要删除的数据");
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
                var delResult = await this.$http.delete(baseURL_.mallUrl+'/company/batchDelete',{ 
                    params: {
                        'ids': ids
                    }
                });
                this.$message(delResult.data.data);
                if(delResult.data.statusCode==200){
                    this.getData();
                }
            },
            async handleUpdate(index, row){
                this.titleName="修改";
                const company = await this.$http.get(baseURL_.mallUrl+'/company/getById',{ 
                    params: {'id':row.id}
                });
                if(company.data.statusCode==200){
                    this.form.id=company.data.data.id;
                    this.form.name=company.data.data.name;
                    this.form.code=company.data.data.code;
                }
                 this.editVisible=true;
            },
            add(){
                this.form={};
                this.editVisible=true;
                this.titleName="添加";
            },
            async saveEdit(){
                var addOrEdit={};
                var code = this.form.code;
                var name = this.form.name;
                if(code == null || code.trim() == ''){
                    this.$message("请输入ID");
                    return;
                }
                if(name == null || name.trim() == ''){
                    this.$message("请输入公司名称");
                    return;
                }
                if(this.form.id!=null){
                   addOrEdit= await this.$http.post(baseURL_.mallUrl+'/company/update',this.$qs.stringify(this.form));
                }else{
                    addOrEdit= await this.$http.post(baseURL_.mallUrl+'/company/save',this.$qs.stringify(this.form));
                }
                this.$message(addOrEdit.data.data);
                if(addOrEdit.data.statusCode==200){
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
    #editor {
        width: 100%;
    /* height: 400px; */
    }
</style>