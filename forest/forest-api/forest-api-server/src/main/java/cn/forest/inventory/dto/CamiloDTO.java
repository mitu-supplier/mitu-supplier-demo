package cn.forest.inventory.dto;

import java.util.Date;

/**
 * 卡密信息
 */
public class CamiloDTO {

    /**
     * 卡密库存标识
     */
    private Long id;

    /**
     * 卡密
     */
    private String pin;

    /**
     * 卡号，当此商品不需要卡号时此字段为空
     */
    private String cardNo;

    /**
     * 卡密过期时间
     */
    private Date expiredDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
