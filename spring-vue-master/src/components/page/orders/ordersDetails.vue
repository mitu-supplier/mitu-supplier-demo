<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 订单详情</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="dityAddbox">
              <el-form
                  :model="addComForm"
                  ref="addComForm"
                  label-width="130px"
                  inline-message
                  class="demo-ruleForm"
              >
                <el-form-item label="订单编号" prop="">
                    <el-input v-model="addComForm.code" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="商品类目" prop="">
                    <el-input v-model="addComForm.catalogName" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="店铺名称" prop="">
                    <el-input v-model="addComForm.supplierName" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="商品名称" prop="">
                    <el-input v-model="addComForm.productName" readonly size="mini" class="w50"></el-input>
                </el-form-item>
                
                <el-form-item label="下单时间" prop="">
                    <el-input v-model="addComForm.orderTime" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="售价（元）" prop="">
                    <el-input v-model="addComForm.price" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="订单数量（份）" prop="">
                    <el-input v-model="addComForm.num" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="订单金额（元）" prop="">
                    <el-input v-model="addComForm.priceTotal" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="用户昵称" prop="">
                    <el-input v-model="addComForm.userName" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="订单状态" prop="">
                    <el-input v-if="addComForm.status == null" size="mini" readonly class="w50"></el-input>
                    <el-input v-else-if="addComForm.status == '1'" size="mini"  readonly class="w50" value="待付款" ></el-input>
                    <el-input v-else-if="addComForm.status == '2'" size="mini"  readonly class="w50" value="待发货" ></el-input>
                    <el-input v-else-if="addComForm.status == '3'" size="mini"  readonly class="w50" value="发货中" ></el-input>
                    <el-input v-else-if="addComForm.status == '4'" size="mini"  readonly class="w50" value="已取消" ></el-input>
                    <el-input v-else-if="addComForm.status == '5'" size="mini"  readonly class="w50" value="售后" ></el-input>
                    <el-input v-else-if="addComForm.status == '6'" size="mini"  readonly class="w50" value="已完成" ></el-input>
                    <el-input v-else size="mini" readonly class="w50" value="" ></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="danger" @click="back">返回</el-button>
                </el-form-item>
            </el-form>
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
            }
        },
        created() {
          this.getData();
        },
        mounted() {

        },
        methods: {
            // 数据回显
            async getData(){
              var id = this.$route.params.id;
              const res = await this.$http.get(baseURL_.mallUrl+'/orders/getById',{
                params: {
                    'id': id
                }
              });
              if(res.data.statusCode==200){
                this.addComForm = res.data.data;
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
</style>