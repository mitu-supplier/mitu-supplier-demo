<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 项目支出计划管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-upload v-if="button_role&&button_role.import"
                    class="upload-excel-file-up"
                    ref="upload"
                    :headers="myHeaders"
                    :action="uploadUrl1()"
                    multiple
                    :limit="1"
                    :show-file-list="false"
                    :before-upload="beforeUpload"
                    :on-success="handleSuccess">
                    <el-button size="small" type="primary" icon="el-icon-upload2">批量导入</el-button>
                </el-upload>
                <el-button type="primary"  @click="exportTemplate" icon="el-icon-download" v-if="button_role&&button_role.down">下载模板</el-button> 
                <el-button type="primary" icon="el-icon-document" class="handle-del mr10" v-if="button_role&&button_role.export" @click="exportq">导出</el-button>
                <el-button type="primary" icon="el-icon-download" class="handle-del mr10" v-if="button_role&&button_role.downEx" @click="downEx">导出未执行计划</el-button>
                <el-button type="primary" icon="el-icon-plus" class="handle-del mr10" v-if="button_role&&button_role.add" @click="add">添加</el-button>
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
                <el-table-column prop="year"  label="计划支出时间"  align="center" width="150">
                     <template slot-scope="scope">
                         <span v-if="scope.row.year&&scope.row.month">{{scope.row.year}}年{{scope.row.month}}月</span>
                    </template>
                </el-table-column>
                <el-table-column prop="planTotal" label="计划支出金额（元）"  align="center" width="150"></el-table-column>
                <el-table-column prop="planUsing" label="用途"  align="center" width="300"></el-table-column> 
                <el-table-column prop="orgName" label="所属科室"  align="center" width="100"></el-table-column>
                 <el-table-column prop="status" label="状态"  align="center" width="80">
                    <template slot-scope="scope">
                         <span class="blue" v-if="scope.row.status=='0'">暂存</span>
                         <span class="blue" v-if="scope.row.status=='1'">归档</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center" v-if="button_role&&(button_role.delete||button_role.edit||button_role.look)" >
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
        <el-dialog :title="titleName"  :visible.sync="editVisible" width="35%" @close="closeDilog('form')">
            <el-form ref="form" :model="form" :rules="rules" label-width="130px">
                <el-form-item label="所属科室" prop="org_value">
                    <el-select v-model="form.org_value" filterable  @change="changeOrg"  style="width:300px;">
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
                <el-form-item label="计划支出时间" prop="year">
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
                   <el-select v-model="form.month" filterable placeholder="月份" style="width:100px;">
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
                <el-form-item label="支出金额（元）" prop="planTotal">
                    <el-input v-model="form.planTotal" class="input"></el-input>
                </el-form-item>
                
                <el-form-item label="用途" prop="planUsing">
                   <el-input type="textarea" v-model="form.planUsing"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit(0)">暂 存</el-button>
                <el-button type="primary" @click="saveEdit(1)">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog title="选择"  :visible.sync="downExPage" width="35%" @close="closeDilog('forms')">
            <el-form ref="forms" :model="forms"  label-width="130px">
                 <el-form-item label="计划支出时间" prop="year">
                    <el-select v-model="forms.year" filterable placeholder="年份" style="width:100px;">
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
                   <el-select v-model="forms.month" filterable placeholder="月份" style="width:100px;">
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
            </el-form>
             <span slot="footer" class="dialog-footer">
                <el-button @click="downExPage = false">取 消</el-button>
                <el-button type="primary" @click="downOk()">确 定</el-button>
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
                downExPage:false,
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
                    year:[
                        { required: true, message: '请选择支出时间', trigger: 'blur' }
                    ],
                     
                    planTotal:[
                        { required: true, message: '请填写支出金额', trigger: 'blur' },
                        { validator:valiNumberPass1, trigger: "blur" }
                    ],
                    planUsing:[
                        { required: true, message: '请填写用途', trigger: 'blur' }
                    ],
                    
                },
               
               
                button_role:{},
                options_org:[],
                
                form:{
                   project_value:'',
                   org_value:'',
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
                forms:{
                   year:'',
                   month:'',
                },
                
                projectName:'',
                orgName:'',
                isAdmin:false
            }
        },
        created() {
            this.button();
            this.getData();
            this.user();
        },
        computed: {
           
        },
        methods: {
            async downOk(){
               if(this.forms.year==''){
                  this.$message("年份不能为空");
                  return;
               }
               if(this.forms.month==''){
                 this.$message("月份不能为空");
                 return;
               }
                const token = localStorage.getItem('forestToken');
               location.href=baseURL_.lyjUrl+'/plan/importNoUsed?year='+this.forms.year+"&month="+this.forms.month+"&token="+token

            },
            downEx(){
                  this.downExPage=true;
            },
            handleSuccess(response, file, fileList){
                this.$refs.upload.clearFiles();
                this.$message(response.data);
                if(response.statusCode == 200){
                    this.getData();
                }
            },
            uploadUrl1() {
              const token = localStorage.getItem('forestToken');
              return baseURL_.lyjUrl+'/plan/importExcel?token='+token;
            },
             async exportq(){
               const token = localStorage.getItem('forestToken');
               location.href=baseURL_.lyjUrl+'/plan/exportList?orgName='+this.orgName+"&projectName="+this.projectName+"&token="+token
            },
            async exportTemplate(){
              const token = localStorage.getItem('forestToken');
              location.href=baseURL_.lyjUrl+'/plan/template?token='+token
            },
            async user(){
                const user = await this.$http.get(baseURL_.sysUrl+'/sysUser/getOnlineUser');
                var roles=user.data.data.roles;
                for(var i=0;i<roles.length;i++){
                   if(roles[i].isAdmin==1){
                     this.isAdmin=true;
                   }
                }
             
            },
            handleLook(index, row){
                this.$router.push({ path:'/plan_details',query: {id: row.id}})
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
                if(!this.isAdmin&&row.status=='1'){
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
                    params:{'orgId':val.split("@")[0],'type':'plan','id':this.edit_id}
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
                var addOrEdit={};
                this.form.status=status;
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
                }
                
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
                if(!this.isAdmin&&row.status=='1'){
                     this.$message("归档数据不允许操作");
                     return;
                }
                this.titleName="修改";
                this.edit_id=row.id;
                
                const user1 = await this.$http.get(baseURL_.lyjUrl+'/home/getOnlineUser',{
                    params:{'id':row.id,'type':'plan'}
                });
                this.options_org=user1.data.data.org;

                const user = await this.$http.get(baseURL_.lyjUrl+'/plan/getById',{ 
                    params: {'id':row.id}
                });
                if(user.data.statusCode==200){
                    this.form=user.data.data
                    this.form.org_value=user.data.data.orgId+"@"+user.data.data.orgName
                    const projects = await this.$http.get(baseURL_.lyjUrl+'/projects/getUserAll',{
                        params:{'orgId':user.data.data.orgId,'type':'plan','id':row.id}
                    });
                    this.project_option=projects.data.data;


                    this.form.project_value=user.data.data.projectId+'@'+user.data.data.projectName;
                    if(user.data.data.year){
                       this.form.year=user.data.data.year+'';
                    }
                    if(user.data.data.month){
                        this.form.month=user.data.data.month+'';
                    }
                    
                }
                 this.editVisible=true;
            },
            async add(){
               this.edit_id='';
               this.form={};
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
   .blue{
        color:blue;
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


.upload-excel-file-up .el-upload--text {    
    border: none !important;
    display: inline-block;
    width: 110px;
    height: 40px;
    float: left;
  }
</style>