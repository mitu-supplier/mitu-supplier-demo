package cn.forest.mall.remote;

import cn.forest.mall.fallback.SysDictionaryDataBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "forest-system-server", fallback = SysDictionaryDataBack.class)
public interface SysDictionaryDataRemote {

    @RequestMapping("/sysDictionaryData/selectByDateTypeCode")
    Object selectByDateTypeCode(@RequestParam("code") String code);

    @RequestMapping("/sysDictionaryData/getById")
    Object getById(@RequestParam("id") Long id);
}
