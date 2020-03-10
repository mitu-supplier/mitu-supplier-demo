<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 商品详情</el-breadcrumb-item>
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

                <el-form-item label="商品类型" prop="">
                    <el-select v-model="addComForm.deliveryType" placeholder="请选择">
                      <el-option
                        disabled
                        v-for="item in options"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                      </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="发货类型" prop="deliveryKind" v-if="addComForm.deliveryName == '直充'">
                    <el-select v-model="addComForm.deliveryKind" placeholder="请选择">
                      <el-option
                        v-for="item in deliverOptions"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                      </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="编码" prop="">
                    <el-input v-model="addComForm.code" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item v-if="isSupplier != '1'" label="所属商户" prop="">
                    <el-input v-model="addComForm.supplierName" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="商品分类:" prop="orgNames2" id="orgTreeBox">
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

                <el-form-item label="商品名称" prop="">
                    <el-input v-model="addComForm.name" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="商品图片" prop="">
                    <el-upload
                        accept=".jpg, .png"
                        :disabled=true
                        list-type="picture-card"
                        :action="uploadUrl()"
                        :on-preview="handlePictureCardPreview"
                        :on-remove="handleRemove"
                        :on-success="fileSuccess"
                        multiple
                        :on-exceed="handleExceed"
                        :file-list="fileList"
                        class="upload_img_view">
                        <el-button size="small" type="primary">点击上传</el-button>
                    </el-upload>
                </el-form-item>

                <el-form-item label="市场价(元)" prop="">
                    <el-input v-model="addComForm.price" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="供货价(元)" prop="">
                    <el-input v-model="addComForm.supplyPrice" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="商品详情" prop="">
                    <div class="editor-container">
                      <div id="editor"></div>
                    </div>
                </el-form-item>

                <el-form-item label="使用说明" prop="useDirections">
                    <div class="editor-container">
                      <div id="useDirections_editor"></div>
                    </div>
                </el-form-item>

                <el-form-item label="维护配置" prop="">
                    <el-date-picker
                      disabled
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
                    <el-input v-model="addComForm.maintainMessage" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="库存售卖阈值" prop="">
                    <el-input v-model="addComForm.inventorySellingThreshold" readonly size="mini" class="w50" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
                </el-form-item>

                <el-form-item label="库存不足提示语" prop="">
                    <el-input v-model="addComForm.inventoryAlertMessage" readonly size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="库存报警阈值" prop="">
                    <el-input v-model="addComForm.inventoryAlertNum" size="mini" readonly class="w50" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
                </el-form-item>

                <el-form-item label="发货状态" prop="">
                    <el-select v-model="deliveryStatus" multiple placeholder="请选择">
                      <el-option
                        disabled
                        v-for="item in deliveryStatusList"
                        :key="item.id"
                        :label="item.deliveryStatus"
                        :value="item.id+''">
                      </el-option>
                    </el-select>
                </el-form-item>
                

                <el-form-item label="状态" prop="">
                    <el-radio v-model="addComForm.status" disabled readonly :label="1">上架</el-radio>
                    <el-radio v-model="addComForm.status" disabled readonly :label="2">下架</el-radio>
                </el-form-item>

                <el-form-item>
                    <el-button type="danger" @click="back">返回</el-button>
                </el-form-item>
                <el-row class="tipsBoxImg">
                  <el-dialog :visible.sync="dialogVisible" size="tiny" style="text-align:center;">
                    <img  :src="dialogImageUrl" style="padding-bottom: 50px;"/>
                  </el-dialog>
                </el-row>
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
        data() {
            return {
                addComForm:{

                },
                rules: {
                    password: [{ required: true, message: "请输入密码", trigger: "blur" }],
                    confirmPassword: [{ required: true, message: "请输入密码", trigger: "blur" }]
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
                isSupplier: 1,
                dialogVisible:false,
                dialogImageUrl:'',
                detailsId:'',
                maintainConfig: [],
                deliveryStatus: [],
                deliveryStatusList: []
            }
        },
        created() {
          
          this.getSelectForm();
          this.getDeliverForm();
          this.vaIsSupplier();
          this.getDeliveryStatus();
        },
        mounted() {
          var id = this.$route.query.id;
          this.detailsId = Base64.decode(id);
          this.getData();
          UE.delEditor("editor");
          ueditor_.methods.loadComponent("editor");
          // UE.getEditor('editor').setDisabled('fullscreen');
          // disableBtn("enable");
          // 使用说明
          UE.delEditor("useDirections_editor");
          ueditor_.methods.loadComponent("useDirections_editor");
        },
        methods: {
            async getDeliverForm(){
              const res = await this.$http.get(baseURL_.mallUrl+'/products/getDeliveryKind');
              this.deliverOptions = [];
              this.deliverOptions = res.data.data;
            },
            // 数据回显
            async getData(){
              var id = this.detailsId;
              const res = await this.$http.get(baseURL_.mallUrl+'/products/getById',{
                params: {
                    'id': id
                }
              });
              if(res.data.statusCode==200){
                this.addComForm = res.data.data;
                // var contracArry=[];
                // var obj={};
                // obj.name="缩略图";
                // obj.url=this.addComForm.img;
                // contracArry.push(obj);
                // this.fileList=contracArry;
                this.fileList = [];
                if(this.addComForm.productPicList.length > 0){
                    var json = {};
                    for(let k=0; k<this.addComForm.productPicList.length; k++){
                        json = {url:this.addComForm.productPicList[k].path};
                        this.fileList.push(json);
                    }
                }
                this.orgNames = this.addComForm.catalogName;
                this.catalogId = this.addComForm.catalogId;
                var details = this.addComForm.details
                // UE.getEditor('editor').setContent(details);
                // var content = this.form.content;
                var usd = UE.getEditor("editor");
                usd.ready(function() {
                  usd.setHeight(366);
                  usd.setContent(details);
                  usd.setDisabled();
                });
                var useDirections = this.addComForm.useDirections;
                var useDirectionsEditor = UE.getEditor("useDirections_editor");
                useDirectionsEditor.ready(function() {
                  useDirectionsEditor.setHeight(366);
                  useDirectionsEditor.setContent(useDirections);
                  useDirectionsEditor.setDisabled();
                });
                this.maintainConfig = [this.addComForm.maintainConfigStart, this.addComForm.maintainConfigEnd];
                var deliveryStatus = this.addComForm.deliveryStatus;
                if(deliveryStatus != null || deliveryStatus != ''){
                  this.deliveryStatus = deliveryStatus.split(',');
                }
              }else{
                this.$message(res.data.data);
              }
            },
            // 获取下拉框
            async getSelectForm(){
              const res = await this.$http.get(baseURL_.mallUrl+'/products/getDelivery_type');
              this.options = [];
              this.options = res.data.data;
            },
            zTreeOnClick(event, treeId, treeNode) {
              //给选中的节点赋值
              this.orgNames = treeNode.name;
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
            
            back() {
                this.$router.push({ path: "/productList" });
            },
            
            // Logo上传
            uploadUrl() {
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
              this.dialogImageUrl = file.url;
              this.dialogVisible = true;
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
              this.$message.warning(
                `当前已经上传了${files.length} 个文件，如需上传新的文件，请先删除之前的文件`
              );
            },

            handleRemove(file, fileList) {},
            async vaIsSupplier(){
              var res = await this.$http.get(baseURL_.mallUrl+'/products/isSupplier');
              this.isSupplier = res.data.data;
            },
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
<style >
  .tipsBoxImg .el-dialog__header{
      padding: 20px 20px 30px !important;
  }
</style>