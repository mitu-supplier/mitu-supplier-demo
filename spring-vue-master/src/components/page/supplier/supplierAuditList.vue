<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 商品审核</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" ref="formInline" :model="formInline" class="demo-form-inline">
                    
                    <el-form-item label="商户名称">
                        <el-input v-model="formInline.name" placeholder="商户名称"></el-input>
                    </el-form-item>
                    
                    <el-form-item label="审核状态">
                         <el-select v-model="formInline.status" placeholder="审核状态">
                            <el-option label="待审核" value="0"></el-option>
                            <el-option label="审核通过" value="1"></el-option>
                            <el-option label="审核失败" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="onSubmit">查询</el-button>
                        <el-button type="primary" icon="el-icon-refresh" @click="onReset">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-check" @click="batchAudit(1)" >审核通过</el-button>
                <!--<el-button type="primary" icon="el-icon-close" @click="batchAudit(2)" >审核失败</el-button>-->
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="code" label="商户号"  align="center" width=""></el-table-column>
                <el-table-column prop="name" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column prop="registerAddress" label="商户地址"  align="center" width=""></el-table-column>
                
                <el-table-column prop="enterTypeName" label="入驻类型" align="center"  width=""></el-table-column>
                <el-table-column prop="contactName" label="联系人" align="center"  width="80"></el-table-column>
                <el-table-column prop="contactMobile" label="联系电话" align="center"  width=""></el-table-column>
                <el-table-column label="状态" align="center"  width="80">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.status == '0'">待审核</span>
                        <span type="text" v-if="scope.row.status == '1'">审核通过</span>
                        <span type="text" class="red" v-if="scope.row.status == '2'">审核失败</span>
                    </template>
                </el-table-column> 
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-document" @click="view(scope.$index, scope.row)">查看</el-button>
                        <el-button type="text" icon="el-icon-document" v-if="scope.row.status == '0'"  @click="toAudit(scope.$index, scope.row)">审核</el-button>
                        <el-button type="text" icon="el-icon-document" v-if="scope.row.status != '0'"  @click="lookAudit(scope.$index, scope.row)">审核记录</el-button>
                        <!-- <el-button type="text" icon="el-icon-check" v-if="scope.row.status == '0'" @click="auditAdopt(scope.row.id)">通过</el-button>
                        <el-button type="text" icon="el-icon-close" v-if="scope.row.status == '0'" class="red" @click="auditReject(scope.row.id)">驳回</el-button> -->
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
        <el-dialog title="审核记录" class="dialogBox" :visible.sync="editVisible" width="40%">
            
           <el-table :data="auditData" border class="table" >
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="auditUserName" label="审核人"  align="center" width="80"></el-table-column>
                <el-table-column prop="createTime" label="审核时间"  align="center" width="150"></el-table-column>
                <el-table-column prop="auditReason" label="审核意见"  align="center" width="">
                   <template slot-scope="scope">
                         <span v-if="scope.row.auditResult=='1'">通过</span>
                         <span v-if="scope.row.auditResult=='2'">${scope.row.auditReason}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="auditResult" label="审核状态"  align="center" width="">
                   <template slot-scope="scope">
                         <span v-if="scope.row.auditResult=='1'">通过</span>
                         <span v-if="scope.row.auditResult=='2'">不通过</span>
                    </template>
                </el-table-column>
           </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                
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
                aaa:false,
                page:1,
                total:0,
                pageSize:10,
                tableData: [],
                editVisible:false,
                multipleSelection: [],
                auditData:[],
                formInline:{
                    name:'',
                    status:''
                }
            }
        },
        created() {
            this.getData();
        },
        methods: {
            async lookAudit(index,row){
                const audit = await this.$http.get(baseURL_.mallUrl+'/supplier_audit/getAuditList',{ 
                    params:{'businessId':row.id}
                })
                this.auditData=audit.data.data;
                this.editVisible=true;
            },
            onReset(){
              this.formInline.name="";
              this.formInline.status="";
              this.getData();
            },
            onSubmit(){
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
                const products = await this.$http.get(baseURL_.mallUrl+'/supplier_audit/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'name':this.formInline.name,
                        'status':this.formInline.status
                        
                    }
                });
                if(products.data.statusCode==200){
                  this.tableData=products.data.data.list;
                  this.total=products.data.data.total;
                  this.page=products.data.data.page;
                }
            },
            handleSelectionChange(val){
                this.multipleSelection = val;
            },
            batchAudit(audit){
                if(this.multipleSelection.length == 0){
                    this.$message("请先选择商品");
                    return ;
                }
                var msg = "";
                if(audit == 1){
                    msg = "审核通过";
                }
                if(audit == 2){
                    msg = "审核不通过";
                }
                var flag = 0;
                var ids = [];
                this.multipleSelection.forEach(e => {
                    ids.push(e.id);
                    if(e.status != 0){
                        flag ++;
                    }
                });
                if(flag == 0){
                    this.$confirm('确认'+msg+'？').then( e=> {
                        this.audit(ids.join(','), audit);

                    }).catch(_ => {});
                }else{
                    this.$message("不能重复审核！");
                }
            },
            async audit(ids, auditStatus){
                var params = {
                    'ids': ids,
                    'auditStatus': auditStatus
                }
                var auditResult = await this.$http.put(baseURL_.mallUrl+'/products_audit/batchAudit', this.$qs.stringify(params));
                this.$message(auditResult.data.data);
                if(auditResult.data.statusCode==200){
                    this.getData();
                }
            },
            view(index,item){
                this.$router.push({
                    path: '/supplierSee',
                    query: {
                        id: item.id
                    }
                });
            },
            toAudit(index,item){
                this.$router.push({
                    path: '/supplierSee',
                    query: {
                        id: item.id,
                        state:item.status
                    }
                });
            },
            auditAdopt(id){
                this.$confirm('确认审核通过').then( e=> {
                    this.confimAudit(id,1);
                }).catch(_ => {});
            },
            auditReject(id){
                this.$prompt('请输入审核失败理由：', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputPattern: /^[\s\S]*.*[^\s][\s\S]*$/,
                    inputErrorMessage: '理由不能为空'
                }).then(({ auditReason }) => {
                    this.confimAudit(id,2,auditReason)
                }).catch(() => { }); 
            },
            async confimAudit(id,auditResult,auditReason){
                var auditResult = await this.$http.get(baseURL_.mallUrl+'/supplier_audit/audit', {
                    params:{
                        businessId:id,
                        auditResult:auditResult,
                        auditReason:auditReason
                    }
                });
                this.$message(auditResult.data.data);
                if(auditResult.data.statusCode==200){
                    this.getData();
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