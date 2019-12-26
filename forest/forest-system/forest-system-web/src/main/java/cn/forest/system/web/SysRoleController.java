package cn.forest.system.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.system.service.SysPermissionsService;
import cn.forest.system.service.SysRoleService;

@RestController
@RequestMapping("sysRole")
@SysLogs(desc  = "角色管理")
public class SysRoleController {

  @Autowired
  private SysRoleService sysRoleService;
  
  @Autowired
  private SysPermissionsService sysPermissionsService;

  @RequestMapping("/list")
  @SysLogs(desc = "角色列表")
  public Map<String, Object> getList(Long page, Long pageSize) {
    return sysRoleService.getList(page, pageSize);
  }
  
  @RequestMapping("/getPermissions")
  @SysLogs(desc = "角色列表")
  public Map<String, Object> getPermissions() {
    return sysRoleService.getPermissions();
  }

  @RequestMapping("/add")
  @SysLogs(desc = "添加角色")
  public Map<String, Object> add(HttpServletRequest request) {
    return sysRoleService.add(RequestMap.requestToMap(request));
  }
  
  
  @RequestMapping("/delete")
  @SysLogs(desc = "删除角色")
  public Map<String, Object> delete(Long id) {
    return sysRoleService.delete(id);
  }
  
  
  @RequestMapping("/getById")
  @SysLogs(desc = "查询角色")
  public Map<String, Object> getById(Long id) {
    return sysRoleService.getById(id);
  }
 
  
  @RequestMapping("/update")
  @SysLogs(desc = "修改角色")
  public Map<String, Object> update(HttpServletRequest request) {
    return sysRoleService.update(RequestMap.requestToMap(request));
  }
  
  @RequestMapping("/getRolePermiss")
  @SysLogs(desc = "获取角色权限")
  public Map<String, Object> getRolePermiss(Long roleId) {
    return sysPermissionsService.getPermissByRoleId(roleId);
  }
  
  @RequestMapping("/saveRolePermiss")
  @SysLogs(desc = "添加角色权限")
  public Map<String, Object> saveRolePermiss(HttpServletRequest request) {
    return sysRoleService.saveRolePermiss(RequestMap.requestToMap(request));
  }
  
}
