package cn.forest.mall.remote;

import cn.forest.mall.fallback.ProductsBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = ProductsBack.class)
public interface ProductsRemote {

    @RequestMapping("/products/list")
    Object list(Map<String, ?> map);

    @RequestMapping("/products/save")
    Object save(Map<String, ?> map);

    @RequestMapping("/products/update")
    int update(Map<String, ?> map);

    @RequestMapping("/products/delete")
    int delete(@RequestParam("id") Long id);

    @RequestMapping("/products/getById")
    Object getById(@RequestParam("id") Long id);

    @RequestMapping("/products/batchAudit")
    int batchAudit(@RequestParam("ids")String ids, @RequestParam("auditStatus")Integer auditStatus);

    @RequestMapping("/products/selectByCode")
    public Object selectByCode(@RequestParam("code") String code);

    @RequestMapping("/products/batchDelete")
    int batchDelete(@RequestParam("ids") String ids);

    @RequestMapping("/products/selectList")
    Object selectList(@RequestParam(value = "auditStatus", required = false) Integer auditStatus,
                               @RequestParam(value = "supplierId", required = false) Long supplierId);

    /**
     * 校验商品code是否重复
     *
     * @param id
     * @param code
     * @param supplierId
     * @return
     */
    @RequestMapping("/products/vaProductCode")
    int vaProductCode(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "code", required = false) String code,
                             @RequestParam(value = "supplierId", required = false) Long supplierId);

    /**
     * 修改商品库存数量
     * @param id
     * @param inventoryNum
     * @return
     */
    @RequestMapping("/products/updateInventory")
    int updateInventory(@RequestParam("id") Long id, @RequestParam("inventoryNum") Integer inventoryNum);

    @RequestMapping("/products/batchUpdate")
    int batchUpdate(List<Map<String, Object>> list);
}