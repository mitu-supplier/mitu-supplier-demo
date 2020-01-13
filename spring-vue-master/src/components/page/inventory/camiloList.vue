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
            <div class="handle-box">
                <el-upload
                    class="upload-excel-file inline-block"
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
                <!-- <el-button type="primary" @click="exportExcel" icon="el-icon-download">批量导出</el-button> -->
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable">
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
                formInline:{
                    name:'',
                    catalogName:''
                },
                myHeaders: {
                    'token': ''
                }
            }
        },
        created() {
            var token = localStorage.getItem('forestToken');
            this.myHeaders.token = token;
            this.getData();
        },
        methods: {
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
</style>