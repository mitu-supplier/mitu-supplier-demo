package cn.forest.mall.remote;

import cn.forest.mall.fallback.SysDictionaryTypeBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "forest-system-server", fallback = SysDictionaryTypeBack.class)
public interface SysDictionaryTypeRemote {

    @RequestMapping("/sysDictionaryType/getAll")
    Object getAll();

    /**
     * 根据前缀查询
     *
     * @param prefix
     * @return
     */
    @RequestMapping("/sysDictionaryType/selectByPrefix")
    Object selectByPrefix(@RequestParam("prefix") String prefix);

    @RequestMapping("/sysDictionaryType/getById")
    Object getById(@RequestParam("id") Long id);
}
