package cn.forest.mall.remote;

import cn.forest.mall.fallback.ProductsBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
