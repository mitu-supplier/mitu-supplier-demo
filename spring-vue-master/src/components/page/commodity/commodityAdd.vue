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
                <el-form-item label="排序" prop="userName">
                    <el-input v-model="addComForm.userName" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="所属商户" prop="password">
                    <!-- <el-input v-model="addComForm.password" size="mini" class="w50"></el-input> -->
                    <el-input
                      id="parentName"
                      readonly
                      autocomplete="off"
                      type="text"
                      class="w50"
                      v-model="orgNames"
                      style="box-sizing: border-box;"
                      @click.native="openOrgTree"
                    ></el-input>
                </el-form-item>

                <el-form-item label="发货类型" prop="confirmPassword">
                    <el-select v-model="addComForm.productEnterType" placeholder="请选择">
                      <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="编码" prop="userName">
                    <el-input v-model="addComForm.userName" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="商品分类" prop="userName">
                    <el-input v-model="addComForm.userName" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="商品名称" prop="userName">
                    <el-input v-model="addComForm.userName" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="商品图片" prop="userName">
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

                <el-form-item label="市场价(元)" prop="userName">
                    <el-input v-model="addComForm.userName" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="供货价(元)" prop="userName">
                    <el-input v-model="addComForm.userName" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item label="状态" prop="userName">
                    <el-radio v-model="addComForm.isSupportApi" label="1">上架</el-radio>
                    <el-radio v-model="addComForm.isSupportApi" label="0">下架</el-radio>
                </el-form-item>

                <el-form-item label="商品详情" prop="userName">
                    <el-input v-model="addComForm.userName" size="mini" class="w50"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="submitAddCom('addComForm')">提交</el-button>
                    <el-button type="danger" @click="back">取消</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
  
<script>
    import { fetchData } from '../../../api/index';
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
                options: [{
                  value: '1',
                  label: '预付款商户'
                }, {
                  value: '2',
                  label: '卡密商户'
                }],
                fileList:[],
            }
        },
        created() {
          
        },
        methods: {
            openOrgTree(){
              alert(12)
            },
            back() {
                this.$router.push({ path: "/login" });
            },
            async submitAddCom(){
              this.$refs['addComForm'].validate(async valid => {
                if (valid) {}
              })
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
              // this.dialogVisible = true;
            },
            fileSuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.bankAccountPermit = file.response.data.path;
            },
            //限制文件的上传数量
            handleExceed(files) {
              this.$message.warning(
                `当前已经上传了${files.length} 个文件，如需上传新的文件，请先删除之前的文件`
              );
            },

            handleRemove(file, fileList) {}
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
</style>