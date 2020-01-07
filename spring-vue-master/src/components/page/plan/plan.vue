<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 字典类型</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-plus" class="handle-del mr10" v-if="button_role&&button_role.add" @click="add">添加</el-button>
                 项目名称：<el-input placeholder="项目名称" v-model="projectName"  class="handle-input mr10" style="width:150px;"></el-input>
                 科室名称：<el-input placeholder="科室名称" v-model="orgName"  class="handle-input mr10" style="width:150px;"></el-input>
                 <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
                 <el-button type="primary" icon="el-icon-search" @click="rest">重置</el-button>

            </div>
            <el-table  :data="tableData" border class="table" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" ></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="projectName" label="所属项目"  align="center" ></el-table-column>
                <el-table-column prop="year"  label="计划支出时间"  align="center" width="200">
                     <template slot-scope="scope">
                         <span >{{scope.row.year}}年{{scope.row.month}}月</span>
                    </template>
                </el-table-column>
                <el-table-column prop="planTotal" label="计划支出金额（元）"  align="center" width="200"></el-table-column>
                <el-table-column prop="planUsing" label="用途"  align="center" width="300"></el-table-column> 
                <el-table-column prop="orgName" label="所属科室"  align="center" width="100"></el-table-column>

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
        <el-dialog :title="titleName"  :visible.sync="editVisible" width="35%">
            <el-form ref="form" :model="form" label-width="130px">
                <el-form-item label="所属科室">
                    <el-input v-model="form.orgName" readOnly class="input"></el-input>
                </el-form-item>
                <el-form-item label="项目名称">
                  <el-select v-model="project_value" filterable placeholder="请选择项目">
                        <el-option
                        v-for="item in project_option"
                        :key="item.id"
                        :label="item.projectName"
                        :value="item.id+'@'+item.projectName">
                        </el-option>
                   </el-select>
                 
                </el-form-item>
                <el-form-item label="计划支出时间">
                    <el-select v-model="options_year" filterable placeholder="年份" style="width:100px;">
                        <el-option label="2020年" value="2020"></el-option>
                        <el-option label="2021年" value="2021"></el-option>
                        <el-option label="2022年" value="2022"></el-option>
                        <el-option label="2023年" value="2023"></el-option>
                        <el-option label="2024年" value="2024"></el-option>
                   </el-select>
                   <el-select v-model="options_month" filterable placeholder="月份" style="width:100px;">
                        <el-option label="1月" value="1"></el-option>
                        <el-option label="2月" value="2"></el-option>
                        <el-option label="3月" value="3"></el-option>
                        <el-option label="4月" value="4"></el-option>
                        <el-option label="5月" value="5"></el-option>
                        <el-option label="6月" value="6"></el-option>
                        <el-option label="7月" value="7"></el-option>
                        <el-option label="8月" value="8"></el-option>
                        <el-option label="9月" value="9"></el-option>
                        <el-option label="10月" value="10"></el-option>
                        <el-option label="11月" value="11"></el-option>
                        <el-option label="12月" value="12"></el-option>
                   </el-select>
                </el-form-item>
                <el-form-item label="支出金额（元）">
                    <el-input v-model="form.planTotal" class="input"></el-input>
                </el-form-item>
                
                <el-form-item label="用途">
                   <el-input type="textarea" v-model="form.planUsing"></el-input>
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
                project_option:[],
                project_value:'',
                options_year:'',
                options_month:'',
                button_role:{},
                form:{
                   id:'',
                   projectName:'',
                   projectId:'',
                   orgName:'',
                   orgId:'',
                   planTotal:'',
                   planUsing:'',
                   year:'',
                   month:'',
                   createTime:''
                },
                
                projectName:'',
                orgName:'',
            }
        },
        created() {
            this.button();
            this.getData();
        },
        computed: {
           
        },
        methods: {
            async button(){
                var but=await this.$http.get(baseURL_.loginUrl+'/permission/button',{ 
                    params: {'code':this.$route.path}
                });
                this.button_role=but.data.data;
            },
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
            search(){
                this.getData();
            },
            rest(){
                this.projectName='';
                this.orgName='';
                this.getData();
            },
            async saveEdit(){
                var addOrEdit={};
                if(this.project_value){
                   this.form.projectId=this.project_value.split("@")[0];
                   this.form.projectName=this.project_value.split("@")[1];
                
                }
                this.form.year=this.options_year;
                this.form.month =this.options_month;
                if(this.form.id!=null){
                   addOrEdit= await this.$http.post(baseURL_.lyjUrl+'/plan/update',this.$qs.stringify(this.form));
                }else{
                    addOrEdit= await this.$http.post(baseURL_.lyjUrl+'/plan/save',this.$qs.stringify(this.form));
                }
                this.$message(addOrEdit.data.data);
                if(addOrEdit.data.statusCode==200){
                    this.editVisible=false;
                }
                this.getData();
            },
            async delete(id){
                const del = await this.$http.get(baseURL_.lyjUrl+'/plan/delete',{ 
                    params: {'id':id}
                });
                this.$message(del.data.data);
                if(del.data.statusCode==200){
                   this.getData();
                }
                  
            },
            async handleEdit(index, row) {
                this.titleName="修改";
                const projects = await this.$http.get(baseURL_.lyjUrl+'/projects/getUserAll');
                this.project_option=projects.data.data;
                const user = await this.$http.get(baseURL_.lyjUrl+'/plan/getById',{ 
                    params: {'id':row.id}
                });
                if(user.data.statusCode==200){
                    this.form=user.data.data
                    this.project_value=user.data.data.projectId+'@'+user.data.data.projectName;
                    this.options_year=user.data.data.year+'';
                    this.options_month=user.data.data.month+'';
                }
                 this.editVisible=true;
            },
            async add(){
               this.form={};

               const user = await this.$http.get(baseURL_.sysUrl+'/sysUser/getOnlineUser');
               this.form.orgName=user.data.data.orgName;
               this.form.orgId=user.data.data.orgId;
               const projects = await this.$http.get(baseURL_.lyjUrl+'/projects/getUserAll');
               this.project_option=projects.data.data;

               this.editVisible=true;
               this.titleName="添加";
            },
            // 获取 easy-mock 的模拟数据
            async getData() {
                const user = await this.$http.get(baseURL_.lyjUrl+'/plan/list',{ 
                    params: {'page':this.page,'pageSize':this.pageSize,'projectName':this.projectName,
                         'orgName':this.orgName}
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