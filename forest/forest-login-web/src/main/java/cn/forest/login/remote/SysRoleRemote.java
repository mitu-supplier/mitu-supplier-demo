package cn.forest.login.remote;

import cn.forest.login.fallback.SysRoleBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-system-server", fallback = SysRoleBack.class)
public interface SysRoleRemote {

  @RequestMapping("/sys_role/getRoleByUserId")
  public Object getRoleByUserId(@RequestParam(value = "userId") Long userId);
  
}
