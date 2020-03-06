package cn.forest.product.dto;

import java.util.List;

/**
 * 发货类型
 */
public class DeliveryTypeDTO {

    /**
     * 发货类型标识
     */
    private Integer id;

    /**
     * 发货类型名称
     */
    private String name;

    /**
     * 发货类型定义
     */
    private List<MemberShipInfoParam> config;

    /**
     * 发货成功短信模板标识，可为空
     */
    private Integer successMSGId;

    /**
     * 发货失败短信模板标识，可为空
     */
    private Integer failseMSGId;

    /**
     * 发货失败短信模板2标识，可为空
     */
    private Integer failseMSG2Id;

    /**
     * 发货失败短信模板3标识，可为空
     */
    private Integer failseMSG3Id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MemberShipInfoParam> getConfig() {
        return config;
    }

    public void setConfig(List<MemberShipInfoParam> config) {
        this.config = config;
    }

    public Integer getSuccessMSGId() {
        return successMSGId;
    }

    public void setSuccessMSGId(Integer successMSGId) {
        this.successMSGId = successMSGId;
    }

    public Integer getFailseMSGId() {
        return failseMSGId;
    }

    public void setFailseMSGId(Integer failseMSGId) {
        this.failseMSGId = failseMSGId;
    }

    public Integer getFailseMSG2Id() {
        return failseMSG2Id;
    }

    public void setFailseMSG2Id(Integer failseMSG2Id) {
        this.failseMSG2Id = failseMSG2Id;
    }

    public Integer getFailseMSG3Id() {
        return failseMSG3Id;
    }

    public void setFailseMSG3Id(Integer failseMSG3Id) {
        this.failseMSG3Id = failseMSG3Id;
    }
}
