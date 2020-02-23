<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i>直充库存</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="商户名称">
                        <el-input v-model="formInline.supplierName" placeholder="商户名称"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="onSubmit">查询</el-button>
                        <el-button type="primary" icon="el-icon-refresh" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="messageBox">
                <div class="mestitle">库存报警信息接收账号</div>
                <el-form :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="110px" class="demo-dynamic">
                    <!-- <el-form-item
                        prop="email"
                        label="邮箱"
                    >
                        <el-input v-model="dynamicValidateForm.email" class="mesInput"></el-input>
                    </el-form-item> -->

                    <el-form-item
                        v-for="(domain, index) in dynamicValidateForm.domains"
                        :label="'邮箱' + (index+1)"
                        :key="domain.key">
                        <el-input v-model="domain.value" class="mesInput"></el-input><el-button @click.prevent="removeDomain(domain)">删除</el-button>
                    </el-form-item>

                    <!-- <el-form-item
                        label="手机号"
                    >
                        <el-input v-model="dynamicValidateForm.phone" maxlength="11" class="mesInput"></el-input>
                    </el-form-item> -->

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
                <el-table-column prop="name" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column label="账户余额（元）" align="center" width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.balance == null" class="red">0.000</span>
                        <span type="text" v-else>{{scope.row.balance.toFixed(3)}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="addBalance(scope.$index, scope.row)">充值</el-button>
                        <el-button type="text" icon="el-icon-document" @click="look(scope.$index, scope.row, 1)">充值记录</el-button>
                        <el-button type="text" icon="el-icon-edit" @click="subtractBalance(scope.$index, scope.row)">扣减</el-button>
                        <el-button type="text" icon="el-icon-document" @click="look(scope.$index, scope.row, 2)">扣减记录</el-button>
                        <el-button type="text" icon="el-icon-document" @click="SetUpMoney(scope.row.id)">设置账户报警金额</el-button>
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
        <el-dialog :title="titleName" :visible.sync="editVisible" width="25%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="商户名称">
                     <el-input v-model="form.supplierName" :disabled="edit" class="input"></el-input>
                </el-form-item>
                <el-form-item label="金额">
                    <el-input v-model="form.money" oninput="value=value.replace(/[^\d^\.]+/g,'').replace('.','$#$').replace(/\./g,'').replace('$#$','.')" v-on:blur="toFix" class="input"></el-input>
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
                alertmoney:'',
                page:1,
                total:0,
                pageSize:10,
                tableData: [],
                titleName:'',
                edit:true,
                formInline:{
                    supplierName:''
                },
                form:{
                    supplierName:'',
                    supplierId:'',
                    money:'',
                    operationType:''
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

            look(index, row, type){
              this.$router.push({path:'/balanceRecordList',query:{id:row.id, operationType:type}});
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
                const products = await this.$http.get(baseURL_.mallUrl+'/supplierBalanceRecord/balanceList',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'supplierName':this.formInline.supplierName
                    }
                });
                if(products.data.statusCode==200){
                  this.tableData=products.data.data.list;
                  this.total=products.data.data.total;
                  this.page=products.data.data.page;
                }
            },
            addBalance(index, row){
                this.form = {};
                this.form.supplierId = row.id;
                this.form.supplierName = row.name;
                this.form.operationType = 1;
                this.editVisible=true;
                this.titleName="充值";
            },
            async saveEdit(){
                var money = this.form.money;
                if(money == null || money.trim() == ''){
                    this.$message("请输入金额");
                    return;
                }
                if(this.form.operationType && this.form.operationType == 2){
                    this.form.money = 0 - money;
                }
                
                var add = await this.$http.post(baseURL_.mallUrl+'/supplierBalanceRecord/save',this.$qs.stringify(this.form));
                this.$message(add.data.data);
                if(add.data.statusCode==200){
                    this.editVisible=false;
                }
                this.getData();
            },
            toFix(){
                this.form.money = Number(this.form.money).toFixed(3);
            },
            subtractBalance(index, row){
                this.form = {};
                this.form.supplierId = row.id;
                this.form.supplierName = row.name;
                this.form.operationType = 2;
                this.editVisible=true;
                this.titleName="扣减";
            },
            SetUpMoney(id){
                this.$prompt('请输入账户报警金额', '提示', {
                    confirmButtonText: '确定',  
                    cancelButtonText: '取消',
                    // inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
                    // inputErrorMessage: '邮箱格式不正确'
                }).then(({ value }) => {
                    // this.$message({
                    //     type: 'success',
                    //     message: '你的邮箱是: ' + value
                    // });
                    this.setMoney(id,value);
                }).catch(() => { });
            },
            async setMoney(id,value){
                const res = await this.$http.get(baseURL_.mallUrl+'/supplierBalanceRecord/updateAlertBalance',{
                    params:{
                        'id':id,
                        'alertBalance':value
                    }
                });
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