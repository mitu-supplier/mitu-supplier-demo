package cn.forest.lyj.entity;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("projects")
public class Projects {

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
  
  @TableField(value = "project_name")
  private String projectName;
  
  @TableField(value = "budget")
  private BigDecimal budget;
  
  @TableField(value = "leader_phone")
  private String leaderPhone;
  
  @TableField(value = "project_leader")
  private String projectLeader;
  
  @TableField(value = "user_id")
  private Long userId;
  
  @TableField(value = "project_level")
  private Integer projectLevel;
  
  @TableField(value = "year")
  private Integer year;
  
  @TableField(value = "parent_id")
  private Long parentId;
  
  //0 草稿 1 正式
  @TableField(value = "status")
  private Integer status;
  
  
  @TableField(value = "project_source")
  private String projectSource;
  
  @TableField(exist = false)
  private BigDecimal per;
  
  @TableField(exist = false)
  private BigDecimal usedTotal;
  
  @TableField(exist = false)
  private BigDecimal laveTotal;
  
  @TableField(exist = false)
  private String xy;
  
  @TableField(exist = false)
  private String dy;
  
  @TableField(exist = false)
  private boolean hasChildren;
  
  
  @TableField(exist = false)
  private List children;
  
  @TableField(exist = false)
  private BigDecimal pt;
  
  @TableField(exist = false)
  private BigDecimal et;
  
  
  
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

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }

  public String getLeaderPhone() {
    return leaderPhone;
  }

  public void setLeaderPhone(String leaderPhone) {
    this.leaderPhone = leaderPhone;
  }

  public String getProjectLeader() {
    return projectLeader;
  }

  public void setProjectLeader(String projectLeader) {
    this.projectLeader = projectLeader;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  

  public BigDecimal getPer() {
    return per;
  }

  public void setPer(BigDecimal per) {
    this.per = per;
  }

  public BigDecimal getUsedTotal() {
    return usedTotal;
  }

  public void setUsedTotal(BigDecimal usedTotal) {
    this.usedTotal = usedTotal;
  }

  public BigDecimal getLaveTotal() {
    return laveTotal;
  }

  public void setLaveTotal(BigDecimal laveTotal) {
    this.laveTotal = laveTotal;
  }

  public String getXy() {
    return xy;
  }

  public void setXy(String xy) {
    this.xy = xy;
  }

  public String getDy() {
    return dy;
  }

  public void setDy(String dy) {
    this.dy = dy;
  }

  public Integer getProjectLevel() {
    return projectLevel;
  }

  public void setProjectLevel(Integer projectLevel) {
    this.projectLevel = projectLevel;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getProjectSource() {
    return projectSource;
  }

  public void setProjectSource(String projectSource) {
    this.projectSource = projectSource;
  }

  public boolean isHasChildren() {
    return hasChildren;
  }

  public void setHasChildren(boolean hasChildren) {
    this.hasChildren = hasChildren;
  }

  public List getChildren() {
    return children;
  }

  public void setChildren(List children) {
    this.children = children;
  }

  public BigDecimal getPt() {
    return pt;
  }

  public void setPt(BigDecimal pt) {
    this.pt = pt;
  }

  public BigDecimal getEt() {
    return et;
  }

  public void setEt(BigDecimal et) {
    this.et = et;
  }

  
  
}
