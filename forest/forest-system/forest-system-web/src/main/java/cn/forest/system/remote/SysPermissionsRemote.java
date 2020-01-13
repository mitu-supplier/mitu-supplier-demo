package cn.forest.system.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.system.fallback.SysPermissionsBack;

@FeignClient(name = "forest-system-server", fallback = SysPermissionsBack.class)
public interface SysPermissionsRemote {

  @RequestMapping("/sys_permissions/list")
  public Object getSysPermissionsList();

  @RequestMapping("/sys_permissions/listfirstLevel")
  public Object getlistfirstLevel(@RequestParam(value = "page") Long page,@RequestParam(value = "pageSize") Long pageSizem, @RequestParam(value = "name", required = false) String name);

  @RequestMapping("/sys_permissions/listChildren")
  public Object getlistChildren(@RequestParam(value = "parentId") Long parentId);
  
  @RequestMapping("/sys_permissions/add")
  public int add(Map<String, ?> map);
  
  @RequestMapping("/sys_permissions/getById")
  public Object getById(@RequestParam(value = "id") Long id);
  
  @RequestMapping("/sys_permissions/delete")
  public int delete(@RequestParam(value = "id") Long id);
  
  @RequestMapping("/sys_permissions/update")
  public int update(Map<String, ?> map);
  
  @RequestMapping("/sys_permissions/getPermissByRoleId")
  public Object getPermissByRoleId(@RequestParam(value = "roleId") Long roleId);
  
  

}
