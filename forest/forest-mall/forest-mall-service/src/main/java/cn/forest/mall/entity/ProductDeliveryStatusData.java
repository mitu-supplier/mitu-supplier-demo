package cn.forest.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 商品发货状态数据存储表
 */
@TableName(value = "product_delivery_status_data")
public class ProductDeliveryStatusData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "id")
    private Long id;

    /**
     * 发货状态
     */
    @TableField(value = "delivery_status")
    private Long deliveryStatus;

    /**
     * 添加时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private String createdAt;

    /**
     * 修改时间
     */
    @TableField(value = "updated_at", fill = FieldFill.UPDATE)
    private String updatedAt;

    /**
     * 删除标示 0未删除   1删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

    @TableField(value = "user_id")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Long deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
