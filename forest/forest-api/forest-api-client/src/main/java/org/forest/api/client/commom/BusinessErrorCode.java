package org.forest.api.client.commom;

public class BusinessErrorCode {

    /**
     * 成功
     */
    public static int SUCCESS = 0;

    /**
     * 系统未知异常，请稍后重试
     */
    public static int ERROR = 1;

    /**
     * 商户商品不存在
     */
    public static int PRODUCT_NOT_FOUND = 100001;

    /**
     * 商户商品无发货信息
     */
    public static int PRODUCT_NOT_DELIVERY_INFO = 100002;

    /**
     * 商品库存不足
     */
    public static int PRODUCT_STOCK_ERROR = 200001;

    /**
     * 商品库存不存在
     */
    public static int PRODUCT_STOCK_NOT_FOUND = 200002;

    /**
     * 直充商品无对应直充库存
     */
    public static int PRODUCT_QB_ERROR = 200003;

    /**
     * 商户订单已存在
     */
    public static int ORDER_ALREADY_EXIST = 300001;
}
