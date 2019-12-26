package cn.forest.common.web.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.forest.common.web.fallback.SysExceptionLogsBack;

@FeignClient(name = "forest-system-server", fallback = SysExceptionLogsBack.class)
public interface SysExceptionLogsRemote {
  
  @RequestMapping("/sys_exception_logs/add")
  public int add(Map<String, ?> map);
}
