<template>
    <div class="table">
        <div class="">
          <el-row style="background:#fff; padding: 20px;">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="联系人信息" name="first">
                <el-form
                      :model="loginForm"
                      ref="loginForm"
                      label-width="130px"
                      inline-message
                      class="demo-ruleForm"
                  >
                    <el-form-item label="登录名" prop="loginName">
                        <el-input v-model="loginForm.loginName" readonly size="mini" class="w50"></el-input>
                    </el-form-item>
                
                      <el-form-item label="姓名" prop="name">
                          <el-input v-model="loginForm.name" readonly size="mini" maxlength="11" class="w50"></el-input>
                      </el-form-item>

                      <el-form-item label="手机号" prop="phone">
                          <el-input v-model="loginForm.phone" readonly size="mini" maxlength="11" class="w50"></el-input>
                      </el-form-item>

                      <el-form-item label="邮箱" prop="email">
                          <el-input v-model="loginForm.email" readonly size="mini" class="w50"></el-input>
                      </el-form-item>

                      <el-form-item label="对接招商人员" >
                          <el-input v-model="newRuleForm.investmentPerson" readonly size="mini" class="w50"></el-input>
                      </el-form-item>
                      
                  </el-form>
              </el-tab-pane>
              <el-tab-pane label="公司主体信息" name="third">
                <el-form
                    :model="newRuleForm"
                    ref="newRuleForm"
                    label-width="130px"
                    inline-message
                    class="demo-ruleForm"
                >
                  <el-form-item label="商户号">
                      <el-input v-model="newRuleForm.code" readonly size="mini" class="w50"></el-input>
                  </el-form-item>
                  
                  <el-form-item label="公司名称">
                      <el-input v-model="newRuleForm.name" readonly size="mini" class="w50"></el-input>
                  </el-form-item>

                  <el-form-item label="公司简称 ">
                      <el-input v-model="newRuleForm.shortName" readonly size="mini" class="w50"></el-input>
                  </el-form-item>

                    <el-form-item label="营业执照">
                        <el-upload
                            list-type="picture-card"
                            action=''
                            disabled
                            :file-list="businessList"
                            :on-preview="handlePictureCardPreview"
                            class="upload_img_view">
                        </el-upload>
                    </el-form-item>

                    <el-form-item label="营业范围">
                      <el-input v-model="newRuleForm.businessScope" readonly size="mini" class="w50"></el-input>
                    </el-form-item>
                    
                    <el-form-item label="公司注册地址">
                        <el-input v-model="newRuleForm.registerAddress" readonly size="mini" class="w50"></el-input>
                    </el-form-item>

                    <el-form-item label="法人姓名">
                        <el-input v-model="newRuleForm.legalName" readonly size="mini" class="w50"></el-input>
                    </el-form-item>

                    <el-form-item label="法人代表证件类型">
                        <el-select v-model="newRuleForm.legalCardType" disabled placeholder="">
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
                          list-type="picture-card"
                          action=''
                          disabled
                          :file-list="legalCardList"
                          :on-preview="handlePictureCardPreview"
                          class="upload_img_view">
                      </el-upload>
                    </el-form-item>

                    <el-form-item label="法人身份证有效期">
                        <el-date-picker
                          disabled
                          size="mini"              
                          v-model="value2"
                          type="datetimerange"
                          align="right"
                          format="yyyy-MM-dd"
                          value-format="yyyy-MM-dd"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期"
                          class="w50">
                        </el-date-picker>
                    </el-form-item>

                    <el-form-item label="公司电话">
                        <el-input v-model="newRuleForm.contactMobile" readonly size="mini" class="w50"></el-input>
                    </el-form-item>

                    <el-form-item label="公司联系人">
                        <el-input v-model="newRuleForm.contactName" readonly size="mini" class="w50"></el-input>
                    </el-form-item>

                    <el-form-item label="纳税人类型">
                        <el-select v-model="newRuleForm.taxpayerType" disabled placeholder="">
                          <el-option
                            v-for="item in TAXPAYER_TYPE"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                          </el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="税号">
                        <el-input v-model="newRuleForm.taxNumber" readonly size="mini" class="w50"></el-input>
                    </el-form-item>

                    <el-form-item label="税务登记证">
                        <el-upload
                            list-type="picture-card"
                            action=''
                            disabled
                            :file-list="taxRegList"
                            :on-preview="handlePictureCardPreview"
                            class="upload_img_view">
                        </el-upload>
                    </el-form-item>

                    <el-form-item label="一般纳税人正面">
                        <el-upload
                            list-type="picture-card"
                            action=''
                            disabled
                            :file-list="taxpayeList"
                            :on-preview="handlePictureCardPreview"
                          class="upload_img_view">
                        </el-upload>
                    </el-form-item>

                    <el-form-item label="银行开户许可证">
                        <el-upload
                            list-type="picture-card"
                            action=''
                            disabled
                            :file-list="bankAccountList"
                            :on-preview="handlePictureCardPreview"
                            class="upload_img_view">
                        </el-upload>
                    </el-form-item>

                    <el-form-item label="开户名称">
                        <el-input v-model="newRuleForm.bankName" readonly size="mini" class="w50"></el-input>
                    </el-form-item>

                    <el-form-item label="开户账号">
                        <el-input v-model="newRuleForm.bankAccount" readonly size="mini" class="w50"></el-input>
                    </el-form-item>

                    <el-form-item label="开户银行所在地">
                        <el-input v-model="newRuleForm.bankAddress" readonly size="mini" class="w50"></el-input>
                    </el-form-item>
                </el-form>
              </el-tab-pane>
              <el-tab-pane label="公司经营信息" name="fourth">
                <el-form
                    ref="newRuleForm"
                    label-width="130px"
                    inline-message
                    class="demo-ruleForm"
                  >
                  
                    <el-form-item label="公司类型" prop="type">
                      <el-select v-model="newRuleForm.type" disabled placeholder="">
                        <el-option
                          v-for="item in COMPANY_TYPE"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                        </el-option>
                      </el-select>
                    </el-form-item>

                    <el-form-item label="近一年销售额" prop="name">
                        <el-input v-model="newRuleForm.sales" readonly size="mini" class="w50"></el-input>
                    </el-form-item>
                    
                    <el-form-item label="入驻类型" prop="address">
                      <el-select v-model="newRuleForm.enterType" disabled placeholder="">
                        <el-option
                          v-for="item in SettledType"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                        </el-option>
                      </el-select>
                    </el-form-item>

                    <el-form-item label="仓储情况">
                        <el-input v-model="newRuleForm.storageInfo" readonly size="mini" class="w50"></el-input>
                    </el-form-item>

                    <el-form-item label="物流情况">
                        <el-input v-model="newRuleForm.logisticsInfo" readonly size="mini" class="w50"></el-input>
                    </el-form-item>

                    <el-form-item label="是否支持api对接">
                        <el-radio v-model="newRuleForm.isSupportApi" disabled readonly :label="1">支持</el-radio>
                        <el-radio v-model="newRuleForm.isSupportApi" disabled readonly :label="0">不支持</el-radio>
                    </el-form-item>

                    <el-form-item label="虚拟产品入驻形式">
                      <el-select v-model="newRuleForm.productEnterType" disabled placeholder="">
                        <el-option
                          v-for="item in PRODUCT_ENTER_TYPE"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                        </el-option>
                      </el-select>
                    </el-form-item>

                    <el-form-item label="签约公司">
                      <el-select v-model="newRuleForm.signCompany" disabled placeholder="">
                        <el-option
                          v-for="item in CompanyType"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                        </el-option>
                      </el-select>
                    </el-form-item>
                </el-form>
              </el-tab-pane>
              <el-tab-pane label="品牌资质" name="fiveth">
                <el-form
                    ref="newRuleForm"
                    label-width="130px"
                    inline-message
                    class="demo-ruleForm"
                >
                  <el-form-item label="商标注册证">
                      <el-upload
                          list-type="picture-card"
                          action=''
                          disabled
                          :file-list="trademarkList"
                          :on-preview="handlePictureCardPreview"
                          class="upload_img_view">
                      </el-upload>
                  </el-form-item>

                  <el-form-item label="品牌授权书">
                      <el-upload
                          list-type="picture-card"
                          action=''
                          disabled
                          :file-list="brandList"
                          :on-preview="handlePictureCardPreview"
                          class="upload_img_view">
                      </el-upload>
                  </el-form-item>

                  <el-form-item label="质检报告">
                      <el-upload
                          list-type="picture-card"
                          action=''
                          disabled
                          :file-list="QualityList"
                          :on-preview="handlePictureCardPreview"
                          class="upload_img_view">
                      </el-upload>
                  </el-form-item>

                  <el-form-item label="卫生许可证">
                      <el-upload
                          disabled
                          action=''
                          list-type="picture-card"
                          :file-list="permitList"
                          :on-preview="handlePictureCardPreview"
                          class="upload_img_view">
                      </el-upload>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
            </el-tabs>
            <div style="margin-left:130px;">
              <el-button v-if="activeShow == '0'" type="primary" @click="auditAdopt">审核通过</el-button>
              <el-button v-if="activeShow == '0'" type="danger" @click="auditReject">审核失败</el-button>
              <el-button type="danger" @click="back">返回</el-button>
            </div>
          </el-row>
        </div>
        <el-row class="tipsBoxImg">
          <el-dialog :visible.sync="dialogVisible" size="tiny" style="text-align:center;">
            <img  :src="dialogImageUrl" style="padding-bottom: 50px;"/>
          </el-dialog>
        </el-row>
    </div>
</template>
  
<script>
    // import { fetchData } from '../../../api/index';
    import baseURL_ from '@/utils/baseUrl.js';
    
    export default {
        // name: 'basetable',
        data() {
            return {
                activeName: 'first',

                activeShow: this.$route.params.state,
                action:1,
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
                supplier_id:'',
                form:{
                   id:'',
                   roleName:'',
                   roleCode:'',
                   isAdmin:'',
                   isBuiltIn:''
                },
                loginForm:{
                  loginName:'',
                  name:'',
                  phone:'',
                  email:'',
                },
                
                // 第三步  供应商入职信息
                newRuleForm:{
                    code:'',
                    name:'',
                    shortName:'',
                    businessScope:'',
                    registerAddress:'',
                    legalName:'',
                    legalCardType:'',
                    contactMobile:'',
                    contactName:'',
                    taxpayerType:'',
                    taxNumber:'',
                    bankName:'',
                    bankAccount:'',
                    bankAddress:'',
                    password:'',
                    confirmPassword:'',
                    name:'',
                    investmentPerson:'',
                    type:'',
                    sales:'',
                    enterType:'',
                    storageInfo:'',
                    logisticsInfo:'',
                    isSupportApi:'',
                    productEnterType:'',
                    signCompany:'',
                    trademarkRegistration:'',
                    brandAuthorization:'',
                    qualityInspectionReport:'',
                    sanitaryPermit:'',
                    status:''
                }, 
                
               
                
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
                userId:'',
                supplierId:'',
                taxRegList:[],
                businessList:[],
                taxpayeList:[],
                bankAccountList:[],
                trademarkList:[],
                brandList:[],
                QualityList:[],
                permitList:[],
                legalCardList:[],
                dialogVisible:false,
                dialogImageUrl:''
            }
        },
        created() {
            this.getData();
            this.getSupplierData();
        },
        methods: {
            handleClick(tab, event) {

            },
          // 获取全部下拉数据
            async getData(){
                const res = await this.$http.get(baseURL_.mallUrl+'/supplier/getDictionaryData');
                this.COMPANY_TYPE = res.data.data.COMPANY_TYPE;
                // this.ENTER_TYPE = res.data.data.ENTER_TYPE;
                this.PRODUCT_ENTER_TYPE = res.data.data.PRODUCT_ENTER_TYPE;
                // this.SIGN_COMPANY = res.data.data.SIGN_COMPANY;
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
            
          // 获取供应商入驻信息回显
            async getSupplierData(){
                const res = await this.$http.get(baseURL_.mallUrl+'/supplier_audit/view',{
                  params:{
                    id:this.$route.params.id
                  }
                });
                this.supplier_id = res.data.data.id;
                for(var i in this.loginForm){
                  this.loginForm[i] = res.data.data.user[i];
                }
                
                for(var k in this.newRuleForm){
                  this.newRuleForm[k] = res.data.data[k];
                }
                this.newRuleForm.investmentPerson = res.data.data.investmentPerson;

                
                this.value2 = [res.data.data.legalCardDateStart,res.data.data.legalCardDateEnd];
                // 营业执照回显
                if(res.data.data.businessLicense){
                  this.businessList = [{url:res.data.data.businessLicense}]
                }
                // this.businessLicense = res.data.data.businessLicense;
                // // 法人代表身份证件回显
                if(res.data.data.legalCardZ){
                  if(res.data.data.legalCardF){
                    this.legalCardList = [{url:res.data.data.legalCardZ},{url:res.data.data.legalCardF}];
                  }else{
                    this.legalCardList = [{url:res.data.data.legalCardZ}];
                  }
                }
                // // 税务登记证
                if(res.data.data.taxRegistration){
                  this.taxRegList = [{url:res.data.data.taxRegistration}]
                }
                // this.taxRegistration = res.data.data.taxRegistration;
                // 一般纳税人正面
                if(res.data.data.taxpayerPositive){
                  this.taxpayeList = [{url:res.data.data.taxpayerPositive}]
                }
                // this.taxpayerPositive = res.data.data.taxpayerPositive;
                // 银行开户许可证
                if(res.data.data.bankAccountPermit){
                  this.bankAccountList = [{url:res.data.data.bankAccountPermit}]
                }

                

                // 第5步数据回显
                // 商标注册证
                if(res.data.data.trademarkRegistration){
                  this.trademarkList = [{url:res.data.data.trademarkRegistration}]
                }
                // // 品牌授权书
                if(res.data.data.brandAuthorization){
                  this.brandList = [{url:res.data.data.brandAuthorization}]
                }
                // // 质检报告
                if(res.data.data.qualityInspectionReport){
                  this.QualityList = [{url:res.data.data.qualityInspectionReport}]
                }
                // // 卫生许可证
                if(res.data.data.sanitaryPermit){
                  this.permitList = [{url:res.data.data.sanitaryPermit}]
                }
                

            },
            back() {
                this.$router.push({ path: "/supplierAuditList" });
            },
            
            
            auditAdopt(){
                var id = this.$route.params.id;
                this.$confirm('确认审核通过').then( e=> {
                    this.confimAudit(id,1);
                }).catch(_ => {});
            },
            auditReject(){
                var id = this.$route.params.id;
                this.$prompt('请输入审核失败理由：', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputPattern: /^[\s\S]*.*[^\s][\s\S]*$/,
                    inputErrorMessage: '理由不能为空'
                }).then(({ value }) => {
                    this.confimAudit(id,2,value)
                }).catch(() => { }); 
            },
            async confimAudit(id,auditResult,auditReason){
                var auditResult = await this.$http.get(baseURL_.mallUrl+'/supplier_audit/audit', {
                    params:{
                        businessId:id,
                        auditResult:auditResult,
                        auditReason:auditReason
                    }
                });
                this.$message(auditResult.data.data);
                if(auditResult.data.statusCode==200){
                    this.$router.push({ path: "/supplierAuditList" });
                }
            },
            handlePictureCardPreview(file) {
              this.dialogImageUrl = file.url;
              this.dialogVisible = true;
            }
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
  .stepList .el-step__title{
      font-size: 12px !important;
    }
  .stepList .el-step__icon{
    width:20px !important;
    height:20px !important;
  }
  .tipsBoxImg .el-dialog__header{
      padding: 20px 20px 30px !important;
  }
</style>