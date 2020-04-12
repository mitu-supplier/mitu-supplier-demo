package org.forest.api.client.product.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商户商品信息
 */
public class ProductDTO {

    /**
     * 商户产品标记识
     */
    private Integer id;

    /**
     * 商户标识
     */
    private Integer merchantId;

    /**
     * 商户别名
     */
    private String merchantAlias;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品编号
     */
    private String merchantProductNo;

    /**
     * 产品面额（市场价）
     */
    private Integer productValue;

    /**
     * 售价（供货价）
     */
    private Integer salePrice;

    /**
     * 库存
     */
    private Integer stockCount;

    /**
     * 库存阀值
     */
    private Integer stockThreshold;

    /**
     * 发货类型标示
     */
    private Integer shipCategory;

    /**
     * 卡券类型：CAMILO("卡密"),DIRECT_CHARGE("直充")
     */
    private String cardCouponType;

    /**
     * 商品状态：正常、服务不可用、库存不足等
     */
    private String status;

    /**
     * 商品状态非正常：服务不可用、库存不足等提示信息
     */
    private String prompt;

    /**
     * 商品图片列表
     */
    private List<String> images;

    /**
     * 商品详情
     */
    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantAlias() {
        return merchantAlias;
    }

    public void setMerchantAlias(String merchantAlias) {
        this.merchantAlias = merchantAlias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchantProductNo() {
        return merchantProductNo;
    }

    public void setMerchantProductNo(String merchantProductNo) {
        this.merchantProductNo = merchantProductNo;
    }

    public Integer getProductValue() {
        return productValue;
    }

    public void setProductValue(Integer productValue) {
        this.productValue = productValue;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getStockThreshold() {
        return stockThreshold;
    }

    public void setStockThreshold(Integer stockThreshold) {
        this.stockThreshold = stockThreshold;
    }

    public Integer getShipCategory() {
        return shipCategory;
    }

    public void setShipCategory(Integer shipCategory) {
        this.shipCategory = shipCategory;
    }

    public String getCardCouponType() {
        return cardCouponType;
    }

    public void setCardCouponType(String cardCouponType) {
        this.cardCouponType = cardCouponType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
