package cn.forest.mall.remote;

import cn.forest.mall.fallback.SysLoginLogsBack;
import cn.forest.mall.fallback.SysRoleBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "forest-system-server", fallback = SysLoginLogsBack.class)
public interface SysLoginLogsRemote {

    @RequestMapping("/sys_login_logs/count7Days")
    Object count7Days();

    /**
     * 查询用户上次登录信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/sys_login_logs/selectUserLastLoginInfo")
    Object selectUserLastLoginInfo(@RequestParam("userId") Long userId);
}
