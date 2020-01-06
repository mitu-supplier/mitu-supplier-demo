package cn.forest.mall.remote;

import cn.forest.mall.fallback.SysDictionaryTypeBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "forest-system-server", fallback = SysDictionaryTypeBack.class)
public interface SysDictionaryTypeRemote {

    @RequestMapping("/sysDictionaryType/getAll")
    Object getAll();
}
