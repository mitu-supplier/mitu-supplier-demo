package cn.forest.mall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "product_pic")
public class ProductPic implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableField(value = "id")
    private Long id;

    @TableField(value = "product_id")
    private Long productId;

    /**
     * 图片地址
     */
    @TableField(value = "path")
    private String path;

    /**
     * 1  商品新增图片    2商品修改图片
     */
    @TableField(value = "type")
    private Integer type;


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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
