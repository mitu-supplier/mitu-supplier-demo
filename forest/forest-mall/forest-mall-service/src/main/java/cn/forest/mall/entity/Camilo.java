package cn.forest.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("camilo")
public class Camilo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "id")
    private Long id;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 卡号
     */
    @TableField(value = "card_number")
    private String cardNumber;

    /**
     * 密码
     */
    @TableField(value = "card_password")
    private String cardPassword;

    /**
     * 过期时间
     */
    @TableField(value = "failure_time")
    private String failureTime;

    /**
     * 供货价
     */
    @TableField(value = "supply_price")
    private BigDecimal supplyPrice;

    /**
     * 操作人
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 操作人名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 删除标示
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

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
     * 使用时间
     */
    private String useTime;

    /**
     * 使用标示  0 未使用  1已使用
     */
    private Integer useFlag;

    @TableField(exist = false)
    private String productCode;

    @TableField(exist = false)
    private String catalogCode;

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    public String getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(String failureTime) {
        this.failureTime = failureTime;
    }

    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public Integer getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Integer useFlag) {
        this.useFlag = useFlag;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }
}
