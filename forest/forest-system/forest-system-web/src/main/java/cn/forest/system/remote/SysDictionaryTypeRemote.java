package cn.forest.system.remote;

import cn.forest.system.fallback.SysDictionaryTypeBack;
import cn.forest.system.fallback.SysExceptionLogsVoBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-system-server", fallback = SysDictionaryTypeBack.class)
public interface SysDictionaryTypeRemote {

    @RequestMapping("/sysDictionaryType/list")
    Object list(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,@RequestParam(value = "name")String name);

    @RequestMapping("/sysDictionaryType/getById")
    Object getById(@RequestParam("id") Long id);

    @RequestMapping("/sysDictionaryType/save")
    int save(Map<String, ?> map);

    @RequestMapping("/sysDictionaryType/update")
    int update(Map<String, ?> map);

    @RequestMapping("/sysDictionaryType/delete")
    int delete(@RequestParam("id") Long id);
    
    @RequestMapping("/sysDictionaryType/getAll")
    Object getAll();
}


