package cn.forest.system.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.system.fallback.SysLoginLogsBack;

@FeignClient(name = "forest-system-server", fallback = SysLoginLogsBack.class)
public interface SysLoginLogsRemote {

  @RequestMapping("/sys_login_logs/list")
  public Object getList(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize
      ,@RequestParam(value = "name") String name);

}
