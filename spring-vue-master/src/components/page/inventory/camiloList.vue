<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 卡密库存</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="类目名称">
                        <el-input v-model="formInline.catalogName" placeholder="类目名称"></el-input>
                    </el-form-item>
                    <el-form-item label="商品名称">
                        <el-input v-model="formInline.name" placeholder="商品名称"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">查询</el-button>
                        <el-button type="primary" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box canahead">
                <el-upload
                    class="upload-excel-file"
                    ref="upload"
                    :headers="myHeaders"
                    :action="uploadUrl()"
                    multiple
                    :limit="1"
                    :show-file-list="false"
                    :before-upload="beforeUpload"
                    :on-success="handleSuccess">
                    <el-button size="small" type="primary" icon="el-icon-upload2">批量导入</el-button>
                </el-upload>
                <el-button type="primary" @click="exportExcel" icon="el-icon-download">批量导出</el-button> 
                <el-button type="primary" @click="exportTemplate" icon="el-icon-download">下载模板</el-button> 
            </div>

            
            <div class="messageBox">
                <div class="mestitle">库存报警信息接收账号</div>
                <el-form :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="100px" class="demo-dynamic">
                    <el-form-item
                        v-for="(domain, index) in dynamicValidateForm.domains"
                        :label="'邮箱' + (index+1)"
                        :key="domain.key">
                        <el-input v-model="domain.value" class="mesInput"></el-input><el-button @click.prevent="removeDomain(domain)">删除</el-button>
                    </el-form-item>
                    <el-form-item
                        v-for="(newphone, index) in dynamicValidateForm.phoneList"
                        :label="'手机号' + (index+1)"
                        :key="newphone.key">
                        <el-input v-model="newphone.value" maxlength="11" class="mesInput"></el-input><el-button @click.prevent="removePhone(newphone)">删除</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm">提交</el-button>
                        <el-button @click="addDomain">新增邮箱</el-button>
                        <el-button @click="addPhone">新增手机号</el-button>
                        
                    </el-form-item>
                </el-form>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="catalogName" label="商品类目"  align="center" width=""></el-table-column>
                <el-table-column prop="name" label="商品名称"  align="center" width=""></el-table-column>
                <el-table-column prop="supplierName" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column prop="stock" label="剩余库存" align="center"  width=""></el-table-column>
                <el-table-column prop="failureTime" label="失效时间" align="center"  width=""></el-table-column>
                <el-table-column prop="" label="库存告急" align="center"  width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.stock <= scope.row.inventoryAlertNum" class="red">库存不足</span>
                        <span type="text" v-else></span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-document" @click="record(scope.$index, scope.row)">入库记录</el-button>
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
                total:0,
                pageSize:10,
                tableData: [],
                 multipleSelection: [],
                formInline:{
                    name:'',
                    catalogName:''
                },
                myHeaders: {
                    'token': ''
                },
                dynamicValidateForm: {
                    domains: [],
                    email: '',
                    phone:'',
                    phoneList:[]
                }
            }
        },
        created() {
            var token = localStorage.getItem('forestToken');
            this.myHeaders.token = token;
            this.getData();
            this.getemails();
        },
        methods: {
            // dynamicValidateForm: {
                //     domains: [],
                //     email: '',
                //     phone:'',
                //     phoneList:[]
                // }
            async getemails(){
                const res = await this.$http.get(baseURL_.mallUrl+'/receivingAccount/list');
                if(res.data.data.length > 0){
                    var data = res.data.data;
                    for(let k=0; k<data.length; k++){
                        if(data[k].type == '1'){
                            this.dynamicValidateForm.domains.push({'value':data[k].tValue,'key': Date.now()});
                        }else{
                            this.dynamicValidateForm.phoneList.push({'value':data[k].tValue,'key': Date.now()});
                        }
                    }
                }else{
                    this.dynamicValidateForm.domains = [{value: '', key: Date.now()}]
                    this.dynamicValidateForm.phoneList = [{value: '', key: Date.now()}]
                }
            },
            async submitForm() {
                var arr = [];
                // if(this.dynamicValidateForm.email != '' && this.dynamicValidateForm.email != null){
                //     if(!(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(this.dynamicValidateForm.email))){ 
                //         this.$message({
                //             type: "error",
                //             message: "请输入正确的邮箱"
                //         });
                //         return false; 
                //     }else{
                //         var json = {'type':'1','tValue':this.dynamicValidateForm.email};
                //         arr.push(json)
                //     }
                // }else{
                //     this.$message({
                //         type: "error",
                //         message: "请输入邮箱"
                //     });
                //     return false; 
                // }

                if(this.dynamicValidateForm.domains.length > 0){
                    var emails = this.dynamicValidateForm.domains;
                    for(let i=0; i<emails.length; i++){
                        if(emails[i].value == '' || emails[i].value == null){
                            this.$message({
                                type: "error",
                                message: "请删除空标签"
                            });
                            return;
                        }else{
                            if(!(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(emails[i].value))){ 
                                this.$message({
                                    type: "error",
                                    message: "请输入正确的邮箱"
                                });
                                return false; 
                            }else{
                                var emailitem = {'type':'1','tValue':emails[i].value};
                                arr.push(emailitem)
                            }
                        }
                    }
                }

                // if(this.dynamicValidateForm.phone != '' && this.dynamicValidateForm.phone != null){
                //     if(!(/^1[3456789]\d{9}$/.test(this.dynamicValidateForm.phone))){ 
                //         this.$message({
                //             type: "error",
                //             message: "请输入正确的手机号"
                //         });
                //         return false; 
                //     }else{
                //         var phoneone = {'type':'2','tValue':this.dynamicValidateForm.phone};
                //         arr.push(phoneone)
                //     }
                // }else{
                //     this.$message({
                //         type: "error",
                //         message: "请输入手机号"
                //     });
                //     return false; 
                // }

                if(this.dynamicValidateForm.phoneList.length > 0){
                    var phones = this.dynamicValidateForm.phoneList;
                    for(let i=0; i<phones.length; i++){
                        if(phones[i].value == '' || phones[i].value == null){
                            this.$message({
                                type: "error",
                                message: "请删除空标签"
                            });
                            return;
                        }else{
                            if(!(/^1[3456789]\d{9}$/.test(phones[i].value))){ 
                                this.$message({
                                    type: "error",
                                    message: "请输入正确的手机号"
                                });
                                return false; 
                            }else{
                                var phoneitem = {'type':'2','tValue':phones[i].value};
                                arr.push(phoneitem)
                            }
                        }
                    }
                }

                // 校验通过，调保存接口
                // var json = [
                //     {'type':'1','tValue':'789@qq.com'},
                //     {'type':'1','tValue':'789@qq.com'},
                //     {'type':'2','tValue':'13145787877'},
                // ]
                const res = await this.$http.post(baseURL_.mallUrl+'/receivingAccount/save',arr);
                if(res.data.statusCode==200){
                  this.$message({
                    type: "success",
                    message: res.data.data || "保存成功"
                  });
                }else{
                  this.$message({
                    type: "error",
                    message: res.data.data || "保存失败"
                  });
                }
                
            },
            removeDomain(item) {
                var index = this.dynamicValidateForm.domains.indexOf(item)
                if (index !== -1) {
                this.dynamicValidateForm.domains.splice(index, 1)
                }
            },
            removePhone(item) {
                var index = this.dynamicValidateForm.phoneList.indexOf(item)
                if (index !== -1) {
                this.dynamicValidateForm.phoneList.splice(index, 1)
                }
            },
            
            addDomain() {
                this.dynamicValidateForm.domains.push({
                    value: '',
                    key: Date.now()
                });
            },
            addPhone() {
                this.dynamicValidateForm.phoneList.push({
                    value: '',
                    key: Date.now()
                });
            },


            onSubmit(){
                this.getData();
            },
            onReset(){
                this.formInline = {};
                this.getData();
            },
             handleSelectionChange(val) {
                this.multipleSelection = val;
            },
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
            // 初始化数据
            async getData() {
                const products = await this.$http.get(baseURL_.mallUrl+'/camilo/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'name':this.formInline.name,
                        'catalogName':this.formInline.catalogName
                    }
                });
                if(products.data.statusCode==200){
                  this.tableData=products.data.data.list;
                  this.total=products.data.data.total;
                  this.page=products.data.data.page;
                }
            },
            importExcel(){
                console.log('import');
            },
            exportExcel(){
                console.log('export');
            },
            uploadUrl() {
              return baseURL_.mallUrl+'/camilo/importExcel';
            },
            beforeUpload(file) {
              var FileExt = file.name.replace(/.+\./, "");
              if (["xlsx", "xls"].indexOf(FileExt.toLowerCase()) === -1) {
                this.$message({
                  type: "warning",
                  message: "请上传后缀名为xls或者xlsx的文件！"
                });
                return false;
              }
            },
            handleSuccess(response, file, fileList){
                this.$refs.upload.clearFiles();
                this.$message(response.data);
                if(response.statusCode == 200){
                    this.getData();
                }
            },
            record(index, row){
              this.$router.push({path:'/camiloRecordList',query:{id:row.id}});
            },
            exportTemplate(){
                window.location.href = baseURL_.mallUrl+'/camilo/downloadTemplate';
            },
            exportExcel(){
                if(this.multipleSelection.length>0){
                    var ids=[];
                    for(var i=0;i<this.multipleSelection.length;i++){
                       ids.push(this.multipleSelection[i].id);
                    }
                 window.location.href = baseURL_.mallUrl+'/camilo/exportExcel?productIds='+ids.join(",");
                }else{
                   this.$message("请选中一条或多条数据");
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
    .inline-block {
        display: inline-block;
    } 
    .messageBox{
        width: 35%;
        height: auto;
        border: 1px solid #ccc;
        padding:20px;
        margin-bottom: 20px;
    }
    .messageBox .mestitle{
        font-size: 16px;
        color: #ff0000;
        margin-bottom: 15px;
    }
    .messageBox .mesInput{
        width: 60%;
    }
</style>
<style>
.canahead .upload-excel-file .el-upload--text {    
    border: none !important;
    display: inline-block;
    width: 110px;
    height: 40px;
    float: left;
  }
</style>