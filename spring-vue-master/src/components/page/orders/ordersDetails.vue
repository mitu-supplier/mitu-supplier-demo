<template>
    <div class="table">
        <!-- <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 订单详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div> -->
        <div class="dityAddbox">
          <el-row class="s_title">
              <el-col>| 订单信息</el-col>
          </el-row>
          <el-row class="tabform">
              <div>
                <span>订单编号：</span>
                <span>{{addComForm.code}}</span>
              </div>
              <div>
                <span>店铺名称：</span>
                <span>{{addComForm.supplierName}}</span>
              </div>
              <div>
                <span>下单时间：</span>
                <span>{{addComForm.createdAt}}</span>
              </div>
              <div>
                <span>订单金额：</span>
                <span>{{addComForm.priceTotal}}</span>
              </div>
              <div>
                <span>用户昵称：</span>
                <span>{{addComForm.userName}}</span>
              </div>
              <div>
                <span>订单状态：</span>
                <span v-if="addComForm.status == null" ></span>
                <span v-else-if="addComForm.status == '1'" >待付款</span>
                <span v-else-if="addComForm.status == '2'" >待发货</span>
                <span v-else-if="addComForm.status == '3'" >发货中</span>
                <span v-else-if="addComForm.status == '4'" >已取消</span>
                <span v-else-if="addComForm.status == '5'" >售后</span>
                <span v-else-if="addComForm.status == '6'" >已完成</span>
                <span v-else size="mini" readonly class="w50" value="" ></span>
              </div>
            </el-row>

            <el-row class="mt20 s_title">
              <el-col>| 商品信息</el-col>
            </el-row>

            <el-table :data="tableData" border class="table w60 ml30 mt20" ref="multipleTable">
              <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
              <el-table-column prop="productName" label="商品名称"  align="center" width=""></el-table-column>
              <el-table-column prop="num" label="数量"  align="center" width=""></el-table-column>
              <el-table-column label="小计（元）" align="center" width="">
                <template slot-scope="scope">
                    <span type="text" v-if="scope.row.price == null">0.00</span>
                    <span type="text" v-else>{{scope.row.price.toFixed(3)}}</span>
                </template>
            </el-table-column>
              <el-table-column label="小计（元）" align="center" width="">
                <template slot-scope="scope">
                    <span type="text" v-if="scope.row.price == null">0.00</span>
                    <span type="text" v-else>{{(scope.row.num * scope.row.price).toFixed(3)}}</span>
                </template>
            </el-table-column>
          </el-table>
          <el-button class="mt20 ml25" type="danger" @click="back">返回</el-button>
        </div>
    </div>
</template>
  
<script>
    import baseURL_ from '@/utils/baseUrl.js';
    export default {
        name: 'orderdetails',
        data() {
            return {
                addComForm:{

                },
                options: [],
                fileList:[],
                orgNames:'',
                orgSetting: {
                  data: {
                    simpleData: {
                      enable: true,
                      idKey: "id",
                      pIdKey: "parentId"
                    }
                  },
                  callback: {
                    onClick: this.zTreeOnClick,
                    beforeClick: this.beforeClick
                  }
                  
                },
                orgTreeShow:false,
                // editor: null,
                // editorContent: ''
                content:'',
                catalogId:'',
                detailsId:'',
                tableData: []
            }
        },
        created() {
          
        },
        mounted() {
          var id = this.$route.query.id;
          this.detailsId = Base64.decode(id);
          this.getData();
        },
        methods: {
            // 数据回显
            async getData(){
              var id = this.detailsId;
              const res = await this.$http.get(baseURL_.mallUrl+'/orders/getById',{
                params: {
                    'id': id
                }
              });
              if(res.data.statusCode==200){
                this.addComForm = res.data.data;
                this.addComForm.priceTotal = this.addComForm.priceTotal.toFixed(3);
                this.tableData = res.data.data.itemList;
              }else{
                this.$message(res.data.data);
              }
            },
            
            back() {
                this.$router.push({ path: "/ordersList" });
            }
        }
    }

</script>

<style scoped>
    .dityAddbox{
        min-height:500px;
        background: #fff;
        padding:20px;
        box-sizing: border-box;
    }
    .w50{
      width:30%;
    }
    .w60{
      width:60%;
    }
    .zTreeStyle {
      width: 240px;
      height: 300px;
      overflow-x: hidden;
      overflow-y: scroll;
      z-index: 999999;
      position: absolute;
      background-color: #fff;
      border:1px solid #ccc;
      border-radius: 4px;
    }
    /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
    ::-webkit-scrollbar {
      width: 8px;
      height: 8px;
      background-color: #f5f5f5;
    }

    /*定义滚动条轨道 内阴影+圆角*/
    ::-webkit-scrollbar-track {
      background-color: #f5f5f5;
    }

    /*定义滑块 内阴影+圆角*/
    ::-webkit-scrollbar-thumb {
      border-radius: 4px;
      background-color: #ccc;
    }
    .ml30{
      margin-left: 30px;
    }
    .mt20{
      margin-top: 20px;
    }
    .tabform{
      width: 100%;

    }
    .tabform div{
      width:34%;
      float: left;
      font-size: 14px;
      color:#333;
      margin-top: 20px;
    }
    .tabform div span:nth-child(1){
      display: inline-block;
      width:120px;
      text-align: right;
      margin-right: 10px;
      color: #666;
    }
    .ml25{
      margin-left: 25%;
    }
    .s_title{
      font-size:14px; 
      color: #07c4a8;
    }
</style>