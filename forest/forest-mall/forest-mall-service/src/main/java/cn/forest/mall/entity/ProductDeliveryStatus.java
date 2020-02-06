package cn.forest.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 商品发货状态关联表
 */
@TableName(value = "product_delivery_status")
public class ProductDeliveryStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "id")
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Long productId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
}
