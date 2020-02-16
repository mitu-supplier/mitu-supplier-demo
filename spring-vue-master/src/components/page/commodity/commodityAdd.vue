<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 新增商品</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="dityAddbox">
              <el-form
                  :model="addComForm"
                  :rules="rules"
                  ref="addComForm"
                  label-width="130px"
                  inline-message
                  class="demo-ruleForm"
              >
                <!-- <el-form-item label="排序" prop="">
                    <el-input v-model="addComForm.sort" size="mini" class="w50"></el-input>
                </el-form-item> -->

                <el-form-item label="发货类型" prop="deliveryType">
                    <el-select v-model="addComForm.deliveryType" placeholder="请选择">
                      <el-option
                        v-for="item in options"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                      </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="编码" prop="">
                    <el-input v-model="addComForm.code" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item v-if="isSupplier != '1'" label="所属商户" prop="supplierId">
                    <el-select v-model="addComForm.supplierId" placeholder="请选择">
                      <el-option
                        v-for="item in suppliers"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                      </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="商品分类:" prop="orgNames2" id="orgTreeBox">
                  <input type="hidden" v-model="addComForm.orgNames2">
                  <!-- catalog_id -->
                  <input type="hidden" >
                  <!-- 所属栏目树 -->
                    <el-input
                      placeholder="请选择"
                      id="parentName"
                      readonly
                      autocomplete="off"
                      type="text"
                      class="w50"
                      v-model="orgNames"
                      style="box-sizing: border-box;"
                      @click.native="openOrgTree"
                    ></el-input>

                    <input id="parentId" type="hidden">
                    <input id="flag" type="hidden">
                    <!-- 树 -->
                    <div id="tree" class="tree-box zTreeStyle" v-show="orgTreeShow">
                      <div class="zTreeDemoBackground left">
                        <ul id="orgTree" class="ztree"></ul>
                      </div>
                    </div>
                </el-form-item>

                <el-form-item label="商品名称" prop="name">
                    <el-input v-model="addComForm.name" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="商品图片" prop="">
                    <el-upload
                        accept=".jpg, .png"
                        list-type="picture-card"
                        :action="uploadUrl()"
                        :on-change="changeImg"
                        :on-preview="handlePictureCardPreview"
                        :on-remove="handleRemove"
                        :on-success="fileSuccess"
                        multiple
                        :limit="1"
                        :on-exceed="handleExceed"
                        :file-list="fileList">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip " >只能上传jpg/png文件</div>
                    </el-upload>
                </el-form-item>

                <el-form-item label="市场价(元)" prop="price">
                    <el-input v-model="addComForm.price" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="供货价(元)" prop="supplyPrice">
                    <el-input v-model="addComForm.supplyPrice" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="商品详情" prop="protalDetails">
                    <input type="hidden" v-model="addComForm.protalDetails">
                    <!-- <UE :defaultMsg=defaultMsg :config=config ref="ue"></UE> -->
                    <div class="editor-container">
                      <div id="editor"></div>
                    </div>
                </el-form-item>

                <el-form-item label="使用说明" prop="useDirections">
                    <input type="hidden" v-model="addComForm.useDirections">
                    <!-- <UE :defaultMsg=defaultMsg :config=config ref="ue"></UE> -->
                    <div class="editor-container">
                      <div id="useDirections_editor"></div>
                    </div>
                </el-form-item>

                <el-form-item label="维护配置" prop="">
                    <el-date-picker
                      size="mini"              
                      v-model="maintainConfig"
                      type="datetimerange"
                      range-separator="至"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      start-placeholder="开始时间"
                      end-placeholder="结束时间">
                    </el-date-picker>
                </el-form-item>

                <el-form-item label="维护提示语" prop="">
                    <el-input v-model="addComForm.maintainMessage" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="库存售卖阈值" prop="">
                    <el-input v-model="addComForm.inventorySellingThreshold" size="mini" class="w50" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
                </el-form-item>

                <el-form-item label="库存不足提示语" prop="">
                    <el-input v-model="addComForm.inventoryAlertMessage" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="库存报警阈值" prop="">
                    <el-input v-model="addComForm.inventoryAlertNum" size="mini" class="w50" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
                </el-form-item>

                <el-form-item label="发货状态" prop="">
                    <el-select v-model="deliveryStatus" multiple placeholder="请选择">
                      <el-option
                        v-for="item in deliveryStatusList"
                        :key="item.id"
                        :label="item.deliveryStatus"
                        :value="item.deliveryStatus">
                      </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="状态" prop="status">
                    <el-radio v-model="addComForm.status" label="1">上架</el-radio>
                    <el-radio v-model="addComForm.status" label="2">下架</el-radio>
                </el-form-item>

                <el-form-item>
                    <el-button type="success" @click="staging('addComForm')">暂存</el-button>
                    <el-button type="primary" @click="submitAddCom('addComForm')">提交</el-button>
                    <el-button type="danger" @click="back">返回</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import { fetchData } from '../../../api/index';
    import ueditor_ from '../../common/ueditor';
    import baseURL_ from '@/utils/baseUrl.js';
    export default {
        name: 'commodityAdd',
        components:{
          UE,
        },
        props: {},
        data() {
            return {
                defaultMsg: '',
                config: {
                  initialFrameWidth: null,
                  initialFrameHeight: 350
                },
                addComForm:{
                  code:'',
                  orgNames2:'',
                  protalDetails:'',
                  useDirections:'',
                  deliveryStatus:'',
                  maintainConfigStart:'',
                  maintainConfigEnd:''
                },
                rules: {
                    deliveryType: [{ required: true, message: "请选择发货类型", trigger: "change" }],
                    supplierId: [{ required: true, message: "请选择所属商户", trigger: "change" }],
                    orgNames2: [{ required: true, message: "请选择商品分类", trigger: "change" }],
                    name: [{ required: true, message: "请输入商品名称", trigger: "blur" }],
                    price: [{ required: true, message: "请输入市场价", trigger: "blur" }],
                    supplyPrice: [{ required: true, message: "请输入供货价", trigger: "blur" }],
                    protalDetails: [{ required: true, message: "请输入商品详情", trigger: "change" }],
                    useDirections: [{ required: true, message: "请输入使用说明", trigger: "change" }],
                    status: [{ required: true, message: "请选择状态", trigger: "blur" }],
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
                content:'',
                catalogId:'',
                isSupplier: 1,
                suppliers:[],
                maintainConfig: [],
                deliveryStatus: [],
                deliveryStatusList: []
            }
        },
        created() {
          this.getSelectForm();
          this.vaIsSupplier();
          this.getSuppliers();
          this.getDeliveryStatus();
        },
        mounted() {
          // 商品详情
          UE.delEditor("editor");
          ueditor_.methods.loadComponent("editor");
          var usd = UE.getEditor("editor");
          usd.addListener("ready", function() {
            usd.setHeight(366);
          });
          // 使用说明
          UE.delEditor("useDirections_editor");
          ueditor_.methods.loadComponent("useDirections_editor");
          var useDirectionsEditor = UE.getEditor("useDirections_editor");
          useDirectionsEditor.addListener("ready", function() {
            useDirectionsEditor.setHeight(366);
          });

        },
        methods: {
            changeImg(){
              console.log(666)
            },
            async getSelectForm(){
              const res = await this.$http.get(baseURL_.mallUrl+'/products/getDelivery_type');
              this.options = [];
              this.options = res.data.data;
            },
            async vaIsSupplier(){
              var res = await this.$http.get(baseURL_.mallUrl+'/products/isSupplier');
              this.isSupplier = res.data.data;
            },
            async getSuppliers(){
              var res = await this.$http.get(baseURL_.mallUrl+'/products/getSuppliers');
              this.suppliers = [];
              this.suppliers = res.data.data;
            },
            zTreeOnClick(event, treeId, treeNode) {
              //给选中的节点赋值
              this.orgNames = treeNode.name;
              this.addComForm.orgNames2 = treeNode.id;
              this.catalogId = treeNode.id;
              this.orgTreeShow = false;
            },
            beforeClick(treeId, treeNode) {
              //设置父节点不能被选择
              if (treeNode.isParent == true) {
                this.$message.error("父节点不能勾选，请勾选子节点");
                return false;
              }
            },
            async openOrgTree() {
                $("#flag").val("");
                const res = await this.$http.get(baseURL_.mallUrl+'/products/getCatalogs');
                $.fn.zTree.init($("#orgTree"), this.orgSetting, res.data.data);
                this.orgTreeShow = true;
                // 小树关闭
                const that = this;
                $(document).click(function(e) {
                  var target = $(e.target);
                  if (target.is("#orgTreeBox .el-form-item__content *")) {
                    // that.orgTreeShow = true;
                  } else {
                    that.orgTreeShow = false;
                  }
                });
            },
            back() {
                this.$router.push({ path: "/productList" });
            },
            // 提交审核
            async submitAddCom(){
              var details = UE.getEditor('editor').getContent();
              this.addComForm.protalDetails = details;
              var useDirections = UE.getEditor('useDirections_editor').getContent();
              this.addComForm.useDirections = useDirections;
              this.$refs['addComForm'].validate(async valid => {
                if (valid) {
                
                  this.addComForm.catalogId = this.catalogId;
                  this.addComForm.details = details;
                  this.addComForm.deliveryStatus = this.deliveryStatus.join(",");
                  var maintainConfigs = this.maintainConfig;
                  if(maintainConfigs != null && maintainConfigs.length == 2){
                    this.addComForm.maintainConfigStart = maintainConfigs[0];
                    this.addComForm.maintainConfigEnd = maintainConfigs[1];
                  }
                  this.addComForm.auditStatus = 0;
                  const res = await this.$http.post(baseURL_.mallUrl+'/products/save',this.$qs.stringify(this.addComForm));
                  this.$message(res.data.data);
                  if(res.data.statusCode==200){
                    this.$router.push('/productList');
                  }
                }
              })
            },
            // 暂存
            async staging(){
              // this.$refs['addComForm'].validate(async valid => {
              //   if (valid) {
                  var useDirections = UE.getEditor('useDirections_editor').getContent();
                  var details = UE.getEditor('editor').getContent();
                  this.addComForm.protalDetails = details;
                  this.addComForm.catalogId = this.catalogId;
                  this.addComForm.details = details;
                  this.addComForm.useDirections = useDirections;
                  this.addComForm.deliveryStatus = this.deliveryStatus.join(",");
                  var maintainConfigs = this.maintainConfig;
                  if(maintainConfigs != null && maintainConfigs.length == 2){
                    this.addComForm.maintainConfigStart = maintainConfigs[0];
                    this.addComForm.maintainConfigEnd = maintainConfigs[1];
                  }
                  this.addComForm.auditStatus = 3;
                  const res = await this.$http.post(baseURL_.mallUrl+'/products/save',this.$qs.stringify(this.addComForm));
                  this.$message(res.data.data);
                  if(res.data.statusCode==200){
                    this.$router.push('/productList');
                  }
              //   }
              // })
            },
            // Logo上传
            uploadUrl() {
              console.log(1111)
              return baseURL_.fileUrl+'/file/upload';
            },
            beforeUpload(file) {
              var FileExt = file.name.replace(/.+\./, "");
              if (["png", "jpg"].indexOf(FileExt.toLowerCase()) === -1) {
                this.$message({
                  type: "warning",
                  message: "请上传后缀名为png或者jpg的文件！"
                });
                return false;
              }
            },
            handlePictureCardPreview(file) {
              console.log(556677)
              this.dialogImageUrl = file.url;
              // this.dialogVisible = true;
            },
            fileSuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.addComForm.img = file.response.data.path;
            },
            //限制文件的上传数量
            handleExceed(files) {
              console.log(222)
              this.$message.warning(
                `当前已经上传了${files.length} 个文件，如需上传新的文件，请先删除之前的文件`
              );
            },

            handleRemove(file, fileList) {},

            async getDeliveryStatus(){
              var res = await this.$http.get(baseURL_.mallUrl+'/products/getDeliveryStatus');
              this.deliveryStatusList = [];
              this.deliveryStatusList = res.data.data;
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
      width:20%;
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