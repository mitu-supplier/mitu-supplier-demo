package cn.forest.system.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.system.fallback.SysRoleBack;

@FeignClient(name = "forest-system-server", fallback = SysRoleBack.class)
public interface SysRoleRemote {
  @RequestMapping("/sys_role/list")
  public Object getSysRoleList(@RequestParam(value = "page") Long page,
                               @RequestParam(value = "pageSize") Long pageSize,
                               @RequestParam(value = "roleName", required = false) String roleName,
                               @RequestParam(value = "roleCode", required = false) String roleCode);
  
  @RequestMapping("/sys_role/add")
  public int add(Map<String, ?> map);
  
  @RequestMapping("/sys_role/getById")
  public Object getById(@RequestParam(value = "id") Long id);
  
  @RequestMapping("/sys_role/delete")
  public int delete(@RequestParam(value = "id") Long id);
  
  @RequestMapping("/sys_role/update")
  public int update(Map<String, ?> map);
  
  @RequestMapping("/sys_role/saveRolePermiss")
  public int saveRolePermiss(Map<String, ?> map);
  
  @RequestMapping("/sys_role/getRoleByUserId")
  public Object getRoleByUserId(@RequestParam(value = "userId") Long userId);

  @RequestMapping("/sys_role/vaNameOrCode")
  int vaNameOrCode(@RequestParam(value = "id") Long id, @RequestParam("roleName") String roleName, @RequestParam("roleCode") String roleCode);
}
