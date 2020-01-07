<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 执行情况管理</el-breadcrumb-item>
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
                <el-table-column prop="expenditureTime" label="支出时间"  align="center" width="150"></el-table-column>
                <el-table-column prop="expenditureTotal" label="支出金额（元）"  align="center" width="200"></el-table-column>
                <el-table-column prop="expenditureUsing" label="用途"  align="center" width="300"></el-table-column> 
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
                 <el-form-item label="支出时间">
                    <el-date-picker
                            v-model="form.expenditureTime"
                            type="date"
                            format="yyyy-MM-dd"
                            value-format="yyyy-MM-dd"
                            placeholder="选择日期">
                        </el-date-picker>
                  
                </el-form-item>
                <el-form-item label="支出金额（元）">
                    <el-input v-model="form.expenditureTotal" class="input"></el-input>
                </el-form-item>
                
                <el-form-item label="用途">
                   <el-input type="textarea" v-model="form.expenditureUsing"></el-input>
                </el-form-item>
                <el-form-item label="支出凭证">
                  <el-upload  class="upload-class"
                    :action="uploadUrl()"
                    :on-success="expenditureSuccess"
                    multiple
                    :limit="1"
                    :file-list="expenditureList">
                    <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
                </el-form-item>
                <el-form-item label="其他附件">
                  <el-upload  class="upload-class"
                    :action="uploadUrl()"
                    :on-success="otherSuccess"
                    multiple
                    :limit="1"
                    :file-list="otherList">
                    <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
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
                expenditureList:'',
                otherList:'',
                button_role:{},
                form:{
                   id:'',
                   projectName:'',
                   projectId:'',
                   orgName:'',
                   orgId:'',
                   expenditureTime:'',
                   expenditureTotal:'',
                   expenditureUsing:'',
                 
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
            uploadUrl() {
              return baseURL_.fileUrl+'/file/upload';
            },
            expenditureSuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.form.expenditureAttachmentName=file.response.data.fileName;
              this.form.expenditureAttachment=file.response.data.path;
            },
            otherSuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
             this.form.otherAttachmentName=file.response.data.fileName;
             this.form.otherAttachment=file.response.data.path;
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
                
                if(this.form.id!=null){
                   addOrEdit= await this.$http.post(baseURL_.lyjUrl+'/expenditure/update',this.$qs.stringify(this.form));
                }else{
                    addOrEdit= await this.$http.post(baseURL_.lyjUrl+'/expenditure/save',this.$qs.stringify(this.form));
                }
                this.$message(addOrEdit.data.data);
                if(addOrEdit.data.statusCode==200){
                    this.editVisible=false;
                }
                this.getData();
            },
            async delete(id){
                const del = await this.$http.get(baseURL_.lyjUrl+'/expenditure/delete',{ 
                    params: {'id':id}
                });
                this.$message(del.data.data);
                if(del.data.statusCode==200){
                   this.getData();
                }
                  
            },
            async handleEdit(index, row) {
                this.titleName="修改";
                this.expenditureList=[];
                this.otherList=[];
                const projects = await this.$http.get(baseURL_.lyjUrl+'/projects/getUserAll');
                this.project_option=projects.data.data;
                const user = await this.$http.get(baseURL_.lyjUrl+'/expenditure/getById',{ 
                    params: {'id':row.id}
                });
                if(user.data.statusCode==200){
                    this.form=user.data.data
                    this.project_value=user.data.data.projectId+'@'+user.data.data.projectName;
                    if(user.data.data.otherAttachment){
                        var contracArry=[];
                        var obj={};
                        obj.name=user.data.data.otherAttachmentName;
                        obj.url=user.data.data.otherAttachment;
                        contracArry.push(obj);
                        this.otherList=contracArry;
                    }
                    if(user.data.data.expenditureAttachment){
                        var contracArry=[];
                        var obj={};
                        obj.name=user.data.data.expenditureAttachmentName;
                        obj.url=user.data.data.expenditureAttachment;
                        contracArry.push(obj);
                        this.expenditureList=contracArry;
                    }



                }
                 this.editVisible=true;
            },
            async add(){
               this.form={};
               this.expenditureList=[];
               this.otherList=[];
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
                const user = await this.$http.get(baseURL_.lyjUrl+'/expenditure/list',{ 
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

<style>
 .upload-class .el-upload--text{
        width: auto;
        height: auto;
        border:none;
        float: left;
        z-index:999;
    }


</style>