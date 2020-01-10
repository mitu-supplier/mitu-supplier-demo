<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 供应商管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="审批人">
                        <el-input v-model="formInline.user" placeholder="审批人"></el-input>
                    </el-form-item>
                    <el-form-item label="活动区域">
                        <el-select v-model="formInline.region" placeholder="活动区域">
                            <el-option label="区域一" value="shanghai"></el-option>
                            <el-option label="区域二" value="beijing"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="onSubmit">查询</el-button>
                        <el-button type="primary" icon="el-icon-refresh" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <!-- <el-input placeholder="筛选关键词" class="handle-input mr10"></el-input> -->
                <el-button type="primary" @click="aaa = true" icon="el-icon-plus" >新增</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable">
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="roleName" label="商户号"  align="center" width=""></el-table-column>
                <el-table-column prop="roleCode" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column prop="createTime" label="商户地址" align="center"  width=""></el-table-column>
                <el-table-column prop="createTime" label="入驻类型" align="center"  width=""></el-table-column>
                <el-table-column prop="createTime" label="联系人" align="center"  width=""></el-table-column>
                <el-table-column prop="createTime" label="联系电话" align="center"  width=""></el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
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
            <!-- <loding v-if="aaa"></loding> -->
        </div>
    </div>
</template>
  
<script>
    import { fetchData } from '../../../api/index';
    import baseURL_ from '@/utils/baseUrl.js';
    // import loding from './loding.vue';
    export default {
        // components:{
        //     loding
        // },
        name: 'basetable',
        data() {
            return {
                // data:["用户名","密码","确认密码"],
                // data:[
                //     {"name":"用户名","type":"text"},
                //     {"name":"密码","type":"password"},
                // ],
                aaa:false,
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
                suppids: '',
                formInline:{
                    region:'',
                    user:''
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
            },
            // 重置
            onReset(){
                this.formInline = {};
                this.getData();
            },
            // 搜索
            async onSubmit(){
                const permissions = await this.$http.get(baseURL_.sysUrl+'/sysRole/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'user':this.formInline.user,
                        'region':this.formInline.region
                    }
                });
                if(permissions.data.statusCode==200){
                  this.tableData=permissions.data.data.list;
                  this.total=permissions.data.data.total;
                }
            },
            // 初始化数据
            async getData() {
                const role = await this.$http.get(baseURL_.sysUrl+'/supplier/list',{ 
                    params: {'page':this.page,'pageSize':this.pageSize}
                });
                if(role.data.statusCode==200){
                  this.tableData=role.data.data.list;
                  this.total=role.data.data.total;
                  this.page=role.data.data.page;
                }
            },
            add(){
               this.form={};
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
            // 批量删除
            async DeteleAll(){
                const del = await this.$http.get(baseURL_.sysUrl+'/sysRole/delete',{ 
                    params: {'id':id}
                });
                this.$message(del.data.data);
                if(del.data.statusCode==200){
                   this.getData();
                }
            },
            async delete(id){
                const del = await this.$http.get(baseURL_.sysUrl+'/sysRole/delete',{ 
                    params: {'id':id}
                });
                this.$message(del.data.data);
                if(del.data.statusCode==200){
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