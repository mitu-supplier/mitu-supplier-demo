<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 用户管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="登录名">
                        <el-input v-model="formInline.loginName" placeholder="登录名"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名">
                        <el-input v-model="formInline.name" placeholder="姓名"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号">
                        <el-input v-model="formInline.phone" placeholder="手机号"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱">
                        <el-input v-model="formInline.email" placeholder="邮箱"></el-input>
                    </el-form-item>
                    
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="searchBtn">搜索</el-button>
                        <el-button type="primary" icon="el-icon-refresh" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-plus" class="handle-del mr10" @click="add">添加</el-button>
                <!-- <el-input placeholder="用户名或登录名" v-model="name"  class="handle-input mr10"></el-input> -->
                
            </div>
            <el-table  :data="tableData" border class="table" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" ></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="loginName" label="登录名"  align="center" width="100"></el-table-column>
                <el-table-column prop="name" label="姓名"  align="center" width="100"></el-table-column>
                <el-table-column prop="phone" label="手机"  align="center" width="100"></el-table-column>
                <el-table-column prop="email" label="邮箱" align="center" width="150"> </el-table-column>
                <el-table-column prop="createTime" label="创建时间" align="center"  width="150"></el-table-column>
                <el-table-column prop="isStatus" label="是否启用" align="center" width="70">
                  <template slot-scope="scope">
                         <span v-if="scope.row.isStatus=='0'">启用</span>
                         <span v-if="scope.row.isStatus=='1'">禁用</span>
                    </template>
                </el-table-column>
                <el-table-column prop="loginTime" label="登录时间"  align="center" width="130"></el-table-column>
                <el-table-column prop="ip" label="登录ip"  align="center" width="100"></el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-setting" @click="ztreeEdit(scope.$index, scope.row)">设置角色</el-button>
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
        <el-dialog :title="titleName"  :visible.sync="editVisible" width="26%">
            <el-form ref="form" :model="form" label-width="70px" :rules="rules">
                <el-form-item label="姓名">
                <el-input v-model="form.name" class="input"></el-input>
                </el-form-item>
                <el-form-item label="登录名" prop="loginName">
                    <el-input v-model="form.loginName" class="input"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" v-if="addFlag">
                    <el-input v-model="form.password" type="password" class="input"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="form.phone" class="input"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" class="input"></el-input>
                </el-form-item>
                 <el-form-item label="是否启用">
                   <el-radio v-model="form.isStatus" :label="'0'">启用</el-radio>
                   <el-radio v-model="form.isStatus" :label="'1'">禁用</el-radio>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 编辑弹出框 添加 修改-->
        <el-dialog :title="ztreeTitleName" class="dialog-ztree dialogBox" :visible.sync="ztreeEditVisible" width="25%">
             <ul id="ztree" class="ztree"></ul>
             <span slot="footer" class="dialog-footer">
                <el-button @click="editVisiblehide">取 消</el-button>
                <el-button type="primary" @click="saveRole" class="m_rt">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import { fetchData } from '../../../../api/index';
    import baseURL_ from '@/utils/baseUrl.js';

    // 手机号校验
    var validPhone = (rule, value,callback)=>{
        if (!value){
            callback()
        }else{
            const reg = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/
            if (reg.test(value)) {
                callback()
            } else {
                return callback(new Error('请输入正确的手机号'))
            }
        }
    }
     
    // 邮箱格式校验
    var validEmail = (rule, value,callback)=>{
        if (!value){
            callback()
        }else{
            var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            if(!reg.test(value)){
                callback(new Error('请输入正确的邮箱'));
            }else{
                callback()
            }
        }
    }

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
                ztreeEditVisible:false,
                ztreeTitleName:'',
                user_id:'',
                form:{
                   id:'',
                   name:'',
                   loginName:'',
                   phone:'',
                   email:'',
                   password:'',
                   isStatus:'0'
                },
                addFlag: false,
                rules: {
                    loginName: [{ required: true, message: "请输入登录名", trigger: "blur" }],
                    password: [{ required: true, message: "请输入密码", trigger: "blur" }],
                    phone: [{ validator: validPhone, trigger: 'blur' }],
                    email: [{ validator: validEmail, trigger: 'blur' }],
                },
                 setting:{
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        },
                        key: {
                            name: "roleName"
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
                name:'',
                formInline:{
                    loginName:'',
                    name:'',
                    phone:'',
                    email:''
                }
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
                this.$refs['form'].validate(async valid => {
                    if (valid) {
                        var addOrEdit={};
                        if(this.form.id!=null){
                            addOrEdit= await this.$http.post(baseURL_.sysUrl+'/sysUser/update',this.$qs.stringify(this.form));
                        }else{
                            addOrEdit= await this.$http.post(baseURL_.sysUrl+'/sysUser/add',this.$qs.stringify(this.form));
                        }
                        this.$message(addOrEdit.data.data);
                        if(addOrEdit.data.statusCode==200){
                            this.editVisible=false;
                            this.getData();
                        }
                        
                    }
                })
            },
            async delete(id){
                const del = await this.$http.get(baseURL_.sysUrl+'/sysUser/delete',{ 
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
                this.addFlag = false;
                const user = await this.$http.get(baseURL_.sysUrl+'/sysUser/getById',{ 
                    params: {'id':row.id}
                });
                if(user.data.statusCode==200){
                    this.form.id=user.data.data.id;
                    this.form.name=user.data.data.name;
                    this.form.loginName=user.data.data.loginName;
                    this.form.phone=user.data.data.phone;
                    this.form.email=user.data.data.email;
                    this.form.isStatus=user.data.data.isStatus+"";
                    this.form.password=user.data.data.password;
                }
                 this.editVisible=true;
                //  this.$refs['form'].resetFields();
            },
            add(){
               this.form={};
               this.addFlag = true;
               this.editVisible=true;
            //    this.$refs['form'].resetFields();
               this.titleName="添加";
               this.form.isStatus='0';
            },
            // 获取 easy-mock 的模拟数据
            async getData() {
                const user = await this.$http.get(baseURL_.sysUrl+'/sysUser/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'loginName':this.formInline.loginName,
                        'name':this.formInline.name,
                        'phone':this.formInline.phone,
                        'email':this.formInline.email
                        }
                    });
                if(user.data.statusCode==200){
                  this.tableData=user.data.data.list;
                  this.total=user.data.data.total;
                  this.page=user.data.data.page;
                }
            },
            // 重置
            onReset(){
                this.formInline = {};
                this.getData();
            },
            // 搜索 
            async searchBtn(){
                const permissions = await this.$http.get(baseURL_.sysUrl+'/sysUser/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'loginName':this.formInline.loginName,
                        'name':this.formInline.name,
                        'phone':this.formInline.phone,
                        'email':this.formInline.email
                    }
                });
                if(permissions.data.statusCode==200){
                  this.tableData=permissions.data.data.list;
                  this.total=permissions.data.data.total;
                }
            },
            async ztreeEdit(index, row){
                this.ztreeTitleName="角色";
                this.ztreeEditVisible=true;
                const permiss = await this.$http.get(baseURL_.sysUrl+'/sysUser/getRoleByUserId',{ 
                    params: {'userId':row.id}
                });
                this.user_id=row.id;
                $.fn.zTree.init($("#ztree"), this.setting, permiss.data.data);
            },
            editVisiblehide(){
                // this.$refs['form'].resetFields();
                this.editVisible = false;
            },
            async saveRole(){
               var treeObj = $.fn.zTree.getZTreeObj("ztree");
               var nodes = treeObj.getCheckedNodes(true);
               if(nodes.length==0){
                 this.$message("请选择角色！");
                 return;
               }
               var permissArr=[];
               for(var i=0;i<nodes.length;i++){
                  permissArr.push(nodes[i].id);
               }
               var obj={};
               obj.userId=this.user_id;
               obj.roleIds=permissArr.join(",");
               var result= await this.$http.post(baseURL_.sysUrl+'/sysUser/saveRole',this.$qs.stringify(obj));
               this.$message(result.data.data);
               if(result.data.statusCode==200){
                 this.ztreeEditVisible=false;
                 this.role_id="";
               }
            },
            checkPhone(rule, value, callback) {
                if (value != null && value != '') {
                    const reg = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/
                    if (reg.test(value)) {
                        callback()
                    } else {
                        return callback(new Error('请输入正确的手机号'))
                    }
                }else{
                   callback() 
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
    .input{
        width:90%;
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