<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 项目管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-plus" class="handle-del mr10" v-if="button_role&&button_role.add" @click="add">添加</el-button>
                项目名称：<el-input placeholder="项目名称" v-model="projectName"  class="handle-input mr10" style="width:150px;"></el-input>
                科室名称：<el-input placeholder="科室名称" v-model="orgName"  class="handle-input mr10" style="width:150px;"></el-input>
                负责人：<el-input placeholder="负责人" v-model="leader"  class="handle-input mr10" style="width:150px;"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
                <el-button type="primary" icon="el-icon-search" @click="rest">重置</el-button>
            </div>
            <el-table  :data="tableData" border class="table" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" ></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="projectName" label="项目名称"  align="center" ></el-table-column>
                <el-table-column prop="budget" label="总预算（ 元）"  align="center" width="100"></el-table-column>
                <el-table-column prop="orgName" label="所属科室"  align="center" width="100"></el-table-column>
                <el-table-column prop="projectLeader" label="项目负责人"  align="center" width="100"></el-table-column>
                <el-table-column prop="leaderPhone" label="联系方式"  align="center" width="100"></el-table-column>
                <el-table-column prop="per" label="执行进度"  align="center" width="100">
                    <template slot-scope="scope">
                         <span class="red">{{scope.row.per}}%</span>
                    </template>
                </el-table-column>
                <el-table-column prop="usedTotal" label="已执行金额（元）"  align="center" width="100">
                   <template slot-scope="scope">
                         <span class="red">{{scope.row.usedTotal}}</span>
                   </template>
                </el-table-column>
                <el-table-column prop="laveTotal" label="剩余金额（元）"  align="center" width="100">
                    <template slot-scope="scope">
                         <span class="red">{{scope.row.laveTotal}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center" v-if="button_role&&(button_role.delete||button_role.edit)">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" v-if="button_role&&button_role.edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" v-if="button_role&&button_role.delete" @click="handleDelete(scope.$index, scope.row)" >删除</el-button>
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
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="所属科室">
                    <el-input v-model="form.orgName" readOnly class="input"></el-input>
                </el-form-item>
                <el-form-item label="项目名称">
                 <el-input v-model="form.projectName" class="input"></el-input>
                </el-form-item>
                <el-form-item label="总预算（ 元）">
                    <el-input v-model="form.budget" class="input"></el-input>
                </el-form-item>
                <el-form-item label="项目负责人">
                    <el-input v-model="form.projectLeader" class="input"></el-input>
                </el-form-item>
                <el-form-item label="联系方式">
                    <el-input v-model="form.leaderPhone" class="input"></el-input>
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
                button_role:{},
                form:{
                   id:'',
                   projectName:'',
                   budget:'',
                   orgName:'',
                   orgId:'',
                   projectLeader:'',
                   leaderPhone:''
                },
                
                projectName:'',
                orgName:'',
                leader:''

            }
        },
        created() {
            this.button();
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
            async button(){
                var but=await this.$http.get(baseURL_.loginUrl+'/permission/button',{ 
                    params: {'code':this.$route.path}
                });
                this.button_role=but.data.data;
            },
             search(){
                this.getData();
            },
            rest(){
                this.projectName='';
                this.orgName='';
                this.leader='';
               this.getData();
            },
            async saveEdit(){
                var addOrEdit={};
                if(this.form.id!=null){
                   addOrEdit= await this.$http.post(baseURL_.lyjUrl+'/projects/update',this.$qs.stringify(this.form));
                }else{
                    addOrEdit= await this.$http.post(baseURL_.lyjUrl+'/projects/save',this.$qs.stringify(this.form));
                }
                this.$message(addOrEdit.data.data);
                if(addOrEdit.data.statusCode==200){
                    this.editVisible=false;
                }
                this.getData();
            },
            async delete(id){
                const del = await this.$http.get(baseURL_.lyjUrl+'/projects/delete',{ 
                    params: {'id':id}
                });
                this.$message(del.data.data);
                if(del.data.statusCode==200){
                   this.getData();
                }
                  
            },
            async handleEdit(index, row) {
                this.titleName="修改";
                const user = await this.$http.get(baseURL_.lyjUrl+'/projects/getById',{ 
                    params: {'id':row.id}
                });
                if(user.data.statusCode==200){
                    this.form=user.data.data
                    
                }
                 this.editVisible=true;
            },
            async add(){
               this.form={};

               const user = await this.$http.get(baseURL_.sysUrl+'/sysUser/getOnlineUser');
               this.form.orgName=user.data.data.orgName;
               this.form.orgId=user.data.data.orgId;
               this.editVisible=true;
               this.titleName="添加";
            },
            // 获取 easy-mock 的模拟数据
            async getData() {
                const user = await this.$http.get(baseURL_.lyjUrl+'/projects/list',{ 
                    params: {'page':this.page,'pageSize':this.pageSize,'projectName':this.projectName,
                            'orgName':this.orgName,'leader':this.leader}
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