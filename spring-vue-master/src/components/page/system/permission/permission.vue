<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 菜单管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="菜单名称">
                        <el-input v-model="formInline.name" placeholder="菜单名称"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="searchBtn">搜索</el-button>
                        <el-button type="primary" icon="el-icon-refresh" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <!-- <el-button type="primary" icon="el-icon-delete" class="handle-del mr10">批量删除</el-button> -->
                <el-button type="primary" icon="el-icon-plus" class="handle-del mr10" @click="add">添加</el-button>
            </div>
            <el-table  row-key="id" lazy :data="tableData" :load="load" :tree-props="{children: 'children', hasChildren: 'hasChildren'}" border id="table_id" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" ></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="name" label="菜单名称"  align="center" width="200"></el-table-column>
                <el-table-column prop="type" label="类别"  align="center" width="150">
                    <template slot-scope="scope">
                         <span v-if="scope.row.type=='0'">菜单</span>
                         <span v-if="scope.row.type!='0'">按钮</span>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" align="center"  width="200"></el-table-column>
                <el-table-column prop="url" label="地址" align="center" width="150"></el-table-column>
                <el-table-column prop="priority" label="排序"  align="center" width="150"></el-table-column>
                <el-table-column prop="iconName" label="图标"  align="center" width="150"></el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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
        <el-dialog :title="titleName"  :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="菜单名称">
                <el-input v-model="form.name" class="input"></el-input>
                </el-form-item>
                <el-form-item label="父菜单" v-show="parentNameShow">
                    <el-input v-model="form.parentName" readOnly class="input"></el-input>
                </el-form-item>
                <el-form-item label="访问地址">
                <el-input v-model="form.url" class="input"></el-input>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="form.priority" class="input"></el-input>
                </el-form-item>
                <el-form-item label="图标">
                    <el-input v-model="form.iconName" class="input"></el-input>
                </el-form-item>
                <el-form-item label="类别">
                    <el-select v-model="form.type" placeholder="请选择">
                        <el-option label="菜单" :value="0"></el-option>
                        <el-option label="按钮" :value="1"></el-option>
                    </el-select>
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
                editVisible:false,
                titleName:'',
                parentNameShow:false,
                form:{
                   id:'',
                   name:'',
                   parentId:'',
                   type:'',
                   treeDepth:'',
                   url:'',
                   priority:'',
                   iconName:'',
                   parentName:'',
                   isParent:''
                },
                searchCount:'',
                formInline:{
                    name:''
                }
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
            // 重置
            onReset(){
                this.formInline = {};
                this.getData();
            },
            // 搜索 
            async searchBtn(){
                const permissions = await this.$http.get(baseURL_.sysUrl+'/sysPermissions/getlistfirstLevel',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'name':this.formInline.name
                    }
                });
                if(permissions.data.statusCode==200){
                  this.tableData=permissions.data.data.list;
                  this.total=permissions.data.data.total;
                }
            },
            // 初始化数据
            async getData() {
                const permissions = await this.$http.get(baseURL_.sysUrl+'/sysPermissions/getlistfirstLevel',{ 
                    params: {'page':this.page,'pageSize':this.pageSize}
                    });
                if(permissions.data.statusCode==200){
                  this.tableData=permissions.data.data.list;
                  this.total=permissions.data.data.total;
                  this.page=permissions.data.data.page;
                }
            },
            async load(tree, treeNode, resolve){
                const permissions = await this.$http.get(baseURL_.sysUrl+'/sysPermissions/getlistChildren',{ 
                    params: {'parentId':tree.id}
                    });
                    resolve(permissions.data.data) 
             
            },
            add(){
               if(this.multipleSelection.length>1){
                  this.$message("不能选择多条数据进行操作");
                  return;
               }
            
               this.form={};
               if(this.multipleSelection.length==0){
                    this.parentNameShow=false;
               }else{
                    this.parentNameShow=true;
                    this.form.parentName=this.multipleSelection[0].name
                    this.form.parentId=this.multipleSelection[0].id;
               }
               this.editVisible=true;
               this.titleName="添加";
            },
            async saveEdit(){
                var addOrEdit={};
                if(this.form.id!=null){
                   addOrEdit= await this.$http.post(baseURL_.sysUrl+'/sysPermissions/update',this.$qs.stringify(this.form));
                }else{
                    if(this.form.parentId==null){
                      this.form.parentId=0;
                      this.form.treeDepth=1;
                    }
                    this.form.isParent='false';
                    addOrEdit= await this.$http.post(baseURL_.sysUrl+'/sysPermissions/add',this.$qs.stringify(this.form));
                }
                this.$message(addOrEdit.data.data);
                if(addOrEdit.data.statusCode==200){
                    this.editVisible=false;
                }
                this.getData();
            },
            handleDelete(index, row){
                if(row.isParent=='true'){
                   this.$message("该节点下有数据，不允许删除！");
                   return;
                }
                this.$confirm('确认删除？')
                .then( e=> {
                  
                   this.delete(row.id);

                }).catch(_ => {});
                 
            },
           async delete(id){
                const del = await this.$http.get(baseURL_.sysUrl+'/sysPermissions/delete',{ 
                    params: {'id':id}
                });
                this.$message(del.data.data);
                if(del.data.statusCode==200){
                   this.getData();
                }
                  
            },
            async handleEdit(index, row) {
                this.titleName="修改";
                const permiss = await this.$http.get(baseURL_.sysUrl+'/sysPermissions/getById',{ 
                    params: {'id':row.id}
                });
                if(permiss.data.statusCode==200){
                    this.form.id=permiss.data.data.id;
                    this.form.name=permiss.data.data.name;
                    this.form.parentId=permiss.data.data.parentId;
                    this.form.type=permiss.data.data.type;
                    this.form.treeDepth=permiss.data.data.treeDepth;
                    this.form.url=permiss.data.data.url;
                    this.form.priority=permiss.data.data.priority;
                    this.form.iconName=permiss.data.data.iconName;
                    this.form.isParent=permiss.data.data.isParent;
                    this.form.parentName=permiss.data.data.parentName;
                    if(this.form.treeDepth=='1'){
                        this.parentNameShow=false;
                    }else{
                        this.parentNameShow=true;
                    }
                   
                }
                 this.editVisible=true;
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
<style>
#table_id .el-table__body .cell .el-table__expand-icon{
     display:inline-block !important;
    }
</style>