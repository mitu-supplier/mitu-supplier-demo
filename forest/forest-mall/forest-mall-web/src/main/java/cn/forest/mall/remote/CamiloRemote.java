package cn.forest.mall.remote;

import cn.forest.mall.fallback.CamiloBack;
import net.sf.json.JSONArray;
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
    Object save(Map<String, ?> map);

    @RequestMapping("/camilo/update")
    int update(Map<String, ?> map);

    @RequestMapping("/camilo/delete")
    int delete(@RequestParam("id") Long id);

    @RequestMapping("/camilo/batchSave")
    int batchSave(List<Map<String, ?>> mapList);

    @RequestMapping("/camilo/selectByProductId")
    Object selectByProductId(@RequestParam("productId") Long productId);

    @RequestMapping("/camilo/countByProductId")
    int countByProductId(@RequestParam("productId") Long productId);

    @RequestMapping("/camilo/selectProductCamiloList")
    Object selectProductCamiloList(Map<String, Object> map);

    @RequestMapping("/camilo/recordList")
    Object recordList(Map<String, Object> map);

    @RequestMapping("/camilo/batchImport")
    Object batchImport(@RequestBody List<Map<String, Object>> list);
}
