<template>
    <div class="table">
        <div class="">
            <el-row style="background: #fff;">
                <el-col :span="14" >
                    <div class="counnet">
                    <div class="tipSize">供应商修改</div>
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
                              <el-input v-model="newRuleForm.loginName" readonly size="mini" class="w50"></el-input>
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

                            <el-form-item
                                v-for="(newemail, index) in newtrony.emailList"
                                :label="'库存报警接收邮箱' + (index+1)"
                                :key="newemail.key">
                                <el-input v-model="newemail.value" class="w50"></el-input><el-button @click.prevent="removeDomain(newemail)">删除</el-button>
                            </el-form-item>

                            <el-form-item
                                v-for="(newphone, index) in newtrony.phoneList"
                                :label="'库存报警接收手机号' + (index+1)"
                                :key="index">
                                <el-input v-model="newphone.value" maxlength="11" class="w50"></el-input><el-button @click.prevent="removePhone(newphone)">删除</el-button>
                            </el-form-item>
                            <el-form-item>
                                <el-button @click="addDomain">新增邮箱</el-button>
                                <el-button @click="addPhone">新增手机号</el-button>
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
                newRuleForm:{
                    sales:'',
                    userName:'',
                }, 
                newtrony:{
                    emailList:[{
                      'value':'',
                      'key': Date.now()
                    }],
                    phoneList:[{
                      'value':'',
                      'key': Date.now()
                    }]
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
                editId:'',
                status:''
            }
        },
        created() {
            this.getData(); 
        },
        mounted(){
          var edid = this.$route.query.id;
          this.editId = Base64.decode(edid); 
          
          this.getById();
        },
        methods: {
            addDomain() {
                this.newtrony.emailList.push({
                    value: '',
                    key: Date.now()
                });
            },
            addPhone() {
                this.newtrony.phoneList.push({
                    value: '',
                    key: Date.now()
                });
            },
            removeDomain(item) {
                var index = this.newtrony.emailList.indexOf(item)
                if (index !== -1) {
                  this.newtrony.emailList.splice(index, 1)
                }
            },
            removePhone(item) {
                var index = this.newtrony.phoneList.indexOf(item)
                if (index !== -1) {
                  this.newtrony.phoneList.splice(index, 1)
                }
            },
            async getById(){
              const res = await this.$http.get(baseURL_.mallUrl+'/supplier/getById',{
                  params:{
                    id:this.editId
                  }
              });
              this.newRuleForm = res.data.data;
              if(res.data.data.alertEmail != null && res.data.data.alertEmail != ''){
                  var alertEmail = res.data.data.alertEmail.split(',');
                  if(alertEmail.length > 1){
                    this.newtrony.emailList = [];
                    for(var j =0; j<alertEmail.length; j++){
                      this.newtrony.emailList.push({'value':alertEmail[j],'key':Date.now()})
                    } 
                  }else{
                    this.newtrony.emailList = [{'value':alertEmail[0],'key':Date.now()}];
                  }
              } 
              if(res.data.data.alertMobile != null && res.data.data.alertMobile != ''){
                  var phoneList = res.data.data.alertMobile.split(',');
                  if(phoneList.length > 1){
                    this.newtrony.phoneList = [];
                    for(var q =0; q<phoneList.length; q++){
                      this.newtrony.phoneList.push({'value':phoneList[q],'key':Date.now()})
                    } 
                  }else{
                    this.newtrony.phoneList = [{'value':phoneList[0],'key':Date.now()}];
                  }
              }
              
              this.newRuleForm.loginName = res.data.data.user.loginName;
              this.$set(this.newRuleForm,'phone',res.data.data.user.phone)
              
              if(res.data.data.status == '1'){
                this.$set(this.newRuleForm,'userName',res.data.data.userName)
                this.$set(this.newRuleForm,'phone',res.data.data.phone)
                this.$set(this.newRuleForm,'email',res.data.data.email)
              }else{
                this.$set(this.newRuleForm,'userName',res.data.data.user.name)
                this.$set(this.newRuleForm,'phone',res.data.data.user.phone)
                this.$set(this.newRuleForm,'email',res.data.data.user.email)
              }
              this.status = res.data.data.status;
                if(res.data.data.legalCardDateStart!=null){
                  this.value2 = [res.data.data.legalCardDateStart,res.data.data.legalCardDateEnd];
                }
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
              var contentType = 0;
              if(this.value2.length > 0){
                var startTime = this.value2[0]; 
                var endTime = this.value2[1]; 
                this.newRuleForm.legalCardDateStart = startTime;
                this.newRuleForm.legalCardDateEnd = endTime;
              }
              if(this.newRuleForm.phone != '' && this.newRuleForm.phone != null){
                  if(!(/^1[3456789]\d{9}$/.test(this.newRuleForm.phone))){ 
                      this.$message({
                        type: "error",
                        message: "请输入正确的手机号"
                      });
                      return false; 
                  } 
              }
              if(this.newRuleForm.email != '' && this.newRuleForm.email != null){
                  if(!(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(this.newRuleForm.email))){ 
                      this.$message({
                        type: "error",
                        message: "请输入正确的邮箱"
                      });
                      return false; 
                  } 
              }

              // 邮箱
              var emailsList = [];
              if(this.newtrony.emailList.length > 0){
                  var emails = this.newtrony.emailList;
                  for(let i=0; i<emails.length; i++){
                      if(emails[i].value == '' || emails[i].value == null){
                          // this.$message({
                          //     type: "error",
                          //     message: "请删除空标签"
                          // });
                          // return;
                      }else{
                          if(!(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(emails[i].value))){ 
                              this.$message({
                                  type: "error",
                                  message: "请输入正确的邮箱"
                              });
                              return false; 
                          }else{
                            if(emails[i].value != null){
                              emailsList.push(emails[i].value)
                            }
                          }
                      }
                  }
              }

              // 手机号
              var phoneList = [];
              if(this.newtrony.phoneList.length > 0){
                  var phones = this.newtrony.phoneList;
                  for(let i=0; i<phones.length; i++){
                      if(phones[i].value == '' || phones[i].value == null){
                          // this.$message({
                          //     type: "error",
                          //     message: "请删除空标签"
                          // });
                          // return;
                      }else{
                          if(!(/^1[3456789]\d{9}$/.test(phones[i].value))){ 
                              this.$message({
                                  type: "error",
                                  message: "请输入正确的手机号"
                              });
                              return false; 
                          }else{
                            if(phones[i].value != null){
                              phoneList.push(phones[i].value)
                            }
                          }
                      }
                  }
              }

              if(emailsList.length > 0){
                this.newRuleForm.alertEmail = emailsList.join(',');
              }
              if(phoneList.length > 0){
                this.newRuleForm.alertMobile = phoneList.join(',');
              }

              this.newRuleForm.trademarkRegistration = this.trademarkRegistration;
              this.newRuleForm.brandAuthorization = this.brandAuthorization;
              this.newRuleForm.qualityInspectionReport = this.qualityInspectionReport;
              this.newRuleForm.sanitaryPermit = this.sanitaryPermit;
              this.newRuleForm.status = 0;
              this.$refs[dataform].validate(async valid => {
                if (valid) {
                    contentType = 0;
                }else{
                    contentType = 1;
                }
              })
              this.$refs['supplierForm'].validate(async valid2 => {
                if(valid2){
                  contentType = 0;
                }else{
                  contentType = 1;
                }
              })
                
              this.$refs['supplierIfrim'].validate(async valid3 => {
                if(valid3){
                  contentType = 0;
                }else{
                  contentType = 1;
                }
              })
              if(contentType == 0){
                var updateUrl = '';
                if(this.status == 1){
                  // 审核通过
                  updateUrl = baseURL_.mallUrl+'/supplier_update/updateRegisterInfo';
                }else{
                  // 待审核   审核不通过
                  updateUrl = baseURL_.mallUrl+'/supplier/update';
                }
                const role = await this.$http.post(updateUrl, this.$qs.stringify(this.newRuleForm));
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