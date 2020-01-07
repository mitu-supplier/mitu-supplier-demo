package cn.forest.lyj.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("organization")
public class Organization {

  @TableField(value = "id")
  private Long id;

  @TableField(value = "name")
  private String name;

  @TableField(value = "code")
  private String code;
  
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  private String createTime;

  @TableField(value = "update_time", fill = FieldFill.UPDATE)
  private String updateTime;
  
  @TableField(value = "parent_id")
  private Long parentId;

  @TableField(value = "tree_depth")
  private Integer treeDepth;
  
  @TableField(value = "priority")
  private Integer priority;
  
  @TableField(value = "is_delete")
  @TableLogic
  private Integer isDelete;

  @TableField(value = "is_parent")
  private String isParent;

  @TableField(exist = false)
  private boolean hasChildren;
  
  @TableField(value = "parent_name")
  private String parentName;
  
  @TableField(value = "leader")
  private String leader;
  
  @TableField(value = "leader_phone")
  private String leaderPhone;
  
  @TableField(value = "leader_sex")
  private String leaderSex;

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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Integer getTreeDepth() {
    return treeDepth;
  }

  public void setTreeDepth(Integer treeDepth) {
    this.treeDepth = treeDepth;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Integer getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(Integer isDelete) {
    this.isDelete = isDelete;
  }

  public String getIsParent() {
    return isParent;
  }

  public void setIsParent(String isParent) {
    this.isParent = isParent;
  }

  public boolean isHasChildren() {
    return hasChildren;
  }

  public void setHasChildren(boolean hasChildren) {
    this.hasChildren = hasChildren;
  }

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

  public String getLeader() {
    return leader;
  }

  public void setLeader(String leader) {
    this.leader = leader;
  }

  public String getLeaderPhone() {
    return leaderPhone;
  }

  public void setLeaderPhone(String leaderPhone) {
    this.leaderPhone = leaderPhone;
  }

  public String getLeaderSex() {
    return leaderSex;
  }

  public void setLeaderSex(String leaderSex) {
    this.leaderSex = leaderSex;
  }
  
  
  
  
}
