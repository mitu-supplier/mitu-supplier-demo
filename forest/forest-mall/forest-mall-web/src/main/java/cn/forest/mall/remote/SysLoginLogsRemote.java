package cn.forest.mall.remote;

import cn.forest.mall.fallback.SysLoginLogsBack;
import cn.forest.mall.fallback.SysRoleBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "forest-system-server", fallback = SysLoginLogsBack.class)
public interface SysLoginLogsRemote {

    @RequestMapping("/sys_login_logs/count7Days")
    Object count7Days();
}
