package cn.forest.product.dto;

public class MemberShipInfoParam {

    /**
     * 参数名
     */
    private String name;

    /**
     * 参数值
     */
    private String value;

    /**
     * 页面显示的标签名称
     */
    private String label;

    /**
     * 页面用户输入类型
     */
    private String type;

    private String parameter;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
