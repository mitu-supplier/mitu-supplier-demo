package cn.forest.server;

import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.ProductDeliveryStatus;
import cn.forest.mall.mapper.ProductDeliveryStatusMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/productDeliveryStatus")
@RestController
public class ProductDeliveryStatusAction {

    @Autowired
    private ProductDeliveryStatusMapper productDeliveryStatusMapper;

    /**
     * 保存商品发货状态
     *
     * @param productId      商品id
     * @param deliveryStatus 发货状态  多个用,隔开
     * @return
     */
    @RequestMapping("/saveByProductId")
    public int saveByProductId(@RequestParam("productId") Long productId, @RequestParam("deliveryStatus") String deliveryStatus, @RequestParam("type") Integer type) {
        int result = 0;
        ProductDeliveryStatus productDeliveryStatus = null;
        if (StringUtil.isNotBlank(deliveryStatus) && productId != null) {
            for (String str : deliveryStatus.split(",")) {
                productDeliveryStatus = new ProductDeliveryStatus();
                productDeliveryStatus.setProductId(productId);
                productDeliveryStatus.setDeliveryStatus(Long.parseLong(str));
                productDeliveryStatus.setType(type);
                result = productDeliveryStatusMapper.insert(productDeliveryStatus);
            }
        }
        return result;
    }

    /**
     * 删除商品发货状态
     *
     * @param productId
     * @return
     */
    @RequestMapping("/deleteByProductId")
    public int deleteByProductId(@RequestParam("productId") Long productId, @RequestParam("type") Integer type) {
        QueryWrapper<ProductDeliveryStatus> qw = new QueryWrapper<>();
        qw.eq("product_id", productId);
        qw.eq("type", type);
        return productDeliveryStatusMapper.delete(qw);
    }

    @RequestMapping("/selectByProductId")
    public Object selectByProductId(@RequestParam("productId") Long productId, @RequestParam("type") Integer type){
        QueryWrapper<ProductDeliveryStatus> qw = new QueryWrapper<>();
        qw.eq("product_id", productId);
        qw.eq("type", type);
        return productDeliveryStatusMapper.selectList(qw);
    }
}
