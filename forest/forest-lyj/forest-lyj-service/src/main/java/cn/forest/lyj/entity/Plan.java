package cn.forest.lyj.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("plan")
public class Plan {

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
  
  @TableField(value = "year")
  private Integer year;
  
  @TableField(value = "month")
  private Integer month;
  
  @TableField(value = "plan_total")
  private BigDecimal planTotal;
  
  @TableField(value = "plan_using")
  private String planUsing;
  
//0 草稿 1 正式
  @TableField(value = "status")
  private Integer  status;

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

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public BigDecimal getPlanTotal() {
    return planTotal;
  }

  public void setPlanTotal(BigDecimal planTotal) {
    this.planTotal = planTotal;
  }

  public String getPlanUsing() {
    return planUsing;
  }

  public void setPlanUsing(String planUsing) {
    this.planUsing = planUsing;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
  
  
}
