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
                 <el-button type="primary" icon="el-icon-document" class="handle-del mr10" v-if="button_role&&button_role.export" @click="exportq">导出</el-button>
                 项目名称：<el-input placeholder="项目名称" v-model="projectName"  class="handle-input mr10" style="width:150px;"></el-input>
                 科室名称：<el-input placeholder="科室名称" v-model="orgName"  class="handle-input mr10" style="width:150px;"></el-input>
                 <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
                 <el-button type="primary" icon="el-icon-search" @click="rest">重置</el-button>
            </div>
            <el-table  :data="tableData" border class="table" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" ></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" >
                    <template slot-scope="scope">
                         <span >{{(page-1)*pageSize+scope.$index+1}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="projectName" label="所属项目"  align="center" ></el-table-column>
                <el-table-column prop="expenditureTime" label="支出时间"  align="center" width="150"></el-table-column>
                <el-table-column prop="expenditureTotal" label="支出金额（元）"  align="center" width="150"></el-table-column>
                <el-table-column prop="expenditureUsing" label="用途"  align="center" ></el-table-column> 
                <el-table-column prop="partyBUnits" label="乙方单位"  align="center" width="100"></el-table-column> 
                <el-table-column prop="orgName" label="所属科室"  align="center" width="100"></el-table-column>
                <el-table-column prop="status" label="状态"  align="center" width="80">
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
        <el-dialog :title="titleName"  :visible.sync="editVisible" width="35%"  @close="closeDilog('form')">
            <el-form ref="form" :model="form" :rules="rules" label-width="130px">
                <el-form-item label="所属科室" prop="org_value">
                    <el-select v-model="form.org_value" filterable @change="changeOrg"  style="width:300px;">
                        <el-option
                        v-for="item in options_org"
                        :key="item.orgId"
                        :label="item.orgName"
                        :value="item.orgId+'@'+item.orgName">
                        </el-option>
                   </el-select>
                </el-form-item>
                <el-form-item label="项目名称" prop="project_value">
                  <el-select v-model="form.project_value" filterable placeholder="请选择项目" @change="selectProject">
                        <el-option
                        v-for="item in project_option"
                        :key="item.id"
                        :label="item.projectName"
                        :value="item.id+'@'+item.projectName">
                        </el-option>
                   </el-select>
                 
                </el-form-item>
                 <el-form-item label="支出时间" prop="expenditureTime">
                    <el-date-picker
                            v-model="form.expenditureTime"
                            type="date"
                            format="yyyy-MM-dd"
                            value-format="yyyy-MM-dd"
                            placeholder="选择日期">
                        </el-date-picker>
                  
                </el-form-item>
                <el-form-item label="支出金额（元）" prop="expenditureTotal">
                    <el-input v-model="form.expenditureTotal" class="input"></el-input>
                </el-form-item>
                <el-form-item label="乙方单位" prop="partyBUnits">
                    <el-input v-model="form.partyBUnits" class="input"></el-input>
                </el-form-item>
                <el-form-item label="用途"  prop="expenditureUsing">
                   <el-input type="textarea" v-model="form.expenditureUsing"></el-input>
                </el-form-item>
                <el-form-item label="支出凭证">
                  <el-upload  class="upload-class"
                    :action="uploadUrl()"
                    :on-success="expenditureSuccess"
                    :on-remove="handleExpenditureRemove"
                    multiple
                    :limit="10"
                    :file-list="expenditureList">
                    <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
                </el-form-item>
                <el-form-item label="其他附件">
                  <el-upload  class="upload-class"
                    :action="uploadUrl()"
                    :on-success="otherSuccess"
                    :on-remove="handleOtherRemove"
                    multiple
                    :limit="10"
                    :file-list="otherList">
                    <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
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
                project_option:[],
                 rules: {
                    org_value: [
                        { required: true, message: '请填选择科室', trigger: 'blur' }
                    ],
                    project_value:[
                        { required: true, message: '请选择项目', trigger: 'blur' }
                    ],
                    expenditureTime:[
                        { required: true, message: '请选择支出时间', trigger: 'blur' }
                    ],
                     
                    expenditureTotal:[
                        { required: true, message: '请填写支出金额', trigger: 'blur' },
                        { validator:valiNumberPass1, trigger: "blur" }
                    ],
                    partyBUnits:[
                        { required: true, message: '请填写乙方单位', trigger: 'blur' }
                    ],
                    expenditureUsing:[
                        { required: true, message: '请填写用途', trigger: 'blur' }
                    ],
                    
                },
                options_year:'',
                options_month:'',
                expenditureList:[],
                otherList:[],
                options_org:[],
                
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
                   partyBUnits:'',
                   org_value:'',
                   project_value:'',
                 
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
            async exportq(){
               const token = localStorage.getItem('forestToken');
               location.href=baseURL_.lyjUrl+'/expenditure/exportList?projectName='+this.projectName+'&orgName='+this.orgName+"&token="+token
              
            },
             handleLook(index, row){
                this.$router.push({ path:'/expenditure_details',query: {id: row.id}})
            },
            selectProject(){
                this.$forceUpdate();
            },
             closeDilog:function(form){
        
                this.$refs[form].resetFields();//将form表单重置
            },
            async button(){
                var but=await this.$http.get(baseURL_.loginUrl+'/permission/button',{ 
                    params: {'code':this.$route.path}
                });
                this.button_role=but.data.data;
            },
            uploadUrl() {
              return baseURL_.fileUrl+'/file/upload';
            },
            handleExpenditureRemove(file,fileList){
               this.expenditureList=fileList;
            },
            handleOtherRemove(file,fileList){
                this.otherList=fileList;
            },
            expenditureSuccess(res, file,fileList) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.expenditureList=fileList;
              
            //   this.form.expenditureAttachmentName=file.response.data.fileName;
            //   this.form.expenditureAttachment=file.response.data.path;
            },
            otherSuccess(res, file,fileList) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.otherList=fileList;
            //  this.form.otherAttachmentName=file.response.data.fileName;
            //  this.form.otherAttachment=file.response.data.path;
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
             async changeOrg(val){
                this.project_option=[];
                this.form.project_value='';
                const projects = await this.$http.get(baseURL_.lyjUrl+'/projects/getUserAll',{ 
                    params:{'orgId':val.split("@")[0],'type':'expenditure','id':this.edit_id}
                    });
                this.project_option=projects.data.data;
            },
            search(){
                this.getData();
            },
            rest(){
                this.projectName='';
                this.orgName='';
                this.getData();
            },
            async saveEdit(status){
                this.form.status=status;
                var addOrEdit={};
                if(this.form.project_value){
                   this.form.projectId=this.form.project_value.split("@")[0];
                   this.form.projectName=this.form.project_value.split("@")[1];
                
                }
                 if(this.form.org_value){
                   this.form.orgId=this.form.org_value.split("@")[0];
                   this.form.orgName=this.form.org_value.split("@")[1];
                 }
                 var flg=true;
                if(this.form.status==1){
                    this.$refs['form'].validate((valid) => {
                    if (!valid) {
                        flg=false;
                        } 
                    })
                 }
                if(flg){

                    var c_list=[]; 
                        if(this.otherList.length>0){
                            for(var i=0;i<this.otherList.length;i++){
                                var obj={};
                                if(this.otherList[i].response){
                                    obj.url=this.otherList[i].response.data.path;
                                    obj.name=this.otherList[i].response.data.fileName;
                                }else{
                                    obj.url=this.otherList[i].url;
                                    obj.name=this.otherList[i].name;
                                }
                                c_list.push(obj);
                            }
                         }
                        this.form.otherAttachment=JSON.stringify(c_list);
                   

                        var o_list=[]; 
                        if(this.expenditureList.length>0){
                                for(var i=0;i<this.expenditureList.length;i++){
                                    var obj={};
                                    if(this.expenditureList[i].response){
                                        obj.url=this.expenditureList[i].response.data.path;
                                        obj.name=this.expenditureList[i].response.data.fileName;
                                    }else{
                                        obj.url=this.expenditureList[i].url;
                                        obj.name=this.expenditureList[i].name;
                                    }
                                    o_list.push(obj);
                                }
                            }
                         
                        this.form.expenditureAttachment=JSON.stringify(o_list);

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
                }
                
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
                if(row.status=='1'){
                     this.$message("归档数据不允许操作");
                     return;
                }
                this.titleName="修改";
                this.edit_id=row.id;
                this.expenditureList=[];
                this.otherList=[];
                

                const user1 = await this.$http.get(baseURL_.lyjUrl+'/home/getOnlineUser',{
                    params:{'id':row.id,'type':'expenditure'}
                });
                this.options_org=user1.data.data.org;

                const user = await this.$http.get(baseURL_.lyjUrl+'/expenditure/getById',{ 
                    params: {'id':row.id}
                });
                if(user.data.statusCode==200){
                    this.form=user.data.data
                    this.form.org_value=user.data.data.orgId+"@"+user.data.data.orgName

                    const projects = await this.$http.get(baseURL_.lyjUrl+'/projects/getUserAll',{
                        params:{'orgId':user.data.data.orgId,'type':'expenditure','id':row.id}
                    });
                    this.project_option=projects.data.data;

                    this.form.project_value=user.data.data.projectId+'@'+user.data.data.projectName;
                    if(user.data.data.otherAttachment){
                        // var contracArry=[];
                        // var obj={};
                        // obj.name=user.data.data.otherAttachmentName;
                        // obj.url=user.data.data.otherAttachment;
                        // contracArry.push(obj);
                        this.otherList=eval(user.data.data.otherAttachment);
                    }
                    if(user.data.data.expenditureAttachment){
                        // var contracArry=[];
                        // var obj={};
                        // obj.name=user.data.data.expenditureAttachmentName;
                        // obj.url=user.data.data.expenditureAttachment;
                        // contracArry.push(obj);
                        this.expenditureList=eval(user.data.data.expenditureAttachment);
                    }
                }
                 this.editVisible=true;
            },
            async add(){
               this.edit_id='';
               this.form={};
               this.expenditureList=[];
               this.otherList=[];
               this.form.org_value='';
               this.options_org=[];
               this.project_option=[];
               this.form.project_value='';
               const user = await this.$http.get(baseURL_.sysUrl+'/sysUser/getOnlineUser');
               this.options_org=user.data.data.org;
               this.form.org_value=user.data.data.org[0].orgId+"@"+user.data.data.org[0].orgName

               const projects = await this.$http.get(baseURL_.lyjUrl+'/projects/getUserAll',{
                    params:{'orgId':this.form.org_value.split("@")[0]}
               });
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
        z-index:999;
    }


</style>