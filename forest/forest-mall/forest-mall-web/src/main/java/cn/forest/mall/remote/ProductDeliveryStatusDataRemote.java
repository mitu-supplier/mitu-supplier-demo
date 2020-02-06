package cn.forest.mall.remote;

import cn.forest.mall.fallback.ProductDeliveryStatusDataBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = ProductDeliveryStatusDataBack.class)
public interface ProductDeliveryStatusDataRemote {

    /**
     * 获取所有的发货状态
     *
     * @return
     */
    @RequestMapping("/productDeliveryStatusData/list")
    public Object list();

    @RequestMapping("/productDeliveryStatusData/save")
    public int save(Map<String, ?> map);

    @RequestMapping("/productDeliveryStatusData/update")
    public int update(Map<String, ?> map);

    @RequestMapping("/productDeliveryStatusData/delete")
    public int delete(@RequestParam("id") Long id);

    /**
     * 查询商品的发货状态
     *
     * @param productId
     * @return
     */
    @RequestMapping("/productDeliveryStatusData/selectByProductId")
    public Object selectByProductId(@RequestParam("product_id") Long productId);
}
