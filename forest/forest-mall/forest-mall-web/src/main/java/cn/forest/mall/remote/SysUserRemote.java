package cn.forest.mall.remote;

import cn.forest.mall.fallback.SysUserBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-system-server", fallback = SysUserBack.class)
public interface SysUserRemote {

    @RequestMapping("/sys_user/add")
    public int add(Map<String, ?> map);

    @RequestMapping("/sys_user/getById")
    public Object getById(@RequestParam(value = "id") Long id);

    @RequestMapping("/sys_user/saveRole")
    public int saveRole(Map<String, ?> map);
}
