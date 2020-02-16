<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 商品列表</el-breadcrumb-item>
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
                    <el-form-item label="类目名称">
                        <el-input v-model="formInline.catalogName" placeholder="类目名称"></el-input>
                    </el-form-item>
                    <el-form-item label="发货类型">
                        <el-select v-model="formInline.deliveryType" placeholder="请选择">
                            <el-option
                                v-for="item in deliveryTypeList"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    
                    <el-form-item label="审核状态">
                         <el-select v-model="formInline.auditStatus" placeholder="审核状态">
                            <el-option label="暂存" value="3"></el-option>
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
                <el-button type="primary" @click="add" icon="el-icon-plus" >添加商品</el-button>
                <el-button type="primary" @click="batchDelete" icon="el-icon-delete" >批量删除</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                <el-table-column prop="supplierName" label="商户名称"  align="center" width=""></el-table-column>
                <el-table-column prop="catalogName" label="商品类目"  align="center" width=""></el-table-column>
                <el-table-column prop="name" label="商品名称"  align="center" width=""></el-table-column>
                <el-table-column prop="price" label="市场价（元）" align="center"  width=""></el-table-column>
                <el-table-column prop="supplyPrice" label="供货价（元）" align="center"  width=""></el-table-column>
                <el-table-column prop="deliveryName" label="发货类型" align="center"  width=""></el-table-column>
                <el-table-column label="上下架状态" align="center"  width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.status == '1'">上架</span>
                        <span type="text" v-if="scope.row.status == '2'">下架</span>
                    </template>
                </el-table-column>
                <el-table-column label="审核状态" align="center"  width="">
                    <template slot-scope="scope">
                        <span type="text" v-if="scope.row.auditStatus == '3'">暂存</span>
                        <span type="text" v-if="scope.row.auditStatus == '0'">待审核</span>
                        <span type="text" v-if="scope.row.auditStatus == '1'">审核通过</span>
                        <span type="text" class="red" v-if="scope.row.auditStatus == '2'">审核失败</span>
                        <!-- <span type="text" class="red hand" v-if="scope.row.auditStatus == '2'" @click="showAuditReason(scope.row.id)" >审核失败  <i class="el-icon-info"></i></span> -->
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-search" class="red" @click="handleLook(scope.$index, scope.row)">查看</el-button>
                        <el-button type="text" v-if="scope.row.status == '2'" icon="el-icon-edit" class="red" @click="updateStatus(scope.$index, scope.row, 1)">上架</el-button>
                        <el-button type="text" v-if="scope.row.status == '1' && scope.row.auditStatus == '1'" icon="el-icon-edit" class="red" @click="updateStatus(scope.$index, scope.row, 2)">下架</el-button>
                        <el-button type="text" v-if="scope.row.auditStatus == '2' || scope.row.auditStatus == '3'" icon="el-icon-edit" class="red" @click="handleUpdate(scope.$index, scope.row)">修改</el-button>
                        <el-button type="text" v-if="(scope.row.auditStatus == '2' && scope.row.status == '2' ) || scope.row.auditStatus == '3'" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                        <el-button type="text" v-if="scope.row.auditStatus != '0' && scope.row.auditStatus != '3'" icon="el-icon-document" class="red" @click="lookAudit(scope.$index, scope.row)">审核记录</el-button>
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
                <el-button type="" @click="reasonVisible = false">取消</el-button>
                <el-button type="primary" @click="updatePro()">修改</el-button>
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
                page:1,
                total:0,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
                deliveryTypeList:[],
                editVisible:false,
                formInline:{
                    name:'',
                    catalogName:'',
                    auditStatus:'',
                    deliveryType:'',
                    supplierName:''
                },
                auditReason:'',
                productId:'',
                auditData:[]
            }
        },
        created() {
            this.getData();
            this.getDeliveryTypeList();
        },
        methods: {
             async lookAudit(index,row){
                const audit = await this.$http.get(baseURL_.mallUrl+'/products_audit/getAuditList',{ 
                    params:{'businessId':row.id}
                })
                this.auditData=audit.data.data;
                this.editVisible=true;
            },
            onSubmit(){
                this.getData();
            },
            onReset(){
                this.formInline = {};
                // this.formInline.auditStatus = '';
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
                const products = await this.$http.get(baseURL_.mallUrl+'/products/list',{ 
                    params: {
                        'page':this.page,
                        'pageSize':this.pageSize,
                        'name':this.formInline.name,
                        'catalogName':this.formInline.catalogName,
                        'auditStatus':this.formInline.auditStatus,
                        'deliveryType':this.formInline.deliveryType,
                        'supplierName':this.formInline.supplierName
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
            batchDelete(){
                if(this.multipleSelection.length == 0){
                    this.$message("请先选择商品");
                    return ;
                }
                var flag = 0;
                var ids = [];
                this.multipleSelection.forEach(e => {
                    ids.push(e.id);
                    if(e.auditStatus != '2' && e.auditStatus != '3'){
                        flag ++;
                    }
                });
                if(flag == 0){
                    this.$confirm('确认删除？').then( e=> {
                        this.delete(ids.join(','));
                    }).catch(_ => {});
                }else{
                    this.$message("审核通过不允许删除！");
                }
            },
            handleDelete(index, row){
                this.$confirm('确认删除？').then( e=> {
                   this.delete(row.id);
                }).catch(_ => {});
            },
            async delete(ids){
                var delResult = await this.$http.delete(baseURL_.mallUrl+'/products/batchDelete',{ 
                    params: {
                        'ids': ids
                    }
                });
                this.$message(delResult.data.data);
                if(delResult.data.statusCode==200){
                    this.getData();
                }
            },
            handleUpdate(index, row){
                var id = Base64.encode(row.id);
                this.$router.push({path:'/commodityEdit',name:"commodityEdit", query: {id: id}});
            },
            async updateStatus(index, row, status){
                var product = {
                    'id': row.id,
                    'status': status
                };
                var res = await this.$http.put(baseURL_.mallUrl+'/products/updateStatus',this.$qs.stringify(product));
                this.$message(res.data.data);
                if(res.data.statusCode==200){
                    this.getData();
                }
            },
            handleLook(index, row){
                var id = Base64.encode(row.id);
                this.$router.push({
                    path: '/commodityLook',
                    name: 'commodityLook',
                    query: {
                        id: id
                    }
                });
            },
            add(){
                this.$router.push('/commodityAdd');
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
                    this.productId = id;
                    this.reasonVisible = true;
                }
            },
            updatePro(){
                this.$router.push({
                    path: '/commodityEdit',
                    query: {
                        id: this.productId
                    }
                });
            },
            async getDeliveryTypeList(){
                const res = await this.$http.get(baseURL_.mallUrl+'/products/getDelivery_type');
                this.deliveryTypeList = [];
                this.deliveryTypeList = res.data.data;
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
    .hand :hover{
        cursor: pointer;
    }
</style>