package cn.forest.mall.remote;

import cn.forest.mall.fallback.ProductPicBack;
import cn.forest.mall.fallback.ProductsBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = ProductPicBack.class)
public interface ProductPicRemote {

    /**
     * 查询商品图片
     *
     * @param productId
     * @param type
     * @return
     */
    @RequestMapping("/productPic/selectByProduct")
    public Object selectByProduct(@RequestParam(value = "productId") Long productId, @RequestParam(value = "type") Integer type);

    /**
     * 删除商品图片
     *
     * @param productId
     * @param type
     * @return
     */
    @RequestMapping("/productPic/deleteByProduct")
    public int deleteByProduct(@RequestParam(value = "productId") Long productId, @RequestParam(value = "type") Integer type);

    @RequestMapping("/productPic/save")
    public void save(@RequestBody List<Map<String, Object>> list);
}
