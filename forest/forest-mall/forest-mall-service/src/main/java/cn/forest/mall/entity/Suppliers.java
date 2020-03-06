package cn.forest.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("suppliers")
public class Suppliers implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "id")
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 简称
     */
    @TableField(value = "short_name")
    private String shortName;

    /**
     * 商户号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 营业执照
     */
    @TableField(value = "business_license")
    private String businessLicense;

    /**
     * 营业范围
     */
    @TableField(value = "business_scope")
    private String businessScope;

    /**
     * 地址
     */
    @TableField(value = "register_address")
    private String registerAddress;

    /**
     * 法人姓名
     */
    @TableField(value = "legal_name")
    private String legalName;

    /**
     * 法人身份证件类型
     */
    @TableField(value = "legal_card_type")
    private Long legalCardType;

    /**
     * 法人代表身份证件 正面
     */
    @TableField(value = "legal_card_z")
    private String legalCardZ;

    /**
     * 法人代表身份证件 反面
     */
    @TableField(value = "legal_card_f")
    private String legalCardF;

    /**
     * 法人身份证件有效期开始时间
     */
    @TableField(value = "legal_card_date_start")
    private String legalCardDateStart;


    /**
     * 法人身份证件有效期结束时间
     */
    @TableField(value = "legal_card_date_end")
    private String legalCardDateEnd;

    /**
     * 联系人名称
     */
    @TableField(value = "contact_name")
    private String contactName;

    /**
     * 联系人电话
     */
    @TableField(value = "contact_mobile")
    private String contactMobile;


    /**
     * 纳税人类型
     */
    @TableField(value = "taxpayer_type")
    private Long taxpayerType;

    /**
     * 税号
     */
    @TableField(value = "tax_number")
    private String taxNumber;

    /**
     * 税务登记证
     */
    @TableField(value = "tax_registration")
    private String taxRegistration;

    /**
     * 一般纳税人正面
     */
    @TableField(value = "taxpayer_positive")
    private String taxpayerPositive;

    /**
     * 银行开户许可证
     */
    @TableField(value = "bank_account_permit")
    private String bankAccountPermit;


    /**
     * 开户名称
     */
    @TableField(value = "bank_name")
    private String bankName;


    /**
     * 开户账号
     */
    @TableField(value = "bank_account")
    private String bankAccount;

    /**
     * 开户行所在地
     */
    @TableField(value = "bank_address")
    private String bankAddress;

    /**
     * 公司类型
     */
    @TableField(value = "type")
    private Long type;

    /**
     * 销售额
     */
    @TableField(value = "sales")
    private BigDecimal sales;

    /**
     * 入驻类型
     */
    @TableField(value = "enter_type")
    private Long enterType;

    /**
     * 仓储信息
     */
    @TableField(value = "storage_info")
    private String storageInfo;

    /**
     * 物流信息
     */
    @TableField(value = "logistics_info")
    private String logisticsInfo;


    /**
     * 是否支持api对接
     */
    @TableField(value = "is_support_api")
    private Integer isSupportApi;


    /**
     * 产品入驻类型
     */
    @TableField(value = "product_enter_type")
    private Long productEnterType;

    /**
     * 签约公司
     */
    @TableField(value = "sign_company")
    private Long signCompany;

    /**
     * 商标注册证
     */
    @TableField(value = "trademark_registration")
    private String trademarkRegistration;
    /**
     * 品牌授权书
     */
    @TableField(value = "brand_authorization")
    private String brandAuthorization;

    /**
     * 质检报告
     */
    @TableField(value = "quality_inspection_report")
    private String qualityInspectionReport;

    /**
     * 卫生许可证
     */
    @TableField(value = "sanitary_permit")
    private String sanitaryPermit;

    /**
     * 对接招商人员
     */
    @TableField(value = "investment_person")
    private String investmentPerson;

    /**
     * 新增审核状态 -1未提交 0待审核  1审核通过 2审核不通过
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 删除标示
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

    /**
     * 商户余额预警值
     */
    @TableField(value = "alert_balance")
    private BigDecimal alertBalance;

    /**
     * 注册时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private String createdAt;

    /**
     * 修改时间
     */
    @TableField(value = "updated_at", fill = FieldFill.UPDATE)
    private String updatedAt;

    /**
     * 注册填写进度
     */
    @TableField(value = "register_step")
    private Integer registerStep;

    /**
     * 库存报警提示邮箱
     */
    @TableField(value = "alert_email")
    private String alertEmail;

    /**
     * 库存报警提示手机号
     */
    @TableField(value = "alert_mobile")
    private String alertMobile;

    /**
     * 修改审核状态  0待审核  1审核通过 2审核不通过
     */
    @TableField(value = "update_audit_status")
    private Integer updateAuditStatus;

    // =====查询字段
    @TableField(exist = false)
    private String enterTypeName;

    @TableField(exist = false)
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Long getLegalCardType() {
        return legalCardType;
    }

    public void setLegalCardType(Long legalCardType) {
        this.legalCardType = legalCardType;
    }

    public String getLegalCardDateStart() {
        return legalCardDateStart;
    }

    public void setLegalCardDateStart(String legalCardDateStart) {
        this.legalCardDateStart = legalCardDateStart;
    }

    public String getLegalCardDateEnd() {
        return legalCardDateEnd;
    }

    public void setLegalCardDateEnd(String legalCardDateEnd) {
        this.legalCardDateEnd = legalCardDateEnd;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public Long getTaxpayerType() {
        return taxpayerType;
    }

    public void setTaxpayerType(Long taxpayerType) {
        this.taxpayerType = taxpayerType;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getTaxRegistration() {
        return taxRegistration;
    }

    public void setTaxRegistration(String taxRegistration) {
        this.taxRegistration = taxRegistration;
    }

    public String getTaxpayerPositive() {
        return taxpayerPositive;
    }

    public void setTaxpayerPositive(String taxpayerPositive) {
        this.taxpayerPositive = taxpayerPositive;
    }

    public String getBankAccountPermit() {
        return bankAccountPermit;
    }

    public void setBankAccountPermit(String bankAccountPermit) {
        this.bankAccountPermit = bankAccountPermit;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }

    public Long getEnterType() {
        return enterType;
    }

    public void setEnterType(Long enterType) {
        this.enterType = enterType;
    }

    public String getStorageInfo() {
        return storageInfo;
    }

    public void setStorageInfo(String storageInfo) {
        this.storageInfo = storageInfo;
    }

    public String getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(String logisticsInfo) {
        this.logisticsInfo = logisticsInfo;
    }

    public Integer getIsSupportApi() {
        return isSupportApi;
    }

    public void setIsSupportApi(Integer isSupportApi) {
        this.isSupportApi = isSupportApi;
    }

    public Long getProductEnterType() {
        return productEnterType;
    }

    public void setProductEnterType(Long productEnterType) {
        this.productEnterType = productEnterType;
    }

    public String getTrademarkRegistration() {
        return trademarkRegistration;
    }

    public void setTrademarkRegistration(String trademarkRegistration) {
        this.trademarkRegistration = trademarkRegistration;
    }

    public String getBrandAuthorization() {
        return brandAuthorization;
    }

    public void setBrandAuthorization(String brandAuthorization) {
        this.brandAuthorization = brandAuthorization;
    }

    public String getQualityInspectionReport() {
        return qualityInspectionReport;
    }

    public void setQualityInspectionReport(String qualityInspectionReport) {
        this.qualityInspectionReport = qualityInspectionReport;
    }

    public String getSanitaryPermit() {
        return sanitaryPermit;
    }

    public void setSanitaryPermit(String sanitaryPermit) {
        this.sanitaryPermit = sanitaryPermit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getInvestmentPerson() {
        return investmentPerson;
    }

    public void setInvestmentPerson(String investmentPerson) {
        this.investmentPerson = investmentPerson;
    }

    public Long getSignCompany() {
        return signCompany;
    }

    public void setSignCompany(Long signCompany) {
        this.signCompany = signCompany;
    }

    public String getLegalCardZ() {
        return legalCardZ;
    }

    public void setLegalCardZ(String legalCardZ) {
        this.legalCardZ = legalCardZ;
    }

    public String getLegalCardF() {
        return legalCardF;
    }

    public void setLegalCardF(String legalCardF) {
        this.legalCardF = legalCardF;
    }

    public BigDecimal getAlertBalance() {
        return alertBalance;
    }

    public void setAlertBalance(BigDecimal alertBalance) {
        this.alertBalance = alertBalance;
    }

    public String getEnterTypeName() {
        return enterTypeName;
    }

    public void setEnterTypeName(String enterTypeName) {
        this.enterTypeName = enterTypeName;
    }

    public Integer getRegisterStep() {
        return registerStep;
    }

    public void setRegisterStep(Integer registerStep) {
        this.registerStep = registerStep;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAlertEmail() {
        return alertEmail;
    }

    public void setAlertEmail(String alertEmail) {
        this.alertEmail = alertEmail;
    }

    public String getAlertMobile() {
        return alertMobile;
    }

    public void setAlertMobile(String alertMobile) {
        this.alertMobile = alertMobile;
    }

    public Integer getUpdateAuditStatus() {
        return updateAuditStatus;
    }

    public void setUpdateAuditStatus(Integer updateAuditStatus) {
        this.updateAuditStatus = updateAuditStatus;
    }
}
