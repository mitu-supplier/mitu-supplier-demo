package cn.forest.mall.remote;

import cn.forest.mall.fallback.CamiloBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = CamiloBack.class)
public interface CamiloRemote {

    @RequestMapping("/camilo/save")
    public Object save(Map<String, ?> map);

    @RequestMapping("/camilo/update")
    public int update(Map<String, ?> map);

    @RequestMapping("/camilo/delete")
    public int delete(@RequestParam("id") Long id);

    @RequestMapping("/camilo/batchSave")
    public int batchSave(List<Map<String, ?>> mapList);

    @RequestMapping("/camilo/selectByProductId")
    public Object selectByProductId(@RequestParam("productId") Long productId);

    @RequestMapping("/camilo/countByProductId")
    public int countByProductId(@RequestParam("productId") Long productId);
}
