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

                <el-form-item label="发货类型" prop="">
                    <el-select v-model="addComForm.deliveryType" disabled readonly placeholder="请选择">
                      <el-option
                        v-for="item in options"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                      </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="编码" prop="">
                    <el-input v-model="addComForm.code" size="mini" readonly class="w50"></el-input>
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
                        list-type="picture-card"
                        :action="uploadUrl()"
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

                <el-form-item label="市场价(元)" prop="">
                    <el-input v-model="addComForm.price" size="mini" readonly class="w50"></el-input>
                </el-form-item>

                <el-form-item label="供货价(元)" prop="">
                    <el-input v-model="addComForm.supplyPrice" size="mini" readonly class="w50"></el-input>
                </el-form-item>

                <el-form-item label="状态" prop="">
                    <el-radio v-if="addComForm.status=='1'" v-model="addComForm.status" :label="1">上架</el-radio>
                    <el-radio v-if="addComForm.status=='2'" v-model="addComForm.status" :label="2">下架</el-radio>
                </el-form-item>

                <el-form-item label="商品详情" prop="">
                    <div class="editor-container">
                      <div id="editor"></div>
                    </div>
                </el-form-item>

                <el-form-item>
                    <el-button type="danger" v-if="addComForm.auditStatus =='0'" @click="auditAdopt()">审核通过</el-button>
                    <el-button type="danger" v-if="addComForm.auditStatus =='0'" @click="auditReject()">审核失败</el-button>
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
                productId:''
            }
        },
        created() {
          this.getData();
          this.getSelectForm();
        },
        mounted() {
          UE.delEditor("editor");
          ueditor_.methods.loadComponent("editor");
        },
        methods: {
            // 数据回显
            async getData(){
              var id = this.$route.query.id;
              const res = await this.$http.get(baseURL_.mallUrl+'/products_audit/getById',{
                params: {
                    'id': id
                }
              });
              if(res.data.statusCode==200){
                this.addComForm = res.data.data;
                this.fileList = [{uel:this.addComForm.img}];
                this.orgNames = this.addComForm.catalogName;
                this.catalogId = this.addComForm.catalogId;
                this.productId = this.addComForm.id;
                var details = this.addComForm.details;
                var usd = UE.getEditor("editor");
                usd.ready(function() {
                  usd.setHeight(366);
                  usd.setContent(details);
                });
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
                this.$router.push({ path: "/productAuditList" });
            },
            // async submitAddCom(){
            //   this.$refs['addComForm'].validate(async valid => {
            //     if (valid) {
            //       this.addComForm.catalogId = this.catalogId;
            //       const res = await this.$http.post(baseURL_.mallUrl+'/products/save',this.$qs.stringify(this.addComForm));
            //       this.$message(res.data.data);
            //       if(res.data.statusCode==200){
            //         this.$router.push('/productList');
            //       }
            //     }
            //   })
            // },
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
              this.$message.warning(
                `当前已经上传了${files.length} 个文件，如需上传新的文件，请先删除之前的文件`
              );
            },

            handleRemove(file, fileList) {},
            // 审核
            auditAdopt(){
                this.$prompt('请输入发货代号：', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputPattern: /^[\s\S]*.*[^\s][\s\S]*$/,
                    inputErrorMessage: '发货代号不能为空'
                }).then(({ value }) => {
                    this.confimAudit(this.productId,1,null,value);
                }).catch(() => { }); 
            },
            auditReject(){
                this.$prompt('请输入审核失败理由：', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputPattern: /^[\s\S]*.*[^\s][\s\S]*$/,
                    inputErrorMessage: '理由不能为空'
                }).then(({ value }) => {
                    this.confimAudit(this.productId,2,value,null)
                }).catch(() => { }); 
            },
            async confimAudit(id,auditStatus,auditReason,deliveryTypeCode){
                var auditResult = await this.$http.get(baseURL_.mallUrl+'/products_audit/audit', {
                    params:{
                        id:id,
                        auditStatus:auditStatus,
                        auditReason:auditReason,
                        deliveryTypeCode:deliveryTypeCode
                    }
                });
                this.$message(auditResult.data.data);
                if(auditResult.data.statusCode==200){
                    this.back();
                }
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