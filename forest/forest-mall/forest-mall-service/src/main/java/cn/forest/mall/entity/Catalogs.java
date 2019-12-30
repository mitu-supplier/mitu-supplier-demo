package cn.forest.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("catalogs")
public class Catalogs {

    @TableField(value = "id")
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private String createdAt;

    /**
     * 修改时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT)
    private String updatedAt;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 父级ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 层级数
     */
    @TableField(value = "tree_depth")
    private Integer treeDepth;

    /**
     * 是否可用  1启用 0禁止
     */
    @TableField(value = "usable")
    private Integer usable;

    /**
     * 树级ID
     */
    @TableField(value = "tree_ids")
    private String treeIds;

    /**
     * 树级名称
     */
    @TableField(value = "tree_names")
    private String treeNames;

    /**
     * 图片
     */
    @TableField(value = "pic")
    private String pic;

    /**
     * 优先级排序
     */
    @TableField(value = "priority")
    private Float priority;

    /**
     * 是否为父节点
     */
    @TableField(value = "is_parent")
    private String isParent;

    @TableField(exist = false)
    private Catalogs parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getUsable() {
        return usable;
    }

    public void setUsable(Integer usable) {
        this.usable = usable;
    }

    public String getTreeIds() {
        return treeIds;
    }

    public void setTreeIds(String treeIds) {
        this.treeIds = treeIds;
    }

    public String getTreeNames() {
        return treeNames;
    }

    public void setTreeNames(String treeNames) {
        this.treeNames = treeNames;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Float getPriority() {
        return priority;
    }

    public void setPriority(Float priority) {
        this.priority = priority;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public Catalogs getParent() {
        return parent;
    }

    public void setParent(Catalogs parent) {
        this.parent = parent;
    }
}
