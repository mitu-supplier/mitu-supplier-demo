<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 科室管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <!-- <el-button type="primary" icon="el-icon-delete" class="handle-del mr10">批量删除</el-button> -->
                <el-button type="primary" icon="el-icon-plus" class="handle-del mr10" v-if="button_role&&button_role.add" @click="add">添加</el-button>
                <el-input placeholder="科室名称或编号" v-model="name"  class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
                <el-button type="primary" icon="el-icon-search" @click="rest">重置</el-button>
            </div>
            <el-table  row-key="id" lazy :data="tableData" :load="load" :tree-props="{children: 'children', hasChildren: 'hasChildren'}" border id="table_id" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" ></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="name" label="科室名称"  align="center" ></el-table-column>
                <el-table-column prop="code" label="科室编号"  align="center" width="100"></el-table-column>
                <el-table-column prop="createTime" label="创建时间" align="center"  width="150"></el-table-column>
                <el-table-column prop="priority" label="排序"  align="center" width="100"></el-table-column>
                <el-table-column prop="leader" label="业务分管领导"  align="center" width="150"></el-table-column>
                <el-table-column prop="leaderPhone" label="联系电话"  align="center" width="100"></el-table-column>
                <el-table-column prop="leaderSex" label="性别"  align="center" width="80">
                    <template slot-scope="scope">
                         <span v-if="scope.row.leaderSex=='1'">男</span>
                         <span v-if="scope.row.leaderSex=='0'">女</span>
                    </template>
                </el-table-column>
                
                <el-table-column label="操作" width="" align="center" v-if="button_role&&(button_role.delete||button_role.edit||button_role.look)">
                    <template slot-scope="scope">
                        
                        <el-button type="text" icon="el-icon-edit" v-if="button_role&&button_role.edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" v-if="button_role&&button_role.delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                        <el-button type="text" icon="el-icon-document"  v-if="button_role&&button_role.look" @click="handleLook(scope.$index, scope.row)">查看</el-button>
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
        <el-dialog :title="titleName"  :visible.sync="editVisible" width="30%"  @close="closeDilog('form')">
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="科室名称" prop="name">
                <el-input v-model="form.name" class="input"></el-input>
                </el-form-item>
                <el-form-item label="父级科室" v-show="parentNameShow">
                    <el-input v-model="form.parentName" readOnly class="input"></el-input>
                </el-form-item>
                <el-form-item label="编号" prop="code">
                <el-input v-model="form.code" class="input"></el-input>
                </el-form-item>
                <el-form-item label="排序" prop="priority">
                    <el-input v-model.number="form.priority" class="input"></el-input>
                </el-form-item>
                <el-form-item label="业务分管领导" prop="leader">
                    <el-input v-model="form.leader" class="input"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" prop="leaderPhone">
                    <el-input v-model="form.leaderPhone" class="input"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="leaderSex">
                    <el-radio v-model="form.leaderSex" label="1">男</el-radio>
                    <el-radio v-model="form.leaderSex" label="0">女</el-radio>
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
                page:1,
                total:1000,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
                editVisible:false,
                titleName:'',
                parentNameShow:false,
                button_role:{},
                rules: {
                    name: [
                        { required: true, message: '请填写科室名称', trigger: 'blur' }
                    ],
                    code:[
                        { required: true, message: '请填写科室编号', trigger: 'blur' }
                    ],
                    priority:[
                        
                        {  required: true, message: '请填写排序序号', trigger: 'change' },
                        {  type:'number', message: '请填写数字', trigger: 'change' },
                    ],
                    leader:[
                        { required: true, message: '请填写业务分管领导', trigger: 'blur' }
                    ],
                    leaderPhone:[
                        { required: true, message: '请填写联系电话', trigger: 'blur' }
                    ],
                    leaderSex:[
                        { required: true, message: '请选择性别', trigger: 'change' }
                    ],
                    
                },
                form:{
                   id:'',
                   name:'',
                   parentId:'',
                   treeDepth:'',
                   priority:'',
                   parentName:'',
                   isParent:'',
                   leader:'',
                   leaderPhone:'',
                   leaderSex:'',
                   code:''

                },
                name:''
            }
        },
        created() {
            this.button();
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
            search(){
                this.getData();
            },
            rest(){
               this.name="";
               this.getData();
            },
             handleLook(index, row){
                 this.$router.push({ path:'/org_details',query: {id: row.id}})
            },
            async button(){
                var but=await this.$http.get(baseURL_.loginUrl+'/permission/button',{ 
                    params: {'code':this.$route.path}
                });
                this.button_role=but.data.data;
            },
            // 初始化数据
            async getData() {
                const permissions = await this.$http.get(baseURL_.lyjUrl+'/organization/getlistfirstLevel',{ 
                    params: {'page':this.page,'pageSize':this.pageSize,'name':this.name}
                    });
                if(permissions.data.statusCode==200){
                  this.tableData=permissions.data.data.list;
                  this.total=permissions.data.data.total;
                  this.page=permissions.data.data.page;
                }
            },
            async load(tree, treeNode, resolve){
                const permissions = await this.$http.get(baseURL_.lyjUrl+'/organization/getlistChildren',{ 
                    params: {'parentId':tree.id}
                    });
                    resolve(permissions.data.data) 
             
            },
            closeDilog:function(form){
        
                this.$refs[form].resetFields();//将form表单重置
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
               this.closeDilog("form");
               this.titleName="添加";
            },
            async saveEdit(){
                var addOrEdit={};
                var flg=true;
                this.$refs['form'].validate((valid) => {
                if (!valid) {
                       flg=false;
                     } 
                })
                if(flg){
                    if(this.form.id!=null){
                        addOrEdit= await this.$http.post(baseURL_.lyjUrl+'/organization/update',this.$qs.stringify(this.form));
                    }else{
                        if(this.form.parentId==null){
                        this.form.parentId=0;
                        this.form.treeDepth=1;
                        }
                        this.form.isParent='false';
                        addOrEdit= await this.$http.post(baseURL_.lyjUrl+'/organization/add',this.$qs.stringify(this.form));
                    }
                    this.$message(addOrEdit.data.data);
                    if(addOrEdit.data.statusCode==200){
                        this.editVisible=false;
                    }
                    this.getData();
                }
                
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
                const del = await this.$http.get(baseURL_.lyjUrl+'/organization/delete',{ 
                    params: {'id':id}
                });
                this.$message(del.data.data);
                if(del.data.statusCode==200){
                   this.getData();
                }
                  
            },
            async handleEdit(index, row) {
                this.titleName="修改";
                const permiss = await this.$http.get(baseURL_.lyjUrl+'/organization/getById',{ 
                    params: {'id':row.id}
                });
                if(permiss.data.statusCode==200){
                    this.form=permiss.data.data
                    if(this.form.treeDepth=='1'){
                        this.parentNameShow=false;
                    }else{
                        this.parentNameShow=true;
                    }
                   
                }
                 this.editVisible=true;
            },
            showUser(index, row){
                this.$router.push({ path:'/org_user',query: {org_id: row.id}})
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
     
</style>
<style>
#table_id .el-table__body .cell .el-table__expand-icon{
     display:inline-block !important;
    }
</style>