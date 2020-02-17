<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 角色管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="角色名称">
                        <el-input v-model="formInline.roleName" placeholder="角色名称"></el-input>
                    </el-form-item>
                    <el-form-item label="CODE">
                        <el-input v-model="formInline.roleCode" placeholder="CODE"></el-input>
                    </el-form-item>
                    
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="searchBtn">搜索</el-button>
                        <el-button type="primary" icon="el-icon-refresh" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-plus" class="handle-del mr10" @click="add">添加</el-button>
                <!-- <el-input placeholder="筛选关键词" v-model="searchCount" class="handle-input mr10"></el-input>
                 -->
                
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" ></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="roleName" label="角色名称"  align="center" width="200"></el-table-column>
                <el-table-column prop="roleCode" label="CODE"  align="center" width="150"></el-table-column>
                <el-table-column prop="createTime" label="创建时间" align="center"  width="200"></el-table-column>
                
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-setting" @click="ztreeEdit(scope.$index, scope.row)">设置权限</el-button>
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

         <!-- 编辑弹出框 添加 修改-->
        <el-dialog :title="titleName"  :visible.sync="editVisible" width="25%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="角色名称">
                   <el-input v-model="form.roleName" class="input input-width"></el-input>
                </el-form-item>
                <el-form-item label="角色编码" >
                    <el-input v-model="form.roleCode"  class="input input-width"></el-input>
                </el-form-item>
                
                
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 编辑弹出框 添加 修改-->
        <el-dialog :title="ztreeTitleName" class="dialog-ztree"  :visible.sync="ztreeEditVisible" width="25%">
             <ul id="ztree" class="ztree"></ul>
             <span slot="footer" class="dialog-footer">
                <el-button @click="ztreeEditVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveRolePermiss">确 定</el-button>
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
                ztreeTitleName:'',
                ztreeEditVisible:false,
                role_id:'',
                form:{
                   id:'',
                   roleName:'',
                   roleCode:'',
                   isAdmin:'',
                   isBuiltIn:''
                },
                setting:{
                    data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "parentId"
                    }
                    },
                    callback: {
                        
                    },
                    check: {
                        enable: true,
                        chkStyle: "checkbox",
                        chkboxType: { Y: "ps", N: "ps" }
                    },
                },
                searchCount:'',
                formInline:{
                    roleName:'',
                    roleCode:''
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
                const permissions = await this.$http.get(baseURL_.sysUrl+'/sysRole/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'roleName':this.formInline.roleName,
                        'roleCode':this.formInline.roleCode
                    }
                });
                if(permissions.data.statusCode==200){
                  this.tableData=permissions.data.data.list;
                  this.total=permissions.data.data.total;
                }
            },
            // 初始化数据
            async getData() {
                const role = await this.$http.get(baseURL_.sysUrl+'/sysRole/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'roleName':this.formInline.roleName,
                        'roleCode':this.formInline.roleCode
                    }
                });
                if(role.data.statusCode==200){
                  this.tableData=role.data.data.list;
                  this.total=role.data.data.total;
                  this.page=role.data.data.page;
                }
            },
            add(){
               this.form={};
               this.form.isAdmin = '0',
               this.form.isBuiltIn = '0',
               this.editVisible=true;
               this.titleName="添加";
            },
             async saveEdit(){
                var addOrEdit={};
                if(this.form.id!=null){
                   addOrEdit= await this.$http.post(baseURL_.sysUrl+'/sysRole/update',this.$qs.stringify(this.form));
                }else{
                    if(this.form.parentId==null){
                      this.form.parentId=0;
                      this.form.treeDepth=1;
                    }
                    this.form.isParent='false';
                    addOrEdit= await this.$http.post(baseURL_.sysUrl+'/sysRole/add',this.$qs.stringify(this.form));
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
                const del = await this.$http.get(baseURL_.sysUrl+'/sysRole/delete',{ 
                    params: {'id':id}
                });
                this.$message(del.data.data);
                if(del.data.statusCode==200){
                    if(this.tableData.length == 1 && this.page > 1){
                        this.page = this.page-1;
                    }
                   this.getData();
                }
                  
            },
            async handleEdit(index, row) {
                this.titleName="修改";
                const permiss = await this.$http.get(baseURL_.sysUrl+'/sysRole/getById',{ 
                    params: {'id':row.id}
                });
                if(permiss.data.statusCode==200){
                    this.form.id=permiss.data.data.id;
                    this.form.roleName=permiss.data.data.roleName;
                    this.form.roleCode=permiss.data.data.roleCode;
                    this.form.isAdmin=permiss.data.data.isAdmin;
                    this.form.isBuiltIn=permiss.data.data.isBuiltIn;
                }
                 this.editVisible=true;
            },
            async ztreeEdit(index, row){
                this.ztreeTitleName="权限菜单";
                this.ztreeEditVisible=true;
                const permiss = await this.$http.get(baseURL_.sysUrl+'/sysRole/getRolePermiss',{ 
                    params: {'roleId':row.id}
                });
                this.role_id=row.id;
                $.fn.zTree.init($("#ztree"), this.setting, permiss.data.data);
            },
            async saveRolePermiss(){
               var treeObj = $.fn.zTree.getZTreeObj("ztree");
               var nodes = treeObj.getCheckedNodes(true);
               if(nodes.length==0){
                 this.$message("请选择权限菜单！");
                 return;
               }
               var permissArr=[];
               for(var i=0;i<nodes.length;i++){
                  permissArr.push(nodes[i].id);
               }
               var obj={};
               obj.roleId=this.role_id;
               obj.permissionsIds=permissArr.join(",");
               var result= await this.$http.post(baseURL_.sysUrl+'/sysRole/saveRolePermiss',this.$qs.stringify(obj));
               this.$message(result.data.data);
               if(result.data.statusCode==200){
                 this.ztreeEditVisible=false;
                 this.role_id="";
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