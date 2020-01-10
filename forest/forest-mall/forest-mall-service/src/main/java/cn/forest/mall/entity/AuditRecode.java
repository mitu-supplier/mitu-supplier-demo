package cn.forest.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("audit_recode")
public class AuditRecode implements Serializable {

    @TableField(value = "id")
    private Long id;

    /**
     * 审核时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;

    /**
     * 审核结果  1通过 2不通过
     */
    @TableField(value = "audit_result")
    private Integer auditResult;

    /**
     * 审核原因
     */
    @TableField(value = "audit_reason")
    private String auditReason;

    /**
     * 审核类型  1供应商  2商品
     */
    @TableField(value = "audit_type")
    private Integer auditType;

    /**
     * 审核业务ID
     */
    @TableField(value = "business_id")
    private Long businessId;

    /**
     * 审核人id
     */
    @TableField(value = "audit_user_id")
    private Long auditUserId;

    /**
     * 审核人名称
     */
    @TableField(value = "audit_user_name")
    private String auditUserName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(Integer auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }
}
