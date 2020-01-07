package cn.forest.system.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.system.fallback.SysUserBack;

@FeignClient(name = "forest-system-server", fallback = SysUserBack.class)
public interface SysUserRemote {

  @RequestMapping("/sys_user/list")
  public Object getList(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,
      @RequestParam(value = "typeId") Long typeId,@RequestParam(value = "name") String name);

  @RequestMapping("/sys_user/delete")
  public int delete(@RequestParam(value = "id") Long id);

  @RequestMapping("/sys_user/add")
  public int add(Map<String, Object> map);

  @RequestMapping("/sys_user/update")
  public int update(Map<String, ?> map);

  @RequestMapping("/sys_user/getById")
  public Object getById(@RequestParam(value = "id") Long id);

  @RequestMapping("/sys_user/saveRole")
  public int saveRole(Map<String, ?> map);

}
