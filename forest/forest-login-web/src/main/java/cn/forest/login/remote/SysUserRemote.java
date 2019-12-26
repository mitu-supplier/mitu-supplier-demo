package cn.forest.login.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.forest.login.fallback.SysUserBack;

@FeignClient(name = "forest-system-server", fallback = SysUserBack.class)
public interface SysUserRemote {

  @RequestMapping("/sys_user/update")
  public int update(Map<String, ?> map);

}
