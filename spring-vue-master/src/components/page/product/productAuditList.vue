<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 商品审核</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div style="background:#f6f6f6;padding:20px 10px 0;margin-bottom:20px;">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="商户名称">
                        <el-input v-model="formInline.supplierName" placeholder="商户名称"></el-input>
                    </el-form-item>
                    <el-form-item label="商品名称">
                        <el-input v-model="formInline.name" placeholder="商品名称"></el-input>
                    </el-form-item>
                    <el-form-item label="商品编码">
                        <el-input v-model="formInline.code" placeholder="商品编码"></el-input>
                    </el-form-item>
                    <el-form-item label="类目名称">
                        <el-input v-model="formInline.catalogName" placeholder="类目名称"></el-input>
                    </el-form-item>
                    <el-form-item label="审核状态">
                         <el-select v-model="formInline.auditStatus" placeholder="审核状态">
                            <el-option label="请选择" value=""></el-option>
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
                <el-button type="primary" @click="batchAudit(1)" >审核通过</el-button>
                <el-button type="primary" @click="batchAudit(2)" >审核失败</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="catalogName" label="商品类目"  align="center" width=""></el-table-column>
                <el-table-column prop="supplierName" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column prop="name" label="商品名称"  align="center" width=""></el-table-column>
                <el-table-column prop="code" label="商品编码"  align="center" width=""></el-table-column>
                <el-table-column prop="price" label="市场价（元）" align="center"  width=""></el-table-column>
                <el-table-column label="审核状态" align="center"  width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.auditStatus == '0'">待审核</span>
                        <span type="text" v-if="scope.row.auditStatus == '1'">审核通过</span>
                        <!-- <span type="text" class="red hand" v-if="scope.row.auditStatus == '2'" @click="showAuditReason(scope.row.id)" >审核失败  <i class="el-icon-info"></i></span> -->
                        <span type="text" class="red" v-if="scope.row.auditStatus == '2'">审核失败</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" @click="view(scope.$index, scope.row)">查看详情</el-button>
                        <el-button type="text" v-if="isSupplier != '1' && scope.row.auditStatus == '0'" @click="toAudit(scope.$index, scope.row)">审核</el-button>
                        <el-button type="text" v-if="scope.row.auditStatus != '0'" @click="lookAudit(scope.$index, scope.row)">审核记录</el-button>
                        <!-- <el-button type="text" v-if="scope.row.auditStatus == '0'" @click="auditAdopt(scope.row.id)">审核通过</el-button>
                        <el-button type="text" v-if="scope.row.auditStatus == '0'" class="red" @click="auditReject(scope.row.id)" >审核失败</el-button> -->
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
        <!-- 审核不通过原因提示框 -->
        <el-dialog
            title="审核未通过原因"
            :visible.sync="reasonVisible"
            width="25%"
            center>
            <span>{{ this.auditReason }}</span>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="reasonVisible = false">取消</el-button>
            </span>
        </el-dialog>

        <el-dialog title="审核记录" class="dialogBox" :visible.sync="editVisible" width="40%">
           <el-table :data="auditData" border class="table" >
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="auditUserName" label="审核人"  align="center" width="80"></el-table-column>
                <el-table-column prop="createTime" label="审核时间"  align="center" width="150"></el-table-column>
                <el-table-column prop="auditReason" label="审核意见"  align="center" width="">
                   <template slot-scope="scope">
                         <span v-if="scope.row.auditResult=='1'">通过</span>
                         <span v-if="scope.row.auditResult=='2'">{{scope.row.auditReason}}</span>
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
                reasonVisible:false,
                editVisible:false,
                page:1,
                total:0,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
                formInline:{
                    supplierName:'',
                    name:'',
                    catalogName:'',
                    auditStatus:'',
                    code:''
                },
                msgTips:'',
                auditReason:'',
                auditData:'',
                isSupplier:1
            }
        },
        created() {
            this.getData();
            this.vaIsSupplier();
        },
        methods: {
            async vaIsSupplier(){
              var res = await this.$http.get(baseURL_.mallUrl+'/supplier/isSupplier');
              this.isSupplier = res.data.data;
            },
            onSubmit(){
                this.getData();
            },
            onReset(){
                this.formInline = {};
                this.formInline.auditStatus = '';
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
                const products = await this.$http.get(baseURL_.mallUrl+'/products_audit/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'name':this.formInline.name,
                        'catalogName':this.formInline.catalogName,
                        'auditStatus':this.formInline.auditStatus,
                        'supplierName':this.formInline.supplierName,
                        'code':this.formInline.code
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
                var flag = 0;
                var ids = [];
                this.multipleSelection.forEach(e => {
                    ids.push(e.id);
                    if(e.auditStatus != 0){
                        flag ++;
                    }
                });
                if(flag == 0){
                    if(audit == 1){
                        this.$confirm('确认审核通过？').then( e=> {
                        this.audit(ids.join(','), audit, "");
                    }).catch(_ => {});
                    }
                    if(audit == 2){
                        this.$prompt('请输入审核失败理由：', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            inputPattern: /^[\s\S]*.*[^\s][\s\S]*$/,
                            inputErrorMessage: '理由不能为空'
                        }).then(({ value }) => {
                            this.audit(ids.join(','),2,value)
                        }).catch(() => { }); 
                    }
                }else{
                    this.$message("不能重复审核！");
                }
            },
            async audit(ids, auditStatus, auditReason){
                var params = {
                    'ids': ids,
                    'auditStatus': auditStatus,
                    'auditReason': auditReason
                }
                var auditResult = await this.$http.put(baseURL_.mallUrl+'/products_audit/batchAudit', this.$qs.stringify(params));
                this.$message(auditResult.data.data);
                if(auditResult.data.statusCode==200){
                    this.getData();
                }
            },
            view(index,row){
                var id = Base64.encode(row.id);
                this.$router.push({
                    path: '/auditDetails',
                    name:'auditDetails',
                    query: {
                        id: id
                    }
                });
            },
            toAudit(index,row){
                var id = Base64.encode(row.id);
                var state = Base64.encode('10');
                this.$router.push({
                    path: '/auditDetails',
                    name:'auditDetails',
                    query: {
                        id: id,
                        state:state
                    }
                });
            },
            async showAuditReason(id){
                var res = await this.$http.get(baseURL_.mallUrl+'/products_audit/getAuditList',{ 
                    params: {
                        'page': 1,
                        'pageSize': 1,
                        'businessId': id
                    }
                });
                if(res.data.statusCode==200){
                    var auditlist = res.data.data.list;
                    if(auditlist.length > 0){
                        this.auditReason = auditlist[0].auditReason;
                    }
                    this.reasonVisible = true;
                }
            },
            async lookAudit(index,row){
                const audit = await this.$http.get(baseURL_.mallUrl+'/products_audit/getAuditList',{ 
                    params:{'businessId':row.id, 'auditTypes':"2"}
                })
                this.auditData=audit.data.data;
                this.editVisible=true;
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
    .hand :hover{
        cursor: pointer;
    }
</style>