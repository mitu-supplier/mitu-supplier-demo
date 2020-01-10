package cn.forest.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName(value = "products")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "id")
    private Long id;

    /**
     * 所属商户
     */
    @TableField(value = "supplier_id")
    private Long supplierId;

    /**
     * 发货类型
     */
    @TableField(value = "delivery_type")
    private Long deliveryType;

    /**
     * 编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 商品分类
     */
    @TableField(value = "catalog_id")
    private Long catalogId;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Long sort;

    /**
     * 商品图片
     */
    @TableField(value = "img")
    private String img;

    /**
     * 市场价
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 供货价
     */
    @TableField(value = "supply_price")
    private BigDecimal supplyPrice;

    /**
     * 状态 1上架 2下架
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 商品详情
     */
    @TableField(value = "details")
    private String details;

    /**
     * 审核状态  0待审核  1审核通过  2审核不通过
     */
    @TableField(value = "audit_status")
    private Integer auditStatus;

    /**
     * 发货代号
     */
    @TableField(value = "delivery_type_code")
    private String deliveryTypeCode;

    @TableField(value = "inventory_alert_num")
    private Integer inventoryAlertNum;

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


    // ==========  查询返回字段

    /**
     * 品目名称
     */
    @TableField(exist = false)
    private String catalogName;

    /**
     * 发货类型
     */
    @TableField(exist = false)
    private String deliveryName;

    /**
     * 供应商名称
     */
    @TableField(exist = false)
    private String supplierName;

    /**
     * 卡密过期时间
     */
    @TableField(exist = false)
    private String failureTime;

    /**
     * 库存
     */
    @TableField(exist = false)
    private Integer stock;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Long deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getDeliveryTypeCode() {
        return deliveryTypeCode;
    }

    public void setDeliveryTypeCode(String deliveryTypeCode) {
        this.deliveryTypeCode = deliveryTypeCode;
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

    public Integer getInventoryAlertNum() {
        return inventoryAlertNum;
    }

    public void setInventoryAlertNum(Integer inventoryAlertNum) {
        this.inventoryAlertNum = inventoryAlertNum;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(String failureTime) {
        this.failureTime = failureTime;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
