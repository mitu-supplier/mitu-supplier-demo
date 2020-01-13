package cn.forest.lyj.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("expenditure")
public class Expenditure {

  
  @TableField(value = "id")
  private Long id;
  
  @TableField(value = "is_delete")
  @TableLogic
  private Integer isDelete;
  
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  private String createTime;

  @TableField(value = "update_time", fill = FieldFill.UPDATE)
  private String updateTime;
  
  @TableField(value = "org_id")
  private Long orgId;
  
  @TableField(value = "org_name")
  private String orgName;
  
  @TableField(value = "user_id")
  private Long userId;
  
  @TableField(value = "project_id")
  private Long projectId;
  
  @TableField(value = "project_name")
  private String projectName;
  
  @TableField(value = "expenditure_time")
  private String expenditureTime;
  
  @TableField(value = "expenditure_total")
  private BigDecimal expenditureTotal;
  
  @TableField(value = "expenditure_using")
  private String expenditureUsing;
  
  @TableField(value = "expenditure_attachment")
  private String expenditureAttachment;
  
  @TableField(value = "other_attachment")
  private String otherAttachment;
  
  @TableField(value = "expenditure_attachment_name")
  private String expenditureAttachmentName;
  
  @TableField(value = "other_attachment_name")
  private String otherAttachmentName;
  
//0 草稿 1 正式
  @TableField(value = "status")
  private Integer  status;
  
  @TableField(value = "party_b_units")
  private String  partyBUnits;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(Integer isDelete) {
    this.isDelete = isDelete;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public Long getOrgId() {
    return orgId;
  }

  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getProjectId() {
    return projectId;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getExpenditureTime() {
    return expenditureTime;
  }

  public void setExpenditureTime(String expenditureTime) {
    this.expenditureTime = expenditureTime;
  }

  public BigDecimal getExpenditureTotal() {
    return expenditureTotal;
  }

  public void setExpenditureTotal(BigDecimal expenditureTotal) {
    this.expenditureTotal = expenditureTotal;
  }

  public String getExpenditureUsing() {
    return expenditureUsing;
  }

  public void setExpenditureUsing(String expenditureUsing) {
    this.expenditureUsing = expenditureUsing;
  }

  public String getExpenditureAttachment() {
    return expenditureAttachment;
  }

  public void setExpenditureAttachment(String expenditureAttachment) {
    this.expenditureAttachment = expenditureAttachment;
  }

  public String getOtherAttachment() {
    return otherAttachment;
  }

  public void setOtherAttachment(String otherAttachment) {
    this.otherAttachment = otherAttachment;
  }

  public String getExpenditureAttachmentName() {
    return expenditureAttachmentName;
  }

  public void setExpenditureAttachmentName(String expenditureAttachmentName) {
    this.expenditureAttachmentName = expenditureAttachmentName;
  }

  public String getOtherAttachmentName() {
    return otherAttachmentName;
  }

  public void setOtherAttachmentName(String otherAttachmentName) {
    this.otherAttachmentName = otherAttachmentName;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getPartyBUnits() {
    return partyBUnits;
  }

  public void setPartyBUnits(String partyBUnits) {
    this.partyBUnits = partyBUnits;
  }
  
  
  
}
