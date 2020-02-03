package cn.forest.mall.remote;

import cn.forest.mall.fallback.CompanyBack;
import cn.forest.mall.fallback.ProductsBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = CompanyBack.class)
public interface CompanyRemote {

    @RequestMapping("/company/list")
    Object list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("code") String code, @RequestParam("name") String name);

    @RequestMapping("/company/getById")
    Object getById(@RequestParam("id") Long id);

    @RequestMapping("/company/save")
    int save(Map<String, ?> Object);

    @RequestMapping("/company/update")
    int update(Map<String, ?> Object);

    @RequestMapping("/company/delete")
    int delete(@RequestParam("id") Long id);

    @RequestMapping("/company/getAll")
    Object getAll();

    @RequestMapping("/company/batchDelete")
    int batchDelete(@RequestParam("ids") String ids);

    @RequestMapping("/company/vaName")
    int vaName(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "name") String name);

}
