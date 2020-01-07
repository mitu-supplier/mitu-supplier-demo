<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 字典类型</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-plus" class="handle-del mr10" @click="add">添加</el-button>
                <el-input placeholder="名称或编码" v-model="name"  class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" >搜索</el-button>
                <span class="red">注：级别数值越小，职位越高</span>
            </div>

            <el-table  :data="tableData" border class="table" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" ></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="name" label="岗位名称"  align="center" width="300"></el-table-column>
                <el-table-column prop="code" label="岗位编码"  align="center" width="300"></el-table-column>
                <el-table-column prop="positionLevel" label="岗位级别"  align="center" width="300"></el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)" >删除</el-button>
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
        <el-dialog :title="titleName"  :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="岗位名称">
                <el-input v-model="form.name" class="input"></el-input>
                </el-form-item>
                <el-form-item label="岗位编码">
                    <el-input v-model="form.code" class="input"></el-input>
                </el-form-item>
                <el-form-item label="岗位级别">
                    <el-input v-model="form.positionLevel" class="input"></el-input>
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
                total:1000,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
                titleName:'',
                form:{
                   id:'',
                   name:'',
                   code:'',
                   positionLevel:''
                },
                
                name:''
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
            handleDelete(index, row){
                this.$confirm('确认删除？')
                .then( e=> {
                  
                   this.delete(row.id);

                }).catch(_ => {});
                 
            },
            async saveEdit(){
                var addOrEdit={};
                if(this.form.id!=null){
                   addOrEdit= await this.$http.post(baseURL_.lyjUrl+'/position/update',this.$qs.stringify(this.form));
                }else{
                    addOrEdit= await this.$http.post(baseURL_.lyjUrl+'/position/save',this.$qs.stringify(this.form));
                }
                this.$message(addOrEdit.data.data);
                if(addOrEdit.data.statusCode==200){
                    this.editVisible=false;
                }
                this.getData();
            },
            async delete(id){
                const del = await this.$http.get(baseURL_.lyjUrl+'/position/delete',{ 
                    params: {'id':id}
                });
                this.$message(del.data.data);
                if(del.data.statusCode==200){
                   this.getData();
                }
                  
            },
            async handleEdit(index, row) {
                this.titleName="修改";
                const user = await this.$http.get(baseURL_.lyjUrl+'/position/getById',{ 
                    params: {'id':row.id}
                });
                if(user.data.statusCode==200){
                    this.form.id=user.data.data.id;
                    this.form.name=user.data.data.name;
                    this.form.code=user.data.data.code;
                    this.form.positionLevel=user.data.data.positionLevel;
                }
                 this.editVisible=true;
            },
            add(){
               this.form={};
               this.editVisible=true;
               this.titleName="添加";
            },
            // 获取 easy-mock 的模拟数据
            async getData() {
                const user = await this.$http.get(baseURL_.lyjUrl+'/position/list',{ 
                    params: {'page':this.page,'pageSize':this.pageSize}
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