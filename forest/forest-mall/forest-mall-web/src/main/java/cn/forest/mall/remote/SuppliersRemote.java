package cn.forest.mall.remote;

import cn.forest.mall.fallback.SuppliersBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = SuppliersBack.class)
public interface SuppliersRemote {

    @RequestMapping("/suppliers/list")
    Object list(Map<String, Object> map);

    @RequestMapping("/suppliers/save")
    Object save(Map<String, ?> map);

    @RequestMapping("/suppliers/update")
    int update(Map<String, ?> map);

    @RequestMapping("/suppliers/delete")
    int delete(@RequestParam("id") Long id);

    @RequestMapping("/suppliers/getById")
    Object getById(@RequestParam("id") Long id);

    @RequestMapping("/suppliers/getAll")
    Object getAll();

    @RequestMapping("/suppliers/batchUpdate")
    int batchUpdate(List<Map<String, Object>> list);

    @RequestMapping("/suppliers/selectList")
    Object selectList(@RequestParam(value = "status", required = false) Integer status,
                      @RequestParam(value = "id", required = false) Long id);

    @RequestMapping("/suppliers/vaNameOrShotName")
    int vaNameOrShotName(@RequestParam("cValue") String cValue, @RequestParam("column") String column, @RequestParam("id") Long id);
}
