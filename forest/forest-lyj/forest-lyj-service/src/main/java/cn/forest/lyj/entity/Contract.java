package cn.forest.lyj.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("contract")
public class Contract {

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
  
  @TableField(value = "contract_name")
  private String contractName;
  
  @TableField(value = "contract_total")
  private BigDecimal contractTotal;
  
  @TableField(value = "contract_time")
  private String contractTime;
  
  @TableField(value = "contract_leader")
  private String contractLeader;
  
  @TableField(value = "contract_leader_phone")
  private String contractLeaderPhone;
  
  @TableField(value = "contract_attachment")
  private String contractAttachment;
  
  @TableField(value = "other_attachment")
  private String otherAttachment;

  @TableField(value = "contract_attachment_name")
  private String contractAttachmentName;
  
  @TableField(value = "other_attachment_name")
  private String otherAttachmentName;
  
  //0 草稿 1 正式
  @TableField(value = "status")
  private Integer  status;
  
  
  //0项目合同,1其他资料
  @TableField(value = "contract_type")
  private Integer  contractType;
  
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

  public String getContractName() {
    return contractName;
  }

  public void setContractName(String contractName) {
    this.contractName = contractName;
  }

  public BigDecimal getContractTotal() {
    return contractTotal;
  }

  public void setContractTotal(BigDecimal contractTotal) {
    this.contractTotal = contractTotal;
  }

  public String getContractTime() {
    return contractTime;
  }

  public void setContractTime(String contractTime) {
    this.contractTime = contractTime;
  }

  public String getContractLeader() {
    return contractLeader;
  }

  public void setContractLeader(String contractLeader) {
    this.contractLeader = contractLeader;
  }

  public String getContractLeaderPhone() {
    return contractLeaderPhone;
  }

  public void setContractLeaderPhone(String contractLeaderPhone) {
    this.contractLeaderPhone = contractLeaderPhone;
  }

  public String getContractAttachment() {
    return contractAttachment;
  }

  public void setContractAttachment(String contractAttachment) {
    this.contractAttachment = contractAttachment;
  }

  public String getOtherAttachment() {
    return otherAttachment;
  }

  public void setOtherAttachment(String otherAttachment) {
    this.otherAttachment = otherAttachment;
  }

  public String getContractAttachmentName() {
    return contractAttachmentName;
  }

  public void setContractAttachmentName(String contractAttachmentName) {
    this.contractAttachmentName = contractAttachmentName;
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

  public Integer getContractType() {
    return contractType;
  }

  public void setContractType(Integer contractType) {
    this.contractType = contractType;
  }
  
  
}
