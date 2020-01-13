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
                <el-button type="primary" icon="el-icon-document" class="handle-del mr10" v-if="button_role&&button_role.export" @click="exportq">导出</el-button>
                项目名称：<el-input placeholder="项目名称" v-model="projectName"  class="handle-input mr10" style="width:150px;"></el-input>
                科室名称：<el-input placeholder="科室名称" v-model="orgName"  class="handle-input mr10" style="width:150px;"></el-input>
                负责人：<el-input placeholder="负责人" v-model="leader"  class="handle-input mr10" style="width:150px;"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
                <el-button type="primary" icon="el-icon-search" @click="rest">重置</el-button>
            </div>
            <el-table id="table_id" row-key="id" lazy :data="tableData" border class="table"  :tree-props="{children: 'children', hasChildren: 'hasChildren'}" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" ></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="projectName" label="项目名称"  align="center" ></el-table-column>
                <el-table-column prop="year" label="年度"  align="center" width="80"></el-table-column>
                <el-table-column prop="budget" label="总预算（ 元）"  align="center" width="100"></el-table-column>
                <el-table-column prop="projectSource" label="资金来源"  align="center" width="100"></el-table-column>
                <el-table-column prop="orgName" label="所属科室"  align="center" width="100"></el-table-column>
                <el-table-column prop="projectLeader" label="项目负责人"  align="center" width="100"></el-table-column>
                <el-table-column prop="leaderPhone" label="联系方式"  align="center" width="100"></el-table-column>
                <el-table-column prop="per" label="执行进度"  align="center" width="80">
                    <template slot-scope="scope">
                         <span class="red">{{scope.row.per}}%</span>
                    </template>
                </el-table-column>
                <el-table-column prop="usedTotal" label="已执行金额（元）"  align="center" width="80">
                   <template slot-scope="scope">
                         <span class="red">{{scope.row.usedTotal}}</span>
                   </template>
                </el-table-column>
                <el-table-column prop="laveTotal" label="剩余金额（元）"  align="center" width="80">
                    <template slot-scope="scope">
                         <span class="red">{{scope.row.laveTotal}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="status" label="状态"  align="center" width="50">
                    <template slot-scope="scope">
                         <span class="blue" v-if="scope.row.status=='0'">暂存</span>
                         <span class="blue" v-if="scope.row.status=='1'">归档</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center" v-if="button_role&&(button_role.delete||button_role.edit||button_role.look)">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" v-if="button_role&&button_role.edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" v-if="button_role&&button_role.delete" @click="handleDelete(scope.$index, scope.row)" >删除</el-button>
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
        <el-dialog :title="titleName"  :visible.sync="editVisible" width="30%" @close="closeDilog('form')">
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                 <el-form-item label="上级项目名称" v-show="parentNameShow">
                   <el-input v-model="form.parentName" readOnly class="input"></el-input>
                 </el-form-item>

                <el-form-item label="所属科室" prop="org_value">
                    
                    <el-select v-model="form.org_value" filterable   style="width:300px;">
                        <el-option
                        v-for="item in options_org"
                        :key="item.orgId"
                        :label="item.orgName"
                        :value="item.orgId+'@'+item.orgName">
                        </el-option>
                   </el-select>

                </el-form-item>
                <el-form-item label="年份" prop="year">
                   <el-select v-model="form.year" filterable placeholder="年份" style="width:100px;">
                        <el-option label="2020年" value="2020"></el-option>
                        <el-option label="2021年" value="2021"></el-option>
                        <el-option label="2022年" value="2022"></el-option>
                        <el-option label="2023年" value="2023"></el-option>
                        <el-option label="2024年" value="2024"></el-option>
                        <el-option label="2025年" value="2025"></el-option>
                        <el-option label="2026年" value="2026"></el-option>
                        <el-option label="2027年" value="2027"></el-option>
                        <el-option label="2028年" value="2028"></el-option>
                        <el-option label="2029年" value="2029"></el-option>
                        <el-option label="2030年" value="2030"></el-option>
                   </el-select>
                </el-form-item>
                
                <el-form-item label="项目名称" prop="projectName">
                 <el-input v-model="form.projectName" class="input"></el-input>
                </el-form-item>
                <el-form-item label="总预算（ 元）" prop="budget">
                    <el-input v-model="form.budget" class="input"></el-input>
                </el-form-item>
                <el-form-item label="资金来源" prop="projectSource">
                    <el-input v-model="form.projectSource" class="input"></el-input>
                </el-form-item>
                <el-form-item label="项目负责人" prop="projectLeader">
                    <el-input v-model="form.projectLeader" class="input"></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="leaderPhone">
                    <el-input v-model="form.leaderPhone" class="input"></el-input>
                </el-form-item>
                
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit(0)">暂 存</el-button>
                <el-button type="primary" @click="saveEdit(1)">确 定</el-button>
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
            var valiNumberPass1 = (rule, value, callback) => {//包含小数的数字
                let reg = /^[+-]?(0|([1-9]\d*))(\.\d+)?$/g;
                if (!reg.test(value)) {
                    callback(new Error('请输正确的金额'));
                } else {
                    callback();
                }
            };
            return {
                editVisible: false,
                page:1,
                total:1000,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
                titleName:'',
                button_role:{},
                options_org:[],
                parentNameShow:false,
                rules: {
                    org_value: [
                        { required: true, message: '请填选择科室', trigger: 'blur' }
                    ],
                    year:[
                        { required: true, message: '请选择年份', trigger: 'blur' }
                    ],
                    projectName:[
                        { required: true, message: '请填写项目名称', trigger: 'blur' }
                    ],
                     budget:[
                        { required: true, message: '请填写总预算', trigger: 'blur' },
                        { validator:valiNumberPass1, trigger: "blur" }
                    ],
                    projectSource:[
                        { required: true, message: '请填写资金来源', trigger: 'blur' }
                    ],
                    projectLeader:[
                        { required: true, message: '请填写项目负责人', trigger: 'change' }
                    ],
                    leaderPhone:[
                        { required: true, message: '请填写联系方式', trigger: 'change' }
                    ],
                },
                form:{
                   id:'',
                   projectName:'',
                   budget:'',
                   orgName:'',
                   orgId:'',
                   projectLeader:'',
                   leaderPhone:'',
                   projectSource:'',
                   projectLevel:'',
                   parentId:'',
                   status:'',
                   year:'',
                   org_value:'',
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
           async exportq(){
               const token = localStorage.getItem('forestToken');
               location.href=baseURL_.lyjUrl+'/projects/exportList?projectName='+this.projectName+'&orgName='+this.orgName+'&leader'+this.leader+"&token="+token
              
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
                if(row.status=='1'){
                     this.$message("归档数据不允许操作");
                     return;
                }
                this.$confirm('确认删除？')
                .then( e=> {
                  
                   this.delete(row.id);

                }).catch(_ => {});
                 
            },

            handleLook(index, row){
                 this.$router.push({ path:'/project_details',query: {id: row.id}})
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
            async saveEdit(status){
                var addOrEdit={};
                 this.form.status=status;
                 var flg=true;
                 if(this.form.status==1){
                    this.$refs['form'].validate((valid) => {
                    if (!valid) {
                        flg=false;
                        } 
                    })
                 }
                 if(flg){
                    if(this.form.org_value){
                        this.form.orgId=this.form.org_value.split("@")[0];
                        this.form.orgName=this.form.org_value.split("@")[1];
                    }
                    
                    delete this.form.children;
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
                 }
                 
              
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
                if(row.status=='1'){
                     this.$message("归档数据不允许操作");
                     return;
                }
                this.titleName="修改";
                this.options_org=[];
                this.form.org_value="";
                
                const user1 = await this.$http.get(baseURL_.lyjUrl+'/home/getOnlineUser',{
                   params:{'id':row.id,'type':'project'}
                });
                this.options_org=user1.data.data.org;
                const user = await this.$http.get(baseURL_.lyjUrl+'/projects/getById',{ 
                    params: {'id':row.id}
                });
                if(user.data.statusCode==200){
                    this.form=user.data.data
                    this.form.org_value=user.data.data.orgId+"@"+user.data.data.orgName
                    this.form.year=user.data.data.year+"";
                }
                 this.editVisible=true;
               
            },
            closeDilog:function(form){
        
                this.$refs[form].resetFields();//将form表单重置
            },
            async add(){
               this.form={};
               this.options_org=[];
               this.form.org_value="";
             
                if(this.multipleSelection.length>1){
                  this.$message("不能选择多条数据进行操作");
                  return;
               }
               const user = await this.$http.get(baseURL_.lyjUrl+'/home/getOnlineUser');
               this.options_org=user.data.data.org;
               if(this.multipleSelection.length==0){
                    this.parentNameShow=false;
                    this.form.org_value=user.data.data.org[0].orgId+"@"+user.data.data.org[0].orgName
               }else{
                     this.parentNameShow=true;
                     this.form.parentName=this.multipleSelection[0].projectName;
                     this.form.parentId=this.multipleSelection[0].id;
                     this.form.org_value=this.multipleSelection[0].orgId+"@"+this.multipleSelection[0].orgName
                     this.form.projectLevel=this.multipleSelection[0].projectLevel+1;
               }

               this.editVisible=true;
               this.titleName="添加";
               //this.closeDilog("form");
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
    .blue{
        color:blue;
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
<style>
#table_id .el-table__body .cell .el-table__expand-icon{
     display:inline-block !important;
    }
</style>