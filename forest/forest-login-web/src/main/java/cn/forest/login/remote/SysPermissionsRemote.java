package cn.forest.login.remote;

import cn.forest.login.fallback.SysPermissionsBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "forest-system-server", fallback = SysPermissionsBack.class)
public interface SysPermissionsRemote {

  @RequestMapping("/sys_permissions/getPermissByRoleId")
  public Object getPermissByRoleId(@RequestParam(value = "roleId") Long roleId);

  @RequestMapping("/sys_permissions/getPermissionByUserId")
  public Object getPermissionByUserId(@RequestParam(value = "userId") Long userId);
  
  @RequestMapping("/sys_permissions/getRoleButton")
  public Object getRoleButton(@RequestParam(value = "userId") Long userId,@RequestParam(value = "parentId") Long parentId);

  @RequestMapping("/sys_permissions/getpermissionByCode")
  public Object getpermissionByCode(@RequestParam(value = "code") String code);
}
