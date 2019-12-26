package cn.forest.common.web.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.forest.common.web.fallback.SysLogsBack;

@FeignClient(name = "forest-system-server", fallback = SysLogsBack.class)
public interface SysLogsRemote {

  @RequestMapping("/sys_logs/add")
  public int add(Map<String, ?> map);
  
}
