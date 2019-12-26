package cn.forest.login.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.forest.login.fallback.SysLoginLogsBack;

@FeignClient(name = "forest-system-server", fallback = SysLoginLogsBack.class)
public interface SysLoginLogsRemote {

  @RequestMapping("/sys_login_logs/add")
  public int add(Map<String, ?> map);

}
