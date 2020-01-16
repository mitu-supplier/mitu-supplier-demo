package cn.forest.mall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 供应商信息修改记录表
 */
@TableName("suppliers_update")
public class SuppliersUpdate extends Suppliers {

    /**
     * 修改前id
     */
    @TableField(value = "supplier_id")
    private Long supplierId;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
