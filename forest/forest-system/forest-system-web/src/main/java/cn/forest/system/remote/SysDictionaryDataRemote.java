package cn.forest.system.remote;

import cn.forest.system.fallback.SysDictionaryDataBack;
import cn.forest.system.fallback.SysDictionaryTypeBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-system-server", fallback = SysDictionaryDataBack.class)
public interface SysDictionaryDataRemote {

    @RequestMapping("/sysDictionaryData/list")
    Object list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("type") Long type);

    @RequestMapping("/sysDictionaryData/getById")
    Object getById(@RequestParam("id") Long id);

    @RequestMapping("/sysDictionaryData/save")
    int save(Map<String, ?> map);

    @RequestMapping("/sysDictionaryData/update")
    int update(Map<String, ?> map);

    @RequestMapping("/sysDictionaryData/delete")
    int delete(@RequestParam("id") Long id);
}
