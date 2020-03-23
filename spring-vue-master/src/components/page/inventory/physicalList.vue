<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 实物库存</el-breadcrumb-item>
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
                    <el-form-item label="商品编码">
                        <el-input v-model="formInline.code" placeholder="商品编码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">查询</el-button>
                        <el-button type="primary" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box canahead">
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

            <el-table :data="tableData" border class="table" ref="multipleTable">
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="catalogName" label="商品类目"  align="center" width=""></el-table-column>
                <el-table-column prop="name" label="商品名称"  align="center" width=""></el-table-column>
                <el-table-column prop="code" label="商品编码"  align="center" width=""></el-table-column>
                <el-table-column prop="supplierName" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column prop="inventoryNum" label="剩余库存" align="center"  width="">
                    <template slot-scope="scope">
                        <!-- v-on:focus="stockfocus(scope.row)"  -->
                        <el-input :value="scope.row.inventoryNum" v-model="scope.row.inventoryNum" v-on:blur="stockBlur(scope.row)" ></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="" label="库存告急" align="center"  width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.inventoryNum <= scope.row.inventoryAlertNum" class="red">库存不足</span>
                        <span type="text" v-else></span>
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
                formInline:{
                    name:'',
                    catalogName:'',
                    code:''
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
            this.getData();
            this.getemails();
        },
        methods: {
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
            // stockfocus(row){
            //     console.log(row)
            //     this.stockRows = row;
            // },
            stockBlur(row){
                // alert(this.stockRows.inventoryNum)
                // alert(row.inventoryNum)
                // console.log(row.inventoryNum)
                // if(this.stockRows.inventoryNum == row.inventoryNum){
                //     return;
                // }
                // console.log(row);
                var num = row.inventoryNum;
                var numfist = 2;
                if(num.length > 1){
                    numfist = num.substring(0,1)
                }
                if(numfist <= 0){
                    this.$message("第一位不能是0");
                    return;
                }
                this.$confirm('确认修改剩余库存？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.editInventory(row);
                }).catch(() => {
                    this.getData();
                });

                
            },
            async editInventory(row){
                const data = await this.$http.get(baseURL_.mallUrl+'/physical/updateInventory',{ 
                    params: {
                        'productId':row.id,
                        'inventoryNum':row.inventoryNum
                    }
                });
                if(data.data.statusCode==200){
                    this.$message({
                        type: "success",
                        message: data.data.data
                    });
                    this.getData();
                }
            },
            onSubmit(){
                this.getData();
            },
            onReset(){
                this.formInline = {};
                this.getData();
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
                const products = await this.$http.get(baseURL_.mallUrl+'/physical/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'name':this.formInline.name,
                        'catalogName':this.formInline.catalogName,
                        'code':this.formInline.code
                    }
                });
                if(products.data.statusCode==200){
                  this.tableData=products.data.data.list;
                  this.total=products.data.data.total;
                  this.page=products.data.data.page;
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