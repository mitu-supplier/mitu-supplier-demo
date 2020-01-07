package cn.forest.mall.remote;

import cn.forest.mall.fallback.SysUserBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-system-server", fallback = SysUserBack.class)
public interface SysUserRemote {

    @RequestMapping("/sys_user/add")
    Object add(Map<String, ?> map);

    @RequestMapping("/sys_user/update")
    int update(Map<String, ?> map);

    @RequestMapping("/sys_user/getById")
    Object getById(@RequestParam(value = "id") Long id);

    @RequestMapping("/sys_user/saveRole")
    int saveRole(Map<String, ?> map);

    @RequestMapping("/sys_user/selectByTypeId")
    Object selectByTypeId(@RequestParam("typeId") Long typeId);
}
