<template>
    <div class="table">
        <div class="">
            <el-row>
                <!-- <el-col :xl="6">&nbsp;</el-col> -->
                <el-col :span="12" :offset="6">
                    <div class="counnet">
                      <div class="supplierTop">供应商入驻</div>
                    <el-steps :active="active" finish-status="success" class="stepList" align-center>
                      <el-step title="商户入驻"></el-step>
                      <el-step title="完善信息"></el-step>
                      <el-step title="完善信息"></el-step>
                      <el-step title="完善信息"></el-step>
                      <el-step title="完善信息"></el-step>
                      <el-step title="等待审核"></el-step>
                    </el-steps>
                    
                    
                    <div v-if="active == 0" style="margin-left:17%">
                        <el-form
                            :model="loginForm"
                            :rules="rules"
                            ref="loginForm"
                            label-width="130px"
                            inline-message
                            class="demo-ruleForm"
                        >
                          <el-form-item label="登录名" prop="loginName">
                              <el-input v-model="loginForm.loginName" size="mini" class="w50"></el-input>
                          </el-form-item>

                          <el-form-item label="密码" prop="password">
                              <el-input type="password" v-model="loginForm.password" size="mini" class="w50"></el-input>
                          </el-form-item>

                          <el-form-item label="确认密码" prop="confirmPassword">
                              <el-input type="password" v-model="loginForm.confirmPassword" size="mini" class="w50"></el-input>
                          </el-form-item>

                          <el-form-item>
                              <el-button type="primary" @click="nextDetail('loginForm')">下一步</el-button>
                              <el-button type="danger" @click="back">取消</el-button>
                          </el-form-item>
                      </el-form>
                    </div>
                    <div v-if="active == 1" style="margin-left:17%">
                          <el-form
                              :model="firstForm"
                              :rules="rules"
                              ref="firstForm"
                              label-width="130px"
                              inline-message
                              class="demo-ruleForm"
                          >
                            
                            <el-form-item label="姓名" prop="name">
                                <el-input v-model="firstForm.name" size="mini" maxlength="11" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="手机号" prop="phone">
                                <el-input v-model="firstForm.phone" size="mini" maxlength="11" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="邮箱" prop="email">
                                <el-input v-model="firstForm.email" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="对接招商人员" >
                                <el-input v-model="firstForm.investmentPerson" size="mini" class="w50"></el-input>
                            </el-form-item>
                            
                            <el-form-item>
                                <el-button type="primary" @click="nextThreeDetail('firstForm')">下一步</el-button>
                                <!-- <el-button type="danger" @click="back">取消</el-button> -->
                            </el-form-item>
                        </el-form>
                    </div>
                    <div v-if="active == 5" class="complete">
                          <div>
                            <img src="../../../assets/img/ok.png" alt="">
                          </div>
                          <div class="waitFor">等待审核</div>
                          <div class="waitForTime">24h内完成审核，您可以登录账号，查看审核进度</div>
                    </div>
                    <div v-if="active == 4" style="margin-left:17%">
                          <el-form
                              :model="suppImgForm"
                              :rules="rules"
                              ref="suppImgForm"
                              label-width="130px"
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
                                <el-button type="primary" @click="nextFive('suppImgForm')">下一步</el-button>
                                <!-- <el-button type="danger" @click="back">取消</el-button> -->
                            </el-form-item>
                        </el-form>
                    </div>
                    <div v-if="active == 3">
                          <el-form
                            :model="supplierForm"
                            :rules="supplierRules"
                            ref="supplierForm"
                            label-width="130px"
                            inline-message
                            class="demo-ruleForm"
                            style="margin-left:17%"
                          >
                          
                            <el-form-item label="公司类型" prop="type">
                              <el-select v-model="supplierForm.type" placeholder="请选择">
                                <el-option
                                  v-for="item in COMPANY_TYPE"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id">
                                </el-option>
                              </el-select>
                            </el-form-item>

                            <el-form-item label="近一年销售额" prop="name">
                                <el-input v-model="supplierForm.sales" size="mini" class="w50"></el-input>
                            </el-form-item>
                            
                            <el-form-item label="入驻类型" prop="address">
                              <el-select v-model="supplierForm.enterType" placeholder="请选择">
                                <el-option
                                  v-for="item in SettledType"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id">
                                </el-option>
                              </el-select>
                            </el-form-item>

                            <el-form-item label="仓储情况">
                                <el-input v-model="supplierForm.storageInfo" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="物流情况">
                                <el-input v-model="supplierForm.logisticsInfo" size="mini" class="w50"></el-input>
                            </el-form-item>

                            <el-form-item label="是否支持api对接">
                                <el-radio v-model="supplierForm.isSupportApi" :label="1">支持</el-radio>
                                <el-radio v-model="supplierForm.isSupportApi" :label="0">不支持</el-radio>
                            </el-form-item>

                            <el-form-item label="虚拟产品入驻形式">
                              <el-select v-model="supplierForm.productEnterType" placeholder="请选择">
                                <el-option
                                  v-for="item in PRODUCT_ENTER_TYPE"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id">
                                </el-option>
                              </el-select>
                            </el-form-item>

                            <el-form-item label="签约公司">
                              <el-select v-model="supplierForm.signCompany" placeholder="请选择">
                                <el-option
                                  v-for="item in CompanyType"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id">
                                </el-option>
                              </el-select>
                            </el-form-item>

                            <el-form-item>
                                <el-button type="primary" @click="submitBtn('supplierForm')">下一步</el-button>
                                <!-- <el-button type="danger" @click="back">取消</el-button> -->
                            </el-form-item>
                        </el-form>
                    </div>
                    <div v-if="active == 2" style="margin-left:17%">
                        <el-form
                            :model="newRuleForm"
                            :rules="rules"
                            ref="newRuleForm"
                            label-width="130px"
                            inline-message
                            class="demo-ruleForm"
                        >
                          <el-form-item label="商户号">
                              <el-input v-model="newRuleForm.code" readonly size="mini" class="w50"></el-input>
                          </el-form-item>
                          
                          <el-form-item label="公司名称">
                              <el-input v-model="newRuleForm.name" size="mini" class="w50"></el-input>
                          </el-form-item>

                          <el-form-item label="公司简称 ">
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
                                <el-date-picker
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
                            

                          <el-form-item>
                              <el-button type="primary" @click="goFore('newRuleForm')">下一步</el-button>
                              <el-button type="danger" @click="back">返回</el-button>
                          </el-form-item>
                      </el-form>
                    </div>
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
                // 第三步  供应商入职信息
                newRuleForm:{
                    code:'',
                    name:'',
                    shortName:'',
                    businessScope:'',
                    registerAddress:'',
                    legalName:'',
                    legalCardType:'',
                    legalCardZ:'',
                    legalCardF:'',
                    contactMobile:'',
                    contactName:'',
                    taxpayerType:'',
                    taxNumber:'',
                    bankName:'',
                    bankAccount:'',
                    bankAddress:''
                }, 
                // 第一步注册信息
                loginForm:{
                  loginName:'',
                  password:'',
                  confirmPassword:''
                },
                // 第二步注册信息
                firstForm:{
                  name:'',
                  phone:'',
                  email:'',
                  investmentPerson:''
                },
                // 第四步注册信息
                supplierForm:{
                  type:'',
                  sales:'',
                  enterType:'',
                  storageInfo:'',
                  logisticsInfo:'',
                  isSupportApi:'',
                  productEnterType:'',
                  signCompany:''
                },
                supplierRules:{
                  type: [{ required: true, message: "请选择公司类型", trigger: "change" }],
                  // type: [{ required: true, message: "请输入用户名", trigger: "blur" }],
                },
                // 第五步注册信息
                suppImgForm:{
                  trademarkRegistration:'',
                  brandAuthorization:'',
                  qualityInspectionReport:'',
                  sanitaryPermit:''
                },
                rules: {
                    loginName: [{ required: true, message: "请输入用户名", trigger: "blur" }],
                    name: [{ required: true, message: "请输入供应商名称", trigger: "blur" }],
                    // loginName: [{ required: true, message: "请输入真实姓名", trigger: "blur" }],
                    phone: [
                        { required: true, message: "请输入手机号码", trigger: "blur" },
                        { min: 11, max: 11, message: "请输入正确的手机号码", trigger: "blur" }
                    ],
                    email: [
                        { required: true, message: "请输入邮箱", trigger: "blur" },
                        {
                          type: "email",
                          message: "请输入正确的邮箱地址",
                          trigger: ["blur", "change"]
                        }
                    ],
                    password: [{ required: true, message: "请输入密码", trigger: "blur" }],
                    confirmPassword: [{ required: true, message: "请输入密码", trigger: "blur" }],
                    address: [{ required: true, message: "请输入地址", trigger: "blur" }],
                    type: [{ required: true, message: "请选择类型", trigger: "change" }],
                    
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
                
                options: [{
                  value: '1',
                  label: '预付款商户'
                }, {
                  value: '2',
                  label: '卡密商户'
                }],
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
                supplierId:''
            }
        },
        created() {
            this.getData();
            this.queryActive = this.$route.query.activeId;
            if(this.queryActive != '' && this.queryActive != null){
              this.active = 1;
              this.getSupplierData();
            }                      
            window.addEventListener('beforeunload', e => {
                localStorage.setItem('newActive', this.active);
                localStorage.setItem('newUserId', this.userId);
                localStorage.setItem('newSupplierId', this.supplierId);
            });
            var activeReload = localStorage.getItem('newActive');
            if(activeReload != null && activeReload != ''){
              this.active = Number(activeReload);
            }

            var userIdReload = localStorage.getItem('newUserId');
            if(userIdReload != null && userIdReload != ''){
              this.userId = userIdReload;
            }

            var supplierIdReload = localStorage.getItem('newSupplierId');
            if(supplierIdReload != null && supplierIdReload != ''){
              this.supplierId = supplierIdReload;
            }
        },
        beforeDestroy () {
            // window.removeEventListener('beforeunload');
            window.removeEventListener('beforeunload', this.beforeunloadFn)
            
        },
        methods: {
          beforeunloadFn(){
            localStorage.removeItem('newActive');
          },
          // 获取供应商入驻信息回显
            async getSupplierData(){
                const res = await this.$http.get(baseURL_.mallUrl+'/supplier/getUserInfo');

                // this.newRuleForm.code = res.data.data.supplier.code;
                this.supplierId = res.data.data.supplier.id;
                this.userId = res.data.data.id;

                // 第2步数据回显
                for(var i in this.firstForm){
                  this.firstForm[i] = res.data.data[i];
                }
                this.firstForm.investmentPerson = res.data.data.supplier.investmentPerson;

                // 第3步数据回显
                for(var i in this.newRuleForm){
                  this.newRuleForm[i] = res.data.data.supplier[i];
                }
                this.value2 = [res.data.data.supplier.legalCardDateStart,res.data.data.supplier.legalCardDateEnd];
                // 营业执照回显
                if(res.data.data.supplier.businessLicense){
                  this.businessList = [{url:res.data.data.supplier.businessLicense}]
                }
                // 法人代表身份证件回显
                if(res.data.data.supplier.legalCardZ){
                  if(res.data.data.supplier.legalCardF){
                    this.legalCardList = [{url:res.data.data.supplier.legalCardZ},{url:res.data.data.supplier.legalCardF}]
                  }else{
                    this.legalCardList = [{url:res.data.data.supplier.legalCardZ}]
                  }
                  
                }
                // 税务登记证
                if(res.data.data.supplier.taxRegistration){
                  this.taxRegList = [{url:res.data.data.supplier.taxRegistration}]
                }
                // 一般纳税人正面
                if(res.data.data.supplier.taxpayerPositive){
                  this.taxpayeList = [{url:res.data.data.supplier.taxpayerPositive}]
                }
                // 银行开户许可证
                if(res.data.data.supplier.bankAccountPermit){
                  this.bankAccountList = [{url:res.data.data.supplier.bankAccountPermit}]
                }

                // 第4步数据回显
                for(var i in this.supplierForm){
                  this.supplierForm[i] = res.data.data.supplier[i];
                } 

                // 第5步数据回显
                // 商标注册证
                if(res.data.data.supplier.trademarkRegistration){
                  this.trademarkList = [{url:res.data.data.supplier.trademarkRegistration}]
                }
                // 品牌授权书
                if(res.data.data.supplier.brandAuthorization){
                  this.brandList = [{url:res.data.data.supplier.brandAuthorization}]
                }
                // 质检报告
                if(res.data.data.supplier.qualityInspectionReport){
                  this.QualityList = [{url:res.data.data.supplier.qualityInspectionReport}]
                }
                // 卫生许可证
                if(res.data.data.supplier.sanitaryPermit){
                  this.permitList = [{url:res.data.data.supplier.sanitaryPermit}]
                }
                

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
            async goFore(dataform){
              var startTime = this.value2[0]; 
              var endTime = this.value2[1]; 
              this.newRuleForm.legalCardDateStart = startTime;
              this.newRuleForm.legalCardDateEnd = endTime;
              this.newRuleForm.id = this.supplierId;
              
              this.$refs[dataform].validate(async valid => {
                if (valid) {
                    const role = await this.$http.post(baseURL_.mallUrl+'/supplier/register',this.$qs.stringify(this.newRuleForm));
                    if(role.data.statusCode==200){
                      this.$message({
                        type: "success",
                        message: "保存成功"
                      });
                      this.active = 3;
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
                this.$router.push({ path: "/login" });
            },
            async nextDetail(dataform){
              this.$refs[dataform].validate(async valid => {
                if (valid) {
                  if(this.newRuleForm.password  == this.newRuleForm.confirmPassword){
                    const role = await this.$http.get(baseURL_.mallUrl+'/supplier/saveStepOneTwo',{
                      params: {'loginName':this.loginForm.loginName,'password':this.loginForm.password,"confirmPassword":this.loginForm.confirmPassword}
                      
                    });
                    if(role.data.statusCode==200){
                      this.$message({
                        type: "success",
                        message: "保存成功"
                      });
                      // var newdataId = JSON.parse(role.data.data.user);
                      this.newRuleForm.code = role.data.data.supplierCode;
                      this.supplierId = role.data.data.supplierId;
                      this.userId = role.data.data.userId;
                      this.active = 1;
                    }else{
                      this.$message({
                        type: "error",
                        message: role.data.data
                      });
                    }
                  }else{
                    this.$message({
                      type: "error",
                      message: "两次密码不一致！"
                    });
                    this.newRuleForm.password = '';
                    this.newRuleForm.confirmPassword = '';
                    return false;
                  }
                  
                }
              })
            },
            async nextThreeDetail(dataform){
              this.$refs[dataform].validate(async valid => {
                if (valid) {
                  this.firstForm.id = this.userId;
                    const role = await this.$http.post(baseURL_.mallUrl+'/supplier/saveStepOneTwo',this.$qs.stringify(this.firstForm));
                    
                    if(role.data.statusCode==200){
                      this.$message({
                        type: "success",
                        message: "保存成功"
                      });
                      
                      this.active = 2;
                    }else{
                      this.$message({
                        type: "success",
                        message: role.data.data
                      });
                      return;
                    }
                }
              })
            },
            
            nextFive(dataform){
                this.$refs[dataform].validate(async valid => {
                  if (valid) {
                      this.suppImgForm.trademarkRegistration = this.trademarkRegistration;
                      this.suppImgForm.brandAuthorization = this.brandAuthorization;
                      this.suppImgForm.qualityInspectionReport = this.qualityInspectionReport;
                      this.suppImgForm.sanitaryPermit = this.sanitaryPermit;
                      this.suppImgForm.id = this.supplierId;
                      this.suppImgForm.status = 0;
                        const role = await this.$http.post(baseURL_.mallUrl+'/supplier/register',this.$qs.stringify(this.suppImgForm));
                        
                        if(role.data.statusCode==200){
                          localStorage.removeItem('newActive');
                          localStorage.removeItem('newUserId');
                          localStorage.removeItem('newSupplierId');
                          this.active = 5;
                        }else{
                          this.$message({
                            type: "success",
                            message: role.data.data
                          });
                          return;
                        }
                  }
                })
                
            },
            submitBtn(dataform){
              this.$refs[dataform].validate(async valid => {
                if (valid) {
                    this.supplierForm.id = this.supplierId;
                      const role = await this.$http.post(baseURL_.mallUrl+'/supplier/register',this.$qs.stringify(this.supplierForm));
                      
                      if(role.data.statusCode==200){
                        
                        this.active = 4;
                      }else{
                        this.$message({
                          type: "success",
                          message: role.data.data
                        });
                        return;
                      }
                }
              })
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
              console.log(fileList)
              console.log(fileList[0].response.data.path)
              this.$message({
                type: "success",
                message: "上传成功",
                duration: 6000
              });
              this.newRuleForm.legalCardZ = fileList[0].response.data.path;
              if(fileList.length > 1){
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
              this.dialogImageUrl = file.url;
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
      box-shadow: -2px -2px 4px 0 #E2E2E2, 2px 2px 4px 0 #E2E2E2;
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