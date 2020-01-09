<template>
    <div class="table">
        <div class="">
            <el-row style="background:#fff;">
                <el-col :span="24">
                    <div class="counnet">
                      <div class="supplierTop">入驻供应商信息</div>
                    
                        <el-form
                            :model="loginForm"
                            ref="loginForm"
                            label-width="140px"
                            inline-message
                            class="demo-ruleForm"
                            style="margin-top:20px;"
                        >
                            <el-form-item label="用户名" prop="loginName">
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

                            
                        </el-form>
                        <el-form
                            :model="newRuleForm"
                            ref="newRuleForm"
                            label-width="140px"
                            inline-message
                            class="demo-ruleForm"
                            style="margin-top:20px;"
                        >
                            <el-form-item label="对接招商人员" >
                                <el-input v-model="newRuleForm.investmentPerson" readonly size="mini" class="w50"></el-input>
                            </el-form-item>
                            
                            
                            <el-form-item label="公司类型" prop="type">
                              <el-select v-model="newRuleForm.type" disabled readonly placeholder="请选择">
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
                              <el-select v-model="newRuleForm.enterType" disabled readonly placeholder="请选择">
                                <el-option
                                  v-for="item in ENTER_TYPE"
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
                                <el-radio v-model="newRuleForm.isSupportApi" readonly :label="1">支持</el-radio>
                                <el-radio v-model="newRuleForm.isSupportApi" readonly :label="0">不支持</el-radio>
                            </el-form-item>

                            <el-form-item label="虚拟产品入驻形式">
                              <el-select v-model="newRuleForm.productEnterType" disabled readonly placeholder="请选择">
                                <el-option
                                  v-for="item in PRODUCT_ENTER_TYPE"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id">
                                </el-option>
                              </el-select>
                            </el-form-item>

                            <el-form-item label="签约公司">
                              <el-select v-model="newRuleForm.signCompany" disabled readonly placeholder="请选择">
                                <el-option
                                  v-for="item in SIGN_COMPANY"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id">
                                </el-option>
                              </el-select>
                            </el-form-item>

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
                                    :file-list="businessList">
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
                                <el-select v-model="newRuleForm.legalCardType" disabled readonly placeholder="请选择">
                                  <el-option
                                    v-for="item in LEGAL_CARD_TYPE"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                                  </el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item label="法人代表身份证件">
                                <el-upload
                                    list-type="picture-card"
                                    action=''
                                    disabled
                                    :file-list="legalCardList">
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="法人身份证有效期">
                                <el-date-picker
                                  size="mini"        
                                  disabled      
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
                                <el-select v-model="newRuleForm.taxpayerType" disabled readonly placeholder="请选择">
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
                                    :file-list="taxRegList">
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="一般纳税人正面">
                                <el-upload
                                    list-type="picture-card"
                                    action=''
                                    disabled
                                    :file-list="taxpayeList">
                                </el-upload>
                            </el-form-item>
                            <el-form-item label="商标注册证">
                                <el-upload
                                    list-type="picture-card"
                                    action=''
                                    disabled
                                    :file-list="trademarkList">
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="品牌授权书">
                                <el-upload
                                    list-type="picture-card"
                                    action=''
                                    disabled
                                    :file-list="brandList">
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="质检报告">
                                <el-upload
                                    list-type="picture-card"
                                    action=''
                                    disabled
                                    :file-list="QualityList">
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="卫生许可证">
                                <el-upload
                                    disabled
                                    action=''
                                    list-type="picture-card"
                                    :file-list="permitList">
                                </el-upload>
                            </el-form-item>

                            <el-form-item label="银行开户许可证">
                                <el-upload
                                    list-type="picture-card"
                                    action=''
                                    disabled
                                    :file-list="bankAccountList">
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

                    </div>

                </el-col>
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
                    sanitaryPermit:''
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
                ENTER_TYPE:[],
                PRODUCT_ENTER_TYPE:[],
                SIGN_COMPANY:[],
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
                legalCardList:[]
            }
        },
        created() {
            this.getData();
            this.getSupplierData();
        },
        methods: {
          // 获取全部下拉数据
            async getData(){
                const res = await this.$http.get(baseURL_.mallUrl+'/supplier/getDictionaryData');
                this.COMPANY_TYPE = res.data.data.COMPANY_TYPE;
                this.ENTER_TYPE = res.data.data.ENTER_TYPE;
                this.PRODUCT_ENTER_TYPE = res.data.data.PRODUCT_ENTER_TYPE;
                this.SIGN_COMPANY = res.data.data.SIGN_COMPANY;
                this.LEGAL_CARD_TYPE = res.data.data.LEGAL_CARD_TYPE;
                this.TAXPAYER_TYPE = res.data.data.TAXPAYER_TYPE;
            },
          // 获取供应商入驻信息回显
            async getSupplierData(){
                const res = await this.$http.get(baseURL_.mallUrl+'/supplier_audit/view',{
                  params:{
                    id:this.$route.query.id
                  }
                });
                
                for(var i in this.loginForm){
                  this.loginForm[i] = res.data.data.user[i];
                }
                
                for(var i in this.newRuleForm){
                  this.newRuleForm[i] = res.data.data[i];
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
                this.$router.push({ path: "/login" });
            },
            
           
        }
    }

</script>

<style scoped>
    @import url("//unpkg.com/element-ui@2.13.0/lib/theme-chalk/index.css");
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
</style>