<template>
    <div class="table">
        <div class="">
            <el-row style="background: #fff;">
                <el-col :span="14" >
                    <div class="counnet">
                    <div class="tipSize">供应商新增</div>
                    <div  style="margin-left:8%;margin-top:20px;">
                        <el-form
                            :model="newRuleForm"
                            :rules="rules"
                            ref="newRuleForm"
                            label-width="180px"
                            inline-message
                            class="demo-ruleForm"
                        >
                          <el-form-item label="登录名" prop="loginName">
                              <el-input v-model="newRuleForm.loginName" size="mini" class="w50"></el-input>
                          </el-form-item>

                          <el-form-item label="密码" prop="password">
                              <el-input type="password" v-model="newRuleForm.password" size="mini" class="w50"></el-input>
                          </el-form-item>

                          <el-form-item label="确认密码" prop="confirmPassword">
                              <el-input type="password" v-model="newRuleForm.confirmPassword" size="mini" class="w50"></el-input>
                          </el-form-item>

                      </el-form>
                    </div>
                    <div class="tipSize">联系人信息</div>
                    <div style="margin-left:8%;">
                          <el-form
                              :model="newRuleForm"
                              label-width="180px"
                              inline-message
                              class="demo-ruleForm"
                          >
                            
                            <el-form-item label="姓名">
                                <el-input v-model="newRuleForm.userName" size="mini" maxlength="11" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="手机号">
                                <el-input v-model="newRuleForm.phone" size="mini" maxlength="11" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="邮箱">
                                <el-input v-model="newRuleForm.email" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="对接招商人员" >
                                <el-input v-model="newRuleForm.investmentPerson" size="mini" class="w50"></el-input>
                            </el-form-item>
                            
                        </el-form>
                    </div>
                    
                    
                    <div class="tipSize">公司主体信息</div>
                    <div style="margin-left:8%">
                        <el-form
                            :model="newRuleForm"
                            ref="supplierForm"
                            :rules="rulessupplier"
                            label-width="180px"
                            inline-message
                            class="demo-ruleForm"
                        >
                          <el-form-item label="商户号">
                              <el-input v-model="newRuleForm.code" readonly size="mini" class="w50"></el-input>
                          </el-form-item>
                          
                          <el-form-item label="公司名称" prop="name">
                              <el-input v-model="newRuleForm.name" size="mini" class="w50"></el-input>
                          </el-form-item>

                          <el-form-item label="公司简称" prop="shortName">
                              <el-input v-model="newRuleForm.shortName" size="mini" class="w50"></el-input>
                          </el-form-item>

                            <el-form-item label="营业执照">
                                <el-upload
                                    accept=".jpg, .png"
                                    list-type="picture-card"
                                    :action="uploadUrl()"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove"
                                    :on-success="businessSuccess"
                                    multiple
                                    :limit="1"
                                    :on-exceed="handleExceed"
                                    :file-list="businessList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip " >只能上传jpg/png文件</div>
                                </el-upload>

                            </el-form-item>

                            <el-form-item label="营业范围">
                              <el-input v-model="newRuleForm.businessScope" size="mini" class="w50"></el-input>
                            </el-form-item>
                            
                            <el-form-item label="公司注册地址">
                                <el-input v-model="newRuleForm.registerAddress" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="法人姓名">
                                <el-input v-model="newRuleForm.legalName" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="法人代表证件类型">
                                <el-select v-model="newRuleForm.legalCardType" placeholder="请选择">
                                  <el-option
                                    v-for="item in LEGAL_CARD_TYPE"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                                  </el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item label="法人代表身份证件(正反面)">
                              <el-upload
                                    accept=".jpg, .png"
                                    list-type="picture-card"
                                    :action="uploadUrl()"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove"
                                    :on-success="legalCardSuccess"
                                    multiple
                                    :limit="2"
                                    :on-exceed="handleExceed2"
                                    :file-list="legalCardList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip " >只能上传jpg/png文件</div>
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="法人身份证有效期">
                                <!-- <el-date-picker
                                    v-model="value2"
                                    type="daterange"
                                    format="yyyy-MM-dd"
                                    value-format="yyyy-MM-dd"
                                    placeholder="选择日期时间">
                                </el-date-picker> -->
                                <el-date-picker
                                  size="mini"              
                                  v-model="value2"
                                  type="daterange"
                                  align="right"
                                  format="yyyy-MM-dd"
                                  value-format="yyyy-MM-dd"
                                  range-separator="至"
                                  start-placeholder="开始日期"
                                  end-placeholder="结束日期"
                                  class="w50">
                                </el-date-picker>
                            </el-form-item>

                            <el-form-item label="公司电话">
                                <el-input v-model="newRuleForm.contactMobile" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="公司联系人">
                                <el-input v-model="newRuleForm.contactName" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="纳税人类型">
                                <el-select v-model="newRuleForm.taxpayerType" placeholder="请选择">
                                  <el-option
                                    v-for="item in TAXPAYER_TYPE"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                                  </el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item label="税号">
                                <el-input v-model="newRuleForm.taxNumber" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="税务登记证">
                                <el-upload
                                    accept=".jpg, .png"
                                    list-type="picture-card"
                                    :action="uploadUrl()"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove"
                                    :on-success="taxRegSuccess"
                                    multiple
                                    :limit="1"
                                    :on-exceed="handleExceed"
                                    :file-list="taxRegList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip " >只能上传jpg/png文件</div>
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="一般纳税人正面">
                                <el-upload
                                    accept=".jpg, .png"
                                    list-type="picture-card"
                                    :action="uploadUrl()"
                                    :on-success="taxpayerSuccess"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove"
                                    multiple
                                    :limit="1"
                                    :on-exceed="handleExceed"
                                    :file-list="taxpayeList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip " >只能上传jpg/png文件</div>
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="银行开户许可证">
                                <el-upload
                                    accept=".jpg, .png"
                                    list-type="picture-card"
                                    :action="uploadUrl()"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove"
                                    :on-success="bankAccountSuccess"
                                    multiple
                                    :limit="1"
                                    :on-exceed="handleExceed"
                                    :file-list="bankAccountList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip " >只能上传jpg/png文件</div>
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="开户名称">
                                <el-input v-model="newRuleForm.bankName" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="开户账号">
                                <el-input v-model="newRuleForm.bankAccount" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="开户银行所在地">
                                <el-input v-model="newRuleForm.bankAddress" size="mini" class="w50"></el-input>
                            </el-form-item>
                            
                      </el-form>
                    </div>
                    <div class="tipSize">公司经营信息</div>
                    <div >
                          <el-form
                            :model="newRuleForm"
                            ref="supplierIfrim"
                            :rules="rulesIfrim"
                            label-width="180px"
                            inline-message
                            class="demo-ruleForm"
                            style="margin-left:8%"
                          >
                          
                            <el-form-item label="公司类型" >
                              <el-select v-model="newRuleForm.type" placeholder="请选择">
                                <el-option
                                  v-for="item in COMPANY_TYPE"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id">
                                </el-option>
                              </el-select>
                            </el-form-item>

                            <el-form-item label="近一年销售额（万元）" prop="name">
                                <el-input v-model="newRuleForm.sales" size="mini" oninput="value=value.replace(/[^\d^\.]+/g,'').replace('.','$#$').replace(/\./g,'').replace('$#$','.')" v-on:blur="toFix" class="w50"></el-input>
                            </el-form-item>
                            
                            <el-form-item label="入驻类型" >
                              <el-select v-model="newRuleForm.enterType" placeholder="请选择">
                                <el-option
                                  v-for="item in SettledType"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id">
                                </el-option>
                              </el-select>
                            </el-form-item>

                            <el-form-item label="仓储情况">
                                <el-input v-model="newRuleForm.storageInfo" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="物流情况">
                                <el-input v-model="newRuleForm.logisticsInfo" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="是否支持api对接">
                                <el-radio v-model="newRuleForm.isSupportApi" :label="1">支持</el-radio>
                                <el-radio v-model="newRuleForm.isSupportApi" :label="0">不支持</el-radio>
                            </el-form-item>

                            <el-form-item label="虚拟产品入驻形式" prop="productEnterType">
                              <el-select v-model="newRuleForm.productEnterType" placeholder="请选择">
                                <el-option
                                  v-for="item in PRODUCT_ENTER_TYPE"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id">
                                </el-option>
                              </el-select>
                            </el-form-item>

                            <el-form-item label="签约公司">
                              <el-select v-model="newRuleForm.signCompany" placeholder="请选择">
                                <el-option
                                  v-for="item in CompanyType"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id">
                                </el-option>
                              </el-select>
                            </el-form-item>

                        </el-form>
                    </div>
                    <div class="tipSize">品牌资质</div>
                    <div style="margin-left:8%">
                          <el-form
                              :model="newRuleForm"
                              label-width="180px"
                              inline-message
                              class="demo-ruleForm"
                          >
                            <el-form-item label="商标注册证">
                                <el-upload
                                    accept=".jpg, .png"
                                    list-type="picture-card"
                                    :action="uploadUrl()"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove"
                                    :on-success="handletradeSuccess"
                                    multiple
                                    :limit="1"
                                    :on-exceed="handleExceed"
                                    :file-list="trademarkList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip " >只能上传jpg/png文件</div>
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="品牌授权书">
                                <el-upload
                                    accept=".jpg, .png"
                                    list-type="picture-card"
                                    :action="uploadUrl()"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove"
                                    multiple
                                    :limit="1"
                                    :on-exceed="handleExceed"
                                    :on-success="handleBrandSuccess"
                                    :file-list="brandList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip " >只能上传jpg/png文件</div>
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="质检报告">
                                <el-upload
                                    accept=".jpg, .png"
                                    list-type="picture-card"
                                    :action="uploadUrl()"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove"
                                    multiple
                                    :limit="1"
                                    :on-success="qualitySuccess"
                                    :on-exceed="handleExceed"
                                    :file-list="QualityList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip " >只能上传jpg/png文件</div>
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="卫生许可证">
                                <el-upload
                                    accept=".jpg, .png"
                                    list-type="picture-card"
                                    :action="uploadUrl()"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove"
                                    :on-success="sanitarySuccess"
                                    multiple
                                    :limit="1"
                                    :on-exceed="handleExceed"
                                    :file-list="permitList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip " >只能上传jpg/png文件</div>
                                </el-upload>
                            </el-form-item>
                            
                            <el-form-item>
                                <el-button type="primary" @click="subitBtn('newRuleForm')">提交</el-button>
                                <el-button type="danger" @click="back">返回</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                    
                    </div>

                </el-col>
            </el-row>
            <el-row class="tipsBoxImg">
              <el-dialog :visible.sync="dialogVisible" size="tiny" style="text-align:center;">
                <img  :src="imgUrl" style="padding-bottom: 50px;"/>
              </el-dialog>
            </el-row>
            
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
                active:0,
                page:1,
                total:1000,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
                editVisible:false,
                titleName:'',
                ztreeTitleName:'',
                ztreeEditVisible:false,
                role_id:'',
                form:{
                   id:'',
                   roleName:'',
                   roleCode:'',
                   isAdmin:'',
                   isBuiltIn:''
                },
                setting:{
                    data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "parentId"
                    }
                    },
                    callback: {
                        
                    },
                    check: {
                        enable: true,
                        chkStyle: "checkbox",
                        chkboxType: { Y: "ps", N: "ps" }
                    },
                },
                newRuleForm:{
                    sales:''
                }, 
                // 第一步注册信息
                rules: {
                    loginName: [{ required: true, message: "请输入登录名", trigger: "blur" }],
                    password: [{ required: true, message: "请输入密码", trigger: "blur" }],
                    confirmPassword: [{ required: true, message: "请输入密码", trigger: "blur" }]
                },
                rulessupplier:{
                    name: [{ required: true, message: "请输入公司名称", trigger: "blur" }],
                    shortName: [{ required: true, message: "请输入公司简称", trigger: "blur" }],
                },
                rulesIfrim:{
                    productEnterType: [{ required: true, message: "请选择虚拟产品入驻形式", trigger: "change" }]
                },
                businessList:[],
                taxRegList:[],
                taxpayeList:[],
                bankAccountList:[],
                trademarkList:[],
                brandList:[],
                QualityList:[],
                permitList:[],
                legalCardList:[],

                trademarkRegistration:'',
                brandAuthorization:'',
                qualityInspectionReport:'',
                taxRegistration:'',
                taxpayerPositive:'',
                bankAccountPermit:'',
                businessLicense:'',
                legalCard:'',
                sanitaryPermit:'',
                
                value: '',
                value2:'',
                // 下拉数据
                COMPANY_TYPE:[],
                SettledType:[],
                PRODUCT_ENTER_TYPE:[],
                CompanyType:[],
                LEGAL_CARD_TYPE:[],
                TAXPAYER_TYPE:[],
                dialogVisible:false,
                imgUrl:'',
            }
        },
        created() {
            this.getData(); 
            this.getCode(); 
        },
        methods: {
            async getCode(){
              const res = await this.$http.get(baseURL_.mallUrl+'/supplier/getSupplierCode');
              this.newRuleForm.code = res.data.data;
            },
            toFix(){
              this.newRuleForm.sales = Number(this.newRuleForm.sales).toFixed(4);
            },
            // 获取全部下拉数据
            async getData(){
                const res = await this.$http.get(baseURL_.mallUrl+'/supplier/getDictionaryData');
                this.COMPANY_TYPE = res.data.data.COMPANY_TYPE;
                this.PRODUCT_ENTER_TYPE = res.data.data.PRODUCT_ENTER_TYPE;
                this.LEGAL_CARD_TYPE = res.data.data.LEGAL_CARD_TYPE;
                this.TAXPAYER_TYPE = res.data.data.TAXPAYER_TYPE;
                this.getSettledType();
                this.getCompany();
            },
            async getSettledType(){
                const res = await this.$http.get(baseURL_.mallUrl+'/supplier/getEnterType');
                this.SettledType = res.data.data;
            },
            async getCompany(){
                const res = await this.$http.get(baseURL_.mallUrl+'/supplier/getCompany');
                this.CompanyType = res.data.data;
            },
            async subitBtn(dataform){
              if(this.value2.length > 0){
                var startTime = this.value2[0]; 
                var endTime = this.value2[1]; 
                this.newRuleForm.legalCardDateStart = startTime;
                this.newRuleForm.legalCardDateEnd = endTime;
              }
              this.newRuleForm.trademarkRegistration = this.trademarkRegistration;
              this.newRuleForm.brandAuthorization = this.brandAuthorization;
              this.newRuleForm.qualityInspectionReport = this.qualityInspectionReport;
              this.newRuleForm.sanitaryPermit = this.sanitaryPermit;
              this.newRuleForm.status = 0;
              this.$refs[dataform].validate(async valid => {
                this.$refs['supplierForm'].validate(async valid2 => {
                  if(valid2){
                    return false;
                  }
                })
                
                this.$refs['supplierIfrim'].validate(async valid3 => {
                  if(valid3){
                    return false;
                  }
                })
                if (valid) {
                    const role = await this.$http.post(baseURL_.mallUrl+'/supplier/save',this.$qs.stringify(this.newRuleForm));
                    if(role.data.statusCode==200){
                      this.$message({
                        type: "success",
                        message: role.data.data || "保存成功"
                      });
                      this.$router.push({ path: "/supplierList" });
                    }else{
                      this.$message({
                        type: "error",
                        message: role.data.data || "保存失败"
                      });
                    }
                }
              })
            },
            back() {
                this.$router.push({ path: "/supplierList" });
            },
            
            // Logo上传
            uploadUrl() {
              return baseURL_.fileUrl+'/file/upload';
            },
            handletradeSuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.trademarkRegistration = file.response.data.path;
            },
            businessSuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.newRuleForm.businessLicense = file.response.data.path;
            },
            legalCardSuccess(res, file, fileList) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.newRuleForm.legalCardZ = fileList[0].response.data.path;
              if(fileList&&fileList.length > 1){
                this.newRuleForm.legalCardF = fileList[1].response.data.path;
              }
            },
            
            handleBrandSuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              
              this.brandAuthorization = file.response.data.path;
            },
            qualitySuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.qualityInspectionReport = file.response.data.path;
            },
            sanitarySuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.sanitaryPermit = file.response.data.path;
            },
            
            taxRegSuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.newRuleForm.taxRegistration = file.response.data.path;
            },
            taxpayerSuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.newRuleForm.taxpayerPositive = file.response.data.path;
            },
            bankAccountSuccess(res, file) {
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.newRuleForm.bankAccountPermit = file.response.data.path;
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
              console.log(file);
              if(file.url != null){
                this.dialogVisible = true;
                this.imgUrl = file.url;
              }else{
                alert("图片路径有问题")
              };
              
              // this.dialogVisible = true;
            },
            //限制文件的上传数量
            handleExceed(files) {
              this.$message.warning(
                `当前已经上传了${files.length} 个文件，如需上传新的文件，请先删除之前的文件`
              );
            },
            //限制文件的上传数量
            handleExceed2(files) {
              this.$message.warning(
                `当前已经上传了2个文件，如需上传新的文件，请先删除之前的文件`
              );
            },

            handleRemove(file, fileList) {}
            
        }
    }

</script>

<style scoped>
    .w50{
        width:350px;
    }
    .counnet{
      margin-top: 20px;
      padding: 10px 20px;
      /* box-shadow: -2px -2px 4px 0 #E2E2E2, 2px 2px 4px 0 #E2E2E2; */
    }
    .supplierTop{
        height: 40px;
        line-height: 40px;
        margin-top:20px;
        font-size: 16px;
        border-bottom: 1px solid #ccc;
    }
    .stepList{
      margin: 30px 0;
    }
    .tipSize{
      width: 100%;
      text-align: left;
      font-size: 18px;
      margin-top: 40px;
      margin-left: 8%;
      margin-bottom: 10px;
      color:#333;
    }
    
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
    .complete{
      width:100%;
      text-align: center;
    }
    .complete img{
      margin: 20px 0;
    }
    .complete .waitFor{
      font-size: 22px;
      color:#666666;
    }
    .complete .waitForTime{
      font-size: 14px;
      color:#666666;
      margin: 10px 0 30px;
    }
</style>
<style >
  .tipsBoxImg .el-dialog__header{
      padding: 20px 20px 30px !important;
  }
</style>