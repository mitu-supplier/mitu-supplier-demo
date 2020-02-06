package cn.forest.mall.remote;

import cn.forest.mall.fallback.ProductDeliveryStatusBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "forest-mall-server", fallback = ProductDeliveryStatusBack.class)
public interface ProductDeliveryStatusRemote {

    /**
     * 保存商品发货状态
     *
     * @param productId      商品id
     * @param deliveryStatus 发货状态  多个用,隔开
     * @return
     */
    @RequestMapping("/productDeliveryStatus/saveByProductId")
    public int saveByProductId(@RequestParam("productId") Long productId, @RequestParam("deliveryStatus") String deliveryStatus);

    /**
     * 删除商品发货状态
     *
     * @param productId
     * @return
     */
    @RequestMapping("/productDeliveryStatus/deleteByProductId")
    public int deleteByProductId(@RequestParam("productId") Long productId);

}
